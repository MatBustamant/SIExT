package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.Enums.TipoEvento;
import com.grupocapa8.siext.DAO.EventoTrazabilidadDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.sql.Connection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadServices implements ServiceGenerico<EventoTrazabilidadDTO>{
    private final EventoTrazabilidadDAOImpl eventoTrazDAO; //acceso a la BD
    private final BienService bienService;
    private final UbicacionDAOImpl ubiDAO;
    
    private final static String CAMPO_ID_TEXT = "Identificador";
    private final static String UBICACION_POR_DEFECTO = "SIN ASIGNAR";
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 80;

    public EventoTrazabilidadServices() {
        this.eventoTrazDAO = new EventoTrazabilidadDAOImpl();
        this.bienService = new BienService();
        this.ubiDAO = new UbicacionDAOImpl();
    }
    
    @Override
    public EventoTrazabilidadDTO buscar(int idEventoTraz) throws NoSuchElementException {
        return this.buscar(idEventoTraz, true);
    }
    
    public EventoTrazabilidadDTO buscar(int idEventoTraz, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(idEventoTraz, CAMPO_ID_TEXT);
        EventoTrazabilidadDTO evento = eventoTrazDAO.buscar(idEventoTraz);
        if (evento == null || (checkEliminado && evento.isEliminado())){
            throw new NoSuchElementException("No existe el evento de trazabilidad.");
        }
        return evento;
    }
    
    @Override
    public List<EventoTrazabilidadDTO> buscarTodos() {
        return eventoTrazDAO.buscarTodos();
    }
    
    @Override
    public void crear(EventoTrazabilidadDTO dto){
        // Valido el id y verifico la existencia del bien y sino, se corta todo
        int idBien = dto.getBienAsociado();
        try {
            bienService.buscar(idBien, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        
        // Obtengo datos, valido y formateo
        TipoEvento tipo = dto.getTipoEvento();
        String detalle = dto.getDetalle();
        if (detalle != null) detalle = detalle.toUpperCase();
        
        String nombreUbicacion = dto.getUbicacionDestino();
        
        dto.setTipoEvento(tipo);
        dto.setDetalle(detalle);
        switch(tipo) {
            case(TipoEvento.AVERIO):
                bienService.averiar(idBien);
                break;
            case(TipoEvento.REPARACION):
                bienService.reparar(idBien);
                break;
            case(TipoEvento.BAJA):
                bienService.actualizarEstadoEliminado(idBien, true);
                break;
            case(TipoEvento.ENTREGA):
                Validador.validarString(nombreUbicacion, CAMPO_UBICACION_TEXT,CAMPO_UBICACION_MIN,CAMPO_UBICACION_MAX);
                if (ubiDAO.buscar(nombreUbicacion) == null) {
                    ubiDAO.insertar(new UbicacionDTO(0, nombreUbicacion, false));
                }
                bienService.trasladar(idBien, nombreUbicacion);
                dto.setUbicacionDestino(nombreUbicacion);
                break;
            case(TipoEvento.DEVOLUCION):
                if (eventoTrazDAO.buscarEventoUbicacionMasReciente(idBien).getTipoEvento().equals(tipo)) {
                    throw new IllegalArgumentException("No se puede registrar la devolución de un evento que no se encuentra asignado.");
                }
                bienService.trasladar(idBien, UBICACION_POR_DEFECTO);
                dto.setUbicacionDestino(UBICACION_POR_DEFECTO);
                break;
            default:
                break;
        }
        eventoTrazDAO.insertar(dto);
    }
    
    void crearEntrega(EventoTrazabilidadDTO dto, Connection con) {
        int idBien = dto.getBienAsociado();
        try {
            bienService.buscar(idBien, false, con);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        String nombreUbicacion = dto.getUbicacionDestino();
        bienService.trasladar(idBien, nombreUbicacion, con);
        dto.setUbicacionDestino(nombreUbicacion);
        eventoTrazDAO.insertar(dto, con);
    }
    
    @Override
    public void modificar(EventoTrazabilidadDTO dto, int id) throws NoSuchElementException {
        EventoTrazabilidadDTO eventoOriginal = this.buscar(id);
        int idBienOriginal = eventoOriginal.getBienAsociado();
        
        int idBienNuevo = dto.getBienAsociado();
        TipoEvento tipoNuevo = dto.getTipoEvento();
        String nombreUbicacion = dto.getUbicacionDestino();
        
        // Valido que no esté intentando asignar un evento futuro a la baja de un bien.
        if(idBienOriginal != idBienNuevo) {
            EventoTrazabilidadDTO eventoBajaDelNuevo = eventoTrazDAO.buscarBajaMasReciente(idBienNuevo);
            if (eventoBajaDelNuevo != null) {
                // El bien nuevo sí tiene un evento de baja
                // Entonces tenemos que ver si este evento es posterior o anterior a esa baja
                if (eventoOriginal.getFechaEvento().isAfter(eventoBajaDelNuevo.getFechaEvento())) {
                    throw new IllegalArgumentException("No se puede asignar un evento posterior a una baja preexistente.");
                }
            }
        }
        
        // Valido que solo pueda cambiar el último evento a una baja
        if (tipoNuevo == TipoEvento.BAJA) {
            EventoTrazabilidadDTO ultimoEvento = eventoTrazDAO.buscarMasRecienteAbsoluto(idBienNuevo);
            // Si el bien vinculado al dto recibido ya tiene eventos...
            if (ultimoEvento != null) {
                // Si el último evento de ese bien tiene un id diferente al mío,
                // debo chequear si estoy intentando insertarme antes o después de la ocurrencia de ese evento
                if(ultimoEvento.getID_Evento() != id && eventoOriginal.getFechaEvento().isBefore(ultimoEvento.getFechaEvento())) {
                    // si estaba antes, tiro excepción
                    throw new IllegalArgumentException("Solo el último evento de un bien puede ser cambiado a BAJA");
                } //sino, todo bien. Soy posterior al último evento, puedo insertarme como BAJA.
            } // si tengo el mismo id, estoy intentando cambiar el tipo del último evento a una baja, todo bien
        }
        
        if (tipoNuevo == TipoEvento.ENTREGA) {
            Validador.validarString(nombreUbicacion, CAMPO_UBICACION_TEXT,CAMPO_UBICACION_MIN,CAMPO_UBICACION_MAX);
            if (ubiDAO.buscar(nombreUbicacion) == null) {
                ubiDAO.insertar(new UbicacionDTO(0, nombreUbicacion, false));
            }
            dto.setUbicacionDestino(nombreUbicacion);
        } else if (tipoNuevo == TipoEvento.DEVOLUCION) {
            dto.setUbicacionDestino(UBICACION_POR_DEFECTO);
        }
        
        // Validamos que el bien nuevo exista
        try {
            bienService.buscar(idBienNuevo, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBienNuevo)
            );
        }
        dto.setID_Evento(id); // Le ponemos al dto recibido el id que llegó por parámetro en el endpoint
        eventoTrazDAO.actualizar(dto);
        
        // Comparo si el id cambió
        if (idBienOriginal != idBienNuevo) {
            // Si cambió, tenemos que recalcular el estado del bien anterior
            this.recalcularEstadoDelBien(idBienOriginal);
            this.recalcularExistenciaDelBien(idBienOriginal);
            this.recalcularUbicacionDelBien(idBienOriginal);
        }
        // Luego recalculamos el estado del bien actual (ya sea que sea el mismo de antes o uno nuevo)
        this.recalcularEstadoDelBien(idBienNuevo);
        this.recalcularExistenciaDelBien(idBienNuevo);
        this.recalcularUbicacionDelBien(idBienNuevo);
    }
    
    @Override
    public void eliminar(int idEventoTraz) throws NoSuchElementException {
        EventoTrazabilidadDTO evento = this.buscar(idEventoTraz);
        
        // Verifico existencia (da igual si está eliminado) del bien asociado y sino, se corta todo
        int idBien = evento.getBienAsociado();
        try {
            bienService.buscar(idBien, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        
        // Eliminamos lo que se nos pide
        eventoTrazDAO.eliminar(idEventoTraz);
        
        this.recalcularEstadoDelBien(idBien);
        this.recalcularExistenciaDelBien(idBien);
        this.recalcularUbicacionDelBien(idBien);
    }
    
    private void recalcularEstadoDelBien(int idBien) {
        EventoTrazabilidadDTO eventoQueDefineEstado = eventoTrazDAO.buscarEventoEstadoMasReciente(idBien);
        
        if (eventoQueDefineEstado != null) {
            switch(eventoQueDefineEstado.getTipoEvento()) {
                case(TipoEvento.AVERIO):
                    bienService.averiar(idBien);
                    break;
                case(TipoEvento.REPARACION):
                    bienService.reparar(idBien);
                    break;
                default:
                    break;
            }
        } else {
            // Si el bien no tiene eventos relevantes
            // Asumimos que su estado es el inicial/base (En condiciones)
            bienService.reparar(idBien);
        }
    }
    
    private void recalcularExistenciaDelBien(int idBien) {
        EventoTrazabilidadDTO ultimoBaja = eventoTrazDAO.buscarBajaMasReciente(idBien);
        
        BienDTO bien = bienService.buscar(idBien, false);
        
        if (ultimoBaja != null) {
            if (!bien.isEliminado()) {
                bienService.actualizarEstadoEliminado(idBien, true);
            }
        } else {
            if (bien.isEliminado())
            bienService.actualizarEstadoEliminado(idBien, false);
        }
    }
    
    private void recalcularUbicacionDelBien(int idBien) {
        EventoTrazabilidadDTO ultimoMovimiento = eventoTrazDAO.buscarEventoUbicacionMasReciente(idBien);
        
        String nombreUbicacionFinal;
        if (ultimoMovimiento != null && ultimoMovimiento.getUbicacionDestino() != null) {
            nombreUbicacionFinal = ultimoMovimiento.getUbicacionDestino();
        } else {
            nombreUbicacionFinal = UBICACION_POR_DEFECTO;
        }
        
        if (ubiDAO.buscar(nombreUbicacionFinal) == null) {
            ubiDAO.insertar(new UbicacionDTO(0, nombreUbicacionFinal, false));
        }
        
        bienService.trasladar(idBien, nombreUbicacionFinal);
    }
    
}

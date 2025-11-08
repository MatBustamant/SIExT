package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.Enums.TipoEvento;
import com.grupocapa8.siext.DAO.EventoTrazabilidadDAOImpl;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadServices implements ServiceGenerico<EventoTrazabilidadDTO>{
    private final EventoTrazabilidadDAOImpl eventoTrazDAO; //acceso a la BD
    private final BienService bienService;
    
    private final static String CAMPO_ID_TEXT = "Identificador";

    public EventoTrazabilidadServices() {
        this.eventoTrazDAO = new EventoTrazabilidadDAOImpl();
        this.bienService = new BienService();
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
        // Verifico existencia del bien asociado y sino, se corta todo
        int idBien = dto.getBienAsociado();
        bienService.buscar(idBien);
        
        Validador.validarId(idBien, CAMPO_ID_TEXT);
        TipoEvento tipo = dto.getTipoEvento();
//        Validador.validarString(tipo,CAMPO_TIPO_TEXT,CAMPO_TIPO_MIN,CAMPO_TIPO_MAX);
        
        dto.setTipoEvento(tipo);
        switch(tipo) {
            case(TipoEvento.AVERIO):
                bienService.averiar(idBien);
                break;
            case(TipoEvento.REPARACION):
                bienService.reparar(idBien);
                break;
            default:
                break;
        }
        eventoTrazDAO.insertar(dto);
    }
    
    @Override
    public void modificar(EventoTrazabilidadDTO dto, int id) throws NoSuchElementException {
        EventoTrazabilidadDTO eventoOriginal = this.buscar(id);
        
        // Verifico existencia (da igual si está eliminado) del bien asociado y sino, se corta todo
        int idBien = dto.getBienAsociado();
        try {
            bienService.buscar(idBien, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        
        Validador.validarId(idBien, CAMPO_ID_TEXT);
        dto.setID_Evento(id); // Le ponemos al dto recibido el id que llegó por parámetro en el endpoint
        TipoEvento tipo = dto.getTipoEvento();
        
        eventoTrazDAO.actualizar(dto);
        
        // Comparo si el id cambió
        int idBienOriginal = eventoOriginal.getBienAsociado();
        if (idBienOriginal != idBien) {
            // Si cambió, tenemos que recalcular el estado del bien anterior
            this.recalcularEstadoDelBien(idBienOriginal);
        }
        // Luego recalculamos el estado del bien actual (ya sea que sea el mismo de antes o uno nuevo)
        this.recalcularEstadoDelBien(idBien);
    }
    
    @Override
    public void eliminar(int idEventoTraz) throws NoSuchElementException {
        EventoTrazabilidadDTO evento = this.buscar(idEventoTraz);
        
        // Verifico existencia (da igual si está eliminado) del bien asociado y sino, se corta todo
        int idBien = evento.getBienAsociado();
        try {
            buscar(idBien, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        
        // Eliminamos lo que se nos pide
        eventoTrazDAO.eliminar(idEventoTraz);
        
        this.recalcularEstadoDelBien(idBien);
    }
    
    private void recalcularEstadoDelBien(int idBien) {
        EventoTrazabilidadDTO eventoQueDefineEstado = eventoTrazDAO.buscarMasReciente(idBien);
        
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
    
}

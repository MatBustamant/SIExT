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
        this.buscar(id);
        
        // Verifico existencia (da igual si está eliminado) del bien asociado y sino, se corta todo
        int idBien = dto.getBienAsociado();
        try {
            buscar(idBien, false);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(
                    String.format("El bien con id %d no existe.", idBien)
            );
        }
        
        Validador.validarId(idBien, CAMPO_ID_TEXT);
        TipoEvento tipo = dto.getTipoEvento();
//        Validador.validarString(tipo,CAMPO_TIPO_TEXT,CAMPO_TIPO_MIN,CAMPO_TIPO_MAX);
        
        dto.setTipoEvento(tipo);
        dto.setID_Evento(id);
        
        // Buscamos el evento más reciente
        EventoTrazabilidadDTO eventoUltimo = eventoTrazDAO.buscarMasReciente(idBien);
        // Si el evento más reciente para ese bien soy yo, cambiamos el estado del bien acordemente
        if(eventoUltimo != null && eventoUltimo.getID_Evento() == id) {
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
        }
        eventoTrazDAO.actualizar(dto);
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
        TipoEvento tipoDelEventoActual = evento.getTipoEvento();
        
        // Eliminamos lo que se nos pide
        eventoTrazDAO.eliminar(idEventoTraz);
        
        // Buscamos el evento anterior (El más reciente luego de la eliminación)
        EventoTrazabilidadDTO eventoAnterior = eventoTrazDAO.buscarMasReciente(idBien);
        if (eventoAnterior != null) {
            // Si encontramos alguno, tomamos su tipo
            TipoEvento tipoDelEventoAnterior = eventoAnterior.getTipoEvento();
            // Si es distinto al que acabamos de eliminar, tenemos que modificar el estado del bien
            if(tipoDelEventoAnterior != null && !tipoDelEventoAnterior.equals(tipoDelEventoActual)) {
                switch(tipoDelEventoAnterior) {
                    case(TipoEvento.AVERIO):
                        bienService.averiar(idBien);
                        break;
                    case(TipoEvento.REPARACION):
                    case(TipoEvento.ENTREGA):
                        bienService.reparar(idBien);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
}

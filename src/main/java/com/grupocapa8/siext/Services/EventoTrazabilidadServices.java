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
        int idBien = dto.getBienAsociado();
        if (bienService.buscar(idBien) == null){
            throw new NoSuchElementException("No existe el bien.");
        }
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
//        if(tipo.equals("AVERIO")) { // Cambiar a switch cuando trabajemos con enums mejor
//            bienService.averiar(idBien);
//        } else if (tipo.equals("REPARACION")) {
//            bienService.reparar(idBien);
//        }
        
        eventoTrazDAO.insertar(dto);
    }
    
    @Override
    public void modificar(EventoTrazabilidadDTO dto, int id) throws NoSuchElementException {
        this.buscar(id);
        
        int idBien = dto.getBienAsociado();
        if (bienService.buscar(idBien) == null){
            throw new NoSuchElementException("No existe el bien.");
        }
        Validador.validarId(idBien, CAMPO_ID_TEXT);
        TipoEvento tipo = dto.getTipoEvento();
//        Validador.validarString(tipo,CAMPO_TIPO_TEXT,CAMPO_TIPO_MIN,CAMPO_TIPO_MAX);
        
        dto.setTipoEvento(tipo);
        dto.setID_Evento(id);
        
        if(eventoTrazDAO.buscarMasReciente(idBien).getID_Evento() == id) {
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
//            if(tipo.equals("AVERIO")) { // Cambiar a switch cuando trabajemos con enums mejor
//                bienService.averiar(idBien);
//            } else if (tipo.equals("REPARACION")) {
//                bienService.reparar(idBien);
//            }
        }
        
        eventoTrazDAO.actualizar(dto);
    }
    
    @Override
    public void eliminar(int idEventoTraz) throws NoSuchElementException {
        EventoTrazabilidadDTO evento = this.buscar(idEventoTraz);
        
        int idBien = evento.getBienAsociado();
        TipoEvento tipoDelEventoActual = evento.getTipoEvento();
        
        // Eliminamos lo que se nos pide
        eventoTrazDAO.eliminar(idEventoTraz);
        
        // Buscamos el evento anterior (El más reciente luego de la eliminación)
        TipoEvento tipoDelEventoAnterior = eventoTrazDAO.buscarMasReciente(idBien).getTipoEvento();
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
//            if(tipoDelEventoAnterior.equals("AVERIO")) {
//                bienService.averiar(idBien);
//            } else if (tipoDelEventoAnterior.equals("REPARACION") || tipoDelEventoAnterior.equals("ENTREGA")) {
//                bienService.reparar(idBien);
//            }
        }
    }
    
}

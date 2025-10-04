package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.EventoTrazabilidadDAOImpl;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadServices implements ServiceGenerico<EventoTrazabilidadDTO>{
    private final EventoTrazabilidadDAOImpl eventoTrazDAO; //acceso a la BD
    private final BienService bienService;

    public EventoTrazabilidadServices() {
        this.eventoTrazDAO = new EventoTrazabilidadDAOImpl();
        this.bienService = new BienService();
    }
    
    @Override
    public EventoTrazabilidadDTO buscar(int idEventoTraz) throws NoSuchElementException {
        validarID(idEventoTraz);
        EventoTrazabilidadDTO evento = eventoTrazDAO.buscar(idEventoTraz);
        if (evento == null){
            throw new NoSuchElementException("No existe el Evento de trazabilidad");
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
        validarID(idBien);
        String tipo = dto.getTipoEvento().toUpperCase();
        validarString(tipo,1);
        
        dto.setTipoEvento(tipo);
        if(tipo.equals("AVERIO")) {
            bienService.averiar(idBien);
        } else if (tipo.equals("REPARACION")) {
            bienService.reparar(idBien);
        }
        
        eventoTrazDAO.insertar(dto);
    }
    
    @Override
    public void modificar(EventoTrazabilidadDTO dto, int id) throws NoSuchElementException {
        validarID(id);
        if (eventoTrazDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe el Evento de trazabilidad");
        }
        int idBien = dto.getBienAsociado();
        validarID(idBien);
        String tipo = dto.getTipoEvento().toUpperCase();
        validarString(tipo,1);
        
        dto.setTipoEvento(tipo);
        dto.setID_Evento(id);
        
        if(eventoTrazDAO.buscarMasReciente(idBien).getID_Evento() == id) {
            if(tipo.equals("AVERIO")) {
                bienService.averiar(idBien);
            } else if (tipo.equals("REPARACION")) {
                bienService.reparar(idBien);
            }
        }
        
        eventoTrazDAO.actualizar(dto);
    }
    
    @Override
    public void eliminar(int idEventoTraz) throws NoSuchElementException {
        validarID(idEventoTraz);
        EventoTrazabilidadDTO evento = eventoTrazDAO.buscar(idEventoTraz);
        if (evento == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe el evento de trazabilidad");
        }
        
        int idBien = evento.getBienAsociado();
        String tipoDelEventoActual = evento.getTipoEvento();
        
        // Eliminamos lo que se nos pide
        eventoTrazDAO.eliminar(idEventoTraz);
        
        // Buscamos el evento anterior (El más reciente luego de la eliminación)
        String tipoDelEventoAnterior = eventoTrazDAO.buscarMasReciente(idBien).getTipoEvento();
        // Si es distinto al que acabamos de eliminar, tenemos que modificar el estado del bien
        if(tipoDelEventoAnterior != null && !tipoDelEventoAnterior.equals(tipoDelEventoActual)) {
            if(tipoDelEventoAnterior.equals("AVERIO")) {
                bienService.averiar(idBien);
            } else if (tipoDelEventoAnterior.equals("REPARACION") || tipoDelEventoAnterior.equals("ENTREGA")) {
                bienService.reparar(idBien);
            }
        }
    }
    
    private void validarID(Integer num){
        if (num == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("El ID   debe ser un número entero positivo.");
        }
    }
    
    private void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("El tipo de evento debe tener entre 3 y 50 caracteres");
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
    
}

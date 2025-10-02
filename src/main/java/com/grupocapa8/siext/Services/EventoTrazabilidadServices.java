package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.EventoTrazabilidadDAOImpl;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadServices implements ServiceGenerico<EventoTrazabilidadDTO>{
    private final EventoTrazabilidadDAOImpl eventoTrazDAO; //acceso a la BD

    public EventoTrazabilidadServices() {
        this.eventoTrazDAO = new EventoTrazabilidadDAOImpl();
    }
    
//    public void lecturaDTOs(){
//        int idEventoTraz = 0;
//        while(eventoTrazDAO.BuscarEventoTraz(idEventoTraz)){
//            EventoTrazabilidadDTO dto = eventoTrazDAO.obtenerEventoTraz(idEventoTraz);
//            recibirEventoTrazDTO(dto);
//            idEventoTraz = idEventoTraz + 1;    
//        }
//        if(idEventoTraz == 0){
//            System.out.println("No Existen Eventos de trazabilidad en la BD");
//        }
//    }
    
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
    public void crear(EventoTrazabilidadDTO dto){
        validarID(dto.getBienAsociado());
        // validarFecha(dto.getFechaEvento()); ver que se va hacer con estas validaciones
        validarString(dto.getTipoEvento(),1);
        // validarHorario(dto.getHorarioEvento().getHoraFormateada()); ver que se va hacer con estas validaciones
        
        eventoTrazDAO.insertar(dto);
    }
    
    @Override
    public void modificar(EventoTrazabilidadDTO dto) throws NoSuchElementException {
        int idEventoTraz = dto.getID_Evento();
        validarID(idEventoTraz);
        if (eventoTrazDAO.buscar(idEventoTraz) == null){
            throw new NoSuchElementException("No existe el Evento de trazabilidad");
        }
        validarID(dto.getBienAsociado());
        // validarFecha(dto.getFechaEvento()); ver que se va hacer con estas validaciones
        validarString(dto.getTipoEvento(),1);
        // validarHorario(dto.getHorarioEvento().getHoraFormateada()); ver que se va hacer con estas validaciones
        
        eventoTrazDAO.actualizar(dto);
    }
    
    @Override
    public void eliminar(int idEventoTraz) throws NoSuchElementException {
        validarID(idEventoTraz);
        if (eventoTrazDAO.buscar(idEventoTraz) == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe el evento de trazabilidad");
        }
        
        eventoTrazDAO.eliminar(idEventoTraz);
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
/**    public void validarFecha(Fecha fecha) {
        if (fecha == null || fecha.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        LocalDate f = fecha.getFecha();
        LocalDate hoy = LocalDate.now();

        // Si el formato de la fecha está mal, el constructor ya lanzará una excepción
        // por eso no es necesario hacer una validación explícita aquí.

        // Ejemplo: la fecha no puede ser posterior a hoy
        if (f.isAfter(hoy)) {
            throw new IllegalArgumentException("La fecha no puede estar en el futuro.");
        }

        // Ejemplo: la fecha no puede ser anterior a 2024
        if (f.isBefore(LocalDate.of(2000, 1, 1))) {
            throw new IllegalArgumentException("La fecha no puede ser anterior al año 2000.");
        }
    }

    public void validarHorario(String horarioStr) {
        if (horarioStr == null || horarioStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El horario no puede ser nulo ni vacío.");
        }
    }
*/
    
}

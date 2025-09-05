package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.DTO.Fecha;
import java.time.LocalDate;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadServices {
    private EventoTrazDAO eventoTrazDAO; //acceso a la BD
    
    public void crearEventoTraz(EventoTrazabilidadDTO dto){
        validarID(dto.getBienAsociado());
        validarFecha(dto.getFechaEvento());
        validarString(dto.getTipoEvento(),1);
        validarHorario(dto.getHorarioEvento().getHoraFormateada());
        
        eventoTrazDAO.guardar(dto);
    } 
    
    public void modificarEventoTraz(Integer idEventoTraz){
        validarID(idEventoTraz);
        if (!eventoTrazDAO.BuscarEventoTraz(idEventoTraz)){
            throw new IllegalArgumentException("No existe el Evento de trazabilidad");
        }
        EventoTrazabilidadDTO dto = eventoTrazDAO.obtenerEventoTraz(idEventoTraz);
        dto = recibirEventoTrazDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarID(dto.getBienAsociado());
        validarFecha(dto.getFechaEvento());
        validarString(dto.getTipoEvento(),1);
        validarHorario(dto.getHorarioEvento().getHoraFormateada());
        
        eventoTrazDAO.guardar(dto);
    } 
    public void eliminarEventoTraz(Integer idEventoTraz, String rol){
        validarID(idEventoTraz);
        validarString(rol,2);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar eventos de trazabilidad");
        }
        if (!eventoTrazDAO.BuscarEventoTraz(idEventoTraz)){ //hacer una validacion del id
            throw new IllegalArgumentException("No existe el evento de trazabilidad");
        }
        
        EventoTrazabilidadDTO dto = eventoTrazDAO.obtenerEventoTraz(idEventoTraz);
        boolean V = confirmacionEliminarEventoTraz(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            eventoTrazDAO.eliminarEventoTraz(idEventoTraz);
        }   
    } 
    public void validarID(Integer num){
        if (num == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("El ID   debe ser un número entero positivo.");
        }
    }
    public void validarFecha(Fecha fecha) {
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
    public void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("El tipo de evento debe tener entre 3 y 50 caracteres");
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
}

package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.Fecha;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import java.time.LocalDate;

public class SolicitudService {
    private SolicitudDAO solicitudDAO; //acceso a la BD
    
    
    public void lecturaDTOs(){
        int numSolicitud = 0;
        while(solicitudDAO.BuscarSolicitud(numSolicitud)){
            SolicitudDTO dto = solicitudDAO.obtenerSolicitud(numSolicitud);
            recibirSolicitudDTO(dto);
            numSolicitud = numSolicitud + 1;    
        }
        if(numSolicitud == 0){
            System.out.println("No Existe la solicitud en la BD");
        }
        
        
         
    }
    public void BuscarDto(int numSolicitud){
        validarID(numSolicitud);
        if (!solicitudDAO.BuscarSolicitud(numSolicitud)){
            throw new IllegalArgumentException("No existe la solicitud");
        }
        SolicitudDTO dto = solicitudDAO.obtenerSolicitud(numSolicitud);
        recibirSolicitudDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre
    }
    
    public void crearSolicitud(SolicitudDTO dto){
        validarNumero(dto.getNumSolicitud());
        validarLegajo(dto.getLegSolicitante());
        // validarFecha(dto.getFechaInicioSolicitud()); ver que hacer
        validarString(dto.getEstado(),3);
        validarString(dto.getDestinoProductos(),1);
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.guardar(dto);
    } 
    
    public void modificarSolicitud(Integer numSolicitud){
        validarNumero(numSolicitud);
        if (!solicitudDAO.BuscarSolicitud(numSolicitud)){
            throw new IllegalArgumentException("No existe la solicitud");
        }
        SolicitudDTO dto = solicitudDAO.obtenerSolicitud(numSolicitud);
        dto = recibirSolicitudDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarNumero(dto.getNumSolicitud());
        validarLegajo(dto.getLegSolicitante());
        // validarFecha(dto.getFechaInicioSolicitud()); ver que hacer
        validarString(dto.getEstado(),3);
        validarString(dto.getDestinoProductos(),1);
        
        solicitudDAO.guardar(dto);
    } 
    public void eliminarSolicitud(Integer numSolicitud, String rol){
        validarNumero(numSolicitud);
        validarString(rol,2);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar solicitudes");
        }
        if (!solicitudDAO.BuscarBien(numSolicitud)){ //hacer una validacion del id
            throw new IllegalArgumentException("No existe la solicitud");
        }
        
        SolicitudDTO dto = solicitudDAO.obtenerUsuario(numSolicitud);
        boolean V = confirmacionEliminarSolicitud(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            solicitudDAO.eliminarSolicitud(numSolicitud);
        }   
    } 
    public void validarNumero(Integer num){
        if (num == null) {
            throw new IllegalArgumentException("El numero de solicitud no puede ser nulo.");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("El numero de solicitud debe ser un número entero positivo.");
        }
        if(solicitudDAO.obtenerNumSolicitud(num)){
            throw new IllegalArgumentException("El numero de solicitud ya existe, ingrese nuevamente.");
        }
    }
    public void validarLegajo(String legajo){
        String regex = "^[0-9]+/[0-9]{4}$";
        if (!legajo.matches(regex)) {
            throw new IllegalArgumentException("Formato de legajo inválido. Ejemplo válido: 30327/2022");
        }
        String[] partes = legajo.split("/");
        int anio = Integer.parseInt(partes[1]);
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("El año del legajo es inválido: " + anio);
        }
    }
    public void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("El destino debe tener entre 3 y 50 caracteres");
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
                case 3 -> throw new IllegalArgumentException("El Estado no debe estar vacio.");
            } 
        }
    }
/*    public void validarFecha(Fecha fecha){
        if (fecha == null || fecha.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate f = fecha.getFecha();

        // Ejemplo: fecha no puede ser posterior a hoy
        if (f.isAfter(hoy)) {
            throw new IllegalArgumentException("La fecha no puede estar en el futuro.");
        }

        // Ejemplo: fecha no puede ser antes de 2024
        if (f.isBefore(LocalDate.of(2024, 1, 1))) {
            throw new IllegalArgumentException("La fecha no puede ser anterior al año 2024.");
        }
    }
*/
   
}

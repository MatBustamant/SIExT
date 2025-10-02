package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO> {
    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
    }
    
//    public void lecturaDTOs(){
//        int numSolicitud = 0;
//        while(solicitudDAO.BuscarSolicitud(numSolicitud)){
//            SolicitudDTO dto = solicitudDAO.obtenerSolicitud(numSolicitud);
//            recibirSolicitudDTO(dto);
//            numSolicitud = numSolicitud + 1;    
//        }
//        if(numSolicitud == 0){
//            System.out.println("No Existe la solicitud en la BD");
//        }
//    }
    
    @Override
    public SolicitudDTO buscar(int numSolicitud) throws NoSuchElementException {
        validarNumero(numSolicitud);
        SolicitudDTO solicitud = solicitudDAO.buscar(numSolicitud);
        if (solicitud == null){
            throw new NoSuchElementException("No existe la solicitud");
        }
        return solicitud;
    }
    
    @Override
    public void crear(SolicitudDTO dto){
        validarNumero(dto.getNumSolicitud());
        // validarFecha(dto.getFechaInicioSolicitud()); ver que hacer
        validarString(dto.getEstado(),3);
        validarString(dto.getUbicacionBienes(),1);
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(SolicitudDTO dto) throws NoSuchElementException {
        int numSolicitud = dto.getNumSolicitud();
        validarNumero(numSolicitud);
        if (solicitudDAO.buscar(numSolicitud) == null){
            throw new NoSuchElementException("No existe la solicitud");
        }
        validarNumero(dto.getNumSolicitud());
        // validarFecha(dto.getFechaInicioSolicitud()); ver que hacer
        validarString(dto.getEstado(),3);
        validarString(dto.getUbicacionBienes(),1);
        
        solicitudDAO.actualizar(dto);
    }
    
    @Override
    public void eliminar(int numSolicitud) throws NoSuchElementException {
        validarNumero(numSolicitud);
        if (solicitudDAO.buscar(numSolicitud) == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe la solicitud");
        }
        
        solicitudDAO.eliminar(numSolicitud);
    }
    
    private void validarNumero(Integer num){
        if (num == null) {
            throw new IllegalArgumentException("El numero de solicitud no puede ser nulo.");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("El numero de solicitud debe ser un número entero positivo.");
        }
    }
    
    private void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("La ubicacion debe tener entre 3 y 50 caracteres");
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

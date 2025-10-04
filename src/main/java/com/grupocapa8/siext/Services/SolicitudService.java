package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import java.util.List;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO> {
    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD
    private final UbicacionDAOImpl ubiDAO;

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
    }
    
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
    public List<SolicitudDTO> buscarTodos() {
        return solicitudDAO.buscarTodos();
    }
    
    @Override
    public void crear(SolicitudDTO dto){
        String estado = dto.getEstado().toUpperCase();
        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        validarString(ubicacion,1);
        
        dto.setEstado(estado);
        dto.setUbicacionBienes(ubicacion);
        
        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion);
            ubiDAO.insertar(nuevaUbicacion);
        }
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(SolicitudDTO dto, int id) throws NoSuchElementException {
        validarNumero(id);
        if (solicitudDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe la solicitud");
        }
        
        String estado = dto.getEstado().toUpperCase();
        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        validarString(ubicacion,1);
        
        dto.setEstado(estado);
        dto.setUbicacionBienes(ubicacion);
        dto.setNumSolicitud(id);
        
        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion);
            ubiDAO.insertar(nuevaUbicacion);
        }
        // Convertir DTO a entidad y guardarlo en BD
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

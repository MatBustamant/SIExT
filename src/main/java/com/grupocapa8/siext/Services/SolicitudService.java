package com.grupocapa8.siext.Services;

import Enums.EstadoSolicitud;
import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO> {
    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD
    private final UbicacionDAOImpl ubiDAO;
    
    private final static String CAMPO_ID_TEXT = "Número de solicitud";
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 80;

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
    }
    
    @Override
    public SolicitudDTO buscar(int numSolicitud) throws NoSuchElementException {
        Validador.validarId(numSolicitud, CAMPO_ID_TEXT);
        SolicitudDTO solicitud = solicitudDAO.buscar(numSolicitud);
        if (solicitud == null){
            throw new NoSuchElementException("No existe la solicitud.");
        }
        return solicitud;
    }
    
    @Override
    public List<SolicitudDTO> buscarTodos() {
        return solicitudDAO.buscarTodos();
    }
    
    @Override
    public void crear(SolicitudDTO dto){
        EstadoSolicitud estado = dto.getEstado();
//        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion,CAMPO_UBICACION_TEXT,CAMPO_UBICACION_MIN,CAMPO_UBICACION_MAX);
        
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
        Validador.validarId(id, CAMPO_ID_TEXT);
        if (solicitudDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe la solicitud.");
        }
        
        EstadoSolicitud estado = dto.getEstado();
//        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion,CAMPO_UBICACION_TEXT,CAMPO_UBICACION_MIN,CAMPO_UBICACION_MAX);
        
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
        Validador.validarId(numSolicitud, CAMPO_ID_TEXT);
        if (solicitudDAO.buscar(numSolicitud) == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe la solicitud.");
        }
        
        solicitudDAO.eliminar(numSolicitud);
    }
   
}

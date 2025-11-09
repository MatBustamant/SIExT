package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.ResponsableDAOImpl;
import com.grupocapa8.siext.Enums.EstadoSolicitud;
import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO, Integer> {
    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD
    private final UbicacionDAOImpl ubiDAO;
    private final ResponsableDAOImpl responsableDAO;
    
    private final static String CAMPO_ID_TEXT = "Número de solicitud";
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private final static String CAMPO_LEGAJO_TEXT = "Legajo";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 80;

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
        this.responsableDAO = new ResponsableDAOImpl();
    }
    
    @Override
    public SolicitudDTO buscar(Integer numSolicitud) throws NoSuchElementException {
        return this.buscar(numSolicitud, true);
    }
    
    public SolicitudDTO buscar(int numSolicitud, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(numSolicitud, CAMPO_ID_TEXT);
        SolicitudDTO solicitud = solicitudDAO.buscar(numSolicitud);
        if (solicitud == null || (checkEliminado && solicitud.isEliminado())){
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
        int legajo = dto.getLegajo();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
        Validador.validarDestinoSolicitud(ubicacion,CAMPO_UBICACION_TEXT);
        Validador.validarId(legajo, CAMPO_LEGAJO_TEXT);
        
        dto.setEstado(estado);
        dto.setUbicacionBienes(ubicacion);
        
        if (responsableDAO.buscar(legajo) == null ){
            throw new NoSuchElementException("No existe un responsable con el legajo " + legajo);
        }
        
        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion, false);
            ubiDAO.insertar(nuevaUbicacion);
        }
        
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(SolicitudDTO dto, Integer id) throws NoSuchElementException {
        this.buscar(id);
        
        EstadoSolicitud estado = dto.getEstado();
        int legajo = dto.getLegajo();
//        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
        Validador.validarDestinoSolicitud(ubicacion,CAMPO_UBICACION_TEXT);
        Validador.validarId(legajo, CAMPO_LEGAJO_TEXT);
        
        dto.setEstado(estado);
        dto.setUbicacionBienes(ubicacion);
        dto.setNumSolicitud(id);
        
        if (responsableDAO.buscar(legajo) == null ){
            throw new NoSuchElementException("No existe un responsable con el legajo " + legajo);
        }
        
        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion, false);
            ubiDAO.insertar(nuevaUbicacion);
        }
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.actualizar(dto);
    }
    
    @Override
    public void eliminar(Integer numSolicitud) throws NoSuchElementException {
        this.buscar(numSolicitud);
        
        solicitudDAO.eliminar(numSolicitud);
    }
   
}

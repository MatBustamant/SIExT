package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DAO.Bienes_por_SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.ResponsableDAOImpl;
import com.grupocapa8.siext.Enums.EstadoSolicitud;
import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO> {
    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD
    private final UbicacionDAOImpl ubiDAO;
    private final ResponsableDAOImpl responsableDAO;
    private final Bienes_por_SolicitudDAOImpl bienesDAO;
    
    private final static String CAMPO_ID_TEXT = "Número de solicitud";
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private final static String CAMPO_LEGAJO_TEXT = "Legajo";
    private final static String CAMPO_CANTIDAD_TEXT = "Cantidad";
    private final static String CAMPO_ID_CATEGORIA_TEXT = "ID Categoría";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 80;

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
        this.responsableDAO = new ResponsableDAOImpl();
        this.bienesDAO = new Bienes_por_SolicitudDAOImpl();
    }
    
    @Override
    public SolicitudDTO buscar(int numSolicitud) throws NoSuchElementException {
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
        
        if (dto.getBienesPedidos() == null || dto.getBienesPedidos().isEmpty()) {
            throw new IllegalArgumentException("La solicitud debe tener al menos un bien pedido.");
        } //valida los datos de los bienes
        for (Bienes_por_SolicitudDTO bien : dto.getBienesPedidos()) {
            Validador.validarId(bien.getID_Categoria(), CAMPO_ID_CATEGORIA_TEXT);
            Validador.validarId(bien.getCantidad(), CAMPO_CANTIDAD_TEXT); // Asumimos > 0
        }
        
        dto.setUbicacionBienes(ubicacion);
        // Convertir DTO a entidad y guardarlo en BD
        int claveGenerada = solicitudDAO.insertar(dto);
        
        for (Bienes_por_SolicitudDTO bien : dto.getBienesPedidos()){
            bien.setNumSolicitud(claveGenerada);
            bienesDAO.insertar(bien);
        }
    } 
    
    @Override
    public void modificar(SolicitudDTO dto, int id) throws NoSuchElementException {
        
        SolicitudDTO solicitudActual = this.buscar(id);
        EstadoSolicitud estadoActual = solicitudActual.getEstado();
        EstadoSolicitud estadoNuevo = dto.getEstado();
        
        int legajo = dto.getLegajo();
//        validarString(estado,3);
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
        Validador.validarDestinoSolicitud(ubicacion,CAMPO_UBICACION_TEXT);
        Validador.validarId(legajo, CAMPO_LEGAJO_TEXT);
        
        for (Bienes_por_SolicitudDTO bien : dto.getBienesPedidos()) {
            Validador.validarId(bien.getID_Categoria(), CAMPO_ID_CATEGORIA_TEXT);
            Validador.validarId(bien.getCantidad(), CAMPO_CANTIDAD_TEXT);
        }
        
        // si está desaprobada o satisfecha no se puede cambiar, así que el estado queda como es actualmente
       if (estadoActual == EstadoSolicitud.DESAPROBADA || estadoActual == EstadoSolicitud.SATISFECHA) {
            dto.setEstado(estadoActual);
        } 
       // si el estado es aprobada, sólo puede cambiar a satisfecha o seguir en aprobada
        else if (estadoActual == EstadoSolicitud.APROBADA) {
            if (estadoNuevo == EstadoSolicitud.SATISFECHA || estadoNuevo == EstadoSolicitud.APROBADA) {
                dto.setEstado(estadoNuevo);
            } else { 
                dto.setEstado(estadoActual); 
            }
        }
        else { //y en caso de estar por revisar, puede cambiar a cualquier estado
            dto.setEstado(estadoNuevo);
        }
        
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
    public void eliminar(int numSolicitud) throws NoSuchElementException {
        this.buscar(numSolicitud);
        Connection con = null;
        try {
            con = BasedeDatos.getConnection();
            con.setAutoCommit(false); // <-- INICIO DE TRANSACCIÓN
            
            // 3. Eliminar hijos
            bienesDAO.eliminarPorSolicitud(numSolicitud, con);
            
            // 4. Eliminar padre
            solicitudDAO.eliminar(numSolicitud);
            
            con.commit(); // <-- FIN DE TRANSACCIÓN
        
        solicitudDAO.eliminar(numSolicitud);
    }   catch (SQLException ex) {
            System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

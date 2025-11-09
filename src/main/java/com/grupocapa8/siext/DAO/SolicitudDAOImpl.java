/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.Enums.EstadoSolicitud;
import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAOImpl implements DAOGenerica<SolicitudDTO, Integer>{
    
    private final UbicacionDAOImpl ubicacionDAO;

    public SolicitudDAOImpl() {
        this.ubicacionDAO = new UbicacionDAOImpl();
    }

    @Override
    public SolicitudDTO buscar(Integer id) {
        SolicitudDTO soli = null;
        String sql = "SELECT * FROM Solicitud WHERE Num_Solicitud = ?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    soli = new SolicitudDTO();
                    soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                    soli.setLegajo(rs.getInt("Legajo"));
                    String fechaString = rs.getString("Fecha_Solicitud");
                    Instant fecha = LocalDateTime.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                    soli.setFechaInicioSolicitud(fecha);
                    
                    int ubicacionID = rs.getInt("Destino");
                    soli.setUbicacionBienes(ubicacionDAO.buscar(ubicacionID).getNombre());
                    
                    soli.setEstado(EstadoSolicitud.valueOf(rs.getString("Estado")));
                    soli.setDescripcion(rs.getString("Descripcion"));
                    soli.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
        } catch (SQLException ex) { 
            System.getLogger(SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return soli;
    }

    @Override
    public List<SolicitudDTO> buscarTodos() {
        List<SolicitudDTO> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM Solicitud";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitudDTO soli = new SolicitudDTO();
                soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                soli.setLegajo(rs.getInt("Legajo"));
                String fechaString = rs.getString("Fecha_Solicitud");
                Instant fecha = LocalDateTime.parse(fechaString, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                soli.setFechaInicioSolicitud(fecha);
                
                int ubicacionID = rs.getInt("Destino");
                soli.setUbicacionBienes(ubicacionDAO.buscar(ubicacionID).getNombre());

                soli.setEstado(EstadoSolicitud.valueOf(rs.getString("Estado")));
                soli.setDescripcion(rs.getString("Descripcion"));
                soli.setEliminado(rs.getInt("Eliminado") != 0);
                
                solicitudes.add(soli);
            }
            }
        } catch (SQLException ex) { 
            System.getLogger(SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return solicitudes;
    }

    @Override
    public int insertar(SolicitudDTO Solicitud) {
        String sql = "INSERT INTO Solicitud (Estado, Destino, Legajo, Descripcion) VALUES (?, ?, ?, ?)";
        int idUbicacion = ubicacionDAO.buscar(Solicitud.getUbicacionBienes()).getID_Ubicacion();
        
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Solicitud.getEstado().name());
            ps.setInt(2, idUbicacion);
            ps.setInt(3, Solicitud.getLegajo());
            ps.setString(4, Solicitud.getDescripcion());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;    
    }

    @Override
    public int actualizar(SolicitudDTO Solicitud) {
        String sql = "UPDATE Solicitud SET Estado = ?, Destino = ?, Legajo = ?, Descripcion = ? WHERE Num_Solicitud = ? AND Eliminado = ?";
        int idUbicacion = ubicacionDAO.buscar(Solicitud.getUbicacionBienes()).getID_Ubicacion();
        
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Solicitud.getEstado().name());
            ps.setInt(2, idUbicacion);
            ps.setInt(3, Solicitud.getLegajo());
            ps.setString(4, Solicitud.getDescripcion());
            ps.setInt(5, Solicitud.getNumSolicitud());
            ps.setInt(6, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }    

    @Override
    public int eliminar(Integer id) {
        String sql = "UPDATE Solicitud SET Eliminado = ? WHERE Num_Solicitud = ? AND Eliminado = ?";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            ps.setInt(3, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
}

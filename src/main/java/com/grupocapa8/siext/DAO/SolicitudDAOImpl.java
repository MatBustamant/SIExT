/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

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

public class SolicitudDAOImpl implements DAOGenerica<SolicitudDTO>{

    @Override
    public SolicitudDTO buscar(int id) {
        SolicitudDTO soli = null;
        String sql = "SELECT * FROM Solicitud WHERE Num_Solicitud = ?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    soli = new SolicitudDTO();
                    soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                    
                    String fechaString = rs.getString("Fecha_Solicitud");
                    Instant fecha = LocalDateTime.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                    soli.setFechaInicioSolicitud(fecha);
                    
                    soli.setUbicacionBienes(rs.getString("Destino"));
                    soli.setEstado(rs.getString("Estado"));
                    soli.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return soli;
    }

    @Override
    public List<SolicitudDTO> buscarTodos() {
        List<SolicitudDTO> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM Solicitud";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitudDTO soli = new SolicitudDTO();
                soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                
                String fechaString = rs.getString("Fecha_Solicitud");
                Instant fecha = LocalDateTime.parse(fechaString, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                soli.setFechaInicioSolicitud(fecha);
                
                soli.setUbicacionBienes(rs.getString("Destino"));
                soli.setEstado(rs.getString("Estado"));
                soli.setDescripcion(rs.getString("Descripcion"));
                
                solicitudes.add(soli);
            }
            
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return solicitudes;
    }

    @Override
    public int insertar(SolicitudDTO Solicitud) {
        String sql = "INSERT INTO Solicitud (Num_Solicitud, Estado, Destino, Descripcion) VALUES (?, ?, ?, ?)";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Solicitud.getNumSolicitud());
            ps.setString(2, Solicitud.getEstado());
            ps.setString(3, Solicitud.getUbicacionBienes());
            ps.setString(4, Solicitud.getDescripcion());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;    
    }

    @Override
    public int actualizar(SolicitudDTO Solicitud) {
        String sql = "UPDATE Solicitud SET Estado = ?, Destino = ?, Descripcion = ? WHERE Num_Solicitud = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Solicitud.getEstado());
            ps.setString(2, Solicitud.getUbicacionBienes());
            ps.setString(3, Solicitud.getDescripcion());
            ps.setInt(4, Solicitud.getNumSolicitud());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }    

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM Solicitud WHERE Num_Solicitud = ?";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
}

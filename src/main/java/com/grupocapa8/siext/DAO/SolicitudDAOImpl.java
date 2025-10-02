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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAOImpl implements DAOGenerica<SolicitudDTO>{

    @Override
    public SolicitudDTO buscar(int id) throws SQLException {
        SolicitudDTO soli = new SolicitudDTO();
        String sql = "SELECT * FROM Solicitud WHERE Num_Solicitud = ?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                    
                    String fechaString = rs.getString("Fecha");
                    LocalDate fecha = LocalDate.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    soli.setFechaInicioSolicitud(fecha);
                    
                    soli.setUbicacionBienes(rs.getString("Destino"));
                    soli.setEstado(rs.getString("Estado"));
                    soli.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return soli;
    }

    @Override
    public List<SolicitudDTO> buscarTodos() throws SQLException {
        List<SolicitudDTO> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM Solicitud";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitudDTO soli = new SolicitudDTO();
                soli.setNumSolicitud(rs.getInt("Num_Solicitud"));
                
                String fechaString = rs.getString("Fecha");
                LocalDate fecha = LocalDate.parse(fechaString, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                soli.setFechaInicioSolicitud(fecha);
                
                soli.setUbicacionBienes(rs.getString("Destino"));
                soli.setEstado(rs.getString("Estado"));
                soli.setDescripcion(rs.getString("Descripcion"));
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return solicitudes;
    }

    @Override
    public int insertar(SolicitudDTO Solicitud) throws SQLException {
        int resultado;
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "INSERT INTO Solicitud (Num_Solicitud, Estado, Destino, Descripcion) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Solicitud.getNumSolicitud());
            ps.setString(2, Solicitud.getEstado());
            ps.setString(3, Solicitud.getUbicacionBienes());
            ps.setString(5, Solicitud.getDescripcion());
            resultado = ps.executeUpdate();
            ps.close();
        }
        return resultado;    
    }

    @Override
    public int actualizar(SolicitudDTO Solicitud) throws SQLException {
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "UPDATE Solicitud set Num_Solicitud = ?, Estado = ?, Destino = ?, Descripcion = ?, where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, (int) Solicitud.getNumSolicitud());
            ps.setString(2, Solicitud.getEstado());
            ps.setString(3, Solicitud.getUbicacionBienes());
            ps.setString(4, Solicitud.getDescripcion());
            
            resultado = ps.executeUpdate();
            ps.close();
        }
        return resultado;
    }    

    @Override
    public int eliminar(SolicitudDTO Solicitud) {
        int result = 0;
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "DELETE FROM Solicitud WHERE Num_Solicitud = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Solicitud.getNumSolicitud());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return result;
    }
    
}

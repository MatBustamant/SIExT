/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oveja
 */
public class UbicacionDAOImpl implements DAOGenerica<UbicacionDTO> {

    @Override
    public int insertar(UbicacionDTO Ubicacion) {
        String sql = "INSERT INTO Ubicacion(Nombre) VALUES (?)";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Ubicacion.getNombre());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;

    }

    @Override
    public int actualizar(UbicacionDTO Ubicacion) {
        String sql = "UPDATE Ubicacion SET Nombre = ? WHERE ID_Ubicacion = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Ubicacion.getNombre());
            ps.setInt(2, Ubicacion.getID_Ubicacion());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public UbicacionDTO buscar(int id) {

        UbicacionDTO ubicacionBien = null;
        String sql = "SELECT * FROM Ubicacion WHERE ID_Ubicacion = ? AND Eliminado = ?";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.setInt(2,0);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ubicacionBien = new UbicacionDTO();
                    ubicacionBien.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                    ubicacionBien.setNombre(rs.getString("Nombre"));
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ubicacionBien;
    }

    @Override
    public List<UbicacionDTO> buscarTodos() {
        List<UbicacionDTO> ubicaciones = new ArrayList<>();

        String sql = "SELECT * FROM Ubicacion WHERE Eliminado = ?";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, 0);
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UbicacionDTO ubicacion = new UbicacionDTO();
                ubicacion.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                ubicacion.setNombre(rs.getString("Nombre"));

                ubicaciones.add(ubicacion);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ubicaciones;
    }

    @Override
    public int eliminar(int id) {
        String sql = "UPDATE Ubicacion SET Eliminado = ? WHERE ID_Ubicacion = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }
    
    public UbicacionDTO buscar(String nombre) {

        UbicacionDTO ubicacion = null;
        String sql = "SELECT ID_Ubicacion, Nombre FROM Ubicacion WHERE Nombre = ?";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ubicacion = new UbicacionDTO();
                    ubicacion.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                    ubicacion.setNombre(rs.getString("Nombre"));
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ubicacion;
    }

}

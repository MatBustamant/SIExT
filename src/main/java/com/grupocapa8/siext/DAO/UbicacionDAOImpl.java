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
public class UbicacionDAOImpl implements DAOGenerica<UbicacionDTO, Integer> {

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
        String sql = "UPDATE Ubicacion SET Nombre = ? WHERE ID_Ubicacion = ? AND Eliminado = ? AND Es_Editable = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Ubicacion.getNombre());
            ps.setInt(2, Ubicacion.getID_Ubicacion());
            ps.setInt(3, 0);
            ps.setInt(4, 1);

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public UbicacionDTO buscar(Integer id) {

        UbicacionDTO ubicacionBien = null;
        String sql = "SELECT * FROM Ubicacion WHERE ID_Ubicacion = ?";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ubicacionBien = new UbicacionDTO();
                    ubicacionBien.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                    ubicacionBien.setNombre(rs.getString("Nombre"));
                    ubicacionBien.setEliminado(rs.getInt("Eliminado") != 0);
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

        String sql = "SELECT * FROM Ubicacion WHERE Es_Editable = ?";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UbicacionDTO ubicacion = new UbicacionDTO();
                ubicacion.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                ubicacion.setNombre(rs.getString("Nombre"));
                ubicacion.setEliminado(rs.getInt("Eliminado") != 0);

                ubicaciones.add(ubicacion);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ubicaciones;
    }

    @Override
    public int eliminar(Integer id) {
        String sql = "UPDATE Ubicacion SET Eliminado = ? WHERE ID_Ubicacion = ? AND Eliminado = ? AND Es_Editable = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            ps.setInt(3, 0);
            ps.setInt(4, 1);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }
    
    public UbicacionDTO buscar(String nombre) {

        UbicacionDTO ubicacion = null;
        String sql = "SELECT * FROM Ubicacion WHERE Nombre = ?";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ubicacion = new UbicacionDTO();
                    ubicacion.setID_Ubicacion(rs.getInt("ID_Ubicacion"));
                    ubicacion.setNombre(rs.getString("Nombre"));
                    ubicacion.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(UbicacionDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ubicacion;
    }

}

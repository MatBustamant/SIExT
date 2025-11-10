/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
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
public class CategoriaDAOImpl implements DAOGenerica<CategoriaBienDTO, Integer> {

    @Override
    public int insertar(CategoriaBienDTO Categoria) {
        String sql = "INSERT INTO Categoria(Nombre) VALUES (?)";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Categoria.getNombre());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;

    }

    @Override
    public int actualizar(CategoriaBienDTO Categoria) {
        String sql = "UPDATE Categoria SET Nombre = ? WHERE ID_Categoria = ? AND Eliminado = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Categoria.getNombre());
            ps.setInt(2, Categoria.getID_Categoria());
            ps.setInt(3, 0);

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public CategoriaBienDTO buscar(Integer id) {

        CategoriaBienDTO categoriaBien = null;
        String sql = "SELECT * FROM Categoria WHERE ID_Categoria = ?";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoriaBien = new CategoriaBienDTO();
                    categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                    categoriaBien.setNombre(rs.getString("Nombre"));
                    categoriaBien.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return categoriaBien;
    }

    @Override
    public List<CategoriaBienDTO> buscarTodos() {
        List<CategoriaBienDTO> categoriaBienes = new ArrayList<>();

        String sql = "SELECT * FROM Categoria";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaBienDTO categoriaBien = new CategoriaBienDTO();
                categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                categoriaBien.setNombre(rs.getString("Nombre"));
                categoriaBien.setEliminado(rs.getInt("Eliminado") != 0);

                categoriaBienes.add(categoriaBien);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return categoriaBienes;
    }

    @Override
    public int eliminar(Integer id) {
        String sql = "UPDATE Categoria SET Eliminado = ? WHERE ID_Categoria = ? AND Eliminado = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            ps.setInt(3, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }
    
    public CategoriaBienDTO buscar(String nombre) {

        CategoriaBienDTO categoriaBien = null;
        String sql = "SELECT * FROM Categoria WHERE Nombre = ? AND Eliminado = 0";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoriaBien = new CategoriaBienDTO();
                    categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                    categoriaBien.setNombre(rs.getString("Nombre"));
                    categoriaBien.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return categoriaBien;
    }

}

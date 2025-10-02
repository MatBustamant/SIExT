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
public class CategoriaDAOImpl implements DAOGenerica<CategoriaBienDTO> {

    @Override
    public int insertar(CategoriaBienDTO Categoria) {
        String sql = "INSERT INTO Categoria(ID_Categoria, Nombre) VALUES (?,?)";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Categoria.getID_Categoria());
            ps.setString(2, Categoria.getNombre());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;

    }

    @Override
    public int actualizar(CategoriaBienDTO Categoria) {
        String sql = "UPDATE Categoria SET Nombre = ? WHERE ID_Categoria = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Categoria.getNombre());
            ps.setInt(2, Categoria.getID_Categoria());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public CategoriaBienDTO buscar(int id) {

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
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaBienDTO categoriaBien = new CategoriaBienDTO();
                categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                categoriaBien.setNombre(rs.getString("Nombre"));

                categoriaBienes.add(categoriaBien);
            }
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return categoriaBienes;
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM Categoria WHERE ID_Categoria = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }
    
    public CategoriaBienDTO buscar(String nombre) {

        CategoriaBienDTO categoriaBien = null;
        String sql = "SELECT ID_Categoria, Nombre FROM Categoria WHERE Nombre = ?";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareCall(sql)) {
            
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoriaBien = new CategoriaBienDTO();
                    categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                    categoriaBien.setNombre(rs.getString("Nombre"));
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return categoriaBien;
    }

}

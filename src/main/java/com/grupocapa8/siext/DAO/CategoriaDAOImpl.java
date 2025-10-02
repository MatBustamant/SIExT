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
    public int insertar(CategoriaBienDTO Categoria) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        String sql = "INSERT INTO Categoria(ID_Categoria, Nombre) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Categoria.getID_Categoria());
        ps.setString(2, Categoria.getNombre());

        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;

    }

    @Override
    public int actualizar(CategoriaBienDTO Categoria) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        String sql = "UPDATE Categoria set ID_Categoria = ?, Nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Categoria.getID_Categoria());
        ps.setString(2, Categoria.getNombre());

        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    

    @Override
    public CategoriaBienDTO buscar(int id) throws SQLException {

        Connection con = BasedeDatos.getConnection();
        CategoriaBienDTO categoriaBien = null;

        String sql = "SELECT ID_Categoria, Nombre FROM Categoria WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int categoriaBienID = rs.getInt("ID_Categoria");
            String nombreCategoriaBien = rs.getString("Nombre");

            categoriaBien = new CategoriaBienDTO(categoriaBienID, nombreCategoriaBien);

        }

        return categoriaBien;
    }

    @Override
    public List<CategoriaBienDTO> buscarTodos() throws SQLException {
        List<CategoriaBienDTO> categoriaBienes = new ArrayList<>();

        String sql = "SELECT * FROM Categoria";
        try (Connection con = BasedeDatos.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaBienDTO categoriaBien = new CategoriaBienDTO();
                categoriaBien.setID_Categoria(rs.getInt("ID_Categoria"));
                categoriaBien.setNombre(rs.getString("Nombre"));

                categoriaBienes.add(categoriaBien);
            }
        }
        return categoriaBienes;
    }

    @Override
    public int eliminar(CategoriaBienDTO Categoria) {
        int result = 0;
        try {
            Connection con = BasedeDatos.getConnection();
            
            String sql = "DELETE FROM Categoria WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, Categoria.getID_Categoria());
            
            result = ps.executeUpdate();
            
            con.close();
            ps.close();
            
            
        } catch (SQLException ex) {
            System.getLogger(CategoriaDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return result;
    }
    
    public CategoriaBienDTO buscar(String nombre) throws SQLException {

        Connection con = BasedeDatos.getConnection();
        CategoriaBienDTO categoriaBien = null;

        String sql = "SELECT ID_Categoria, Nombre FROM Categoria WHERE Nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int categoriaBienID = rs.getInt("ID_Categoria");
            String nombreCategoriaBien = rs.getString("Nombre");

            categoriaBien = new CategoriaBienDTO(categoriaBienID, nombreCategoriaBien);

        }

        return categoriaBien;
    }

}

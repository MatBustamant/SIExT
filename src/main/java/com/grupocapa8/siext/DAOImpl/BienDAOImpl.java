/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAOImpl;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.InterfacesDAO.DAOGenerica;
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
public class BienDAOImpl implements DAOGenerica<BienDTO> {

    @Override
    public BienDTO get(int id) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        BienDTO bien = null;
        
        String sql = "SELECT ID_Bien, Nombre, ID_Categoria, Estado, Ubicacion FROM Bien WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            int bienID = rs.getInt("ID_Bien");
            String nombreBien = rs.getString("Nombre");
            String estado = rs.getString("Estado");
            String ubicacion = rs.getString("Ubicacion");
            int categoriaID = rs.getInt("ID_Categoria");
            String nombreCategoriaBien = getNombreCatBienesByID(categoriaID);
            
            bien = new BienDTO(bienID, nombreBien, ubicacion, estado, nombreCategoriaBien);
        }
        return bien;
    }

    public String getNombreCatBienesByID(int idCat) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        String nombreCatBien = null;

        String sql = "SELECT Nombre FROM Categoria WHERE ID_Categoria = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idCat);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            nombreCatBien = rs.getString("Nombre");
        }

        rs.close();
        ps.close();
        con.close();

        return nombreCatBien;
    }

    @Override
    public List getAll() throws SQLException {
        List<BienDTO> bienes = new ArrayList<>();

        String sql = "SELECT * FROM Bien";
        try (Connection con = BasedeDatos.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BienDTO bien = new BienDTO();
                bien.setID_Bien(rs.getInt("ID_Bien"));
                bien.setNombre(rs.getString("Nombre"));
                int categoriaID = rs.getInt("ID_Categoria");
                bien.setNombreCatBienes(getNombreCatBienesByID(categoriaID));  //Ocurre que ID_Categoria es clave foranea, NO tengo el nombre de la cat
                //llamo a una busqueda en la tabla Categoria, enviandole el id que tengo desde tabla Bien
                //Porque en el DTO no me interesa el idcat, sino nombre de categoria, entonces debo buscarlo 
                //para ponerlo en la lista y sea compatible con BienDTO
                bien.setUbicacionBien(rs.getString("Ubicacion"));
                bien.setEstadoBien(rs.getString("Estado"));

                bienes.add(bien);
            }
        }
        return bienes;
    }

    public int insertar(BienDTO Bien) throws SQLException {
        Connection con = BasedeDatos.getConnection();

        int idCategoria = getIdCategoriaByNombre(Bien.getNombreCatBienes());
        if (idCategoria == -1) {
            throw new SQLException("La categoría '" + Bien.getNombreCatBienes() + "' no existe en la base de datos"); //evitamos errores si pregunta si existe la categoria primero
        }

        String sql = "INSERT INTO Bien (ID_Bien, Nombre, ID_Categoria, Estado, Ubicacion) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Bien.getID_Bien());
        ps.setString(2, Bien.getNombre());
        ps.setInt(3, idCategoria); //el id de categoria que mande a buscar antes
        ps.setString(4, Bien.getEstadoBien());
        ps.setString(5, Bien.getUbicacionBien());

        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    public int getIdCategoriaByNombre(String nombreCategoria) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        int idCategoria = -1;  // valor por defecto si no se encuentra

        String sql = "SELECT ID_Categoria FROM Categoria WHERE Nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreCategoria);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            idCategoria = rs.getInt("ID_Categoria");
        }

        rs.close();
        ps.close();
        con.close();

        return idCategoria;
    }

    @Override
    public int guardar(BienDTO Bien) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int actualizar(BienDTO Bien) throws SQLException {
        Connection con = BasedeDatos.getConnection();

        int idCategoria = getIdCategoriaByNombre(Bien.getNombreCatBienes());
        if (idCategoria == -1) {
            throw new SQLException("La categoría '" + Bien.getNombreCatBienes() + "' no existe en la base de datos"); //evitamos errores si pregunta si existe la categoria primero
        }

        String sql = "UPDATE Bien set ID_Bien = ?, Nombre = ?, ID_Categoria = ?, Estado = ?, Ubicacion = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Bien.getID_Bien());
        ps.setString(2, Bien.getNombre());
        ps.setInt(3, idCategoria);  //use lo mismo en insertar ya que buscamos el id antes de actualizar, si existe, lo setea
        ps.setString(4, Bien.getEstadoBien());
        ps.setString(4, Bien.getUbicacionBien());

        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int eliminar(BienDTO Bien) {
        int result = 0;
        try {
            Connection con = BasedeDatos.getConnection();
            
            String sql = "DELETE FROM Bien WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, Bien.getID_Bien());
            
            result = ps.executeUpdate();
            
            con.close();
            ps.close();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return result;
    }

   

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.Enums.EstadoBien;
import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.BienDTO;
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

    private final CategoriaDAOImpl categoriaDAO;
    private final UbicacionDAOImpl ubicacionDAO;
    private final EventoTrazabilidadDAOImpl eventoDAO;

    public BienDAOImpl() {
        this.categoriaDAO = new CategoriaDAOImpl();
        this.eventoDAO = new EventoTrazabilidadDAOImpl();
        this.ubicacionDAO = new UbicacionDAOImpl();
    }

    @Override
    public BienDTO buscar(int id) {

        BienDTO bien = null;
        String sql = "SELECT * FROM Bien WHERE ID_Bien = ? AND Eliminado = ?";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, 0);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bien = new BienDTO();
                    bien.setID_Bien(rs.getInt("ID_Bien"));
                    bien.setNombre(rs.getString("Nombre"));
                    bien.setEstadoBien(EstadoBien.valueOf(rs.getString("Estado")));
                    bien.setDetalle(rs.getString("Detalle"));
                    
                    int ubicacionID = rs.getInt("ID_Ubicacion");
                    bien.setUbicacionBien(ubicacionDAO.buscar(ubicacionID).getNombre());

                    int categoriaID = rs.getInt("ID_Categoria");
                    bien.setNombreCatBienes(categoriaDAO.buscar(categoriaID).getNombre());
                    
                    bien.setCantBienes(null);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return bien;
    }

    @Override
    public List<BienDTO> buscarTodos() {
        List<BienDTO> bienes = new ArrayList<>();

        String sql = "SELECT * FROM Bien WHERE Eliminado = ?";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 0);
        
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    BienDTO bien = new BienDTO();
                    bien.setID_Bien(rs.getInt("ID_Bien"));
                    bien.setNombre(rs.getString("Nombre"));
                    int categoriaID = rs.getInt("ID_Categoria");
                    bien.setNombreCatBienes(categoriaDAO.buscar(categoriaID).getNombre());  //Ocurre que ID_Categoria es clave foranea, NO tengo el nombre de la cat
                    //llamo a una busqueda en la tabla Categoria, enviandole el id que tengo desde tabla Bien
                    //Porque en el DTO no me interesa el idcat, sino nombre de categoria, entonces debo buscarlo 
                    //para ponerlo en la lista y sea compatible con BienDTO
                    int ubicacionID = rs.getInt("ID_Ubicacion");
                    bien.setUbicacionBien(ubicacionDAO.buscar(ubicacionID).getNombre());
                    bien.setEstadoBien(EstadoBien.valueOf(rs.getString("Estado")));
                    bien.setDetalle(rs.getString("Detalle"));
                    
                    bien.setCantBienes(null);

                    bienes.add(bien);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return bienes;
    }

    @Override
    public int insertar(BienDTO bien) {
        String sql = "INSERT INTO Bien (Nombre, ID_Categoria, Estado, ID_Ubicacion, Detalle) VALUES (?,?,?,?,?)";
        int resultado = 0;
        int idCategoria = categoriaDAO.buscar(bien.getNombreCatBienes()).getID_Categoria();
        int idUbicacion = ubicacionDAO.buscar(bien.getUbicacionBien()).getID_Ubicacion();

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bien.getNombre());
            ps.setInt(2, idCategoria); //el id de categoria que mande a buscar antes
            ps.setString(3, bien.getEstadoBien().name());
            ps.setInt(4, idUbicacion);
            ps.setString(5, bien.getDetalle()); // puede ser nulo
            
            resultado = ps.executeUpdate();
            
            // Se crea un evento de ENTREGA junto a la creación de un nuevo bien. Básicamente para tener la fecha y hora de cuando pasa esto
            if (resultado > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int claveGenerada = rs.getInt(1);
                        eventoDAO.insertarNuevoIngreso(claveGenerada);
                    }
                }
            }
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

    @Override
    public int actualizar(BienDTO bien) {
        String sql = "UPDATE Bien SET Nombre = ?, ID_Categoria = ?, ID_Ubicacion = ?, Detalle = ? WHERE ID_Bien = ?";
        int idCategoria = categoriaDAO.buscar(bien.getNombreCatBienes()).getID_Categoria();
        int idUbicacion = ubicacionDAO.buscar(bien.getUbicacionBien()).getID_Ubicacion();
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, bien.getNombre());
            ps.setInt(2, idCategoria);  //use lo mismo en insertar ya que buscamos el id antes de actualizar, si existe, lo setea
            ps.setInt(3, idUbicacion);
            ps.setString(4, bien.getDetalle()); // puede ser nulo
            ps.setInt(5, bien.getID_Bien());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }
    
    public void cambiarEstado(EstadoBien estado, int id) {
        String sql = "UPDATE Bien SET Estado = ? WHERE ID_Bien = ?";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, estado.name());
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "UPDATE Bien SET Eliminado = ? WHERE ID_Bien = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

}

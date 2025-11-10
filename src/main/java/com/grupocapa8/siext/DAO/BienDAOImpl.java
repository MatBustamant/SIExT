/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.Enums.EstadoBien;
import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.Enums.TipoEvento;
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
public class BienDAOImpl implements DAOGenerica<BienDTO, Integer> {

    private final CategoriaDAOImpl categoriaDAO;
    private final UbicacionDAOImpl ubicacionDAO;
    private final EventoTrazabilidadDAOImpl eventoDAO;

    public BienDAOImpl() {
        this.categoriaDAO = new CategoriaDAOImpl();
        this.eventoDAO = new EventoTrazabilidadDAOImpl();
        this.ubicacionDAO = new UbicacionDAOImpl();
    }

    public BienDTO buscar(Integer id, Connection con) {

        BienDTO bien = null;
        String sql = "SELECT b.*, c.Nombre AS CategoriaNombre, u.Nombre AS UbicacionNombre " +
                     "FROM Bien b " +
                     "JOIN Categoria c ON b.ID_Categoria = c.ID_Categoria " +
                     "JOIN Ubicacion u ON b.ID_Ubicacion = u.ID_Ubicacion " +
                     "WHERE b.ID_Bien = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bien = new BienDTO();
                    bien.setID_Bien(rs.getInt("ID_Bien"));
                    bien.setNombre(rs.getString("Nombre"));
                    bien.setEstadoBien(EstadoBien.valueOf(rs.getString("Estado")));
                    bien.setDetalle(rs.getString("Detalle"));
                    bien.setUbicacionBien(rs.getString("UbicacionNombre"));
                    bien.setNombreCatBienes(rs.getString("CategoriaNombre"));
                    
                    bien.setCantBienes(null);
                    bien.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return bien;
    }
    
    @Override
    public BienDTO buscar(Integer id) {
        try (Connection con = BasedeDatos.getConnection()) {
            return buscar(id, con);
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }

    @Override
    public List<BienDTO> buscarTodos() {
        List<BienDTO> bienes = new ArrayList<>();

        String sql = "SELECT b.*, c.Nombre AS CategoriaNombre, u.Nombre AS UbicacionNombre " +
                     "FROM Bien b " +
                     "JOIN Categoria c ON b.ID_Categoria = c.ID_Categoria " +
                     "JOIN Ubicacion u ON b.ID_Ubicacion = u.ID_Ubicacion";

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    BienDTO bien = new BienDTO();
                    bien.setID_Bien(rs.getInt("ID_Bien"));
                    bien.setNombre(rs.getString("Nombre"));
                    bien.setNombreCatBienes(rs.getString("CategoriaNombre"));
                    bien.setUbicacionBien(rs.getString("UbicacionNombre"));
                    bien.setEstadoBien(EstadoBien.valueOf(rs.getString("Estado")));
                    bien.setDetalle(rs.getString("Detalle"));
                    
                    bien.setCantBienes(null);
                    bien.setEliminado(rs.getInt("Eliminado") != 0);

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
        int idUbicacion = 0; //Id de la ubicación por defecto SIN ASIGNAR

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bien.getNombre());
            ps.setInt(2, idCategoria); //el id de categoria que mande a buscar antes
            ps.setString(3, bien.getEstadoBien().name());
            ps.setInt(4, idUbicacion);
            ps.setString(5, bien.getDetalle()); // puede ser nulo
            
            resultado = ps.executeUpdate();
            
            // Se crea un evento de REGISTRO junto a la creación de un nuevo bien. Básicamente para tener la fecha y hora de cuando pasa esto
            if (resultado > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int claveGenerada = rs.getInt(1);
                        EventoTrazabilidadDTO registro = new EventoTrazabilidadDTO();
                        registro.setBienAsociado(claveGenerada);
                        registro.setTipoEvento(TipoEvento.REGISTRO);
                        eventoDAO.insertar(registro);
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
        String sql = "UPDATE Bien SET Nombre = ?, ID_Categoria = ?, ID_Ubicacion = ?, Detalle = ? WHERE ID_Bien = ? AND Eliminado = ?";
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
            ps.setInt(6, 0);
            
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
    
    public void cambiarUbicacion(BienDTO bien, String nombreUbicacion, Connection con) {
        String sql = "UPDATE Bien SET ID_Ubicacion = ? WHERE ID_Bien = ?";
        int idUbicacion = ubicacionDAO.buscar(nombreUbicacion, con).getID_Ubicacion();
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUbicacion);
            ps.setInt(2, bien.getID_Bien());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void cambiarUbicacion(BienDTO bien, String nombreUbicacion) {
        try (Connection con = BasedeDatos.getConnection()) {
            this.cambiarUbicacion(bien, nombreUbicacion, con);
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public int eliminar(Integer id) {
        String sql = "UPDATE Bien SET Eliminado = ? WHERE ID_Bien = ? AND Eliminado = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            ps.setInt(3, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }
    
    public int rehabilitar(int id) {
        String sql = "UPDATE Bien SET Eliminado = ? WHERE ID_Bien = ? AND Eliminado = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.setInt(3, 1);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BienDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

}

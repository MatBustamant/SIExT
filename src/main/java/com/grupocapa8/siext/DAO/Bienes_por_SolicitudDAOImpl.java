/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
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
public class Bienes_por_SolicitudDAOImpl implements DAOGenerica<Bienes_por_SolicitudDTO, ArrayList<Integer>> {
    
    private final CategoriaDAOImpl categoriaDAO;
    
    public Bienes_por_SolicitudDAOImpl() {
        categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    public Bienes_por_SolicitudDTO buscar(ArrayList<Integer> claves) {
        int id_cat = claves.get(0);
        int num_soli = claves.get(1);
        Bienes_por_SolicitudDTO bienesSolicitud = null;
        String sql = "SELECT bs.*, c.Nombre AS CategoriaNombre "
                   + "FROM Bienes_por_Solicitud bs "
                   + "JOIN Categoria c ON bs.ID_Categoria = c.ID_Categoria "
                   + "WHERE bs.Num_Solicitud = ? AND bs.ID_Categoria = ?";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id_cat);
            ps.setInt(2, num_soli);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    bienesSolicitud = new Bienes_por_SolicitudDTO();
                    bienesSolicitud.setCategoria(rs.getString("CategoriaNombre"));
                    bienesSolicitud.setCantidad(rs.getInt("Cantidad"));
                    bienesSolicitud.setEliminado(rs.getInt("Eliminado") != 0);
                }
            } 
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            return bienesSolicitud;
    }

    @Override
    public List<Bienes_por_SolicitudDTO> buscarTodos() {
        List<Bienes_por_SolicitudDTO> lista_bienesSoli = new ArrayList<>();

        String sql = "SELECT bs.*, c.Nombre AS CategoriaNombre "
                   + "FROM Bienes_por_Solicitud bs "
                   + "JOIN Categoria c ON bs.ID_Categoria = c.ID_Categoria ";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bienes_por_SolicitudDTO bienesSolicitud = new Bienes_por_SolicitudDTO();
                bienesSolicitud.setCategoria(rs.getString("CategoriaNombre"));
                bienesSolicitud.setCantidad(rs.getInt("Cantidad"));
                bienesSolicitud.setEliminado(rs.getInt("Eliminado") != 0);

                lista_bienesSoli.add(bienesSolicitud);
            }
            }
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return lista_bienesSoli;
    }

    public int insertar(Bienes_por_SolicitudDTO bienesSolicitud, Connection con) {
        String sql = "INSERT INTO Bienes_por_Solicitud(ID_Categoria, Num_Solicitud, Cantidad) VALUES (?, ?, ?)";
        int resultado = 0;
        int idCategoria = categoriaDAO.buscar(bienesSolicitud.getCategoria()).getID_Categoria();
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCategoria);
            ps.setInt(2, bienesSolicitud.getNumSolicitud());
            ps.setInt(3, bienesSolicitud.getCantidad());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
    @Override
    public int insertar(Bienes_por_SolicitudDTO bienesSolicitud) {
        try {
            Connection con = BasedeDatos.getConnection();
            return this.insertar(bienesSolicitud, con);
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
    }

    @Override
    public int actualizar(Bienes_por_SolicitudDTO bienesSolicitud) {
        String sql = "UPDATE Bienes_por_Solicitud SET Cantidad = ? WHERE Num_Solicitud = ? AND ID_Categoria = ? AND Eliminado = ?";
        int resultado = 0;
        int idCategoria = categoriaDAO.buscar(bienesSolicitud.getCategoria()).getID_Categoria();

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, bienesSolicitud.getCantidad());
            ps.setInt(2, bienesSolicitud.getNumSolicitud());
            ps.setInt(3, idCategoria);
            ps.setInt(4, 0);

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public int eliminar(ArrayList<Integer> claves) {
        String sql = "DELETE FROM Bienes_por_Solicitud WHERE Num_Solicitud = ? AND ID_Categoria = ?";
        int resultado = 0;
        int id_cat = claves.get(0);
        int numSoli = claves.get(1);
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, numSoli);
            ps.setInt(2, id_cat);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
    // Este me lo tiró Gemini, lo dejo porque podría ser útil.
    public int eliminarPorSolicitud(int numSolicitud, Connection con) {
        String sql = "DELETE FROM Bienes_por_Solicitud WHERE Num_Solicitud = ?";
        int resultado = 0;
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, numSolicitud);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
    public int eliminarPorSolicitud(int numSolicitud) {
        try {
            Connection con = BasedeDatos.getConnection();
            return eliminarPorSolicitud(numSolicitud, con);
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
    }
    
    public List<Integer> reemplazarPorSolicitud(int numSolicitud, List<Bienes_por_SolicitudDTO> bienesNuevos, Connection con) {
        // primero eliminamos todos de la solicitud
        List<Integer> listaResultado = new ArrayList<>();
        int resultado1 = this.eliminarPorSolicitud(numSolicitud, con);
        listaResultado.add(resultado1);
        
        int resultado2 = 0;
        int resultadoError = 0;
        
        // luego recorremos uno a uno y los insertamos
        for (Bienes_por_SolicitudDTO bien : bienesNuevos) {
            bien.setNumSolicitud(numSolicitud);
            int resultadoInsert = this.insertar(bien, con);
            if (resultadoInsert > 0) resultado2++;
            else resultadoError++;
        }
        listaResultado.add(resultado2);
        listaResultado.add(resultadoError);
        
        return listaResultado;
    }
    
    public List<Bienes_por_SolicitudDTO> buscarPorSolicitud(int numSolicitud) {
        List<Bienes_por_SolicitudDTO> lista_bienesSoli = new ArrayList<>();
        String sql = "SELECT bs.*, c.Nombre AS CategoriaNombre "
           + "FROM Bienes_por_Solicitud bs "
           + "JOIN Categoria c ON bs.ID_Categoria = c.ID_Categoria "
           + "WHERE bs.Num_Solicitud = ? AND bs.Eliminado = 0";
        try (Connection con = BasedeDatos.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, numSolicitud);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bienes_por_SolicitudDTO bienesSolicitud = new Bienes_por_SolicitudDTO();
                    bienesSolicitud.setCategoria(rs.getString("CategoriaNombre"));
                    bienesSolicitud.setCantidad(rs.getInt("Cantidad"));
                    bienesSolicitud.setEliminado(rs.getInt("Eliminado") != 0);
                    lista_bienesSoli.add(bienesSolicitud);
                }
            }
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return lista_bienesSoli;
    }

    
}

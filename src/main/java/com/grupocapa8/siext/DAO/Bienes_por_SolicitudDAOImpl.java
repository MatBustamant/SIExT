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
public class Bienes_por_SolicitudDAOImpl implements DAOBienes_Solicitud {

    @Override
    public Bienes_por_SolicitudDTO buscar(int id_cat, int legajo) {
        Bienes_por_SolicitudDTO bienesSolicitud = null;
        String sql = "SELECT * FROM Bienes_por_Solicitud WHERE ID_Categoria = ? AND Legajo = ?";
        
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id_cat);
            ps.setInt(2, legajo);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    bienesSolicitud = new Bienes_por_SolicitudDTO();
                    bienesSolicitud.setID_Categoria(rs.getInt("ID_Categoria"));
                    bienesSolicitud.setLegajo(rs.getInt("Legajo"));
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

        String sql = "SELECT * FROM Bienes_por_Solicitud";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bienes_por_SolicitudDTO bienesSolicitud = new Bienes_por_SolicitudDTO();
                bienesSolicitud.setLegajo(rs.getInt("Legajo"));
                bienesSolicitud.setID_Categoria(rs.getInt("ID_Categoria"));
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

    @Override
    public int insertar(Bienes_por_SolicitudDTO bienesSolicitud) {
        String sql = "INSERT INTO Bienes_por_Solicitud(ID_Categoria, Legajo, Cantidad) VALUES (?, ?, ?)";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, bienesSolicitud.getID_Categoria());
            ps.setInt(2, bienesSolicitud.getLegajo());
            ps.setInt(3, bienesSolicitud.getCantidad());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

    @Override
    public int actualizar(Bienes_por_SolicitudDTO bienesSolicitud) {
        String sql = "UPDATE Bienes_por_Solicitud SET Cantidad = ? WHERE Legajo = ? AND ID_Categoria = ? AND Eliminado = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, bienesSolicitud.getCantidad());
            ps.setInt(2, bienesSolicitud.getLegajo());
            ps.setInt(3, bienesSolicitud.getID_Categoria());
            ps.setInt(4, 0);

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public int eliminar(int id_cat, int legajo) {
        String sql = "UPDATE Bienes_por_Solicitud SET Eliminado = ? WHERE Legajo = ? AND ID_Categoria = ? AND Eliminado = ?";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, legajo);
            ps.setInt(3, id_cat);
            ps.setInt(4, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(Bienes_por_SolicitudDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
}

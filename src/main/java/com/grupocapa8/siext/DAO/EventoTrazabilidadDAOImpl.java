/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.Enums.TipoEvento;
import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oveja
 */
public class EventoTrazabilidadDAOImpl implements DAOGenerica<EventoTrazabilidadDTO> {

    @Override
    public EventoTrazabilidadDTO buscar(int id) {

        EventoTrazabilidadDTO evento = null;
        String sql = "SELECT * FROM EventoTrazabilidad WHERE ID_Evento = ?";
        
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    evento = new EventoTrazabilidadDTO();
                    evento.setID_Evento(rs.getInt("ID_Evento"));
                    evento.setBienAsociado(rs.getInt("ID_Bien"));
                    evento.setTipoEvento(TipoEvento.valueOf(rs.getString("TipoEvento")));
                    
                    String fechaString = rs.getString("Fecha");
                    Instant fecha = LocalDateTime.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                    evento.setFechaEvento(fecha);
                    evento.setEliminado(rs.getInt("Eliminado") != 0);
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return evento;
    }

    @Override
    public List<EventoTrazabilidadDTO> buscarTodos() {
        List<EventoTrazabilidadDTO> eventos = new ArrayList<>();
        String sql = "SELECT * FROM EventoTrazabilidad";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EventoTrazabilidadDTO evento = new EventoTrazabilidadDTO();
                evento.setID_Evento(rs.getInt("ID_Evento"));
                evento.setBienAsociado(rs.getInt("ID_Bien"));
                evento.setTipoEvento(TipoEvento.valueOf(rs.getString("TipoEvento")));
                
                String fechaString = rs.getString("Fecha");
                Instant fecha = LocalDateTime.parse(fechaString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                evento.setFechaEvento(fecha);
                evento.setEliminado(rs.getInt("Eliminado") != 0);
                
                eventos.add(evento);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return eventos;
    }
    
    public EventoTrazabilidadDTO buscarMasReciente(int idBienAsociado) {
        String sql = String.format("SELECT * FROM EventoTrazabilidad "
                + "WHERE ID_Bien = ? AND Eliminado = ? "
                + "AND TipoEvento IN ('%s', '%s') "
                + "ORDER BY Fecha DESC, ID_Evento DESC LIMIT 1"
                , TipoEvento.AVERIO.name(), TipoEvento.REPARACION.name());
        EventoTrazabilidadDTO evento = null;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBienAsociado);
            ps.setInt(2, 0);
            
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    evento = new EventoTrazabilidadDTO();
                    evento.setID_Evento(rs.getInt("ID_Evento"));
                    evento.setBienAsociado(rs.getInt("ID_Bien"));
                    evento.setTipoEvento(TipoEvento.valueOf(rs.getString("TipoEvento")));
                    
                    String fechaString = rs.getString("Fecha");
                    Instant fecha = LocalDateTime.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
                    evento.setFechaEvento(fecha);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return evento;
    }

    @Override
    public int insertar(EventoTrazabilidadDTO Evento) {
        String sql = "INSERT INTO EventoTrazabilidad (ID_Bien, TipoEvento) VALUES (?, ?)";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Evento.getBienAsociado());
            ps.setString(2, Evento.getTipoEvento().name());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
    public int insertarNuevoIngreso(int idBien) {
        String sql = "INSERT INTO EventoTrazabilidad (ID_Bien, TipoEvento) VALUES (?, ?)";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idBien);
            ps.setString(2, TipoEvento.ENTREGA.name());
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }

    @Override
    public int actualizar(EventoTrazabilidadDTO Evento) {
        String sql = "UPDATE EventoTrazabilidad SET ID_Bien = ?, TipoEvento = ? WHERE ID_Evento = ? AND Eliminado = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Evento.getBienAsociado());
            ps.setString(2, Evento.getTipoEvento().name());
            ps.setInt(3, Evento.getID_Evento());
            ps.setInt(4, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        String sql = "UPDATE EventoTrazabilidad SET Eliminado = ? WHERE ID_Evento = ? AND Eliminado = ?";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            ps.setInt(3, 0);
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }

}

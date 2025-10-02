/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oveja
 */
public class EventoTrazabilidadDAOImpl implements DAOGenerica<EventoTrazabilidadDTO> {

    @Override
    public EventoTrazabilidadDTO buscar(int id) throws SQLException {

        EventoTrazabilidadDTO evento = null;
        String sql = "SELECT * FROM EventoTrazabilidad WHERE ID_Evento = ?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    evento.setID_Evento(rs.getInt("ID_Evento"));
                    evento.setBienAsociado(rs.getInt("ID_Bien"));
                    evento.setTipoEvento(rs.getString("TipoEvento"));
                    
                    String fechaString = rs.getString("Fecha");
                    LocalDate fecha = LocalDate.parse(fechaString, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    evento.setFechaEvento(fecha);
                    
                    String horaString = rs.getString("Hora");
                    LocalTime hora = LocalTime.parse(horaString, DateTimeFormatter.ofPattern("HH:mm:ss"));
                    evento.setHorarioEvento(hora);
                    evento.setHorarioEvento(hora);
                }
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return evento;
    }

    @Override
    public List<EventoTrazabilidadDTO> buscarTodos() throws SQLException {
        
        List<EventoTrazabilidadDTO> eventos = new ArrayList<>();
        String sql = "SELECT * FROM EventoTrazabilidad";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EventoTrazabilidadDTO evento = new EventoTrazabilidadDTO();
                evento.setID_Evento(rs.getInt("ID_Evento"));
                evento.setBienAsociado(rs.getInt("ID_Bien"));
                evento.setTipoEvento(rs.getString("TipoEvento"));
                
                String fechaString = rs.getString("Fecha");
                LocalDate fecha = LocalDate.parse(fechaString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                evento.setFechaEvento(fecha);
                
                String horaString = rs.getString("Hora");
                LocalTime hora = LocalTime.parse(horaString, DateTimeFormatter.ofPattern("HH:mm:ss"));
                evento.setHorarioEvento(hora);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return eventos;
    }

    @Override
    public int insertar(EventoTrazabilidadDTO Evento) throws SQLException {
        
        int resultado;
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "INSERT INTO EventoTrazabilidad (ID_Evento, ID_Bien, TipoEvento) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Evento.getID_Evento());
            ps.setInt(2, Evento.getBienAsociado());
            ps.setString(5, Evento.getTipoEvento());
            resultado = ps.executeUpdate();
            ps.close();
        }
        return resultado;
    }

    @Override
    public int actualizar(EventoTrazabilidadDTO Evento) throws SQLException {
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "UPDATE EventoTrazabilidad set ID_Evento = ?, ID_Bien = ?, TipoEvento = ? where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Evento.getID_Evento());
            ps.setInt(2, Evento.getBienAsociado());
            ps.setString(3, Evento.getTipoEvento());
            
            resultado = ps.executeUpdate();
            ps.close();
        }
        return resultado;
    }

    @Override
    public int eliminar(EventoTrazabilidadDTO Evento) {
        int result = 0;
        try (Connection con = BasedeDatos.getConnection()) {
            String sql = "DELETE FROM EventoTrazabilidad WHERE ID_Evento = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Evento.getID_Evento());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
        
        return result;
    }

}

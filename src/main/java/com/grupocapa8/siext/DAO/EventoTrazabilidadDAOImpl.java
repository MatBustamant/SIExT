/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.Enums.TipoEvento;
import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
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
public class EventoTrazabilidadDAOImpl implements DAOGenerica<EventoTrazabilidadDTO, Integer> {

    private final UbicacionDAOImpl ubicacionDAO;

    public EventoTrazabilidadDAOImpl() {
        this.ubicacionDAO = new UbicacionDAOImpl();
    }
    
    @Override
    public EventoTrazabilidadDTO buscar(Integer id) {

        EventoTrazabilidadDTO evento = null;
        String sql = "SELECT e.*, u.Nombre AS UbicacionNombre "
                   + "FROM EventoTrazabilidad e "
                   + "LEFT JOIN Ubicacion u ON e.ID_Ubicacion_Destino = u.ID_Ubicacion "
                   + "WHERE ID_Evento = ?";
        
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
                    evento.setNumSolicitud(rs.getInt("Num_Solicitud"));
                    
                    evento.setDetalle(rs.getString("Detalle"));
                    evento.setUbicacionDestino(rs.getString("UbicacionNombre"));
                    
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
        String sql = "SELECT e.*, u.Nombre AS UbicacionNombre "
                   + "FROM EventoTrazabilidad e "
                   + "LEFT JOIN Ubicacion u ON e.ID_Ubicacion_Destino = u.ID_Ubicacion";
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
                evento.setNumSolicitud(rs.getInt("Num_Solicitud"));

                evento.setDetalle(rs.getString("Detalle"));
                evento.setUbicacionDestino(rs.getString("UbicacionNombre"));

                evento.setEliminado(rs.getInt("Eliminado") != 0);
                
                eventos.add(evento);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return eventos;
    }
    
    public EventoTrazabilidadDTO buscarEventoEstadoMasReciente(int idBienAsociado) {
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
    
    public EventoTrazabilidadDTO buscarBajaMasReciente(int idBienAsociado) {
        String sql = "SELECT * FROM EventoTrazabilidad "
                + "WHERE ID_Bien = ? AND Eliminado = ? "
                + "AND TipoEvento = ? "
                + "ORDER BY Fecha DESC, ID_Evento DESC LIMIT 1";
        EventoTrazabilidadDTO evento = null;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBienAsociado);
            ps.setInt(2, 0);
            ps.setString(3, TipoEvento.BAJA.name());
            
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
    
    public EventoTrazabilidadDTO buscarEventoUbicacionMasReciente(int idBienAsociado) {
        String sql = String.format(
                "SELECT e.*, u.Nombre AS UbicacionNombre " +
                "FROM EventoTrazabilidad e " +
                "LEFT JOIN Ubicacion u ON e.ID_Ubicacion_Destino = u.ID_Ubicacion " +
                "WHERE e.ID_Bien = ? AND e.Eliminado = ? " +
                "AND e.TipoEvento IN ('%s', '%s') " +
                "ORDER BY e.Fecha DESC, e.ID_Evento DESC LIMIT 1",
                TipoEvento.ENTREGA.name(), TipoEvento.DEVOLUCION.name()
        );
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
                    evento.setUbicacionDestino(rs.getString("UbicacionNombre"));
                }
            }
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return evento;
    }
    
    public EventoTrazabilidadDTO buscarMasRecienteAbsoluto(int idBienAsociado) {
        String sql = "SELECT * FROM EventoTrazabilidad "
                + "WHERE ID_Bien = ? AND Eliminado = ? "
                + "ORDER BY Fecha DESC, ID_Evento DESC LIMIT 1";
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

    public int insertar(EventoTrazabilidadDTO Evento, Connection con) {
        String sql = "INSERT INTO EventoTrazabilidad (ID_Bien, TipoEvento, Detalle, ID_Ubicacion_Destino, Num_Solicitud)"
                   + "VALUES (?, ?, ?, ?, ?)";
        int resultado = 0;
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Evento.getBienAsociado());
            ps.setString(2, Evento.getTipoEvento().name());
            ps.setString(3, Evento.getDetalle());
            
            String nombreUbicacion = Evento.getUbicacionDestino();
            if (nombreUbicacion != null) {
                UbicacionDTO ubi = ubicacionDAO.buscar(nombreUbicacion);
                if (ubi != null) {
                    ps.setInt(4, ubi.getID_Ubicacion());
                } else {
                    ps.setNull(4, java.sql.Types.INTEGER);
                }
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            Integer numSol = Evento.getNumSolicitud();
            if (numSol != null) ps.setInt(5, numSol);
            else ps.setNull(5, java.sql.Types.INTEGER);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
    @Override
    public int insertar(EventoTrazabilidadDTO Evento) {
        try (Connection con = BasedeDatos.getConnection()) {
            return this.insertar(Evento, con);
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
    }

    @Override
    public int actualizar(EventoTrazabilidadDTO Evento) {
        String sql = "UPDATE EventoTrazabilidad SET ID_Bien = ?, TipoEvento = ?, Detalle = ?, ID_Ubicacion_Destino = ?, Num_Solicitud = ?"
                   + "WHERE ID_Evento = ? AND Eliminado = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, Evento.getBienAsociado());
            ps.setString(2, Evento.getTipoEvento().name());
            ps.setString(3, Evento.getDetalle());
            
            String nombreUbicacion = Evento.getUbicacionDestino();
            if (nombreUbicacion != null) {
                UbicacionDTO ubi = ubicacionDAO.buscar(nombreUbicacion);
                if (ubi != null) {
                    ps.setInt(4, ubi.getID_Ubicacion());
                } else {
                    ps.setNull(4, java.sql.Types.INTEGER);
                }
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            Integer numSol = Evento.getNumSolicitud();
            if (numSol != null) ps.setInt(5, numSol);
            else ps.setNull(5, java.sql.Types.INTEGER);
            
            ps.setInt(5, Evento.getID_Evento());
            ps.setInt(6, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(EventoTrazabilidadDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }

    @Override
    public int eliminar(Integer id) {
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

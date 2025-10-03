/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements DAOGenerica <UsuarioDTO>{

    @Override
    public UsuarioDTO buscar(int id) {
        UsuarioDTO usuario = null;
        String sql = "SELECT ID_Usuario, Nombre_Usuario, Contraseña, Rol FROM Usuario WHERE ID_Usuario = ? AND Eliminado = ?";
        
        try(Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.setInt(2,0);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    usuario = new UsuarioDTO();
                    usuario.setID_Usuario(rs.getInt("ID_Usuario"));
                    usuario.setNombre(rs.getString("Nombre_Usuario"));
                    usuario.setContraseña(rs.getString("Contraseña"));
                    usuario.setRol(rs.getString("Rol"));
                }
            }
            
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return usuario;
    }

    @Override
    public List<UsuarioDTO> buscarTodos() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE Eliminado = ?";
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,0);
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setID_Usuario(rs.getInt("ID_Usuario"));
                usuario.setNombre(rs.getString("Nombre_Usuario"));
                usuario.setContraseña(rs.getString("Contraseña"));
                usuario.setRol(rs.getString("Rol"));

                usuarios.add(usuario);
            }
            }
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return usuarios;
    }

    @Override
    public int insertar(UsuarioDTO Usuario) {
        String sql = "INSERT INTO Usuario (Nombre_Usuario, Contraseña, Rol) VALUES (?,?,?)";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
                ps.setString(1, Usuario.getNombre());
                ps.setString(2, Usuario.getContraseña());
                ps.setString(3, Usuario.getRol());

                resultado = ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }

    @Override
    public int actualizar(UsuarioDTO Usuario) {
        String sql = "UPDATE Usuario SET Nombre_Usuario = ?, Contraseña = ?, Rol = ? WHERE ID_Usuario = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Usuario.getNombre());
            ps.setString(2, Usuario.getContraseña());
            ps.setString(3, Usuario.getRol());
            ps.setInt(4, Usuario.getID_Usuario());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

    @Override
    public int eliminar(int id) {
        String sql = "UPDATE Usuario SET Eliminado = ? WHERE id = ?";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, id);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
}

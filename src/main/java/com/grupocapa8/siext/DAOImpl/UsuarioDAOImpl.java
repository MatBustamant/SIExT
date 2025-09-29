/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DAOImpl;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DTO.UsuarioDTO;
import com.grupocapa8.siext.InterfacesDAO.DAOGenerica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements DAOGenerica <UsuarioDTO>{

    @Override
    public UsuarioDTO get(int id) throws SQLException {
        Connection con = BasedeDatos.getConnection();
        UsuarioDTO usuario = null;
        
        String sql = "SELECT ID_Usuario, Nombre_Usuario, Contraseña, Rol FROM Usuario WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            int usuarioID = rs.getInt("ID_Usuario");
            String nombreUsuario = rs.getString("Nombre_Usuario");
            String contraseña = rs.getString("Contraseña");
            String rol = rs.getString("Rol");
            
            usuario = new UsuarioDTO(usuarioID, nombreUsuario, contraseña, rol);
            
        }
        
        return usuario;
    }

    @Override
    public List<UsuarioDTO> getAll() throws SQLException {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        
        String sql = "SELECT * FROM Usuario";
         try (Connection con = BasedeDatos.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setID_Usuario(rs.getInt("ID_Usuario"));
            usuario.setNombre(rs.getString("Nombre_Usuario"));
            usuario.setContraseña(rs.getString("Contraseña"));
            usuario.setRol(rs.getString("Rol"));
            
            usuarios.add(usuario);
        }
    }
        return usuarios;
    }

    @Override
    public int insertar(UsuarioDTO Usuario) throws SQLException {
       Connection con = BasedeDatos.getConnection();
       String sql = "INSERT INTO Usuario (ID_Usuario, Nombre_Usuario, Contraseña, Rol) VALUES (?,?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       
       ps.setInt(1, Usuario.getID_Usuario());
       ps.setString(2, Usuario.getNombre());
       ps.setString(3, Usuario.getContraseña());
       ps.setString(4, Usuario.getRol());
       
       int result = ps.executeUpdate();
       
       ps.close();
       con.close();
       
       return result;
       
    
    }
    @Override
    public int guardar(UsuarioDTO Usuario) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int actualizar(UsuarioDTO Usuario) throws SQLException {
      Connection con= BasedeDatos.getConnection();
      String sql = "UPDATE Usuario set ID_Usuario = ?, Nombre_Usuario = ?, Contraseña = ?, Rol = ?";
      
      PreparedStatement ps = con.prepareStatement(sql);
      
      ps.setInt(1, Usuario.getID_Usuario());
      ps.setString(2, Usuario.getNombre());
      ps.setString(3, Usuario.getContraseña());
      ps.setString(4, Usuario.getRol());
      
      int result = ps.executeUpdate();
       
      ps.close();
      con.close();
      
      return result;
       
    }

    @Override
    public int eliminar(UsuarioDTO Usuario) {
        int result = 0;
        try {
            Connection con = BasedeDatos.getConnection();
            
            String sql = "DELETE FROM Usuario WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, Usuario.getID_Usuario());
            
            result = ps.executeUpdate();
            
            con.close();
            ps.close();
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return result;
    }

    

}

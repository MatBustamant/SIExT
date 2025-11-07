package com.grupocapa8.siext.DTO;

import com.grupocapa8.siext.Enums.RolUsuario;

/**
 *
 * @author geroj
 */
public class UsuarioDTO {
    private int ID_Usuario;
    private String Nombre;
    private String Contraseña;
    private RolUsuario Rol;
    private boolean eliminado;

    public UsuarioDTO(int ID_Usuario, String Nombre, String Contraseña, RolUsuario Rol, boolean eliminado) {
        this.ID_Usuario = ID_Usuario;
        this.Nombre = Nombre;
        this.Contraseña = Contraseña;
        this.Rol = Rol;
        this.eliminado = eliminado;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }
    
    public UsuarioDTO(){
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public RolUsuario getRol() {
        return Rol;
    }

    public void setRol(RolUsuario Rol) {
        this.Rol = Rol;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class UsuarioDTO {
    private int ID_Usuario;
    private String Nombre;
    private String Contraseña;
    private String Rol;

    public UsuarioDTO(int ID_Usuario, String Nombre, String Contraseña, String Rol) {
        this.ID_Usuario = ID_Usuario;
        this.Nombre = Nombre;
        this.Contraseña = Contraseña;
        this.Rol = Rol;
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

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
}

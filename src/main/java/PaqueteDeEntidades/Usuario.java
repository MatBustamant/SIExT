package PaqueteDeEntidades;

/**
 *
 * @author geroj
 */
public class Usuario {
    private String Nombre;
    private String Contraseña;
    private String Rol;

    public Usuario(String Nombre, String Contraseña, String Rol) {
        this.Nombre = Nombre;
        this.Contraseña = Contraseña;
        this.Rol = Rol;
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

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class UbicacionDTO {
    private int ID_Ubicacion;
    private String Nombre;
    private boolean eliminado;

    public UbicacionDTO(int ID_Ubicacion, String Nombre, boolean eliminado) {
        this.ID_Ubicacion = ID_Ubicacion;
        this.Nombre = Nombre;
        this.eliminado = eliminado;
    }
    
    public UbicacionDTO(){
    
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }    

    public int getID_Ubicacion() {
        return ID_Ubicacion;
    }

    public void setID_Ubicacion(int ID_Ubicacion) {
        this.ID_Ubicacion = ID_Ubicacion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}

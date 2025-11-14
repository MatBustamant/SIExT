package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private int ID_Categoria;
    private String Nombre;
    private boolean eliminado;

    public CategoriaBienDTO(int ID_Categoria, String Nombre, boolean eliminado) {
        this.ID_Categoria = ID_Categoria;
        this.Nombre = Nombre;
        this.eliminado = eliminado;
    }
    
    public CategoriaBienDTO(){
    
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }    

    public int getID_Categoria() {
        return ID_Categoria;
    }

    public void setID_Categoria(int ID_Categoria) {
        this.ID_Categoria = ID_Categoria;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}

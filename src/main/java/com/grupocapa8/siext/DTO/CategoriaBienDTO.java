package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private int ID_Categoria;
    private String Nombre;
    

    public CategoriaBienDTO(int ID_Categoria, String Nombre) {
        this.ID_Categoria = ID_Categoria;
        this.Nombre = Nombre;
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
    
}

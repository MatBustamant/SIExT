package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaDTO {
    private String Nombre;

    public CategoriaDTO(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}

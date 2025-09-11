package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private String Nombre;

    public CategoriaBienDTO(String Nombre) {
        this.Nombre = Nombre;

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }             
}

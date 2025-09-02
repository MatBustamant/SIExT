package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaInsumoDTO {
    private String Nombre;
    private String ClaseInsumo;

    public CategoriaInsumoDTO(String Nombre, String ClaseInsumo) {
        this.Nombre = Nombre;
        this.ClaseInsumo = ClaseInsumo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getClaseInsumo() {
        return ClaseInsumo;
    }

    public void setClaseInsumo(String ClaseInsumo) {
        this.ClaseInsumo = ClaseInsumo;
    }

   
    
    
}

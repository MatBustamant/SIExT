package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private String Nombre;
    private Integer MinReposicion;
    private Integer Stock;

    public CategoriaBienDTO(String Nombre, Integer MinReposicion, Integer Stock) {
        this.Nombre = Nombre;
        this.MinReposicion = MinReposicion;
        this.Stock = Stock;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Integer getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(Integer MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer Stock) {
        this.Stock = Stock;
    }

   
            
}

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private String Nombre;
    private int MinReposicion;
    private int Stock;

    public CategoriaBienDTO(String Nombre, int MinReposicion, int Stock) {
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

    public int getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(int MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

   
            
}

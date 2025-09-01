package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienDTO {
    private int MinReposicion;
    private int Stock;

    public CategoriaBienDTO(int MinReposicion, int Stock) {
        this.MinReposicion = MinReposicion;
        this.Stock = Stock;
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

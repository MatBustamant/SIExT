package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class InsumoDTO {
    private String Nombre;
    private String ClaseProducto;
    private int Stock;
    private int MinReposicion;
    private String NombreCatInsumo;

    public InsumoDTO(String Nombre, String ClaseProducto, int Stock, int MinReposicion, String NombreCatInsumo) {
        this.Nombre = Nombre;
        this.ClaseProducto = ClaseProducto;
        this.Stock = Stock;
        this.MinReposicion = MinReposicion;
        this.NombreCatInsumo = NombreCatInsumo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getClaseProducto() {
        return ClaseProducto;
    }

    public void setClaseProducto(String ClaseProducto) {
        this.ClaseProducto = ClaseProducto;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public int getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(int MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public String getNombreCatInsumo() {
        return NombreCatInsumo;
    }

    public void setNombreCatInsumo(String NombreCatInsumo) {
        this.NombreCatInsumo = NombreCatInsumo;
    }

    
    
}

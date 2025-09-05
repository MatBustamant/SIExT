package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class InsumoDTO {
    private String Nombre;
    private String ClaseProducto;
    private Integer Stock;
    private Integer MinReposicion;
    private String NombreCatInsumo;

    public InsumoDTO(String Nombre, String ClaseProducto, Integer Stock, Integer MinReposicion, String NombreCatInsumo) {
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

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer Stock) {
        this.Stock = Stock;
    }

    public Integer getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(Integer MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public String getNombreCatInsumo() {
        return NombreCatInsumo;
    }

    public void setNombreCatInsumo(String NombreCatInsumo) {
        this.NombreCatInsumo = NombreCatInsumo;
    }

    
    
}

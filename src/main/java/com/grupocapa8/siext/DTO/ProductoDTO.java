package com.grupocapa8.siext.DTO;

public class ProductoDTO {

    private int IdProducto;
    private String Nombre;
    private String ClaseProducto;

    public ProductoDTO(int Id, String Nom, String ClaseP) {
        IdProducto = Id;
        Nombre = Nom;
        ClaseProducto = ClaseP;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
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

}

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class IngresoDTO {
    private int ID_Ingreso;
    private String Fecha_Ingreso;
    private String Hora_Ingreso;
    private int Cantidad;
    private int Producto_Ingresado;

    public IngresoDTO(int ID_Ingreso, String Fecha_Ingreso, String Hora_Ingreso, int Cantidad, int Producto_Ingresado) {
        this.ID_Ingreso = ID_Ingreso;
        this.Fecha_Ingreso = Fecha_Ingreso;
        this.Hora_Ingreso = Hora_Ingreso;
        this.Cantidad = Cantidad;
        this.Producto_Ingresado = Producto_Ingresado;
    }

    public int getID_Ingreso() {
        return ID_Ingreso;
    }

    public void setID_Ingreso(int ID_Ingreso) {
        this.ID_Ingreso = ID_Ingreso;
    }

    public String getFecha_Ingreso() {
        return Fecha_Ingreso;
    }

    public void setFecha_Ingreso(String Fecha_Ingreso) {
        this.Fecha_Ingreso = Fecha_Ingreso;
    }

    public String getHora_Ingreso() {
        return Hora_Ingreso;
    }

    public void setHora_Ingreso(String Hora_Ingreso) {
        this.Hora_Ingreso = Hora_Ingreso;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getProducto_Ingresado() {
        return Producto_Ingresado;
    }

    public void setProducto_Ingresado(int Producto_Ingresado) {
        this.Producto_Ingresado = Producto_Ingresado;
    }
    
    
    
}

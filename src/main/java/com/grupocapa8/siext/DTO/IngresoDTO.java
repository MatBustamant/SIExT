package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class IngresoDTO {
    private Fecha Fecha_Ingreso;
    private Horario Hora_Ingreso;
    private int Cantidad;
    private int Producto_Ingresado;

    public IngresoDTO(Fecha Fecha_Ingreso, Horario Hora_Ingreso, int Cantidad, int Producto_Ingresado) {
        this.Fecha_Ingreso = Fecha_Ingreso;
        this.Hora_Ingreso = Hora_Ingreso;
        this.Cantidad = Cantidad;
        this.Producto_Ingresado = Producto_Ingresado;
    }

    public Fecha getFecha_Ingreso() {
        return Fecha_Ingreso;
    }

    public void setFecha_Ingreso(Fecha Fecha_Ingreso) {
        this.Fecha_Ingreso = Fecha_Ingreso;
    }

    public Horario getHora_Ingreso() {
        return Hora_Ingreso;
    }

    public void setHora_Ingreso(Horario Hora_Ingreso) {
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

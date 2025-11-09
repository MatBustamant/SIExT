/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DTO;

public class Bienes_por_SolicitudDTO {
    private int ID_Categoria;
    private int Legajo;
    private int cantidad;
    private boolean eliminado;

    public Bienes_por_SolicitudDTO(int ID_Categoria, int Legajo, int cantidad, boolean eliminado) {
        this.ID_Categoria = ID_Categoria;
        this.Legajo = Legajo;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
    }

    public Bienes_por_SolicitudDTO() {
    }

    public int getID_Categoria() {
        return ID_Categoria;
    }

    public void setID_Categoria(int ID_Categoria) {
        this.ID_Categoria = ID_Categoria;
    }

    public int getLegajo() {
        return Legajo;
    }

    public void setLegajo(int Legajo) {
        this.Legajo = Legajo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}

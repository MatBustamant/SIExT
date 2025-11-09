/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DTO;


public class ResponsableDTO {
    private int legajo;
    private String nombre_apellido;
    private boolean eliminado;

    public ResponsableDTO(int legajo, String nombre_apellido, boolean eliminado) {
        this.legajo = legajo;
        this.nombre_apellido = nombre_apellido;
        this.eliminado = eliminado;
    }

    public ResponsableDTO() {
    }
    
    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DTO;


public class ResponsableDTO {
    private int legajo;
    private String nombre;
    private boolean eliminado;

    public ResponsableDTO(int legajo, String nombre_apellido, boolean eliminado) {
        this.legajo = legajo;
        this.nombre = nombre_apellido;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    
}

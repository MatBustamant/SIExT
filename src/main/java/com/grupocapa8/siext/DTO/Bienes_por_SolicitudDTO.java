/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DTO;

import java.util.Objects;

public class Bienes_por_SolicitudDTO {

    private Integer numSolicitud;
    private String categoria;
    private int cantidad;
    private boolean eliminado;

    public Bienes_por_SolicitudDTO(String categoria, int cantidad, boolean eliminado, Integer numSolicitud) {
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
        this.numSolicitud = numSolicitud;
    }

    public Bienes_por_SolicitudDTO() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.categoria);
        hash = 97 * hash + this.cantidad;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bienes_por_SolicitudDTO other = (Bienes_por_SolicitudDTO) obj;
        if (this.cantidad != other.cantidad) {
            return false;
        }
        return Objects.equals(this.categoria, other.categoria);
    }
    
}

package com.grupocapa8.siext.DTO;

import com.grupocapa8.siext.Enums.EstadoSolicitud;
import java.time.Instant;

/**
 *
 * @author geroj
 */
public class SolicitudDTO {
    private Integer numSolicitud;
    private Instant FechaInicioSolicitud;
    private String UbicacionBienes;
    private int Legajo;
    private EstadoSolicitud Estado;
    private String Descripcion;
    private boolean eliminado;
    
    public SolicitudDTO(){
        
    }
    public SolicitudDTO(Integer numSolicitud, Instant FechaInicioSolicitud, String UbicacionBienes, int legajo, EstadoSolicitud Estado, boolean eliminado) {
        this.numSolicitud = numSolicitud;
        this.FechaInicioSolicitud = FechaInicioSolicitud;
        this.UbicacionBienes = UbicacionBienes;
        this.Legajo = legajo;
        this.Estado = Estado;
        this.eliminado = eliminado;
    }

    public int getLegajo() {
        return Legajo;
    }

    public void setLegajo(int Legajo) {
        this.Legajo = Legajo;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public Instant getFechaInicioSolicitud() {
        return FechaInicioSolicitud;
    }

    public void setFechaInicioSolicitud(Instant FechaInicioSolicitud) {
        this.FechaInicioSolicitud = FechaInicioSolicitud;
    }

    public String getUbicacionBienes() {
        return UbicacionBienes;
    }

    public void setUbicacionBienes(String UbicacionBienes) {
        this.UbicacionBienes = UbicacionBienes;
    }

    public EstadoSolicitud getEstado() {
        return Estado;
    }

    public void setEstado(EstadoSolicitud Estado) {
        this.Estado = Estado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}

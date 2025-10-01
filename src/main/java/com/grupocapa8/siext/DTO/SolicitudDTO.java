package com.grupocapa8.siext.DTO;

import java.time.LocalDate;

/**
 *
 * @author geroj
 */
public class SolicitudDTO {
    private Integer numSolicitud;
    private LocalDate FechaInicioSolicitud;
    private String UbicacionBienes;
    private String Estado;
    private String Descripcion;
    
    public SolicitudDTO(){
        
    }
    public SolicitudDTO(Integer numSolicitud, LocalDate FechaInicioSolicitud, String UbicacionBienes, String Estado) {
        this.numSolicitud = numSolicitud;
        this.FechaInicioSolicitud = FechaInicioSolicitud;
        this.UbicacionBienes = UbicacionBienes;
        this.Estado = Estado;
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

    public LocalDate getFechaInicioSolicitud() {
        return FechaInicioSolicitud;
    }

    public void setFechaInicioSolicitud(LocalDate FechaInicioSolicitud) {
        this.FechaInicioSolicitud = FechaInicioSolicitud;
    }

    public String getUbicacionBienes() {
        return UbicacionBienes;
    }

    public void setUbicacionBienes(String UbicacionBienes) {
        this.UbicacionBienes = UbicacionBienes;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }   
}

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class SolicitudDTO {
    private Integer numSolicitud;
    private String FechaInicioSolicitud;
    private String UbicacionBienes;
    private String Estado;

    public SolicitudDTO(Integer numSolicitud, String FechaInicioSolicitud, String UbicacionBienes, String Estado) {
        this.numSolicitud = numSolicitud;
        this.FechaInicioSolicitud = FechaInicioSolicitud;
        this.UbicacionBienes = UbicacionBienes;
        this.Estado = Estado;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getFechaInicioSolicitud() {
        return FechaInicioSolicitud;
    }

    public void setFechaInicioSolicitud(String FechaInicioSolicitud) {
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

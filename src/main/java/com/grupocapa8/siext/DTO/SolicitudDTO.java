package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class SolicitudDTO {
    private int numSolicitud;
    private String FechaInicioSolicitud;
    private String DestinoProductos;
    private String Estado;
    private String LegSolicitante;

    public SolicitudDTO(int numSolicitud, String FechaInicioSolicitud, String DestinoProductos, String Estado, String LegSolicitante) {
        this.numSolicitud = numSolicitud;
        this.FechaInicioSolicitud = FechaInicioSolicitud;
        this.DestinoProductos = DestinoProductos;
        this.Estado = Estado;
        this.LegSolicitante = LegSolicitante;
    }

    public int getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(int numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getFechaInicioSolicitud() {
        return FechaInicioSolicitud;
    }

    public void setFechaInicioSolicitud(String FechaInicioSolicitud) {
        this.FechaInicioSolicitud = FechaInicioSolicitud;
    }

    public String getDestinoProductos() {
        return DestinoProductos;
    }

    public void setDestinoProductos(String DestinoProductos) {
        this.DestinoProductos = DestinoProductos;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getLegSolicitante() {
        return LegSolicitante;
    }

    public void setLegSolicitante(String LegSolicitante) {
        this.LegSolicitante = LegSolicitante;
    }
    
}

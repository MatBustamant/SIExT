package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class BienDTO {
    private String UbicacionBien;
    private String EstadoBien;
    private String NombreCatBienes;

    public BienDTO(String UbicacionBien, String EstadoBien, String NombreCatBienes) {
        this.UbicacionBien = UbicacionBien;
        this.EstadoBien = EstadoBien;
        this.NombreCatBienes = NombreCatBienes;
    }

    public String getUbicacionBien() {
        return UbicacionBien;
    }

    public void setUbicacionBien(String UbicacionBien) {
        this.UbicacionBien = UbicacionBien;
    }

    public String getEstadoBien() {
        return EstadoBien;
    }

    public void setEstadoBien(String EstadoBien) {
        this.EstadoBien = EstadoBien;
    }

    public String getNombreCatBienes() {
        return NombreCatBienes;
    }

    public void setNombreCatBienes(String NombreCatBienes) {
        this.NombreCatBienes = NombreCatBienes;
    }
    
    
}

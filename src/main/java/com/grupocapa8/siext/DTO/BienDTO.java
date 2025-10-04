package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class BienDTO {
    private int ID_Bien;
    private String Nombre;
    private String UbicacionBien;
    private String EstadoBien; //Cambiaria a EnUso o EnDesuso
    private String NombreCatBienes;
    private int cantBienes;

    public BienDTO(int ID_Bien, String Nombre, String UbicacionBien, String EstadoBien, String NombreCatBienes, int cantBienes) {
        this.ID_Bien = ID_Bien;
        this.Nombre = Nombre;
        this.UbicacionBien = UbicacionBien;
        this.EstadoBien = EstadoBien;
        this.NombreCatBienes = NombreCatBienes;
        this.cantBienes = cantBienes;
    }
    public BienDTO(){
        
    }

    public int getCantBienes() {
        return cantBienes;
    }

    public void setCantBienes(int cantBienes) {
        this.cantBienes = cantBienes;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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

    public int getID_Bien() {
        return ID_Bien;
    }

    public void setID_Bien(int ID_Bien) {
        this.ID_Bien = ID_Bien;
    }

   
    
    
}

package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadDTO {
    private String FechaEvento;
    private String HorarioEvento;
    private int BienAsociado;
    private String TipoEvento;

    public EventoTrazabilidadDTO(String FechaEvento, String HorarioEvento, int BienAsociado, String TipoEvento) {
        this.FechaEvento = FechaEvento;
        this.HorarioEvento = HorarioEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
    }

    public String getFechaEvento() {
        return FechaEvento;
    }

    public void setFechaEvento(String FechaEvento) {
        this.FechaEvento = FechaEvento;
    }

    public String getHorarioEvento() {
        return HorarioEvento;
    }

    public void setHorarioEvento(String HorarioEvento) {
        this.HorarioEvento = HorarioEvento;
    }

    public int getBienAsociado() {
        return BienAsociado;
    }

    public void setBienAsociado(int BienAsociado) {
        this.BienAsociado = BienAsociado;
    }

    public String getTipoEvento() {
        return TipoEvento;
    }

    public void setTipoEvento(String TipoEvento) {
        this.TipoEvento = TipoEvento;
    }
    
}

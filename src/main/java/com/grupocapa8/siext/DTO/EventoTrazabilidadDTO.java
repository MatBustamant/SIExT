package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadDTO {
    private Fecha FechaEvento;
    private Horario HorarioEvento;
    private Integer BienAsociado;
    private String TipoEvento;

    public EventoTrazabilidadDTO(Fecha FechaEvento, Horario HorarioEvento, Integer BienAsociado, String TipoEvento) {
        this.FechaEvento = FechaEvento;
        this.HorarioEvento = HorarioEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
    }

    public Fecha getFechaEvento() {
        return FechaEvento;
    }

    public void setFechaEvento(Fecha FechaEvento) {
        this.FechaEvento = FechaEvento;
    }

    public Horario getHorarioEvento() {
        return HorarioEvento;
    }

    public void setHorarioEvento(Horario HorarioEvento) {
        this.HorarioEvento = HorarioEvento;
    }

    public Integer getBienAsociado() {
        return BienAsociado;
    }

    public void setBienAsociado(Integer BienAsociado) {
        this.BienAsociado = BienAsociado;
    }

    public String getTipoEvento() {
        return TipoEvento;
    }

    public void setTipoEvento(String TipoEvento) {
        this.TipoEvento = TipoEvento;
    }
    
}

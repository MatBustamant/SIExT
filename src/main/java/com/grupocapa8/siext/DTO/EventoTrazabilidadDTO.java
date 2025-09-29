package com.grupocapa8.siext.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadDTO {
    private int ID_Evento;
    private LocalDate FechaEvento;
    private LocalTime HorarioEvento;
    private int BienAsociado;
    private String TipoEvento;

    
    public EventoTrazabilidadDTO(){
    }
    
    public EventoTrazabilidadDTO(LocalDate FechaEvento, LocalTime HorarioEvento, Integer BienAsociado, String TipoEvento) {
        this.FechaEvento = FechaEvento;
        this.HorarioEvento = HorarioEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
    }

    public int getID_Evento() {
        return ID_Evento;
    }

    public void setID_Evento(int ID_Evento) {
        this.ID_Evento = ID_Evento;
    }

    public LocalDate getFechaEvento() {
        return FechaEvento;
    }

    public void setFechaEvento(LocalDate FechaEvento) {
        this.FechaEvento = FechaEvento;
    }

    public LocalTime getHorarioEvento() {
        return HorarioEvento;
    }

    public void setHorarioEvento(LocalTime HorarioEvento) {
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

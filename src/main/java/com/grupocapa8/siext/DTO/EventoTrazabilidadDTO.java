package com.grupocapa8.siext.DTO;

import java.time.Instant;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadDTO {
    private int ID_Evento;
    private Instant FechaEvento;
    private int BienAsociado;
    private String TipoEvento;

    
    public EventoTrazabilidadDTO(){
    }
    
    public EventoTrazabilidadDTO(Instant FechaEvento, Integer BienAsociado, String TipoEvento) {
        this.FechaEvento = FechaEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
    }

    public int getID_Evento() {
        return ID_Evento;
    }

    public void setID_Evento(int ID_Evento) {
        this.ID_Evento = ID_Evento;
    }

    public Instant getFechaEvento() {
        return FechaEvento;
    }

    public void setFechaEvento(Instant FechaEvento) {
        this.FechaEvento = FechaEvento;
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

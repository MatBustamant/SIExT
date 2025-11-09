package com.grupocapa8.siext.DTO;

import com.grupocapa8.siext.Enums.TipoEvento;
import java.time.Instant;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidadDTO {
    private int ID_Evento;
    private Instant FechaEvento;
    private int BienAsociado;
    private TipoEvento TipoEvento;
    private String detalle;
    private String ubicacionDestino;
    private boolean eliminado;
    
    public EventoTrazabilidadDTO(){
    }
    
    public EventoTrazabilidadDTO(Instant FechaEvento, Integer BienAsociado, TipoEvento TipoEvento, boolean eliminado,
            String detalle, String ubicacionDestino) {
        this.FechaEvento = FechaEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
        this.detalle = detalle;
        this.ubicacionDestino = ubicacionDestino;
        this.eliminado = eliminado;
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

    public TipoEvento getTipoEvento() {
        return TipoEvento;
    }

    public void setTipoEvento(TipoEvento TipoEvento) {
        this.TipoEvento = TipoEvento;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }
    
}

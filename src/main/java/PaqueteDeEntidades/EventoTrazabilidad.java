package PaqueteDeEntidades;

/**
 *
 * @author geroj
 */
public class EventoTrazabilidad {
    private int IdEvento;
    private String FechaEvento;
    private String HorarioEvento;
    private int BienAsociado;
    private String TipoEvento;

    public EventoTrazabilidad(int IdEvento, String FechaEvento, String HorarioEvento, int BienAsociado, String TipoEvento) {
        this.IdEvento = IdEvento;
        this.FechaEvento = FechaEvento;
        this.HorarioEvento = HorarioEvento;
        this.BienAsociado = BienAsociado;
        this.TipoEvento = TipoEvento;
    }

    public int getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(int IdEvento) {
        this.IdEvento = IdEvento;
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

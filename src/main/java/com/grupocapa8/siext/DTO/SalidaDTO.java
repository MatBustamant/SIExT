package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class SalidaDTO {
    private String LegRetirador;
    private String DestinoProducto;
    private Fecha FechaSalida;
    private Horario HorarioSalida;
    private int NumSolicitudAsociado;

    public SalidaDTO(String LegRetirador, String DestinoProducto, Fecha FechaSalida, Horario HorarioSalida, int NumSolicitudAsociado) {
        this.LegRetirador = LegRetirador;
        this.DestinoProducto = DestinoProducto;
        this.FechaSalida = FechaSalida;
        this.HorarioSalida = HorarioSalida;
        this.NumSolicitudAsociado = NumSolicitudAsociado;
    }

    public String getLegRetirador() {
        return LegRetirador;
    }

    public void setLegRetirador(String LegRetirador) {
        this.LegRetirador = LegRetirador;
    }

    public String getDestinoProducto() {
        return DestinoProducto;
    }

    public void setDestinoProducto(String DestinoProducto) {
        this.DestinoProducto = DestinoProducto;
    }

    public Fecha getFechaSalida() {
        return FechaSalida;
    }

    public void setFechaSalida(Fecha FechaSalida) {
        this.FechaSalida = FechaSalida;
    }

    public Horario getHorarioSalida() {
        return HorarioSalida;
    }

    public void setHorarioSalida(Horario HorarioSalida) {
        this.HorarioSalida = HorarioSalida;
    }

    public int getNumSolicitudAsociado() {
        return NumSolicitudAsociado;
    }

    public void setNumSolicitudAsociado(int NumSolicitudAsociado) {
        this.NumSolicitudAsociado = NumSolicitudAsociado;
    }
    
}

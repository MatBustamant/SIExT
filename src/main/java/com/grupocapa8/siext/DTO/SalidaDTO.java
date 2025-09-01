package com.grupocapa8.siext.DTO;

/**
 *
 * @author geroj
 */
public class SalidaDTO {
    private int IdSalidaProducto;
    private String LegRetirador;
    private String DestinoProducto;
    private String FechaSalida;
    private String HorarioSalida;
    private int NumSolicitudAsociado;

    public SalidaDTO(int IdSalidaProducto, String LegRetirador, String DestinoProducto, String FechaSalida, String HorarioSalida, int NumSolicitudAsociado) {
        this.IdSalidaProducto = IdSalidaProducto;
        this.LegRetirador = LegRetirador;
        this.DestinoProducto = DestinoProducto;
        this.FechaSalida = FechaSalida;
        this.HorarioSalida = HorarioSalida;
        this.NumSolicitudAsociado = NumSolicitudAsociado;
    }

    public int getIdSalidaProducto() {
        return IdSalidaProducto;
    }

    public void setIdSalidaProducto(int IdSalidaProducto) {
        this.IdSalidaProducto = IdSalidaProducto;
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

    public String getFechaSalida() {
        return FechaSalida;
    }

    public void setFechaSalida(String FechaSalida) {
        this.FechaSalida = FechaSalida;
    }

    public String getHorarioSalida() {
        return HorarioSalida;
    }

    public void setHorarioSalida(String HorarioSalida) {
        this.HorarioSalida = HorarioSalida;
    }

    public int getNumSolicitudAsociado() {
        return NumSolicitudAsociado;
    }

    public void setNumSolicitudAsociado(int NumSolicitudAsociado) {
        this.NumSolicitudAsociado = NumSolicitudAsociado;
    }
    
}

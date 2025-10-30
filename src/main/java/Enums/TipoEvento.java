/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Matias
 */
public enum TipoEvento {
    AVERIO("AVERIO"),
    REPARACION("REPARACION"),
    ENTREGA("ENTREGA");

    private final String nombre;

    private TipoEvento(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

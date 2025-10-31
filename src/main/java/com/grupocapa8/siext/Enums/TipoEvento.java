/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.grupocapa8.siext.Enums;

/**
 *
 * @author Matias
 */
public enum TipoEvento {
    AVERIO,
    REPARACION,
    ENTREGA;
    
    @Override
    public String toString() {
        return name();
    }
}

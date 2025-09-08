/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("insumo")
public class InsumoController extends AbstractController<Object, Integer> {

    public InsumoController() {
        this.servicio = new Object(); //InsumoService
    }
    
}

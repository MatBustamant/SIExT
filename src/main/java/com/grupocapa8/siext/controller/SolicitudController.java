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
@Path("solicitud")
public class SolicitudController extends AbstractController<Object, Integer>{

    public SolicitudController() {
        this.servicio = new Object(); //SolicitudService
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.Services.SolicitudService;
import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("solicitud")
public class SolicitudController extends AbstractController<SolicitudDTO, Integer>{

    public SolicitudController() {
        this.servicio = new SolicitudService(); //SolicitudService
    }
    
}

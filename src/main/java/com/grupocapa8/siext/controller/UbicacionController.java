/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Services.UbicacionService;
import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("ubicacion")
public class UbicacionController extends AbstractController<UbicacionDTO>{

    public UbicacionController() {
        this.servicio = new UbicacionService();
    }
    
}

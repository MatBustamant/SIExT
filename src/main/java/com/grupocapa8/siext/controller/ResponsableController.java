/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.ResponsableDTO;
import com.grupocapa8.siext.Services.ResponsableService;
import jakarta.ws.rs.Path;

/**
 *
 * @author oveja
 */
@Path("responsable")
public class ResponsableController  extends AbstractController<ResponsableDTO, Integer> {
    
    public ResponsableController(){
        this.servicio = new ResponsableService();
    }
}

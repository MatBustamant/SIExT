/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.Services.BienService;
import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("bien")
public class BienController extends AbstractController<BienDTO>{

    public BienController() {
        this.servicio = new BienService(); //BienService
    }
    
}

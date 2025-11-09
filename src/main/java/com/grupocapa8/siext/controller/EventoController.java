/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.Services.EventoTrazabilidadServices;
import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("evento")
public class EventoController extends AbstractController<EventoTrazabilidadDTO, Integer>{

    public EventoController() {
        this.servicio = new EventoTrazabilidadServices(); //EventoService
    }
    
}

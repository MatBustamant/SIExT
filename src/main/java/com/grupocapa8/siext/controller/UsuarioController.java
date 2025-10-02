/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.UsuarioDTO;
import com.grupocapa8.siext.Services.UsuarioService;
import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("usuario")
public class UsuarioController extends AbstractController<UsuarioDTO, Integer>{

    public UsuarioController() {
        this.servicio = new UsuarioService(); //UsuarioService
    }
    
}

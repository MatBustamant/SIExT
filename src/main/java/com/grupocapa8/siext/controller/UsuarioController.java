/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.DTO.UsuarioDTO;
import com.grupocapa8.siext.Services.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.NoSuchElementException;

/**
 *
 * @author Matias
 */
@Path("usuario")
public class UsuarioController extends AbstractController<UsuarioDTO>{

    public UsuarioController() {
        this.servicio = new UsuarioService(); //UsuarioService
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(UsuarioDTO credenciales) {
        try {
            UsuarioDTO datosUsuario = ((UsuarioService) servicio).login(credenciales.getNombre(), credenciales.getContraseña());
            return Response.ok(datosUsuario).build();
        } catch (NoSuchElementException | SecurityException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Usuario o contraseña incorrecta\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422, "Unprocessable Entity").entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
}

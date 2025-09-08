/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author Matias
 */
public abstract class AbstractController<E, ID> {
    
    protected Object servicio;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("crear")
    public void crear(E entidad) {
        // servicio.crear(entidad);
        throw new UnsupportedOperationException("servicios no creados todavía");
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leer/{id}")
    public E buscarPorId(ID id) {
        // return servicio.buscarPorId(id);
        throw new UnsupportedOperationException("servicios no creados todavía");
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leer")
    public E[] buscarTodos() {
        // return servicio.buscarTodos();
        throw new UnsupportedOperationException("servicios no creados todavía");
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("modificar/{id}")
    public void modificar(E entidad, ID id) {
        // return servicio.modificar(entidad);
        throw new UnsupportedOperationException("servicios no creados todavía");
    }
    
    @DELETE
    @Path("eliminar/{id}")
    public void eliminar(ID id) {
        // return servicio.eliminar(id);
        throw new UnsupportedOperationException("servicios no creados todavía");
    }
    
}

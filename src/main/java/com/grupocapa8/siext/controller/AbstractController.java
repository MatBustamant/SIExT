/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.controller;

import com.grupocapa8.siext.Services.ServiceGenerico;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author Matias
 */
public abstract class AbstractController<E> {
    
    protected ServiceGenerico<E> servicio;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("crear")
    public void crear(E entidad) {
        servicio.crear(entidad);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leer/{id}")
    public E buscarPorId(@PathParam("id") int id) {
        return servicio.buscar(id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leer")
    public List<E> buscarTodos() {
        return servicio.buscarTodos();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("modificar/{id}")
    public void modificar(E entidad, @PathParam("id") int id) {
        servicio.modificar(entidad);
    }
    
    @DELETE
    @Path("eliminar/{id}")
    public void eliminar(@PathParam("id") int id) {
        servicio.eliminar(id);
    }
    
}

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
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;

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
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            E entidad = servicio.buscar(id);
            return Response.ok(entidad).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leer")
    public Response buscarTodos() {
        try {
            List<E> entidades = servicio.buscarTodos();
            return Response.ok(entidades).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("modificar/{id}")
    public Response modificar(E entidad, @PathParam("id") int id) {
        try {
            servicio.modificar(entidad);
            return Response.ok().build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
    @DELETE
    @Path("eliminar/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            servicio.eliminar(id);
            return Response.ok().build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
}
    

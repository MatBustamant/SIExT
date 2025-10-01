/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.controller;

import jakarta.ws.rs.Path;

/**
 *
 * @author Matias
 */
@Path("categoria")
public class CategoriaController extends AbstractController<Object, Integer>{

    public CategoriaController() {
        this.servicio = new Object(); //CategoriaBienService y CategoriaInsumoService. O capaz un solo service a dos daos? ya veremos.
    }
    
}

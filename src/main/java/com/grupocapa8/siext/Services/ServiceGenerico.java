/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.Services;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Matias
 */
public interface ServiceGenerico<E> {
    
    public E buscar(int id) throws NoSuchElementException;
    public List<E> buscarTodos();
    public void crear(E entidad);
    public void modificar(E entidad, int id) throws NoSuchElementException;
    public void eliminar(int id) throws NoSuchElementException;
    
}

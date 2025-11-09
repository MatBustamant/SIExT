/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.DAO;

/**
 *
 * @author oveja
 */
import java.util.List;

public interface DAOGenerica<T, E> {
    T buscar(E id);
    List<T> buscarTodos();
    int insertar(T entidad);
    int actualizar(T entidad);
    int eliminar(E id);
}
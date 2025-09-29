/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.InterfacesDAO;

/**
 *
 * @author oveja
 */
import java.sql.SQLException;
import java.util.List;

public interface DAOGenerica<T> {
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    int insertar(T entidad) throws SQLException;
    int guardar(T entidad) throws SQLException;
    int actualizar(T entidad) throws SQLException;
    int eliminar(T entidad);
}
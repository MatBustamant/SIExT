/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.DAO;

/**
 *
 * @author oveja
 */
import java.sql.SQLException;
import java.util.List;

public interface DAOGenerica<T> {
    T buscar(int id) throws SQLException;
    List<T> buscarTodos() throws SQLException;
    int insertar(T entidad) throws SQLException;
    int actualizar(T entidad) throws SQLException;
    int eliminar(T entidad);
}
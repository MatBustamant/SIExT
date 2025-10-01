/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.InterfacesDAO;

import java.sql.SQLException;

/**
 *
 * @author oveja
 */
public interface BienDAO {
    public interface DAOBien {
    String getNombreCatBienesByID(int idCat) throws SQLException;
    public int getIdCategoriaByNombre(String nombreCategoria) throws SQLException;
}
}

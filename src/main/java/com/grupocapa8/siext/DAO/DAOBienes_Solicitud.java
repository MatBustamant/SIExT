/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import java.util.List;

/**
 *
 * @author oveja
 */
public interface DAOBienes_Solicitud {
    Bienes_por_SolicitudDTO buscar(int id_cat, int legajo);
    List<Bienes_por_SolicitudDTO> buscarTodos();
    int insertar(Bienes_por_SolicitudDTO entidad);
    int actualizar(Bienes_por_SolicitudDTO entidad);
    int eliminar(int id_cat, int legajo);
}

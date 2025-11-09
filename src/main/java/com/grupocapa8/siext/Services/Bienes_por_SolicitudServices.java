/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.Bienes_por_SolicitudDAOImpl;
import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author oveja
 */
public class Bienes_por_SolicitudServices implements ServiceBienes_Solicitud {
    
    private final Bienes_por_SolicitudDAOImpl bienesDAO; //acceso a la BD

    private final static String CAMPO_ID_CATEGORIA_TEXT = "ID_Categoría";
    private final static String CAMPO_LEGAJO_TEXT = "Legajo";
    private final static String CAMPO_CANTIDAD_TEXT = "Cantidad";

    
    public Bienes_por_SolicitudServices(){
        this.bienesDAO = new Bienes_por_SolicitudDAOImpl();
    }
    
    @Override
    public Bienes_por_SolicitudDTO buscar(int id_cat, int legajo) throws NoSuchElementException {
        return this.buscar(id_cat, legajo, true);
    }
    
    public Bienes_por_SolicitudDTO buscar(int id_cat, int legajo, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(id_cat, CAMPO_ID_CATEGORIA_TEXT);
        Validador.validarId(legajo, CAMPO_LEGAJO_TEXT);


        Bienes_por_SolicitudDTO bienPorSol = bienesDAO.buscar(id_cat, legajo);
        
        if (bienPorSol == null || (checkEliminado && bienPorSol.isEliminado())) {
            throw new NoSuchElementException("No existe el registro.");
        }
        return bienPorSol;
    }
    
    @Override
    public List<Bienes_por_SolicitudDTO> buscarTodos() {
        return bienesDAO.buscarTodos();
    }

    @Override
    public void crear(Bienes_por_SolicitudDTO dto) {
        Validador.validarId(dto.getID_Categoria(), CAMPO_ID_CATEGORIA_TEXT);
        Validador.validarId(dto.getLegajo(), CAMPO_LEGAJO_TEXT);
        
        Validador.validarId(dto.getCantidad(), CAMPO_CANTIDAD_TEXT); //cantidad tiene que ser mayor o igual a 1 
        bienesDAO.insertar(dto);
    }

    @Override
    public void modificar(Bienes_por_SolicitudDTO dto, int id_cat, int legajo) throws NoSuchElementException {
        this.buscar(id_cat, legajo);
        
        Validador.validarId(dto.getCantidad(), CAMPO_CANTIDAD_TEXT);
        
        dto.setID_Categoria(id_cat);
        dto.setLegajo(legajo);
        bienesDAO.actualizar(dto);
    }

    @Override
    public void eliminar(int id_cat, int legajo) throws NoSuchElementException {
        this.buscar(id_cat, legajo);
        
        bienesDAO.eliminar(id_cat, legajo);
    }
    
}

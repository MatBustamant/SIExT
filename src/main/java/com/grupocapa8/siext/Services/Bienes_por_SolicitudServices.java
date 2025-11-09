/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.Bienes_por_SolicitudDAOImpl;
import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author oveja
 */
public class Bienes_por_SolicitudServices implements ServiceGenerico<Bienes_por_SolicitudDTO, ArrayList<Integer>> {
    
    private final Bienes_por_SolicitudDAOImpl bienesDAO; //acceso a la BD

    private final static String CAMPO_ID_CATEGORIA_TEXT = "ID_Categoría";
    private final static String CAMPO_NUMSOLI_TEXT = "Num_Solicitud";
    private final static String CAMPO_CANTIDAD_TEXT = "Cantidad";

    
    public Bienes_por_SolicitudServices(){
        this.bienesDAO = new Bienes_por_SolicitudDAOImpl();
    }
    
    @Override
    public Bienes_por_SolicitudDTO buscar(ArrayList<Integer> claves) throws NoSuchElementException {
        int id_cat = 0;
        int numSoli = 1;
        return this.buscar(claves.get(id_cat), claves.get(numSoli), true);
    }
    
    public Bienes_por_SolicitudDTO buscar(int id_cat, int numSoli, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(id_cat, CAMPO_ID_CATEGORIA_TEXT);
        Validador.validarId(numSoli, CAMPO_NUMSOLI_TEXT);
        
        ArrayList claves = new ArrayList<Integer>();
        claves.add(0, id_cat);
        claves.add(1, numSoli);
        
        Bienes_por_SolicitudDTO bienPorSol = bienesDAO.buscar(claves);
        
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
        Validador.validarId(dto.getNumSolicitud(), CAMPO_NUMSOLI_TEXT);
        
        Validador.validarId(dto.getCantidad(), CAMPO_CANTIDAD_TEXT); //cantidad tiene que ser mayor o igual a 1 
        bienesDAO.insertar(dto);
    }

    @Override
    public void modificar(Bienes_por_SolicitudDTO dto, ArrayList<Integer> claves) throws NoSuchElementException {
        int id_cat = 0;
        int numSoli = 1;
        this.buscar(claves);
        
        Validador.validarId(dto.getCantidad(), CAMPO_CANTIDAD_TEXT);
        
        dto.setID_Categoria(claves.get(id_cat));
        dto.setNumSolicitud(claves.get(numSoli));
        bienesDAO.actualizar(dto);
    }

    @Override
    public void eliminar(ArrayList<Integer> claves) throws NoSuchElementException {

        this.buscar(claves);
        
        bienesDAO.eliminar(claves);
    }
    
}

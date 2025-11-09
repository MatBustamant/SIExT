
package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.ResponsableDAOImpl;
import com.grupocapa8.siext.DTO.ResponsableDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author oveja
 */
public class ResponsableService implements ServiceGenerico<ResponsableDTO> {
    
    private final ResponsableDAOImpl responsableDAO; //acceso a la BD

    private final static String CAMPO_ID_TEXT = "Legajo";
    private final static String CAMPO_NOMBRE_TEXT = "Nombre y Apellido";
    private static final int CAMPO_NOMBRE_MIN = 3;
    private static final int CAMPO_NOMBRE_MAX = 50;
    
    public ResponsableService(){
        this.responsableDAO = new ResponsableDAOImpl();
    }
    
    @Override
    public void eliminar(int legajo) throws NoSuchElementException {
        this.buscar(legajo);

        responsableDAO.eliminar(legajo);    
    }

    @Override
    public void crear(ResponsableDTO dto) {
        String nombre = dto.getNombre_apellido().trim().toUpperCase();
        Validador.validarString(nombre, CAMPO_NOMBRE_TEXT, CAMPO_NOMBRE_MIN, CAMPO_NOMBRE_MAX);
        
        dto.setNombre_apellido(nombre);
        
        if (responsableDAO.buscar(dto.getLegajo()) != null) {
            throw new IllegalArgumentException("Ya existe un responsable con el legajo " + dto.getLegajo());
        }
        
        responsableDAO.insertar(dto);
    }

    @Override
    public void modificar(ResponsableDTO dto, int legajo) throws NoSuchElementException {
        this.buscar(legajo);

        String nombre = dto.getNombre_apellido().trim().toUpperCase();
        Validador.validarString(nombre, CAMPO_NOMBRE_TEXT, CAMPO_NOMBRE_MIN, CAMPO_NOMBRE_MAX);

        dto.setNombre_apellido(nombre);
        dto.setLegajo(legajo);

        responsableDAO.actualizar(dto);
    }

    @Override
    public ResponsableDTO buscar(int legajo) throws NoSuchElementException {
        return this.buscar(legajo, true);
    }
    
    public ResponsableDTO buscar(int legajo, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(legajo, CAMPO_ID_TEXT);
        ResponsableDTO responsable = responsableDAO.buscar(legajo);
        if (responsable == null || (checkEliminado && responsable.isEliminado())) {
            throw new NoSuchElementException("No existe el responsable con legajo " + legajo);
        }
        return responsable; //enviando el dto a la capa de presentacion para que lo muestre
    }
    
    
    
    @Override
    public List<ResponsableDTO> buscarTodos() {
        return responsableDAO.buscarTodos();
    }
    
}

package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class UbicacionService implements ServiceGenerico<UbicacionDTO>{
    private final UbicacionDAOImpl ubicacionDAO; //acceso a la BD
    
    private final static String CAMPO_ID_TEXT = "Identificador";
    private final static String CAMPO_NOMBRE_TEXT = "Nombre";
    private static final int CAMPO_NOMBRE_MIN = 3;
    private static final int CAMPO_NOMBRE_MAX = 50;

    public UbicacionService() {
        this.ubicacionDAO = new UbicacionDAOImpl();
    }
    
    @Override
    public UbicacionDTO buscar(int idUbicacion) throws NoSuchElementException {
        return this.buscar(idUbicacion, true);
    }
    
    public UbicacionDTO buscar(int idUbicacion, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(idUbicacion, CAMPO_ID_TEXT);
        UbicacionDTO ubicacion = ubicacionDAO.buscar(idUbicacion);
        if (ubicacion == null || (checkEliminado && ubicacion.isEliminado())){
            throw new NoSuchElementException("No existe la ubicación.");
        }
        return ubicacion;
    }
    
    @Override
    public List<UbicacionDTO> buscarTodos() {
        return ubicacionDAO.buscarTodos();
    }
    
    @Override
    public void crear(UbicacionDTO dto){
        String nombre = dto.getNombre().trim().toUpperCase();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        
        dto.setNombre(nombre);

        ubicacionDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(UbicacionDTO dto, int id) throws NoSuchElementException {
        this.buscar(id);
        
        String nombre = dto.getNombre().trim().toUpperCase();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        
        dto.setNombre(nombre);
        dto.setID_Ubicacion(id);
       
        ubicacionDAO.actualizar(dto);
    } 
   
    @Override
    public void eliminar(int idUbicacion) throws NoSuchElementException {
        this.buscar(idUbicacion);
        
        ubicacionDAO.eliminar(idUbicacion);
    }
    
}

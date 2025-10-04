package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class UbicacionService implements ServiceGenerico<UbicacionDTO>{
    private final UbicacionDAOImpl ubicacionDAO; //acceso a la BD

    public UbicacionService() {
        this.ubicacionDAO = new UbicacionDAOImpl();
    }
    
    @Override
    public UbicacionDTO buscar(int idUbicacion) throws NoSuchElementException {
        validarID(idUbicacion);
        UbicacionDTO ubicacion = ubicacionDAO.buscar(idUbicacion);
        if (ubicacion == null){
            throw new NoSuchElementException("No existe la ubicación");
        }
        return ubicacion;
    }
    
    @Override
    public List<UbicacionDTO> buscarTodos() {
        return ubicacionDAO.buscarTodos();
    }
    
    @Override
    public void crear(UbicacionDTO dto){
        validarString(dto.getNombre(),1);

        ubicacionDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(UbicacionDTO dto, int id) throws NoSuchElementException {
        validarID(id);
        if (ubicacionDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe la ubicación");
        }
        validarString(dto.getNombre(),1);
       
        ubicacionDAO.actualizar(dto);
    } 
   
    @Override
    public void eliminar(int idUbicacion) throws NoSuchElementException {
        validarID(idUbicacion);
        if (ubicacionDAO.buscar(idUbicacion) == null){
            throw new NoSuchElementException("No existe la ubicación");
        }
        ubicacionDAO.eliminar(idUbicacion);
    } 
  
    private void validarID(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número entero positivo.");
        }
    }
    private void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
}

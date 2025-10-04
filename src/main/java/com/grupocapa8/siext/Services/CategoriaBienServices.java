package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.CategoriaDAOImpl;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class CategoriaBienServices implements ServiceGenerico<CategoriaBienDTO>{
    private final CategoriaDAOImpl categoriaBienDAO; //acceso a la BD

    public CategoriaBienServices() {
        this.categoriaBienDAO = new CategoriaDAOImpl();
    }
    
    @Override
    public CategoriaBienDTO buscar(int idCategoriaBien) throws NoSuchElementException {
        validarID(idCategoriaBien);
        CategoriaBienDTO categoria = categoriaBienDAO.buscar(idCategoriaBien);
        if (categoria == null){
            throw new NoSuchElementException("No existe la categoria");
        }
        return categoria;
    }
    
    @Override
    public List<CategoriaBienDTO> buscarTodos() {
        return categoriaBienDAO.buscarTodos();
    }
    
    @Override
    public void crear(CategoriaBienDTO dto){
        String nombre = dto.getNombre().trim().toUpperCase();
        validarString(nombre,1);
        
        dto.setNombre(nombre);

        categoriaBienDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(CategoriaBienDTO dto, int id) throws NoSuchElementException {
        validarID(id);
        if (categoriaBienDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe la categoria");
        }
        String nombre = dto.getNombre().trim().toUpperCase();
        validarString(dto.getNombre(),1);
        
        dto.setNombre(nombre);
        dto.setID_Categoria(id);
       
        categoriaBienDAO.actualizar(dto);
    } 
   
    @Override
    public void eliminar(int idCategoriaBien) throws NoSuchElementException {
        validarID(idCategoriaBien);
        if (categoriaBienDAO.buscar(idCategoriaBien) == null){
            throw new NoSuchElementException("No existe la categoria");
        }
        categoriaBienDAO.eliminar(idCategoriaBien);
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

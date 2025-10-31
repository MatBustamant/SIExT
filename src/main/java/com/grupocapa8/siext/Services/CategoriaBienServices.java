package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.CategoriaDAOImpl;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class CategoriaBienServices implements ServiceGenerico<CategoriaBienDTO>{
    private final CategoriaDAOImpl categoriaBienDAO; //acceso a la BD
    
    private final static String CAMPO_ID_TEXT = "Identificador";
    private static final String CAMPO_NOMBRE_TEXT = "Nombre";
    private static final int CAMPO_NOMBRE_MIN = 3;
    private static final int CAMPO_NOMBRE_MAX = 50;

    public CategoriaBienServices() {
        this.categoriaBienDAO = new CategoriaDAOImpl();
    }
    
    @Override
    public CategoriaBienDTO buscar(int idCategoriaBien) throws NoSuchElementException {
        Validador.validarId(idCategoriaBien, CAMPO_ID_TEXT);
        CategoriaBienDTO categoria = categoriaBienDAO.buscar(idCategoriaBien);
        if (categoria == null){
            throw new NoSuchElementException("No existe la categoría.");
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
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        
        dto.setNombre(nombre);

        categoriaBienDAO.insertar(dto);
    } 
    
    @Override
    public void modificar(CategoriaBienDTO dto, int id) throws NoSuchElementException {
        Validador.validarId(id, CAMPO_ID_TEXT);
        if (categoriaBienDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe la categoría.");
        }
        String nombre = dto.getNombre().trim().toUpperCase();
        Validador.validarString(dto.getNombre(),CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        
        dto.setNombre(nombre);
        dto.setID_Categoria(id);
       
        categoriaBienDAO.actualizar(dto);
    } 
   
    @Override
    public void eliminar(int idCategoriaBien) throws NoSuchElementException {
        Validador.validarId(idCategoriaBien, CAMPO_ID_TEXT);
        if (categoriaBienDAO.buscar(idCategoriaBien) == null){
            throw new NoSuchElementException("No existe la categoría.");
        }
        categoriaBienDAO.eliminar(idCategoriaBien);
    }
    
}

package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.BienDAOImpl;
import com.grupocapa8.siext.DAO.CategoriaDAOImpl;
import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class BienService implements ServiceGenerico<BienDTO> {
    private final BienDAOImpl bienDAO; //acceso a la BD
    private final CategoriaDAOImpl catDAO;

    public BienService() {
        this.bienDAO = new BienDAOImpl();
        this.catDAO = new CategoriaDAOImpl();
    }
    
    @Override
    public BienDTO buscar(int idBien) throws NoSuchElementException {
        validarID(idBien);
        BienDTO bien = bienDAO.buscar(idBien);
        if (bien == null){
            throw new NoSuchElementException("No existe el Bien");
        }
        return bien;
    }
    
    @Override
    public List<BienDTO> buscarTodos() {
        return bienDAO.buscarTodos();
    }
    
    @Override
    public void crear(BienDTO dto){
        // Validamos solo que debemos recibir
        String nombre = dto.getNombre().toUpperCase();
        validarString(nombre,1);
        String categoria = dto.getNombreCatBienes().toUpperCase();
        validarString(categoria,1);
        String ubcacion = dto.getUbicacionBien().toUpperCase();
        validarUbicacionBien(dto.getUbicacionBien());
        
        // Una vez validado, dejamos en el dto los datos formateados y agregamos el resto
        dto.setNombre(nombre);
        dto.setNombreCatBienes(categoria);
        dto.setUbicacionBien(ubcacion);
        dto.setEstadoBien("EN CONDICIONES");
        
        // Si no existe la categoría, la creamos
        if (catDAO.buscar(categoria) == null) {
            CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria);
            catDAO.insertar(nuevaCategoria);
        }
        
        // Creamos la cantidad de bienes que nos pide el front
        for (int i = 0; i < dto.getCantBienes(); i++) {
            bienDAO.insertar(dto);
        }
    } 
    
    @Override
    public void modificar(BienDTO dto, int id) throws NoSuchElementException {
        validarID(id);
        if (bienDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe el Bien");
        }
        String nombre = dto.getNombre().toUpperCase();
        validarString(nombre,1);
        String categoria = dto.getNombreCatBienes();
        validarString(categoria,1);
        String ubicacion = dto.getUbicacionBien();
        validarUbicacionBien(ubicacion);
        
        dto.setNombre(nombre);
        dto.setNombreCatBienes(categoria);
        dto.setUbicacionBien(ubicacion);
        dto.setID_Bien(id);
        
        // Si no existe la categoría, la creamos
        if (catDAO.buscar(categoria) == null) {
            CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria);
            catDAO.insertar(nuevaCategoria);
        }
       
        bienDAO.actualizar(dto);
    } 
   
    @Override
    public void eliminar(int idBien) throws NoSuchElementException {
        validarID(idBien);
        if (bienDAO.buscar(idBien) == null){
            throw new NoSuchElementException("No existe el Bien");
        }
        bienDAO.eliminar(idBien);
    } 
    
    private void validarUbicacionBien(String ubi) {
        if (ubi== null || ubi.length() < 3 || ubi.length() > 80) {
            throw new IllegalArgumentException("La ubicacion debe tener entre 3 y 80 caracteres");
        }
    }
    private void validarID(Integer id) throws IllegalArgumentException{
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
                case 2 -> throw new IllegalArgumentException("El estado del bien no debe estar vacio");
                case 3 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }

}

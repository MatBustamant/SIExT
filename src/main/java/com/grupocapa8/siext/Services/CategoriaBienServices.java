package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.CategoriaBienDTO;

/**
 *
 * @author geroj
 */
public class CategoriaBienServices {
    private CategoriaBienDAO categoriaBienDAO; //acceso a la BD
    
    public void lecturaDTOs(){
        int idCategoriaBien = 0;
        while(categoriaBienDAO.BuscarCategoriaBien(idCategoriaBien)){
            CategoriaBienDTO dto = categoriaBienDAO.obtenerCategoriaBien(idCategoriaBien);
            recibirCategoriaBienDTO(dto);
            idCategoriaBien = idCategoriaBien + 1;    
        }
        if(idCategoriaBien == 0){
            System.out.println("No Existen Categoria de Bienes almacenados en BD");
        }
        
        
         
    }
    public void BuscarDto(int idCategoriaBien){
        validarID(idCategoriaBien);
        if (!categoriaBienDAO.BuscarCategoriaBien(idCategoriaBien)){
            throw new IllegalArgumentException("No existe la categoria del Bien");
        }
        CategoriaBienDTO dto = categoriaBienDAO.obtenerCategoriaBien(idCategoriaBien);
        recibirCategoriaBienDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre
    }
    public void crearCategoriaBien(CategoriaBienDTO dto){
        validarString(dto.getNombre(),1);
        validarStock(dto.getStock(),dto.getMinReposicion());

        categoriaBienDAO.guardar(dto);
    } 
    
    public void modificarCategoriaBien(int idCategoriaBien){
        validarID(idCategoriaBien);
        if (!categoriaBienDAO.BuscarCategoriaBien(idCategoriaBien)){
            throw new IllegalArgumentException("No existe la categoria");
        }
        CategoriaBienDTO dto = categoriaBienDAO.obtenerCategoriaBien(idCategoriaBien);
        dto = recibirCategoriaBienDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarString(dto.getNombre(),1);
        validarStock(dto.getStock(),dto.getMinReposicion());
       
        categoriaBienDAO.guardar(dto);
    } 
   
    public void eliminarCategoriaBien(int idCategoriaBien, String rol){
        validarID(idCategoriaBien);
        validarString(rol,2);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar categorias");
        }
        if (!categoriaBienDAO.BuscarCategoriaBien(idCategoriaBien)){
            throw new IllegalArgumentException("No existe la categoria");
        }
        
        CategoriaBienDTO dto = categoriaBienDAO.obtenerCategoriaBien(idCategoriaBien);
        boolean V = confirmacionEliminarCategoriaBien(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            categoriaBienDAO.eliminarCategoriaBien(idCategoriaBien);
        }   
    } 
    public void validarStock(Integer stock, Integer minimoReposicion){
        if (stock == null) {
            throw new IllegalArgumentException("El stock no puede ser nulo.");
        }
        if (minimoReposicion == null) {
            throw new IllegalArgumentException("El mínimo de reposición no puede ser nulo.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (minimoReposicion < 0) {
            throw new IllegalArgumentException("El mínimo de reposición no puede ser negativo.");
        }
        if (minimoReposicion > stock) {
            System.out.println("⚠️ Advertencia: el stock actual está por debajo del mínimo de reposición.");
        }
    }
    public void validarID(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número entero positivo.");
        }
    }
    public void validarString(String string,int a) {
        if (string == null || string.length() < 3 || string.length() > 50) {
            switch (a){
                case 1 -> throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
}

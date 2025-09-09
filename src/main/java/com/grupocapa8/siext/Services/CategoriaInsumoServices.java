package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.CategoriaInsumoDTO;

/**
 *
 * @author geroj
 */
public class CategoriaInsumoServices {
  private CategoriaInsumoDAO categoriaInsumoDAO; //acceso a la BD
    
    public void lecturaDTOs(){
        int idCategoriaInsumoDTO = 0;
        while(categoriaInsumoDAO.BuscarCategoriaInsumo(idCategoriaInsumoDTO)){
            CategoriaInsumoDTO dto = categoriaInsumoDAO.obtenerCategoriaInsumo(idCategoriaInsumoDTO);
            recibirCategoriaInsumoDTO(dto);
            idCategoriaInsumoDTO = idCategoriaInsumoDTO + 1;    
        }
        if(idCategoriaInsumoDTO == 0){
            System.out.println("No Existen Categoria de Insumos almacenados en BD");
        }
        
        
         
    }
    public void BuscarDto(int idCategoriaInsumoDTO){
        validarID(idCategoriaInsumoDTO);
        if (!categoriaInsumoDAO.BuscarCategoriaInsumo(idCategoriaInsumoDTO)){
            throw new IllegalArgumentException("No existe la categoria del Insumo");
        }
        CategoriaInsumoDTO dto = categoriaInsumoDAO.obtenerCategoriaInsumo(idCategoriaInsumoDTO);
        recibirCategoriaInsumoDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre
    }
  
  
    public void crearCategoriaInsumoDTO(CategoriaInsumoDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getClaseInsumo(),2);
        
        categoriaInsumoDAO.guardar(dto);
    } 
    
    public void modificarCategoriaInsumoDTO(int idCategoriaInsumoDTO){
        validarID(idCategoriaInsumoDTO);
        if (!categoriaInsumoDAO.BuscarCategoriaInsumo(idCategoriaInsumoDTO)){
            throw new IllegalArgumentException("No existe la categoria");
        }
        CategoriaInsumoDTO dto = categoriaInsumoDAO.obtenerCategoriaInsumo(idCategoriaInsumoDTO);
        dto = recibirCategoriaInsumoDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarString(dto.getNombre(),1);
        validarString(dto.getClaseInsumo(),2);
       
        categoriaInsumoDAO.guardar(dto);
    } 
   
    public void eliminarCategoriaInsumoDTO(int idCategoriaInsumoDTO, String rol){
        validarID(idCategoriaInsumoDTO);
        validarString(rol,3);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar categorias de insumos");
        }
        if (!categoriaInsumoDAO.BuscarCategoriaInsumo(idCategoriaInsumoDTO)){
            throw new IllegalArgumentException("No existe la categoria de insumos");
        }
        
        CategoriaInsumoDTO dto = categoriaInsumoDAO.obtenerCategoriaInsumo(idCategoriaInsumoDTO);
        boolean V = confirmacionEliminarCategoriaInsumo(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            categoriaInsumoDAO.eliminarCategoriaInsumo(idCategoriaInsumoDTO);
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
                case 2 -> throw new IllegalArgumentException("La clase de insumo no debe estar vacia");
                case 3 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }  
}

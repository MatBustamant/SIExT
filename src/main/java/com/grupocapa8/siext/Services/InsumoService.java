package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.InsumoDTO;

/**
 *
 * @author geroj
 */
public class InsumoService{
    private InsumoDAO insumoDAO; //acceso a la BD
    
    public void crearInsumo(InsumoDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatInsumo(),1);
        validarStock(dto.getStock(),dto.getMinReposicion());
        validarString(dto.getClaseProducto(),2);
        
        insumoDAO.guardar(dto);
    } 
    
    public void modificarInsumo(int idInsumo){
        validarID(idInsumo);
        if (!insumoDAO.BuscarInsumo(idInsumo)){
            throw new IllegalArgumentException("No existe el insumo");
        }
        InsumoDTO dto = insumoDAO.obtenerInsumo(idInsumo);
        dto = recibirInsumoDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatInsumo(),1);
        validarStock(dto.getStock(),dto.getMinReposicion());
        validarString(dto.getClaseProducto(),2);
       
        insumoDAO.guardar(dto);
    } 
   
    public void eliminarInsumo(int idInsumo, String rol){
        validarID(idInsumo);
        validarString(rol,3);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar insumos");
        }
        if (!insumoDAO.BuscarInsumo(idInsumo)){
            throw new IllegalArgumentException("No existe el Insumo");
        }
        
        InsumoDTO dto = insumoDAO.obtenerInsumo(idInsumo);
        boolean V = confirmacionEliminarInsumo(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            insumoDAO.eliminarInsumo(idInsumo);
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
                case 2 -> throw new IllegalArgumentException("La clase de producto no debe estar vacia");
                case 3 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
}
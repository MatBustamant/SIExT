package com.grupocapa8.siext.DTO.Services;

/**
 *
 * @author geroj
 */
public class UsuarioService {
    private ProductoDao UsuarioDAO; //acceso a la BD
    
    public void crearUsuario(UsuarioDTO dto){
        validarNombre(dto.getNombre());
        validarNombre(dto.getNombreCatBien());
        if (dto.getClaseProducto()== null || dto.getClaseProducto().isBlank()){
            throw new IllegalArgumentException("La clase del producto es obligatoria, no debe estar vacio");
        }
        if (dto.getEstadoBien()== null || dto.getEstadoBien().isBlank()){
            throw new IllegalArgumentException("El estado del bien es obligatoria, no debe estar vacio");
        }
        if (dto.getUbicacionBien()== null ||dto.getUbicacionBien().isBlank()){
             throw new IllegalArgumentException("La ubicacion del bien es obligatoria, no debe estar vacio");
        }
        if (dto.getUbicacionBien().length() < 3 || dto.getUbicacionBien().length() > 80) {
            throw new IllegalArgumentException("La ubicacion debe tener entre 3 y 80 caracteres");
        }
    } 
    public void validarNombre(String nombre) {
        // 1. No nulo ni vacío
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio, no debe estar vacio.");
        }

        // 2. Longitud
        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
        }


    }
}

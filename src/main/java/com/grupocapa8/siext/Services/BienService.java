package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.BienDTO;

/**
 *
 * @author geroj
 */
public class BienService {
    private BienDAO bienDAO; //acceso a la BD
    
    public void crearBien(BienDTO dto){
        validarNombre(dto.getNombre());
        validarNombre(dto.getNombreCatBienes());
        validarUbicacionBien(dto.getUbicacionBien());
        claseProducto(dto.getClaseProducto());
        // Convertir DTO a entidad y guardarlo en BD
        ConvertirGuardarDTO(dto);
    } 
    
    public void modificarBien(int idBien){
        if (!bienDAO.BuscarBien(idBien)){
            throw new IllegalArgumentException("No existe el Bien");
        }
        BienDTO dto = bienDAO.obtenerBien(idBien);
        dto = recibirBienDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarNombre(dto.getNombre());
        validarNombre(dto.getNombreCatBienes());
        validarUbicacionBien(dto.getUbicacionBien());
        claseProducto(dto.getClaseProducto());
        if (dto.getEstadoBien()== null || dto.getEstadoBien().isBlank()){
            throw new IllegalArgumentException("El estado del bien es obligatoria, no debe estar vacio");
        }
        
        // Convertir DTO a entidad y guardarlo en BD
        ConvertirGuardarDTO(dto);
    } 
    public void eliminarBien(int idBien){
        if (!bienDAO.BuscarBien(idBien)){
            throw new IllegalArgumentException("No existe el Bien");
        }
        BienDTO dto = bienDAO.obtenerBien(idBien);
        boolean V = confirmacionEliminarBien(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            bienDAO.eliminarBien(idBien);
        }   
    } 
    public void validarUbicacionBien(String ubi){
        if (ubi== null ||ubi.isBlank()){
             throw new IllegalArgumentException("La ubicacion del bien es obligatoria, no debe estar vacio");
        }
        if (ubi.length() < 3 || ubi.length() > 80) {
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
    public void claseProducto(String CP){
        if (CP== null || CP.isBlank()){
            throw new IllegalArgumentException("La clase del producto es obligatoria, no debe estar vacio");
        }
    }
    public void ConvertirGuardarDTO(BienDTO dto){
        EntidadBien Bien = new EntidadBien();
        Bien.setNombre(dto.getNombre());
        Bien.setClaseProducto(dto.getClaseProducto());
        Bien.setEstadoBien(dto.getEstadoBien());
        Bien.setNombreCatBien(dto.getNombreCatBienes());
        Bien.setUbicacionBien(dto.getUbicacionBien());

        // Guardar en BD
        bienDAO.guardar(Bien);
    }
    
}

package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.BienDTO;

/**
 *
 * @author geroj
 */
public class BienService {
    private BienDAO bienDAO; //acceso a la BD
    
    public void crearBien(BienDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatBienes(),1);
        validarUbicacionBien(dto.getUbicacionBien());
        validarString(dto.getClaseProducto(),2);

        bienDAO.guardar(dto);
    } 
    
    public void modificarBien(int idBien){
        validarID(idBien);
        if (!bienDAO.BuscarBien(idBien)){
            throw new IllegalArgumentException("No existe el Bien");
        }
        BienDTO dto = bienDAO.obtenerBien(idBien);
        dto = recibirBienDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatBienes(),1);
        validarUbicacionBien(dto.getUbicacionBien());
        validarString(dto.getClaseProducto(),2);
        validarString(dto.getEstadoBien(),3);
       
        bienDAO.guardar(dto);
    } 
   
    public void eliminarBien(int idBien, String rol){
        validarID(idBien);
        validarString(rol,4);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar bienes");
        }
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
        if (ubi== null || ubi.length() < 3 || ubi.length() > 80) {
            throw new IllegalArgumentException("La ubicacion debe tener entre 3 y 80 caracteres");
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
                case 3 -> throw new IllegalArgumentException("El estado del bien no debe estar vacio");
                case 4 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }

}

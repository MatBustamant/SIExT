package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.BienDAOImpl;
import com.grupocapa8.siext.DTO.BienDTO;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class BienService {
    private final BienDAOImpl bienDAO; //acceso a la BD

    public BienService() {
        this.bienDAO = new BienDAOImpl();
    }
    
//    public void lecturaDTOs(){
//        int idBien = 0;
//        while(bienDAO.buscar(idBien) != null){
//            BienDTO dto = bienDAO.obtenerBien(idBien);
//            recibirBienDTO(dto);
//            idBien = idBien + 1;    
//        }
//        if(idBien == 0){
//            System.out.println("No Existen Bienes almacenados en BD");
//        }
//        
//        
//         //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
//    }
    
    public BienDTO BuscarDto(int idBien) throws NoSuchElementException {
        validarID(idBien);
        BienDTO bien = bienDAO.buscar(idBien);
        if (bien == null){
            throw new NoSuchElementException("No existe el Bien");
        }
        return bien;
    }
    
    public void crearBien(BienDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatBienes(),1);
        validarUbicacionBien(dto.getUbicacionBien());
        validarString(dto.getEstadoBien(),3);
        
        bienDAO.insertar(dto);
    } 
    
    public void modificarBien(BienDTO dto) throws NoSuchElementException {
        int idBien = dto.getID_Bien();
        validarID(idBien);
        if (bienDAO.buscar(idBien) == null){
            throw new NoSuchElementException("No existe el Bien");
        }
        validarString(dto.getNombre(),1);
        validarString(dto.getNombreCatBienes(),1);
        validarUbicacionBien(dto.getUbicacionBien());
        validarString(dto.getEstadoBien(),3);
       
        bienDAO.actualizar(dto);
    } 
   
    public void eliminarBien(int idBien) throws NoSuchElementException {
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

package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.UsuarioDTO;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author geroj
 */
public class UsuarioService {
   private UsuarioDAO usuarioDAO; //acceso a la BD
   
    public void lecturaDTOs(){
        int idUsuario = 0;
        while(usuarioDAO.BuscarUsuario(idUsuario)){
            UsuarioDTO dto = usuarioDAO.obtenerUsuario(idUsuario);
            recibirUsuarioDTO(dto);
            idUsuario = idUsuario + 1;    
        }
        if(idUsuario == 0){
            System.out.println("No Existe el usuario en la BD");
        }   
    }
    public void BuscarDto(int idUsuario){
        validarID(idUsuario);
        if (!usuarioDAO.BuscarUsuario(idUsuario)){
            throw new IllegalArgumentException("No existe el usuario");
        }
        UsuarioDTO dto = usuarioDAO.obtenerUsuario(idUsuario);
        recibirUsuarioDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre
    }
    public void crearUsuario(UsuarioDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getRol(),2);
        validarContraseña(dto.getContraseña());
        String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
        // Convertir DTO a entidad y guardarlo en BD
        dto.setContraseña(contraseñaHash);
        usuarioDAO.guardar(dto);
    } 
    
    public void modificarUsuario(Integer idUsuario){
        validarID(idUsuario);
        if (!usuarioDAO.BuscarUsuario(idUsuario)){
            throw new IllegalArgumentException("No existe el Usuario");
        }
        UsuarioDTO dto = usuarioDAO.obtenerUsuario(idUsuario);
        UsuarioDTO dto2 = recibirUsuarioDTO(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva el dto modificado
        validarString(dto2.getNombre(),1);
        validarString(dto2.getRol(),2);
        if (!dto2.getContraseña().equals(dto.getContraseña())) {
            validarContraseña(dto2.getContraseña());
            String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
            dto2.setContraseña(contraseñaHash);
        }
        
        // Convertir DTO a entidad y guardarlo en BD
        usuarioDAO.guardar(dto2);
    } 
    public void eliminarUsuario(Integer idUsuario, String rol){
        validarID(idUsuario);
        validarString(rol,2);
        if (!rol.equals("ADMIN")) {
            throw new SecurityException("No tiene permisos para eliminar usuarios");
        }
        if (!usuarioDAO.BuscarBien(idUsuario)){ //hacer una validacion del id
            throw new IllegalArgumentException("No existe el usuario");
        }
        
        UsuarioDTO dto = usuarioDAO.obtenerUsuario(idUsuario);
        boolean V = confirmacionEliminarUsuario(dto); //enviando el dto a la capa de presentacion para que lo muestre y me devuelva verdadero o falso para continuar con la eliminacion
        if(V){
            usuarioDAO.eliminarUsuario(idUsuario);
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
    public void validarContraseña(String contraseña){
        // 1. Longitud mínima y máxima
        if (contraseña==null || contraseña.length() < 8 || contraseña.length() > 64) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 64 caracteres"); 
        }
        // 2. Al menos una mayúscula
        if (!contraseña.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula");
        }

        // 3. Al menos una minúscula
        if (!contraseña.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra minúscula");
        }

        // 4. Al menos un número
        if (!contraseña.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un número");
        }

        // 5. Al menos un signo o símbolo
        if (!contraseña.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un carácter especial");
        }  
    }  
}

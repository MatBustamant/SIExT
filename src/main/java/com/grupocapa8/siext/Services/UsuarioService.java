package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DAO.UsuarioDAOImpl;
import com.grupocapa8.siext.DTO.UsuarioDTO;
import java.util.List;
import java.util.NoSuchElementException;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author geroj
 */
public class UsuarioService implements ServiceGenerico<UsuarioDTO> {
   private final UsuarioDAOImpl usuarioDAO; //acceso a la BD

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
   
//    public void lecturaDTOs(){
//        int idUsuario = 0;
//        while(usuarioDAO.BuscarUsuario(idUsuario)){
//            UsuarioDTO dto = usuarioDAO.obtenerUsuario(idUsuario);
//            recibirUsuarioDTO(dto);
//            idUsuario = idUsuario + 1;    
//        }
//        if(idUsuario == 0){
//            System.out.println("No Existe el usuario en la BD");
//        }   
//    }
    
   @Override
    public UsuarioDTO buscar(int idUsuario) throws NoSuchElementException {
        validarID(idUsuario);
        UsuarioDTO usuario = usuarioDAO.buscar(idUsuario);
        
        if (usuario == null){
            throw new NoSuchElementException("No existe el usuario");
        }
        
        return usuario; //enviando el dto a la capa de presentacion para que lo muestre
    }
    
    @Override
    public List<UsuarioDTO> buscarTodos() {
        return usuarioDAO.buscarTodos();
    }
    
   @Override
    public void crear(UsuarioDTO dto){
        validarString(dto.getNombre(),1);
        validarString(dto.getRol(),2);
        validarContraseña(dto.getContraseña());
        String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
        // Convertir DTO a entidad y guardarlo en BD
        dto.setContraseña(contraseñaHash);
        usuarioDAO.insertar(dto);
    }
    
   @Override
    public void modificar(UsuarioDTO dto) throws NoSuchElementException {
        int idUsuario = dto.getID_Usuario();
        validarID(idUsuario);
        if (usuarioDAO.buscar(idUsuario) == null){
            throw new NoSuchElementException("No existe el Usuario");
        }
        validarString(dto.getNombre(),1);
        validarString(dto.getRol(),2);
        validarContraseña(dto.getContraseña());
        String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
        dto.setContraseña(contraseñaHash);
        
        // Convertir DTO a entidad y guardarlo en BD
        usuarioDAO.actualizar(dto);
    }
    
   @Override
    public void eliminar(int idUsuario) throws NoSuchElementException {
        validarID(idUsuario);
        if (usuarioDAO.buscar(idUsuario) == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe el usuario");
        }
        
        usuarioDAO.eliminar(idUsuario);
    } 
    
    private void validarID(Integer id){
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
                case 2 -> throw new IllegalArgumentException("El Rol debe tener entre 3 y 50 caracteres");
            } 
        }
    }
    
    private void validarContraseña(String contraseña){
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

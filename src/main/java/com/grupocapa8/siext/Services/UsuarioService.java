package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.Enums.RolUsuario;
import com.grupocapa8.siext.DAO.UsuarioDAOImpl;
import com.grupocapa8.siext.DTO.UsuarioDTO;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author geroj
 */
public class UsuarioService implements ServiceGenerico<UsuarioDTO> {
   private final UsuarioDAOImpl usuarioDAO; //acceso a la BD
   
    private final static String CAMPO_ID_TEXT = "Identificador";
    private final static String CAMPO_NOMBRE_TEXT = "Nombre";
    private static final int CAMPO_NOMBRE_MIN = 3;
    private static final int CAMPO_NOMBRE_MAX = 50;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
   @Override
    public UsuarioDTO buscar(int idUsuario) throws NoSuchElementException {
        Validador.validarId(idUsuario, CAMPO_ID_TEXT);
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
        // Validamos solo que debemos recibir
        String nombre = dto.getNombre();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT, CAMPO_NOMBRE_MIN, CAMPO_NOMBRE_MAX);
        RolUsuario rol = dto.getRol();
//        validarString(rol,2);
        Validador.validarContraseña(dto.getContraseña());
        String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
        // Convertir DTO a entidad y guardarlo en BD
        dto.setNombre(nombre);
        dto.setRol(rol);
        dto.setContraseña(contraseñaHash);
        
        usuarioDAO.insertar(dto);
    }
    
   @Override
    public void modificar(UsuarioDTO dto, int id) throws NoSuchElementException {
        Validador.validarId(id, CAMPO_ID_TEXT);
        if (usuarioDAO.buscar(id) == null){
            throw new NoSuchElementException("No existe el Usuario");
        }
        String nombre = dto.getNombre();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT, CAMPO_NOMBRE_MIN, CAMPO_NOMBRE_MAX);
        RolUsuario rol = dto.getRol();
//        validarString(dto.getRol(),2);
        Validador.validarContraseña(dto.getContraseña());
        String contraseñaHash = BCrypt.hashpw(dto.getContraseña(), BCrypt.gensalt());
        
        dto.setNombre(nombre);
        dto.setRol(rol);
        dto.setContraseña(contraseñaHash);
        dto.setID_Usuario(id);
        
        // Convertir DTO a entidad y guardarlo en BD
        usuarioDAO.actualizar(dto);
    }
    
   @Override
    public void eliminar(int idUsuario) throws NoSuchElementException {
        Validador.validarId(idUsuario, CAMPO_ID_TEXT);
        if (usuarioDAO.buscar(idUsuario) == null){ //hacer una validacion del id
            throw new NoSuchElementException("No existe el usuario");
        }
        
        usuarioDAO.eliminar(idUsuario);
    }
    
}

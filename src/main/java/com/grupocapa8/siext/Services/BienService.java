package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.Enums.EstadoBien;
import com.grupocapa8.siext.DAO.BienDAOImpl;
import com.grupocapa8.siext.DAO.CategoriaDAOImpl;
import com.grupocapa8.siext.DAO.EventoTrazabilidadDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.BienDTO;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
import com.grupocapa8.siext.DTO.EventoTrazabilidadDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Enums.TipoEvento;
import com.grupocapa8.siext.Util.Validador;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author geroj
 */
public class BienService implements ServiceGenerico<BienDTO> {
    private final BienDAOImpl bienDAO; //acceso a la BD
    private final CategoriaDAOImpl catDAO;
    private final UbicacionDAOImpl ubiDAO;
    private final EventoTrazabilidadDAOImpl evenDAO;
    
    private final static String CAMPO_ID_TEXT = "Identificador";
    private final static String CAMPO_NOMBRE_TEXT = "Nombre";
    private static final int CAMPO_NOMBRE_MIN = 3;
    private static final int CAMPO_NOMBRE_MAX = 50;
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 80;
    private final static String CAMPO_CATEGORIA_TEXT = "Categoría";
    private static final int CAMPO_CATEGORIA_MIN = 3;
    private static final int CAMPO_CATEGORIA_MAX = 50;

    public BienService() {
        this.bienDAO = new BienDAOImpl();
        this.catDAO = new CategoriaDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
        this.evenDAO = new EventoTrazabilidadDAOImpl();
    }
    
    @Override
    public BienDTO buscar(int idBien) throws NoSuchElementException {
        return this.buscar(idBien, true);
    }
    
    public BienDTO buscar(int idBien, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(idBien, CAMPO_ID_TEXT);
        BienDTO bien = bienDAO.buscar(idBien);
        if (bien == null || (checkEliminado && bien.isEliminado())){
            throw new NoSuchElementException("No existe el bien.");
        }
        return bien;
    }
    
    @Override
    public List<BienDTO> buscarTodos() {
        return bienDAO.buscarTodos();
    }
    
    @Override
    public void crear(BienDTO dto){
        // Validamos solo que debemos recibir
        String nombre = dto.getNombre().toUpperCase();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        String categoria = dto.getNombreCatBienes().toUpperCase();
        Validador.validarString(categoria,CAMPO_CATEGORIA_TEXT,CAMPO_CATEGORIA_MIN,CAMPO_CATEGORIA_MAX);
        String detalle = dto.getDetalle();
        if (detalle != null) detalle = detalle.toUpperCase();
        
        // Una vez validado, dejamos en el dto los datos formateados y agregamos el resto
        dto.setNombre(nombre);
        dto.setNombreCatBienes(categoria);
        dto.setEstadoBien(EstadoBien.EN_CONDICIONES);
        dto.setDetalle(detalle);
        
        // Si no existe la categoría, la creamos
        if (catDAO.buscar(categoria) == null) {
            CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria, false);
            catDAO.insertar(nuevaCategoria);
        }
        
        // Creamos la cantidad de bienes que nos pide el front
        for (int i = 0; i < dto.getCantBienes(); i++) {
            bienDAO.insertar(dto);
        }
    } 
    
    @Override
    public void modificar(BienDTO dto, int id) throws NoSuchElementException {
        this.buscar(id);
        
        String nombre = dto.getNombre().toUpperCase();
        Validador.validarString(nombre,CAMPO_NOMBRE_TEXT,CAMPO_NOMBRE_MIN,CAMPO_NOMBRE_MAX);
        String categoria = dto.getNombreCatBienes();
        Validador.validarString(categoria,CAMPO_CATEGORIA_TEXT,CAMPO_CATEGORIA_MIN,CAMPO_CATEGORIA_MAX);
        String ubicacion = dto.getUbicacionBien();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT,CAMPO_UBICACION_MIN,CAMPO_UBICACION_MAX);
        String detalle = dto.getDetalle();
        if (detalle != null) detalle = detalle.toUpperCase();
        
        dto.setNombre(nombre);
        dto.setNombreCatBienes(categoria);
        dto.setUbicacionBien(ubicacion);
        dto.setID_Bien(id);
        dto.setDetalle(detalle);
        
        // Si no existe la categoría, la creamos
        if (catDAO.buscar(categoria) == null) {
            CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria, false);
            catDAO.insertar(nuevaCategoria);
        }
        
        // Si no existe la ubicación, la creamos
        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion, false);
            ubiDAO.insertar(nuevaUbicacion);
        }
       
        bienDAO.actualizar(dto);
    }
    
    void averiar(int id) throws NoSuchElementException {
        this.buscar(id, false);
        bienDAO.cambiarEstado(EstadoBien.AVERIADO, id);
    }
    
    void reparar(int id) throws NoSuchElementException {
        this.buscar(id, false);
        bienDAO.cambiarEstado(EstadoBien.EN_CONDICIONES, id);
    }
   
    @Override
    public void eliminar(int idBien) throws NoSuchElementException {
        this.buscar(idBien);
        this.actualizarEstadoEliminado(idBien, true);
        
        EventoTrazabilidadDTO eventoBaja = new EventoTrazabilidadDTO();
        eventoBaja.setBienAsociado(idBien);
        eventoBaja.setTipoEvento(TipoEvento.BAJA);
        
        evenDAO.insertar(eventoBaja);
    }
    
    void actualizarEstadoEliminado(int idBien, boolean eliminar) {
        BienDTO bien = this.buscar(idBien, false);
        if (eliminar) {
            if (!bien.isEliminado()) {
                bienDAO.eliminar(idBien);
            }
        } else {
            if (bien.isEliminado()) {
                bienDAO.rehabilitar(idBien);
            }
        }
    }

}

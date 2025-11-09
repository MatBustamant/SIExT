
package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author oveja
 */
public interface ServiceBienes_Solicitud {
    public Bienes_por_SolicitudDTO buscar(int id_cat, int legajo) throws NoSuchElementException;
    public List<Bienes_por_SolicitudDTO> buscarTodos();
    public void crear(Bienes_por_SolicitudDTO bienesSolicitud);
    public void modificar(Bienes_por_SolicitudDTO bienesSolicitud, int id_cat, int legajo) throws NoSuchElementException;
    public void eliminar(int id_cat, int legajo) throws NoSuchElementException;
}

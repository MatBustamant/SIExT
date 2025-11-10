package com.grupocapa8.siext.Services;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import com.grupocapa8.siext.DAO.Bienes_por_SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.CategoriaDAOImpl;
import com.grupocapa8.siext.DAO.ResponsableDAOImpl;
import com.grupocapa8.siext.Enums.EstadoSolicitud;
import com.grupocapa8.siext.DAO.SolicitudDAOImpl;
import com.grupocapa8.siext.DAO.UbicacionDAOImpl;
import com.grupocapa8.siext.DTO.Bienes_por_SolicitudDTO;
import com.grupocapa8.siext.DTO.CategoriaBienDTO;
import com.grupocapa8.siext.DTO.SolicitudDTO;
import com.grupocapa8.siext.DTO.UbicacionDTO;
import com.grupocapa8.siext.Util.Validador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class SolicitudService implements ServiceGenerico<SolicitudDTO> {

    private final SolicitudDAOImpl solicitudDAO; //acceso a la BD
    private final UbicacionDAOImpl ubiDAO;
    private final CategoriaDAOImpl catDAO;
    private final ResponsableDAOImpl responsableDAO;
    private final ResponsableService responsableService;
    private final Bienes_por_SolicitudDAOImpl bienesDAO;

    private final static String CAMPO_ID_TEXT = "Número de solicitud";
    private final static String CAMPO_UBICACION_TEXT = "Ubicación";
    private final static String CAMPO_LEGAJO_TEXT = "Legajo";
    private final static String CAMPO_CANTIDAD_TEXT = "Cantidad";
    private final static String CAMPO_CATEGORIA_TEXT = "Categoría";
    private static final int CAMPO_UBICACION_MIN = 3;
    private static final int CAMPO_UBICACION_MAX = 50;

    public SolicitudService() {
        this.solicitudDAO = new SolicitudDAOImpl();
        this.ubiDAO = new UbicacionDAOImpl();
        this.responsableDAO = new ResponsableDAOImpl();
        this.bienesDAO = new Bienes_por_SolicitudDAOImpl();
        this.responsableService = new ResponsableService();
        this.catDAO = new CategoriaDAOImpl();
    }

    @Override
    public SolicitudDTO buscar(int numSolicitud) throws NoSuchElementException {
        return this.buscar(numSolicitud, true);
    }

    public SolicitudDTO buscar(int numSolicitud, boolean checkEliminado) throws NoSuchElementException {
        Validador.validarId(numSolicitud, CAMPO_ID_TEXT);
        SolicitudDTO solicitud = solicitudDAO.buscar(numSolicitud);
        if (solicitud == null || (checkEliminado && solicitud.isEliminado())) {
            throw new NoSuchElementException("No existe la solicitud.");
        }
        return solicitud;
    }

    @Override
    public List<SolicitudDTO> buscarTodos() {
        return solicitudDAO.buscarTodos();
    }

    @Override
    public void crear(SolicitudDTO dto) {
        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
        Validador.validarDestinoSolicitud(ubicacion, CAMPO_UBICACION_TEXT);

        responsableService.buscar(dto.getSolicitante());

        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion, false);
            ubiDAO.insertar(nuevaUbicacion);
        }

        if (dto.getBienesPedidos() == null || dto.getBienesPedidos().isEmpty()) {
            throw new IllegalArgumentException("La solicitud debe pedir al menos un bien.");
        } //valida los datos de los bienes
        for (Bienes_por_SolicitudDTO bien : dto.getBienesPedidos()) {
            String categoria = bien.getCategoria();
            Validador.validarString(categoria, CAMPO_CATEGORIA_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
            if (catDAO.buscar(categoria) == null) {
                CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria, false);
                catDAO.insertar(nuevaCategoria);
            }
            Validador.validarId(bien.getCantidad(), CAMPO_CANTIDAD_TEXT); // Asumimos > 0
        }

        dto.setEstado(EstadoSolicitud.POR_REVISAR);
        dto.setUbicacionBienes(ubicacion);
        // Convertir DTO a entidad y guardarlo en BD
        solicitudDAO.insertar(dto);
    }

    @Override
    public void modificar(SolicitudDTO dto, int id) throws NoSuchElementException {

        SolicitudDTO solicitudActual = this.buscar(id);
        EstadoSolicitud estadoActual = solicitudActual.getEstado();
        EstadoSolicitud estadoNuevo = dto.getEstado();

        String ubicacion = dto.getUbicacionBienes().toUpperCase();
        Validador.validarString(ubicacion, CAMPO_UBICACION_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
        Validador.validarDestinoSolicitud(ubicacion, CAMPO_UBICACION_TEXT);

        for (Bienes_por_SolicitudDTO bien : dto.getBienesPedidos()) {
            String categoria = bien.getCategoria();
            Validador.validarString(categoria, CAMPO_CATEGORIA_TEXT, CAMPO_UBICACION_MIN, CAMPO_UBICACION_MAX);
            if (catDAO.buscar(categoria) == null) {
                CategoriaBienDTO nuevaCategoria = new CategoriaBienDTO(0, categoria, false);
                catDAO.insertar(nuevaCategoria);
            }
            Validador.validarId(bien.getCantidad(), CAMPO_CANTIDAD_TEXT); // Asumimos > 0
        }

        // si está desaprobada o satisfecha, no se pueden cambiar los datos. Solo el estado.
        if ((estadoActual == EstadoSolicitud.DESAPROBADA || estadoActual == EstadoSolicitud.SATISFECHA) && estadoActual == estadoNuevo) {
            throw new IllegalArgumentException("No se puede modificar los datos de una solicitud ya " + estadoActual.name());
        } else if ((estadoActual == EstadoSolicitud.DESAPROBADA || estadoActual == EstadoSolicitud.SATISFECHA) && estadoNuevo != EstadoSolicitud.POR_REVISAR) {
            throw new IllegalArgumentException("La solicitud solo puede volver a revisión.");
        }
        // si el estado es aprobada, sólo puede cambiar a satisfecha o volver a revisión
        if (estadoActual == EstadoSolicitud.APROBADA && estadoActual != estadoNuevo) {
            if (estadoNuevo != EstadoSolicitud.SATISFECHA && estadoNuevo != EstadoSolicitud.POR_REVISAR) {
                throw new IllegalArgumentException("Una solicitud ya aprobada no puede rechazarse sin antes volver a revisión.");
            }
        } else if (estadoActual == EstadoSolicitud.APROBADA && estadoActual == estadoNuevo) {
            throw new IllegalArgumentException("Una solicitud ya aprobada no puede modificar su contenido sin antes volver a revisión.");
        }
        // si el estado es en revision, solo puede cambiar a aprobada o desaprobada.
        if (estadoActual == EstadoSolicitud.POR_REVISAR && estadoNuevo == EstadoSolicitud.SATISFECHA) {
            throw new IllegalArgumentException("No puede satisfacerse una solicitud sin antes ser aprobada.");
        }

        dto.setUbicacionBienes(ubicacion);
        dto.setNumSolicitud(id);

        responsableService.buscar(dto.getSolicitante());

        if (ubiDAO.buscar(ubicacion) == null) {
            UbicacionDTO nuevaUbicacion = new UbicacionDTO(0, ubicacion, false);
            ubiDAO.insertar(nuevaUbicacion);
        }

        // si las lista de bienes no son iguales se elimina y recrea
        List<Bienes_por_SolicitudDTO> bienesActuales = bienesDAO.buscarPorSolicitud(id);
        List<Bienes_por_SolicitudDTO> bienesNuevos = dto.getBienesPedidos();
        Connection con = null;
        try {
            con = BasedeDatos.getConnection();
            con.setAutoCommit(false); // <-- INICIO DE TRANSACCIÓN

            if (bienesActuales.size() != bienesNuevos.size() || !bienesActuales.containsAll(bienesNuevos)) {
                bienesDAO.reemplazarPorSolicitud(id, bienesNuevos, con);
            }
            solicitudDAO.actualizar(dto, con);

            con.commit(); // <-- FIN DE TRANSACCIÓN
        } catch (SQLException ex) {
            System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            try {
                if (con != null) {
                    con.rollback(); // <-- DESHACER CAMBIOS (FALLO)
                }
            } catch (SQLException eRollback) {
                System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, "Error al hacer rollback", eRollback);
            }
        } finally {
            // --- MANEJO DE CONEXIÓN CORREGIDO ---
            // Esto es CRÍTICO para no bloquear la base de datos.
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar estado original
                    con.close();             // Liberar la conexión
                }
            } catch (SQLException eClose) {
                System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, "Error al cerrar la conexión", eClose);
            }
        }
    }

    @Override
    public void eliminar(int numSolicitud) throws NoSuchElementException {
        this.buscar(numSolicitud);
        Connection con = null;
        try {
            con = BasedeDatos.getConnection();
            con.setAutoCommit(false); // <-- INICIO DE TRANSACCIÓN

            // 3. Eliminar hijos
            bienesDAO.eliminarPorSolicitud(numSolicitud, con);

            // 4. Eliminar padre
            solicitudDAO.eliminar(numSolicitud, con);

            con.commit(); // <-- FIN DE TRANSACCIÓN
        } catch (SQLException ex) {
            System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            try {
                if (con != null) {
                    con.rollback(); // <-- DESHACER CAMBIOS (FALLO)
                }
            } catch (SQLException eRollback) {
                System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, "Error al hacer rollback", eRollback);
            }
        } finally {
            // --- MANEJO DE CONEXIÓN CORREGIDO ---
            // Esto es CRÍTICO para no bloquear la base de datos.
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar estado original
                    con.close();             // Liberar la conexión
                }
            } catch (SQLException eClose) {
                System.getLogger(SolicitudService.class.getName()).log(System.Logger.Level.ERROR, "Error al cerrar la conexión", eClose);
            }
        }
    }
}

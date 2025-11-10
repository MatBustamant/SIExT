// constantes y funciones helpers para las del crud
const API_BASE = "http://localhost:8080/api";

function POST(bien) {
    return {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bien)
    };
}

function PUT(bien) {
    return {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bien)
    };
}

const DELETE = {
    method: "DELETE"
};

// funciones con el crud de bienes
async function leerBienes() {
    return fetch(`${API_BASE}/bien/leer`);
}

async function leerBien(id) {
    return fetch(`${API_BASE}/bien/leer/${id}`);
}

async function crearBien(bien) {
    return fetch(`${API_BASE}/bien/crear`, POST(bien));
}

async function modificarBien(bien, id) {
    return fetch(`${API_BASE}/bien/modificar/${id}`, PUT(bien));
}

async function eliminarBien(id) {
    return fetch(`${API_BASE}/bien/eliminar/${id}`, DELETE);
}

// funciones con el crud de solicitudes
async function leerSolicitudes() {
    return fetch(`${API_BASE}/solicitud/leer`);
}

async function leerSolicitud(id) {
    return fetch(`${API_BASE}/solicitud/leer/${id}`);
}

async function crearSolicitud(solicitud) {
    return fetch(`${API_BASE}/solicitud/crear`, POST(solicitud));
}

async function modificarSolicitud(solicitud, id) {
    return fetch(`${API_BASE}/solicitud/modificar/${id}`, PUT(solicitud));
}

async function eliminarSolicitud(id) {
    return fetch(`${API_BASE}/solicitud/eliminar/${id}`, DELETE);
}

// funciones con el crud de usuarios
async function leerUsuarios() {
    return fetch(`${API_BASE}/usuario/leer`);
}

async function leerUsuario(id) {
    return fetch(`${API_BASE}/usuario/leer/${id}`);
}

async function crearUsuario(usuario) {
    return fetch(`${API_BASE}/usuario/crear`, POST(usuario));
}

async function modificarUsuario(usuario, id) {
    return fetch(`${API_BASE}/usuario/modificar/${id}`, PUT(usuario));
}

async function eliminarUsuario(id) {
    return fetch(`${API_BASE}/usuario/eliminar/${id}`, DELETE);
}

async function loginUsuario(credentials) {
    return fetch(`${API_BASE}/usuario/login`, POST(credentials));
}

// funciones con el crud de categorias
async function leerCategorias() {
    return fetch(`${API_BASE}/categoria/leer`);
}

async function leerCategoria(id) {
    return fetch(`${API_BASE}/categoria/leer/${id}`);
}

async function crearCategoria(categoria) {
    return fetch(`${API_BASE}/categoria/crear`, POST(categoria));
}

async function modificarCategoria(categoria, id) {
    return fetch(`${API_BASE}/categoria/modificar/${id}`, PUT(categoria));
}

async function eliminarCategoria(id) {
    return fetch(`${API_BASE}/categoria/eliminar/${id}`, DELETE);
}

// funciones con el crud de eventos
async function leerEventos() {
    return fetch(`${API_BASE}/evento/leer`);
}

async function leerEvento(id) {
    return fetch(`${API_BASE}/evento/leer/${id}`);
}

async function crearEvento(evento) {
    return fetch(`${API_BASE}/evento/crear`, POST(evento));
}

async function modificarEvento(evento, id) {
    return fetch(`${API_BASE}/evento/modificar/${id}`, PUT(evento));
}

async function eliminarEvento(id) {
    return fetch(`${API_BASE}/evento/eliminar/${id}`, DELETE);
}

// funciones con el crud de ubicaciones
async function leerUbicaciones() {
    return fetch(`${API_BASE}/ubicacion/leer`);
}

async function leerUbicacion(id) {
    return fetch(`${API_BASE}/ubicacion/leer/${id}`);
}

async function crearUbicacion(ubicacion) {
    return fetch(`${API_BASE}/ubicacion/crear`, POST(ubicacion));
}

async function modificarUbicacion(ubicacion, id) {
    return fetch(`${API_BASE}/ubicacion/modificar/${id}`, PUT(ubicacion));
}

async function eliminarUbicacion(id) {
    return fetch(`${API_BASE}/ubicacion/eliminar/${id}`, DELETE);
}

// funciones con el crud de solicitantes
async function leerSolicitantes() {
    return fetch(`${API_BASE}/solicitante/leer`);
}

async function leerSolicitante(id) {
    return fetch(`${API_BASE}/solicitante/leer/${id}`);
}

async function crearSolicitante(ubicacion) {
    return fetch(`${API_BASE}/solicitante/crear`, POST(ubicacion));
}

async function modificarSolicitante(ubicacion, id) {
    return fetch(`${API_BASE}/solicitante/modificar/${id}`, PUT(ubicacion));
}

async function eliminarSolicitante(id) {
    return fetch(`${API_BASE}/solicitante/eliminar/${id}`, DELETE);
}
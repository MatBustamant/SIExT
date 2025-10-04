async function recargarBienes() {
    const response = await leerBienes();
    const bienes = await response.json();
    localStorage.setItem('bienes', JSON.stringify(bienes));
}

async function recargarSolicitudes() {
    const response = await leerSolicitudes();
    const solicitudes = await response.json();
    localStorage.setItem('solicitudes', JSON.stringify(solicitudes));
}

async function recargarCategorias() {
    const response = await leerCategorias();
    const categorias = await response.json();
    localStorage.setItem('categorias', JSON.stringify(categorias));
}

async function recargarUsuarios() {
    const response = await leerUsuarios();
    const usuarios = await response.json();
    localStorage.setItem('usuarios', JSON.stringify(usuarios));
}

async function recargarEventos() {
    const response = await leerEventos();
    const eventos = await response.json();
    localStorage.setItem('eventos', JSON.stringify(eventos));
}

async function recargarUbicaciones() {
    const response = await leerUbicaciones();
    const ubicaciones = await response.json();
    localStorage.setItem('ubicaciones', JSON.stringify(ubicaciones));
}

function recargarTodo() {
    recargarBienes();
    recargarSolicitudes();
    recargarCategorias();
    recargarUsuarios();
    recargarEventos();
    recargarUbicaciones();
}
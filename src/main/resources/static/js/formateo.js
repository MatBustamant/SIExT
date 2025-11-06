function formatearNroBien(id) {
    return `FCE-${id.toString().padStart(6, '0')}`;
}

function desformatearNroBien(nroFormateado) {
    return parseInt(nroFormateado.replace('FCE-', ''), 10);
}
function formatearNroBien(id) {
    return `FCE-${id.toString().padStart(6, '0')}`;
}

function desformatearNroBien(nroFormateado) {
    return parseInt(nroFormateado.replace('FCE-', ''), 10);
}

function formatearMayusComoTitulo(str) {
    return str
        .toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}

function formatearMayusComoOracion(str) {
    const lower = str.toLowerCase();
    return lower.charAt(0).toUpperCase() + lower.slice(1);
}
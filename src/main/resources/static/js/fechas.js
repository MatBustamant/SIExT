const optionsResumido = {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  };

const optionsDetallado = {
    weekday: "long",
    day: "2-digit",
    month: "long",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    hour12: false
  };

function obtenerFechaHoy() {
  return new Date().toLocaleDateString("en-CA");
}

function obtenerFechaHoraActual() {
  return new Date().toLocaleString(undefined, optionsResumido).replace(",", "");
}

function formatearFechaDeYankeeAEsp(fecha) {
  const [year, month, day] = fecha.split('-');
  return `${day}/${month}/${year}`;
}

function formatearFechaLocal(fecha) {
  return new Date(fecha).toLocaleDateString("en-CA");
}

function formatearFechaLocalEsp(fecha) {
  return new Date(fecha).toLocaleDateString("es-AR");
}

function formatearFechaLocalLegible(fecha) {
  // Esto nos da momento completo legible para detalle
  fechaFinal = new Date(fecha).toLocaleString("es-AR", optionsDetallado);
  return fechaFinal.charAt(0).toUpperCase() + fechaFinal.slice(1);
}

function formatearFechaHora(fecha) {
  return new Date(fecha).toLocaleString(undefined, optionsResumido).replace(",", "");
}

function validarRangoFechas(desde, hasta, hoy) {
  if (!desde || !hasta) return "Seleccioná ambas fechas.";
  if (desde > hasta) return "La fecha inicial no puede ser posterior a la fecha final.";
  if (desde > hoy || hasta > hoy) return "Las fechas no pueden ser futuras.";
  return null;
}
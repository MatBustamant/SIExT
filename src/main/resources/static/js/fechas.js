function obtenerFechaHoy() {
  return new Date().toLocaleDateString("en-CA");
}

function obtenerFechaHoraActual() {
  return new Date().toLocaleString().replace(",", "");
}

function formatearFechaLocal(fecha) {
  return new Date(fecha).toLocaleDateString("en-CA");
}

function formatearFechaHora(fecha) {
  return new Date(fecha).toLocaleString().replace(",", "");
}

function validarRangoFechas(desde, hasta, hoy) {
  if (!desde || !hasta) return "Seleccioná ambas fechas.";
  if (desde > hasta) return "La fecha inicial no puede ser posterior a la fecha final.";
  if (desde > hoy || hasta > hoy) return "Las fechas no pueden ser futuras.";
  return null;
}
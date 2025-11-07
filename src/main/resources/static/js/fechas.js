function obtenerFechaHoy() {
  return new Date().toLocaleDateString("en-CA");
}

function obtenerFechaHoraActual() {
  return new Date().toLocaleString().replace(",", "");
}

function formatearFechaLocal(fecha) {
  return new Date(fecha).toLocaleDateString("en-CA");
}

function formatearFechaLocalEsp(fecha) {
  return new Date(fecha).toLocaleDateString("es-AR");
}

function formatearFechaLocalLegible(fecha) {
  fechaFinal = new Date(fecha).toLocaleString("es-AR", {
    weekday: "long",
    day: "2-digit",
    month: "long",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  });
  return fechaFinal.charAt(0).toUpperCase() + fechaFinal.slice(1);
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
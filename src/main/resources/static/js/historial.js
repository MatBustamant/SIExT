// Referencias a los elementos del modal (asume que el HTML está en la página)
const modal = document.getElementById("historialModal");
const modalTitulo = document.getElementById("modalTitulo");
const modalContenido = document.getElementById("historialContenido");
const modalCerrarBtn = document.getElementById("modalCerrarBtn");

// Cierra el modal
function cerrarModal() {
    modal.classList.add("hidden");
    modalContenido.innerHTML = ""; // Limpia el contenido al cerrar
}

// Genera el HTML para el detalle de cada evento
function generarDetalleEvento(evento) {
    let detalleHtml = "";
    let claseDetalle = "";

    // Usamos tu lógica de negocio
    switch (evento.tipoEvento) {
        case "ENTREGA":
            claseDetalle = "entrega";
            detalleHtml = `<p>Se destina a la ubicación <strong>${evento.ubicacionDestino || "N/A"}</strong></p>${evento.detalle ? `<p>Detalle adicionales: ${evento.detalle}</p>` : ""}`;
            break;
        case "DEVOLUCION":
            claseDetalle = "devolucion";
            detalleHtml = `<p>El bien queda <strong>${evento.ubicacionDestino || "SIN ASIGNAR"}</strong></p>${evento.detalle ? `<p>Detalle adicionales: ${evento.detalle}</p>` : ""}`;
            break;
        case "AVERIO":
            claseDetalle = "averia";
            detalleHtml = `<p>Detalles adicionales: ${evento.detalle || "Sin detalles sobre el evento."}</p>`;
            break;
        case "REPARACION":
            claseDetalle = "reparacion";
            detalleHtml = `<p>Detalles adicionales: ${evento.detalle || "Sin detalles sobre el evento."}</p>`;
            break;
        case "BAJA":
            claseDetalle = "baja";
            detalleHtml = `<p>Detalles adicionales: ${evento.detalle || "Sin detalles sobre el evento."}</p>`;
            break;
        case "REGISTRO":
            detalleHtml = `<p>Bien registrado en el sistema.</p>`;
            break;
        default:
            detalleHtml = `<p>${evento.detalle || "N/A"}</p>`;
    }
    return `<div class="detalle ${claseDetalle}">${detalleHtml}</div>`;
}

// Función principal que filtra, ordena y muestra el historial
function mostrarHistorial(idBien) {
    const todosLosEventos = JSON.parse(localStorage.getItem("eventos")) || [];
    const bienes = JSON.parse(localStorage.getItem("bienes")) || [];

    // 1. Encontrar el bien para el título
    const bien = bienes.find(b => b.id_Bien === idBien);
    const nombreBien = bien ? bien.nombre : `ID ${idBien}`;
    // Asumimos que tenés la función formatearNroBien() en tu 'js/formateo.js'
    modalTitulo.textContent = `${nombreBien} (${formatearNroBien(idBien)})`; 

    // 2. Filtrar eventos para este bien
    const eventosDelBien = todosLosEventos.filter(e => e.bienAsociado === idBien && e.eliminado === false);

    // 3. Ordenar por fecha (más reciente primero)
    eventosDelBien.sort((a, b) => new Date(b.fechaEvento) - new Date(a.fechaEvento));

    // 4. Generar y mostrar el HTML
    modalContenido.innerHTML = ""; // Limpiar
    if (eventosDelBien.length === 0) {
        modalContenido.innerHTML = "<p>Este bien no tiene eventos de trazabilidad registrados.</p>";
    } else {
        eventosDelBien.forEach(evento => {
            const item = document.createElement("div");
            item.className = "historial-item";
            
            item.innerHTML = `
                <p class="fecha">${formatearFechaHora(evento.fechaEvento)}</p>
                <p class="tipo">${evento.tipoEvento.replace("_", " ")} <span class="fecha">#EV-${evento.id_Evento}</span></p>
                ${generarDetalleEvento(evento)}
            `;
            modalContenido.appendChild(item);
        });
    }

    // 5. Mostrar el modal
    modal.classList.remove("hidden");
}

// --- Event Listeners para cerrar el modal ---
// (Nos aseguramos que se ejecuten después de que cargue el DOM)
document.addEventListener("DOMContentLoaded", () => {
    // Estas constantes deben estar aquí o declaradas arriba
    const modal = document.getElementById("historialModal");
    const modalCerrarBtn = document.getElementById("modalCerrarBtn");

    if (modalCerrarBtn) {
        modalCerrarBtn.addEventListener("click", cerrarModal);
    }
    
    if (modal) {
        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                cerrarModal();
            }
        });
    }
});
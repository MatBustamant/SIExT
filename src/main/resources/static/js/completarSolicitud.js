
// ======================================================
// === NUEVA FUNCIÓN PARA EL MODAL DE ASIGNACIÓN ===
// ======================================================
/**
 * Abre un modal para seleccionar bienes específicos.
 * Devuelve una Promise que se resuelve si el usuario confirma,
 * o se rechaza si cancela o no hay stock.
 * @param {Array} itemsRequeridos - El array de {categoria, cantidad}
 */
function openAsignarBienesModal(itemsRequeridos) {
    // Obtener elementos del DOM
    const modal = document.getElementById("asignarBienesModal");
    const btnContinuar = document.getElementById("btnModalContinuar");
    const btnCancelar = document.getElementById("btnModalCancelar");
    const btnCerrar = document.getElementById("btnModalCerrar");
    const resumenEl = document.getElementById("resumenSeleccion");
    const listaEl = document.getElementById("listaBienesDisponibles");
    const filterNombreEl = document.getElementById("filterModalNombre");
    const filterInventarioEl = document.getElementById("filterModalInventario");

    // Variables de estado (se resetean cada vez que se abre)
    let bienesSeleccionados = new Set(); // Almacena los id_Bien seleccionados
    let bienesDisponiblesFiltrados = []; // Bienes de "SIN ASIGNAR" de las categorías relevantes
    let mapaRequeridos = new Map(); // Mapa de {categoria -> cantidad}

    // --- Función para validar la selección ---
    function validarSeleccion() {
        const conteoActual = new Map();

        bienesSeleccionados.forEach(idBien => {
            const bien = bienesDisponiblesFiltrados.find(b => b.id_Bien === idBien);
            if (bien) {
                const cat = bien.nombreCatBienes;
                conteoActual.set(cat, (conteoActual.get(cat) || 0) + 1);
            }
        });

        let esValido = true;
        resumenEl.innerHTML = ""; // Limpiar resumen

        // Comparamos el conteo actual con el requerido
        for (const [cat, cantRequerida] of mapaRequeridos.entries()) {
            const cantActual = conteoActual.get(cat) || 0;

            const p = document.createElement("p");
            p.innerHTML = `<strong>${cat}:</strong> `;
            const span = document.createElement("span");
            span.textContent = `${cantActual} / ${cantRequerida} seleccionados`;

            if (cantActual !== cantRequerida) {
                esValido = false;
                span.className = "invalido";
            } else {
                span.className = "valido";
            }
            p.appendChild(span);
            resumenEl.appendChild(p);
        }

        btnContinuar.disabled = !esValido;
    }

    // --- Función para renderizar la lista de bienes ---
    function renderListaBienes() {
        const filtroNombre = filterNombreEl.value.toUpperCase().trim();
        const filtroInventario = filterInventarioEl.value;

        const bienesARenderizar = bienesDisponiblesFiltrados.filter(b =>
            (matchTexto(b.nombre.toUpperCase().trim(), filtroNombre) &&
            matchTexto(formatearNroBien(b.id_Bien), filtroInventario)
        ));

        listaEl.innerHTML = "";
        if (bienesARenderizar.length === 0) {
            listaEl.innerHTML = "<p>No hay bienes que coincidan con los filtros.</p>";
            return;
        }

        // Agrupar por categoría
        const bienesPorCategoria = {};
        bienesARenderizar.forEach(b => {
            if (!bienesPorCategoria[b.nombreCatBienes]) {
                bienesPorCategoria[b.nombreCatBienes] = [];
            }
            bienesPorCategoria[b.nombreCatBienes].push(b);
        });

        // Renderizar grupos
        for (const categoria in bienesPorCategoria) {
            const header = document.createElement("h4");
            header.textContent = categoria;
            listaEl.appendChild(header);

            const table = document.createElement("table");
            table.className = "tabla-modal";
            table.innerHTML = `<thead><tr><th>Sel.</th><th>ID</th><th>Nombre</th></tr></thead>`;
            const tbody = document.createElement("tbody");

            bienesPorCategoria[categoria].forEach(bien => {
                const tr = document.createElement("tr");
                const isChecked = bienesSeleccionados.has(bien.id_Bien);
                tr.innerHTML = `
                            <td>
                                <input 
                                    type="checkbox" 
                                    class="checkbox-bien"
                                    data-id="${bien.id_Bien}"
                                    ${isChecked ? "checked" : ""}>
                            </td>
                            <td>${formatearNroBien(bien.id_Bien)}</td>
                            <td>${bien.nombre}</td>
                        `;
                tbody.appendChild(tr);
            });
            table.appendChild(tbody);
            listaEl.appendChild(table);
        }
    }

    // --- Función para cerrar y limpiar todo ---
    function limpiarYCerrarModal() {
        modal.classList.add("hidden");
        // Limpiar estado
        filterNombreEl.value = "";
        filterInventarioEl.value = "";
        bienesSeleccionados.clear();
        // Limpiar listeners
        filterNombreEl.oninput = null;
        filterInventarioEl.oninput = null;
        listaEl.onchange = null;
        btnContinuar.onclick = null;
        btnCancelar.onclick = null;
        btnCerrar.onclick = null;
    }


    // --- Lógica principal de la Promise ---
    return new Promise((resolve, reject) => {
        // 1. Preparar datos
        const todosBienes = JSON.parse(localStorage.getItem("bienes")) || [];
        const bienesDisponibles = todosBienes.filter(b => b.ubicacionBien === "SIN ASIGNAR" && !b.eliminado);

        // Llenar mapa de requeridos
        mapaRequeridos.clear();
        itemsRequeridos.forEach(item => {
            mapaRequeridos.set(item.categoria, item.cantidad);
        });
        const categoriasRequeridas = new Set(mapaRequeridos.keys());

        // Filtrar bienes relevantes
        bienesDisponiblesFiltrados = bienesDisponibles.filter(b =>
            categoriasRequeridas.has(b.nombreCatBienes)
        );

        // 2. Chequeo de Stock Suficiente (PRE-VUELO)
        const stockDisponiblePorCategoria = new Map();
        bienesDisponiblesFiltrados.forEach(b => {
            const cat = b.nombreCatBienes;
            stockDisponiblePorCategoria.set(cat, (stockDisponiblePorCategoria.get(cat) || 0) + 1);
        });

        let stockSuficiente = true;
        let errorMsg = "";
        for (const [cat, cantRequerida] of mapaRequeridos.entries()) {
            const cantDisponible = stockDisponiblePorCategoria.get(cat) || 0;
            if (cantDisponible < cantRequerida) {
                stockSuficiente = false;
                errorMsg += `\n- Faltan ${cantRequerida - cantDisponible} de ${cat} (solo hay ${cantDisponible} disponibles en "SIN ASIGNAR")`;
            }
        }

        if (!stockSuficiente) {
            alert(`Stock insuficiente para completar esta solicitud.` + errorMsg);
            reject("Stock insuficiente."); // Rechaza la promise
            return; // No abras el modal
        }

        // 3. Si hay stock, adjuntar listeners
        filterNombreEl.oninput = renderListaBienes;
        filterInventarioEl.oninput = renderListaBienes;

        listaEl.onchange = (e) => {
            if (e.target.classList.contains("checkbox-bien")) {
                const idBien = Number(e.target.dataset.id);
                if (e.target.checked) {
                    bienesSeleccionados.add(idBien);
                } else {
                    bienesSeleccionados.delete(idBien);
                }
                validarSeleccion(); // Re-validar con cada click
            }
        };

        btnContinuar.onclick = () => {
            // El botón solo está activo si la validación es correcta
            const idsSeleccionados = Array.from(bienesSeleccionados);
            limpiarYCerrarModal();
            resolve(idsSeleccionados); // Resuelve la promise con los IDs
        };

        const onCancel = () => {
            limpiarYCerrarModal();
            reject("Asignación cancelada por el usuario.");
        };
        btnCancelar.onclick = onCancel;
        btnCerrar.onclick = onCancel;

        // 4. Renderizar y mostrar
        renderListaBienes();
        validarSeleccion(); // Llamada inicial para estado de resumen y botón
        modal.classList.remove("hidden");
    });
}
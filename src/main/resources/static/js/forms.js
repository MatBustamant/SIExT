function cargarOpciones(idlist, nombreLista) {
    const datalist = document.getElementById(idlist);
    datalist.innerHTML = "";
    const registros = JSON.parse(localStorage.getItem(nombreLista)) || [];
    if (registros.length === 0) {
        datalist.disabled = true;
        datalist.innerHTML = "<option value=''>Sin datos</option>";
        return;
    }
    registros.forEach(r => {
        const opcion = document.createElement("option");
        opcion.value = r.nombre;
        opcion.textContent = r.nombre;
        datalist.appendChild(opcion);
    });
    datalist.disabled = false;
}

function matchTexto(texto, filtro) {
    if (!filtro) return true;
    const textoNormalizado = String(texto || "").toUpperCase();
    const filtroNormalizado = filtro.toUpperCase().trim();
    return textoNormalizado.includes(filtroNormalizado);
}

function matchFecha(fecha, filtro) {
    return !filtro || formatearFechaLocal(fecha) === filtro;
}

function matchValor(valor, filtro) {
    return !filtro || valor === filtro;
}

function matchNumero(numero, filtro) {
    return !filtro || String(numero).includes(filtro);
}
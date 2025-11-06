function obtenerAuth() {
    return JSON.parse(localStorage.getItem('auth'));
}

function verificarAuth(rolRequerido = null, loginPanel = true) {
    const auth = obtenerAuth();

    if (!auth) {
        window.location.href = 'login.html';
        alert('Por favor, inicie sesión para continuar.');
        return;
    }

    // Si se requiere un rol específico (por ejemplo "SUPERVISOR")
    if (rolRequerido && auth.rol !== rolRequerido) {
        window.location.href = 'panel.html';
        alert('No tenés permisos para acceder a esta sección.');
        return;
    }

    if (loginPanel && auth) {
        // Mostrar datos del usuario en el header
        const usernameEl = document.getElementById('username');
        const userRoleEl = document.getElementById('userRole');
        const rolIcon = document.getElementById('rolIcon');

        if (usernameEl && userRoleEl) {
            usernameEl.textContent = auth.nombre;
            userRoleEl.insertAdjacentText('beforeend', ' ' + auth.rol);
        }

        // Icono según el rol
        if (rolIcon) {
            if (auth.rol === 'SUPERVISOR') rolIcon.className = 'bi bi-shield-lock-fill';
            else if (auth.rol === 'ADMINISTRATIVO') rolIcon.className = 'bi bi-person-badge-fill';
            else rolIcon.className = 'bi bi-person-circle';
        }

        // Mostrar/ocultar menú
        const userInfo = document.getElementById('userInfo');
        const userMenu = document.getElementById('userMenu');
        if (userInfo && userMenu) {
            userInfo.addEventListener('click', () => {
                userMenu.classList.toggle('hidden');
            });
        }

        // Cerrar sesión
        const logoutBtn = document.getElementById('logoutBtn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', () => {
                if (confirm('¿Cerrar sesión?')) {
                    localStorage.clear();
                    window.location.href = 'login.html';
                }
            });
        }
    }
}

function mostrarSegunRol(rolNecesario) {
    const auth = obtenerAuth();
    if (auth && auth.rol === rolNecesario) {
        const accion = document.getElementById("supervisor-only-accion");
        const seccion = document.getElementById("supervisor-only-seccion");
        [accion, seccion].forEach(el => {
            if (el) {
                el.classList.remove('hidden');
            }
        }
        );
    }
}
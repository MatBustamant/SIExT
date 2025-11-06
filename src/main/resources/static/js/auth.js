function verificarAuth(rolRequerido = null, loginPanel = true) {
    const auth = JSON.parse(localStorage.getItem('auth'));

    if (!auth) {
        alert('Por favor, inicie sesión para continuar.');
        window.location.href = 'login.html';
        return;
    }

    // Si se requiere un rol específico (por ejemplo "SUPERVISOR")
    if (rolRequerido && auth.rol !== rolRequerido) {
        alert('No tenés permisos para acceder a esta sección.');
        window.location.href = 'panel.html';
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
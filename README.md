# SIExT — Sistema de Inventario de Exactas y Tecnologías

![Java](https://img.shields.io/badge/Java-24+-orange?style=flat-square&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-blue?style=flat-square&logo=apache-maven&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-Database-003B57?style=flat-square&logo=sqlite&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

**Un sistema web de gestión de inventario institucional** con trazabilidad completa de bienes, solicitudes de asignación y auditoría de eventos.

Este prototipo fue desarrollado como trabajo integrador académico para la asignatura **Ingeniería de Software** de la **Licenciatura en Sistemas de Información** en la **Universidad Nacional de Santiago del Estero (UNSE)**.

---

## Stack Tecnológico

* **Backend:** Java (JDK 24+), Jersey (Servicios REST).
* **Persistencia:** SQLite, JDBC API nativo.
* **Frontend:** HTML, CSS, JavaScript.
* **Testing y Calidad:** JUnit 5 para pruebas unitarias y JaCoCo para la generación automática de reportes de cobertura (limitada a la clase `Validador`).

---

## Arquitectura y Características del Sistema

El sistema está organizado bajo una **arquitectura en capas tradicional basada en DTOs y DAOs** (Controlador, Servicio y Acceso a Datos). Para el acceso a datos, se utiliza JDBC sin frameworks de persistencia extras.

### Características principales:
* **Lógica guiada por eventos:** El estado y la ubicación de un bien no se modifican de forma aislada. Se calculan en conjunto con los eventos de un bien para mantener consistente el historial del mismo.
* **Flujo de solicitudes definido:** Las solicitudes pasan por diferentes estados de forma controlada (`POR_REVISAR` ➔ `APROBADA` ➔ `SATISFECHA`). 
* **Generación dinámica de reportes:** Pueden generarse reportes (detallados o resumidos) tanto de eventos y solicitudes, listos para ser impresos desde el navegador.

---

## API REST

El sistema expone una API REST implementada con Jakarta REST (Jersey), organizada mediante un controlador genérico que estandariza las operaciones CRUD.

*Nota: La validación de permisos y accesos se encuentra integrada a nivel de interfaz en el cliente frontend; los endpoints del backend no implementan restricciones de autenticación en este prototipo.*

### Convención de endpoints

Todos los recursos del sistema (`BIEN`, `CATEGORIA`, `UBICACION`, `EVENTO`, `SOLICITUD`, `RESPONSABLE`, `USUARIO`) siguen la estructura:

```
/api/{recurso}/
```

### Operaciones estándar disponibles

* **Crear registros:** `POST /api/{recurso}/crear`
* **Leer todos los registros:** `GET /api/{recurso}/leer`
* **Consultar registro:** `GET /api/{recurso}/leer/{id}`
* **Modificar registro:** `PUT /api/{recurso}/modificar/{id}`
* **Eliminar registro:** `DELETE /api/{recurso}/eliminar/{id}`

---

## Instalación y Ejecución Local

### Requisitos previos
* Java JDK 24 o superior.
* Apache Maven instalado y configurado.

### Pasos para el despliegue
1. Clonar el repositorio:
```bash
git clone https://github.com/MatBustamant/SIExT.git
cd SIExT
```

2. Compilar el proyecto:
```bash
mvn clean package
```

3. Ejecutar la aplicación:
```bash
mvn exec:java -Dexec.mainClass="com.grupocapa8.siext.SIExT"
```

4. Acceder a la interfaz de usuario desde el navegador:
```
http://localhost:8080/login.html
```

*Nota: La base de datos SQLite se distribuye pre-inicializada en el archivo `bd.sqlite` con datos de prueba. El script `bd.sql` contiene el esquema y las inserciones iniciales como referencia para recrearla manualmente si fuera necesario.*

---

## Credenciales de Prueba

El sistema cuenta con los siguientes usuarios precargados en la base de datos:

| Usuario | Clave | Rol | Alcance |
| :--- | :--- | :--- | :--- |
| `ROCIO` | `Hjkloiuy1!` | **SUPERVISOR** | Acceso total a las funciones del sistema. |
| `MAURO` | `Hjkloiuy1!` | **ADMINISTRATIVO** | Gestión de bienes, solicitudes y eventos. |
| `GERONIMO` | `Hjkloiuy1!` | **ADMINISTRATIVO** | Gestión de bienes, solicitudes y eventos. |

---

## Licencia

Este proyecto está bajo licencia MIT. Ver archivo [LICENSE](https://github.com/MatBustamant/SIExT/blob/1d5680eed45ffdd57f7c175614698a3d631e3eb7/LICENSE) para más detalles.

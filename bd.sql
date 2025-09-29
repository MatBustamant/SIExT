DROP TABLE IF EXISTS "Bien";
DROP TABLE IF EXISTS "Categoria";
DROP TABLE IF EXISTS "Solicitud";
DROP TABLE IF EXISTS "Usuario";
DROP TABLE IF EXISTS "EventoTrazabilidad";

CREATE TABLE "Bien" (
	"ID_Bien"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	"ID_Categoria"	INTEGER NOT NULL,
	"Estado"	TEXT NOT NULL,
	"Ubicacion"	TEXT NOT NULL,
	PRIMARY KEY("ID_Bien" AUTOINCREMENT),
	FOREIGN KEY("ID_Categoria") REFERENCES "Categoria"("ID_Categoria")
);
CREATE TABLE "Categoria" (
	"ID_Categoria"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("ID_Categoria" AUTOINCREMENT)
);
CREATE TABLE "EventoTrazabilidad" (
	"ID_Evento"	INTEGER NOT NULL,
	"ID_Bien"	INTEGER NOT NULL,
	"Fecha"	TEXT NOT NULL,
	"Hora"	TEXT NOT NULL,
	"TipoEvento"	TEXT NOT NULL,
	PRIMARY KEY("ID_Evento" AUTOINCREMENT),
	FOREIGN KEY("ID_Bien") REFERENCES "Bien"("ID_Bien")
);
CREATE TABLE "Solicitud" (
	"Num_Solicitud"	INTEGER NOT NULL,
	"Estado"	TEXT NOT NULL,
	"Destino"	TEXT NOT NULL,
	"Fecha_Solicitud"	TEXT NOT NULL DEFAULT (datetime('now', 'localtime')),
	"Descripcion"	TEXT NOT NULL,
	PRIMARY KEY("Num_Solicitud" AUTOINCREMENT)
);
CREATE TABLE "Usuario" (
	"ID_Usuario"	INTEGER NOT NULL,
	"Nombre_Usuario"	TEXT NOT NULL,
	"Contraseña"	TEXT NOT NULL,
	"Rol"	TEXT NOT NULL,
	PRIMARY KEY("ID_Usuario" AUTOINCREMENT)
);
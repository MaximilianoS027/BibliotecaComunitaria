-- Script de inicialización de la base de datos biblioteca_bd
-- Ejecutar después de crear la base de datos

-- Crear la base de datos si no existe
-- CREATE DATABASE biblioteca_bd;

-- Conectar a la base de datos
-- \c biblioteca_bd;

-- Crear tabla de usuarios (tabla padre)
CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Crear tabla de lectores (hereda de usuarios)
CREATE TABLE IF NOT EXISTS lectores (
    id VARCHAR(50) PRIMARY KEY,
    direccion VARCHAR(200) NOT NULL,
    fecha_registro DATE NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('ACTIVO', 'SUSPENDIDO')),
    zona VARCHAR(50) NOT NULL CHECK (zona IN ('BIBLIOTECA_CENTRAL', 'SUCURSAL_ESTE', 'SUCURSAL_OESTE', 'BIBLIOTECA_INFANTIL', 'ARCHIVO_GENERAL')),
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear tabla de bibliotecarios (hereda de usuarios)
CREATE TABLE IF NOT EXISTS bibliotecarios (
    id VARCHAR(50) PRIMARY KEY,
    numero_empleado VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_lectores_estado ON lectores(estado);
CREATE INDEX IF NOT EXISTS idx_lectores_zona ON lectores(zona);
CREATE INDEX IF NOT EXISTS idx_bibliotecarios_numero_empleado ON bibliotecarios(numero_empleado);

-- Insertar datos de ejemplo (opcional)
-- INSERT INTO usuarios (id, nombre, email) VALUES ('U1', 'Usuario Ejemplo', 'usuario@ejemplo.com');
-- INSERT INTO lectores (id, direccion, fecha_registro, estado, zona) VALUES ('U1', 'Dirección Ejemplo', '2024-01-01', 'ACTIVO', 'BIBLIOTECA_CENTRAL');

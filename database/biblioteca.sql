-- =========================================================
-- Biblioteca Digital
-- Script de creación de base de datos MariaDB
-- =========================================================

CREATE DATABASE IF NOT EXISTS biblioteca
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE biblioteca;

-- =========================================================
-- Tabla: libros
-- =========================================================
CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(120) NOT NULL,
    isbn VARCHAR(20) NULL UNIQUE,
    anio_publicacion INT NULL,
    categoria VARCHAR(80) NULL,
    disponible TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabla: usuarios
-- =========================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(30) NULL
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabla: prestamos
-- =========================================================
CREATE TABLE IF NOT EXISTS prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libro_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NULL,
    devuelto TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_prestamos_libros
        FOREIGN KEY (libro_id)
        REFERENCES libros(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_prestamos_usuarios
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Índices auxiliares
-- =========================================================
CREATE INDEX idx_prestamos_libro_id
    ON prestamos(libro_id);

CREATE INDEX idx_prestamos_usuario_id
    ON prestamos(usuario_id);

CREATE INDEX idx_prestamos_devuelto
    ON prestamos(devuelto);

-- =========================================================
-- Datos de ejemplo opcionales
-- Descomentar si se desea poblar inicialmente la base de datos.
-- =========================================================

-- INSERT INTO libros
--     (titulo, autor, isbn, anio_publicacion, categoria, disponible)
-- VALUES
--     ('Cien años de soledad', 'Gabriel García Márquez', '9780307474728', 1967, 'Novela', 1),
--     ('El Principito', 'Antoine de Saint-Exupéry', '9780156012195', 1943, 'Novela corta', 1);

-- INSERT INTO usuarios
--     (nombre, email, telefono)
-- VALUES
--     ('Ana Pérez', 'ana.perez@example.com', '+56 9 1111 1111'),
--     ('Carlos Soto', 'carlos.soto@example.com', '+56 9 3333 3333');

-- =========================================================
-- Fin del script
-- =========================================================

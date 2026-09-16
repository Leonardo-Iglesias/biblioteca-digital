package cl.biblioteca.dao;

import cl.biblioteca.model.Prestamo;
import cl.biblioteca.util.ConexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // LISTAR TODOS LOS PRÉSTAMOS
    public List<Prestamo> listar() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = """
                SELECT p.id,
                       p.libro_id,
                       p.usuario_id,
                       p.fecha_prestamo,
                       p.fecha_devolucion,
                       p.devuelto,
                       l.titulo AS libro_titulo,
                       u.nombre AS usuario_nombre
                FROM prestamos p
                INNER JOIN libros l
                    ON p.libro_id = l.id
                INNER JOIN usuarios u
                    ON p.usuario_id = u.id
                ORDER BY p.id
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultado = statement.executeQuery()
        ) {

            while (resultado.next()) {

                Prestamo prestamo = new Prestamo();

                prestamo.setId(
                        resultado.getInt("id")
                );

                prestamo.setLibroId(
                        resultado.getInt("libro_id")
                );

                prestamo.setUsuarioId(
                        resultado.getInt("usuario_id")
                );

                prestamo.setLibroTitulo(
                        resultado.getString("libro_titulo")
                );

                prestamo.setUsuarioNombre(
                        resultado.getString("usuario_nombre")
                );

                Date fechaPrestamo =
                        resultado.getDate("fecha_prestamo");

                if (fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(
                            fechaPrestamo.toLocalDate()
                    );
                }

                Date fechaDevolucion =
                        resultado.getDate("fecha_devolucion");

                if (fechaDevolucion != null) {

                    prestamo.setFechaDevolucion(
                            fechaDevolucion.toLocalDate()
                    );

                } else {

                    prestamo.setFechaDevolucion(null);
                }

                prestamo.setDevuelto(
                        resultado.getBoolean("devuelto")
                );

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR AL LISTAR LOS PRÉSTAMOS"
            );

            e.printStackTrace();
        }

        return prestamos;
    }

    // REGISTRAR UN NUEVO PRÉSTAMO
    public boolean registrarPrestamo(Prestamo prestamo) {

        String sqlActualizarLibro = """
                UPDATE libros
                SET disponible = 0
                WHERE id = ?
                  AND disponible = 1
                """;

        String sqlInsertarPrestamo = """
                INSERT INTO prestamos
                (libro_id, usuario_id, fecha_prestamo, fecha_devolucion, devuelto)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            conexion.setAutoCommit(false);

            try (
                    PreparedStatement actualizarLibro =
                            conexion.prepareStatement(sqlActualizarLibro);

                    PreparedStatement insertarPrestamo =
                            conexion.prepareStatement(sqlInsertarPrestamo)
            ) {

                actualizarLibro.setInt(
                        1,
                        prestamo.getLibroId()
                );

                int librosActualizados =
                        actualizarLibro.executeUpdate();

                if (librosActualizados == 0) {

                    conexion.rollback();

                    System.out.println(
                            "EL LIBRO NO EXISTE O NO ESTÁ DISPONIBLE"
                    );

                    return false;
                }

                insertarPrestamo.setInt(
                        1,
                        prestamo.getLibroId()
                );

                insertarPrestamo.setInt(
                        2,
                        prestamo.getUsuarioId()
                );

                insertarPrestamo.setDate(
                        3,
                        Date.valueOf(
                                prestamo.getFechaPrestamo()
                        )
                );

                if (prestamo.getFechaDevolucion() != null) {

                    insertarPrestamo.setDate(
                            4,
                            Date.valueOf(
                                    prestamo.getFechaDevolucion()
                            )
                    );

                } else {

                    insertarPrestamo.setNull(
                            4,
                            java.sql.Types.DATE
                    );
                }

                insertarPrestamo.setBoolean(
                        5,
                        prestamo.isDevuelto()
                );

                int filasAfectadas =
                        insertarPrestamo.executeUpdate();

                if (filasAfectadas > 0) {

                    conexion.commit();
                    return true;

                } else {

                    conexion.rollback();
                    return false;
                }

            } catch (SQLException e) {

                conexion.rollback();

                System.out.println(
                        "ERROR AL REGISTRAR EL PRÉSTAMO"
                );

                e.printStackTrace();

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR DE CONEXIÓN AL REGISTRAR EL PRÉSTAMO"
            );

            e.printStackTrace();

            return false;
        }
    }

    // REGISTRAR LA DEVOLUCIÓN DE UN LIBRO
    public boolean registrarDevolucion(int prestamoId) {

        String sqlBuscarPrestamo = """
                SELECT libro_id, devuelto
                FROM prestamos
                WHERE id = ?
                FOR UPDATE
                """;

        String sqlActualizarPrestamo = """
                UPDATE prestamos
                SET fecha_devolucion = CURRENT_DATE,
                    devuelto = 1
                WHERE id = ?
                """;

        String sqlActualizarLibro = """
                UPDATE libros
                SET disponible = 1
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            conexion.setAutoCommit(false);

            try {

                int libroId;

                try (
                        PreparedStatement buscarPrestamo =
                                conexion.prepareStatement(
                                        sqlBuscarPrestamo
                                )
                ) {

                    buscarPrestamo.setInt(
                            1,
                            prestamoId
                    );

                    try (
                            ResultSet resultado =
                                    buscarPrestamo.executeQuery()
                    ) {

                        if (!resultado.next()) {

                            conexion.rollback();

                            System.out.println(
                                    "EL PRÉSTAMO NO EXISTE"
                            );

                            return false;
                        }

                        boolean devuelto =
                                resultado.getBoolean("devuelto");

                        if (devuelto) {

                            conexion.rollback();

                            System.out.println(
                                    "EL PRÉSTAMO YA FUE DEVUELTO"
                            );

                            return false;
                        }

                        libroId =
                                resultado.getInt("libro_id");
                    }
                }

                try (
                        PreparedStatement actualizarPrestamo =
                                conexion.prepareStatement(
                                        sqlActualizarPrestamo
                                )
                ) {

                    actualizarPrestamo.setInt(
                            1,
                            prestamoId
                    );

                    int prestamosActualizados =
                            actualizarPrestamo.executeUpdate();

                    if (prestamosActualizados == 0) {

                        conexion.rollback();
                        return false;
                    }
                }

                try (
                        PreparedStatement actualizarLibro =
                                conexion.prepareStatement(
                                        sqlActualizarLibro
                                )
                ) {

                    actualizarLibro.setInt(
                            1,
                            libroId
                    );

                    int librosActualizados =
                            actualizarLibro.executeUpdate();

                    if (librosActualizados == 0) {

                        conexion.rollback();
                        return false;
                    }
                }

                conexion.commit();

                return true;

            } catch (SQLException e) {

                conexion.rollback();

                System.out.println(
                        "ERROR AL REGISTRAR LA DEVOLUCIÓN"
                );

                e.printStackTrace();

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR DE CONEXIÓN AL REGISTRAR LA DEVOLUCIÓN"
            );

            e.printStackTrace();

            return false;
        }
    }
}
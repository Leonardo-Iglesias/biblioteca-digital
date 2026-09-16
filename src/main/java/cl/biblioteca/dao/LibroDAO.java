package cl.biblioteca.dao;

import cl.biblioteca.model.Libro;
import cl.biblioteca.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // LISTAR TODOS LOS LIBROS
    public List<Libro> listar() {

        List<Libro> libros = new ArrayList<>();

        String sql = """
                SELECT id,
                       titulo,
                       autor,
                       isbn,
                       anio_publicacion,
                       categoria,
                       disponible
                FROM libros
                ORDER BY id
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultado = statement.executeQuery()
        ) {

            while (resultado.next()) {

                Libro libro = new Libro();

                libro.setId(resultado.getInt("id"));
                libro.setTitulo(resultado.getString("titulo"));
                libro.setAutor(resultado.getString("autor"));
                libro.setIsbn(resultado.getString("isbn"));

                int anio = resultado.getInt("anio_publicacion");

                if (resultado.wasNull()) {
                    libro.setAnioPublicacion(null);
                } else {
                    libro.setAnioPublicacion(anio);
                }

                libro.setCategoria(resultado.getString("categoria"));
                libro.setDisponible(resultado.getBoolean("disponible"));

                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR LOS LIBROS");
            e.printStackTrace();
        }

        return libros;
    }

    // INSERTAR UN LIBRO
    public boolean insertar(Libro libro) {

        String sql = """
                INSERT INTO libros
                (titulo, autor, isbn, anio_publicacion, categoria, disponible)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());

            if (libro.getAnioPublicacion() != null) {
                statement.setInt(4, libro.getAnioPublicacion());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setString(5, libro.getCategoria());
            statement.setBoolean(6, libro.isDisponible());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL INSERTAR EL LIBRO");
            e.printStackTrace();

            return false;
        }
    }

    // BUSCAR UN LIBRO POR SU ID
    public Libro buscarPorId(int id) {

        String sql = """
                SELECT id,
                       titulo,
                       autor,
                       isbn,
                       anio_publicacion,
                       categoria,
                       disponible
                FROM libros
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    Libro libro = new Libro();

                    libro.setId(resultado.getInt("id"));
                    libro.setTitulo(resultado.getString("titulo"));
                    libro.setAutor(resultado.getString("autor"));
                    libro.setIsbn(resultado.getString("isbn"));

                    int anio = resultado.getInt("anio_publicacion");

                    if (resultado.wasNull()) {
                        libro.setAnioPublicacion(null);
                    } else {
                        libro.setAnioPublicacion(anio);
                    }

                    libro.setCategoria(resultado.getString("categoria"));
                    libro.setDisponible(resultado.getBoolean("disponible"));

                    return libro;
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL BUSCAR EL LIBRO");
            e.printStackTrace();
        }

        return null;
    }

    // ACTUALIZAR UN LIBRO
    public boolean actualizar(Libro libro) {

        String sql = """
                UPDATE libros
                SET titulo = ?,
                    autor = ?,
                    isbn = ?,
                    anio_publicacion = ?,
                    categoria = ?,
                    disponible = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());

            if (libro.getAnioPublicacion() != null) {
                statement.setInt(4, libro.getAnioPublicacion());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setString(5, libro.getCategoria());
            statement.setBoolean(6, libro.isDisponible());
            statement.setInt(7, libro.getId());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR EL LIBRO");
            e.printStackTrace();

            return false;
        }
    }

    // ELIMINAR UN LIBRO
    public boolean eliminar(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL LIBRO");
            e.printStackTrace();

            return false;
        }
    }
}
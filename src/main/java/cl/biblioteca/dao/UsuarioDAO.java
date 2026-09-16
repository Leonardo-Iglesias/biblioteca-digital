package cl.biblioteca.dao;

import cl.biblioteca.model.Usuario;
import cl.biblioteca.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // LISTAR TODOS LOS USUARIOS
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT id,
                       nombre,
                       email,
                       telefono
                FROM usuarios
                ORDER BY id
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultado = statement.executeQuery()
        ) {

            while (resultado.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(resultado.getInt("id"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setTelefono(resultado.getString("telefono"));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR LOS USUARIOS");
            e.printStackTrace();
        }

        return usuarios;
    }

    // INSERTAR UN USUARIO
    public boolean insertar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nombre, email, telefono)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getTelefono());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL INSERTAR EL USUARIO");
            e.printStackTrace();

            return false;
        }
    }

    // BUSCAR UN USUARIO POR SU ID
    public Usuario buscarPorId(int id) {

        String sql = """
                SELECT id,
                       nombre,
                       email,
                       telefono
                FROM usuarios
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setId(resultado.getInt("id"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setTelefono(resultado.getString("telefono"));

                    return usuario;
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL BUSCAR EL USUARIO");
            e.printStackTrace();
        }

        return null;
    }

    // ACTUALIZAR UN USUARIO
    public boolean actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    email = ?,
                    telefono = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getTelefono());
            statement.setInt(4, usuario.getId());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR EL USUARIO");
            e.printStackTrace();

            return false;
        }
    }

    // ELIMINAR UN USUARIO
    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL USUARIO");
            e.printStackTrace();

            return false;
        }
    }
}
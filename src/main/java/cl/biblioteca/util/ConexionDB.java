package cl.biblioteca.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USUARIO =
            System.getenv("DB_USER");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "No se pudo cargar el driver JDBC de MariaDB",
                    e
            );
        }
    }

    public static Connection obtenerConexion()
            throws SQLException {

        if (URL == null || USUARIO == null || PASSWORD == null) {
            throw new SQLException(
                    "Faltan las variables de entorno DB_URL, DB_USER o DB_PASSWORD"
            );
        }

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
package cl.biblioteca.controller;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.model.Libro;
import cl.biblioteca.model.Prestamo;
import cl.biblioteca.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/prestamos")
public class PrestamoServlet extends HttpServlet {

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final LibroDAO libroDAO = new LibroDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;

            case "devolver":
                devolverPrestamo(request, response);
                break;

            default:
                listarPrestamos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        request.setCharacterEncoding("UTF-8");

        int libroId = Integer.parseInt(
                request.getParameter("libroId")
        );

        int usuarioId = Integer.parseInt(
                request.getParameter("usuarioId")
        );

        Prestamo prestamo = new Prestamo(
                libroId,
                usuarioId,
                LocalDate.now(),
                null,
                false
        );

        prestamoDAO.registrarPrestamo(prestamo);

        response.sendRedirect(
                request.getContextPath() + "/prestamos"
        );
    }

    private void listarPrestamos(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Prestamo> prestamos = prestamoDAO.listar();

        request.setAttribute("prestamos", prestamos);

        request.getRequestDispatcher(
                "/WEB-INF/views/prestamos/lista.jsp"
        ).forward(request, response);
    }

    private void mostrarFormularioNuevo(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Libro> libros = libroDAO.listar();
        List<Usuario> usuarios = usuarioDAO.listar();

        request.setAttribute("libros", libros);
        request.setAttribute("usuarios", usuarios);

        request.getRequestDispatcher(
                "/WEB-INF/views/prestamos/formulario.jsp"
        ).forward(request, response);
    }

    private void devolverPrestamo(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        prestamoDAO.registrarDevolucion(id);

        response.sendRedirect(
                request.getContextPath() + "/prestamos"
        );
    }
}
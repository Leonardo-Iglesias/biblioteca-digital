package cl.biblioteca.controller;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.model.Libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/libros")
public class LibroServlet extends HttpServlet {

    private final LibroDAO libroDAO = new LibroDAO();

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

            case "editar":
                mostrarFormularioEditar(request, response);
                break;

            case "eliminar":
                eliminarLibro(request, response);
                break;

            default:
                listarLibros(request, response);
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("actualizar".equals(accion)) {
            actualizarLibro(request, response);
        } else {
            insertarLibro(request, response);
        }
    }

    private void listarLibros(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Libro> libros = libroDAO.listar();

        request.setAttribute("libros", libros);

        request.getRequestDispatcher(
                "/WEB-INF/views/libros/lista.jsp"
        ).forward(request, response);
    }

    private void mostrarFormularioNuevo(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/libros/formulario.jsp"
        ).forward(request, response);
    }

    private void mostrarFormularioEditar(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        Libro libro = libroDAO.buscarPorId(id);

        request.setAttribute("libro", libro);

        request.getRequestDispatcher(
                "/WEB-INF/views/libros/formulario.jsp"
        ).forward(request, response);
    }

    private void insertarLibro(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String anioTexto = request.getParameter("anioPublicacion");
        String categoria = request.getParameter("categoria");

        Integer anioPublicacion = null;

        if (anioTexto != null && !anioTexto.isBlank()) {
            anioPublicacion = Integer.parseInt(anioTexto);
        }

        Libro libro = new Libro(
                titulo,
                autor,
                isbn,
                anioPublicacion,
                categoria,
                true
        );

        libroDAO.insertar(libro);

        response.sendRedirect(
                request.getContextPath() + "/libros"
        );
    }

    private void actualizarLibro(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String anioTexto = request.getParameter("anioPublicacion");
        String categoria = request.getParameter("categoria");

        Integer anioPublicacion = null;

        if (anioTexto != null && !anioTexto.isBlank()) {
            anioPublicacion = Integer.parseInt(anioTexto);
        }

        Libro libroActual = libroDAO.buscarPorId(id);

        boolean disponible =
                libroActual != null && libroActual.isDisponible();

        Libro libro = new Libro(
                id,
                titulo,
                autor,
                isbn,
                anioPublicacion,
                categoria,
                disponible
        );

        libroDAO.actualizar(libro);

        response.sendRedirect(
                request.getContextPath() + "/libros"
        );
    }

    private void eliminarLibro(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        libroDAO.eliminar(id);

        response.sendRedirect(
                request.getContextPath() + "/libros"
        );
    }
}
package cl.biblioteca.controller;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

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

            case "editar":
                mostrarFormularioEditar(request, response);
                break;

            case "eliminar":
                eliminarUsuario(request, response);
                break;

            default:
                listarUsuarios(request, response);
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
            actualizarUsuario(request, response);
        } else {
            insertarUsuario(request, response);
        }
    }

    private void listarUsuarios(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Usuario> usuarios = usuarioDAO.listar();

        request.setAttribute("usuarios", usuarios);

        request.getRequestDispatcher(
                "/WEB-INF/views/usuarios/lista.jsp"
        ).forward(request, response);
    }

    private void mostrarFormularioNuevo(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/usuarios/formulario.jsp"
        ).forward(request, response);
    }

    private void mostrarFormularioEditar(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        Usuario usuario = usuarioDAO.buscarPorId(id);

        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher(
                "/WEB-INF/views/usuarios/formulario.jsp"
        ).forward(request, response);
    }

    private void insertarUsuario(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);

        usuarioDAO.insertar(usuario);

        response.sendRedirect(
                request.getContextPath() + "/usuarios"
        );
    }

    private void actualizarUsuario(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);

        usuarioDAO.actualizar(usuario);

        response.sendRedirect(
                request.getContextPath() + "/usuarios"
        );
    }

    private void eliminarUsuario(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        usuarioDAO.eliminar(id);

        response.sendRedirect(
                request.getContextPath() + "/usuarios"
        );
    }
}
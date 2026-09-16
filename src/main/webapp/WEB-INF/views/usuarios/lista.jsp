<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Biblioteca Digital - Usuarios</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body>

<div class="contenedor">

    <h1>Biblioteca Digital</h1>

    <nav>
        <a href="${pageContext.request.contextPath}/">Inicio</a>
        <a href="${pageContext.request.contextPath}/libros">Libros</a>
        <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
        <a href="${pageContext.request.contextPath}/prestamos">Préstamos</a>
    </nav>

    <h2>Listado de usuarios</h2>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/usuarios?accion=nuevo">
            Registrar nuevo usuario
        </a>
    </p>

    <c:choose>

        <c:when test="${empty usuarios}">
            <p>No existen usuarios registrados.</p>
        </c:when>

        <c:otherwise>

            <table>

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="usuario" items="${usuarios}">

                        <tr>

                            <td>${usuario.id}</td>

                            <td>${usuario.nombre}</td>

                            <td>${usuario.email}</td>

                            <td>${usuario.telefono}</td>

                            <td class="acciones">

                                <a href="${pageContext.request.contextPath}/usuarios?accion=editar&id=${usuario.id}">
                                    Editar
                                </a>

                                <a href="${pageContext.request.contextPath}/usuarios?accion=eliminar&id=${usuario.id}"
                                   onclick="return confirm('¿Confirma la eliminación de este usuario?');">
                                    Eliminar
                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                </tbody>

            </table>

        </c:otherwise>

    </c:choose>

</div>

</body>

</html>
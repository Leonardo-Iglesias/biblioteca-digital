<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Biblioteca Digital - Libros</title>

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

    <h2>Listado de libros</h2>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/libros?accion=nuevo">
            Registrar nuevo libro
        </a>
    </p>

    <c:choose>

        <c:when test="${empty libros}">
            <p>No existen libros registrados.</p>
        </c:when>

        <c:otherwise>

            <table>

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>ISBN</th>
                        <th>Año</th>
                        <th>Categoría</th>
                        <th>Disponible</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="libro" items="${libros}">

                        <tr>

                            <td>${libro.id}</td>

                            <td>${libro.titulo}</td>

                            <td>${libro.autor}</td>

                            <td>${libro.isbn}</td>

                            <td>${libro.anioPublicacion}</td>

                            <td>${libro.categoria}</td>

                            <td>
                                <c:choose>

                                    <c:when test="${libro.disponible}">
                                        <span class="estado-disponible">
                                            Sí
                                        </span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="estado-prestado">
                                            No
                                        </span>
                                    </c:otherwise>

                                </c:choose>
                            </td>

                            <td class="acciones">

                                <a href="${pageContext.request.contextPath}/libros?accion=editar&id=${libro.id}">
                                    Editar
                                </a>

                                <a href="${pageContext.request.contextPath}/libros?accion=eliminar&id=${libro.id}"
                                   onclick="return confirm('¿Confirma la eliminación de este libro?');">
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
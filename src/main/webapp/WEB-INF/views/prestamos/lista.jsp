<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Biblioteca Digital - Préstamos</title>

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

    <h2>Listado de préstamos</h2>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/prestamos?accion=nuevo">
            Registrar nuevo préstamo
        </a>
    </p>

    <c:choose>

        <c:when test="${empty prestamos}">
            <p>No existen préstamos registrados.</p>
        </c:when>

        <c:otherwise>

            <table>

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Libro</th>
                        <th>Usuario</th>
                        <th>Fecha de préstamo</th>
                        <th>Fecha de devolución</th>
                        <th>Devuelto</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="prestamo" items="${prestamos}">

                        <tr>

                            <td>${prestamo.id}</td>

                            <td>${prestamo.libroTitulo}</td>

                            <td>${prestamo.usuarioNombre}</td>

                            <td>${prestamo.fechaPrestamo}</td>

                            <td>
                                <c:choose>

                                    <c:when test="${not empty prestamo.fechaDevolucion}">
                                        ${prestamo.fechaDevolucion}
                                    </c:when>

                                    <c:otherwise>
                                        Pendiente
                                    </c:otherwise>

                                </c:choose>
                            </td>

                            <td>

                                <c:choose>

                                    <c:when test="${prestamo.devuelto}">
                                        Sí
                                    </c:when>

                                    <c:otherwise>
                                        No
                                    </c:otherwise>

                                </c:choose>

                            </td>

                            <td class="acciones">

                                <c:choose>

                                    <c:when test="${not prestamo.devuelto}">

                                        <a href="${pageContext.request.contextPath}/prestamos?accion=devolver&id=${prestamo.id}"
                                           onclick="return confirm('¿Confirma la devolución de este libro?');">
                                            Devolver
                                        </a>

                                    </c:when>

                                    <c:otherwise>
                                        Devuelto
                                    </c:otherwise>

                                </c:choose>

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
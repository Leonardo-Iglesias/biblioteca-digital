<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Biblioteca Digital - Nuevo préstamo</title>

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

    <h2>Registrar nuevo préstamo</h2>

    <form action="${pageContext.request.contextPath}/prestamos"
          method="post">

        <p>
            <label for="libroId">Libro:</label>

            <select id="libroId"
                    name="libroId"
                    required>

                <option value="">
                    Seleccione un libro
                </option>

                <c:forEach var="libro" items="${libros}">

                    <c:if test="${libro.disponible}">

                        <option value="${libro.id}">
                            ${libro.titulo} - ${libro.autor}
                        </option>

                    </c:if>

                </c:forEach>

            </select>
        </p>

        <p>
            <label for="usuarioId">Usuario:</label>

            <select id="usuarioId"
                    name="usuarioId"
                    required>

                <option value="">
                    Seleccione un usuario
                </option>

                <c:forEach var="usuario" items="${usuarios}">

                    <option value="${usuario.id}">
                        ${usuario.nombre}
                    </option>

                </c:forEach>

            </select>
        </p>

        <div class="form-acciones">

            <button type="submit">
                Registrar préstamo
            </button>

            <a href="${pageContext.request.contextPath}/prestamos">
                Cancelar
            </a>

        </div>

    </form>

</div>

</body>

</html>
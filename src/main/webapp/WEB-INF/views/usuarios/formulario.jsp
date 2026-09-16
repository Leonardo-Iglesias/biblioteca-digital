<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <c:choose>
        <c:when test="${not empty usuario}">
            <title>Biblioteca Digital - Editar usuario</title>
        </c:when>

        <c:otherwise>
            <title>Biblioteca Digital - Nuevo usuario</title>
        </c:otherwise>
    </c:choose>

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

    <c:choose>

        <c:when test="${not empty usuario}">
            <h2>Editar usuario</h2>
        </c:when>

        <c:otherwise>
            <h2>Registrar nuevo usuario</h2>
        </c:otherwise>

    </c:choose>

    <form action="${pageContext.request.contextPath}/usuarios"
          method="post">

        <c:if test="${not empty usuario}">

            <input type="hidden"
                   name="accion"
                   value="actualizar">

            <input type="hidden"
                   name="id"
                   value="${usuario.id}">

        </c:if>

        <p>
            <label for="nombre">Nombre:</label>

            <input type="text"
                   id="nombre"
                   name="nombre"
                   value="${usuario.nombre}"
                   required>
        </p>

        <p>
            <label for="email">Email:</label>

            <input type="email"
                   id="email"
                   name="email"
                   value="${usuario.email}"
                   required>
        </p>

        <p>
            <label for="telefono">Teléfono:</label>

            <input type="text"
                   id="telefono"
                   name="telefono"
                   value="${usuario.telefono}">
        </p>

        <div class="form-acciones">

            <button type="submit">

                <c:choose>

                    <c:when test="${not empty usuario}">
                        Guardar cambios
                    </c:when>

                    <c:otherwise>
                        Registrar usuario
                    </c:otherwise>

                </c:choose>

            </button>

            <a href="${pageContext.request.contextPath}/usuarios">
                Cancelar
            </a>

        </div>

    </form>

</div>

</body>

</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <c:choose>
        <c:when test="${not empty libro}">
            <title>Biblioteca Digital - Editar libro</title>
        </c:when>

        <c:otherwise>
            <title>Biblioteca Digital - Nuevo libro</title>
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

        <c:when test="${not empty libro}">
            <h2>Editar libro</h2>
        </c:when>

        <c:otherwise>
            <h2>Registrar nuevo libro</h2>
        </c:otherwise>

    </c:choose>

    <form action="${pageContext.request.contextPath}/libros"
          method="post">

        <c:if test="${not empty libro}">

            <input type="hidden"
                   name="accion"
                   value="actualizar">

            <input type="hidden"
                   name="id"
                   value="${libro.id}">

        </c:if>

        <p>
            <label for="titulo">Título:</label>

            <input type="text"
                   id="titulo"
                   name="titulo"
                   value="${libro.titulo}"
                   required>
        </p>

        <p>
            <label for="autor">Autor:</label>

            <input type="text"
                   id="autor"
                   name="autor"
                   value="${libro.autor}"
                   required>
        </p>

        <p>
            <label for="isbn">ISBN:</label>

            <input type="text"
                   id="isbn"
                   name="isbn"
                   value="${libro.isbn}">
        </p>

        <p>
            <label for="anioPublicacion">
                Año de publicación:
            </label>

            <input type="number"
                   id="anioPublicacion"
                   name="anioPublicacion"
                   value="${libro.anioPublicacion}">
        </p>

        <p>
            <label for="categoria">Categoría:</label>

            <input type="text"
                   id="categoria"
                   name="categoria"
                   value="${libro.categoria}">
        </p>

        <div class="form-acciones">

            <button type="submit">

                <c:choose>

                    <c:when test="${not empty libro}">
                        Guardar cambios
                    </c:when>

                    <c:otherwise>
                        Registrar libro
                    </c:otherwise>

                </c:choose>

            </button>

            <a href="${pageContext.request.contextPath}/libros">
                Cancelar
            </a>

        </div>

    </form>

</div>

</body>

</html>
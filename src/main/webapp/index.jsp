<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Biblioteca Digital</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body>

<div class="contenedor">

    <h1>Biblioteca Digital</h1>

    <nav>
        <a href="${pageContext.request.contextPath}/libros">Libros</a>
        <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
        <a href="${pageContext.request.contextPath}/prestamos">Préstamos</a>
    </nav>

    <h2>Sistema de gestión bibliotecaria</h2>

    <p>
        Bienvenido al sistema Biblioteca Digital.
        Desde esta aplicación puede administrar libros,
        usuarios y préstamos.
    </p>

    <h2>Opciones principales</h2>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/libros">
            Gestionar libros
        </a>
    </p>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/usuarios">
            Gestionar usuarios
        </a>
    </p>

    <p>
        <a class="boton"
           href="${pageContext.request.contextPath}/prestamos">
            Gestionar préstamos
        </a>
    </p>

</div>

</body>

</html>
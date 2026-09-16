# 📚 Biblioteca Digital

Aplicación web desarrollada en Java para la gestión de una biblioteca digital. El sistema permite administrar libros, usuarios y préstamos, incluyendo el control automático de disponibilidad de los ejemplares y el registro de devoluciones.

El proyecto fue desarrollado aplicando una arquitectura **MVC (Modelo - Vista - Controlador)**, utilizando **Servlets, JSP, JSTL, JDBC, DAO, MariaDB y Apache Tomcat**.

---

## 🚀 Funcionalidades

### Gestión de libros

El módulo de libros permite:

- Registrar nuevos libros.
- Consultar los libros almacenados.
- Editar información de los libros.
- Eliminar libros.
- Visualizar su estado de disponibilidad.
- Gestionar título, autor, ISBN, año de publicación y categoría.

### Gestión de usuarios

El módulo de usuarios permite:

- Registrar nuevos usuarios.
- Consultar usuarios registrados.
- Editar sus datos.
- Eliminar usuarios.
- Gestionar nombre, correo electrónico y teléfono.

### Gestión de préstamos

El módulo de préstamos permite:

- Registrar préstamos de libros.
- Asociar un libro a un usuario.
- Registrar automáticamente la fecha del préstamo.
- Mostrar únicamente libros disponibles al crear un préstamo.
- Cambiar automáticamente el estado del libro a no disponible.
- Registrar devoluciones.
- Registrar automáticamente la fecha de devolución.
- Marcar nuevamente el libro como disponible.
- Mostrar el título del libro y el nombre del usuario en el historial de préstamos.

---

## 🏗️ Arquitectura

El proyecto utiliza el patrón **MVC**.

### Model

Contiene las clases que representan las entidades principales:

```text
Libro.java
Usuario.java
Prestamo.java
```

### View

Las vistas están desarrolladas mediante:

- JSP
- JSTL
- HTML
- CSS

Se encuentran principalmente en:

```text
src/main/webapp/WEB-INF/views/
```

### Controller

La lógica de control de las solicitudes HTTP se implementa mediante Servlets:

```text
LibroServlet.java
UsuarioServlet.java
PrestamoServlet.java
```

### DAO

El acceso y manipulación de datos se realiza mediante el patrón DAO:

```text
LibroDAO.java
UsuarioDAO.java
PrestamoDAO.java
```

La conexión a MariaDB se centraliza en:

```text
ConexionDB.java
```

---

## 🛠️ Tecnologías utilizadas

- Java
- Jakarta Servlet API
- JSP
- JSTL
- JDBC
- Maven
- MariaDB
- Apache Tomcat 11
- HTML5
- CSS3
- Git
- GitHub

---

## 📁 Estructura principal

```text
biblioteca-digital/
│
├── pom.xml
├── README.md
├── .gitignore
│
└── src/
    └── main/
        ├── java/
        │   └── cl/
        │       └── biblioteca/
        │           ├── controller/
        │           │   ├── LibroServlet.java
        │           │   ├── PrestamoServlet.java
        │           │   └── UsuarioServlet.java
        │           │
        │           ├── dao/
        │           │   ├── LibroDAO.java
        │           │   ├── PrestamoDAO.java
        │           │   └── UsuarioDAO.java
        │           │
        │           ├── model/
        │           │   ├── Libro.java
        │           │   ├── Prestamo.java
        │           │   └── Usuario.java
        │           │
        │           └── util/
        │               └── ConexionDB.java
        │
        └── webapp/
            ├── index.jsp
            ├── css/
            │   └── estilos.css
            │
            └── WEB-INF/
                └── views/
                    ├── libros/
                    ├── usuarios/
                    └── prestamos/
```

---

## 🗄️ Base de datos

La aplicación utiliza una base de datos MariaDB llamada:

```text
biblioteca
```

Las principales tablas son:

```text
libros
usuarios
prestamos
```

La tabla `prestamos` relaciona los libros y usuarios mediante claves foráneas.

---

## 🔐 Configuración de la conexión

Por seguridad, las credenciales de MariaDB **no están almacenadas directamente en el código fuente**.

La aplicación utiliza las siguientes variables de entorno:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Ejemplo de configuración:

```text
DB_URL=jdbc:mariadb://localhost:3306/biblioteca
DB_USER=usuario_mariadb
DB_PASSWORD=contraseña_mariadb
```

Cada desarrollador debe configurar estas variables en su propio entorno antes de ejecutar la aplicación.

> No se deben almacenar contraseñas reales ni archivos `.env` con credenciales dentro del repositorio.

---

## ▶️ Ejecución del proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Leonardo-Iglesias/biblioteca-digital.git
```

### 2. Ingresar al proyecto

```bash
cd biblioteca-digital
```

### 3. Configurar MariaDB

Crear la base de datos:

```sql
CREATE DATABASE biblioteca;
```

Posteriormente se deben crear las tablas correspondientes a libros, usuarios y préstamos.

### 4. Configurar las variables de entorno

Configurar:

```text
DB_URL
DB_USER
DB_PASSWORD
```

con los datos correspondientes a la instalación local de MariaDB.

### 5. Compilar con Maven

```bash
mvn package -DskipTests
```

Al finalizar se generará:

```text
target/biblioteca.war
```

### 6. Desplegar en Apache Tomcat

Copiar:

```text
target/biblioteca.war
```

a la carpeta:

```text
TOMCAT_HOME/webapps/
```

### 7. Iniciar Tomcat

Una vez desplegada la aplicación, acceder desde el navegador a:

```text
http://localhost:8080/biblioteca/
```

---

## 🔄 Flujo de un préstamo

El funcionamiento general es el siguiente:

1. Se registra un libro.
2. Se registra un usuario.
3. Se crea un préstamo asociando el libro con el usuario.
4. El libro cambia automáticamente a estado no disponible.
5. Mientras se encuentra prestado, no aparece como opción para un nuevo préstamo.
6. Al registrar la devolución, se almacena la fecha correspondiente.
7. El libro vuelve automáticamente a quedar disponible.

---

## 📌 Consideraciones

- Los libros que se encuentran prestados no pueden volver a prestarse hasta registrar su devolución.
- La operación de préstamo actualiza tanto el registro del préstamo como la disponibilidad del libro.
- La devolución actualiza el préstamo y habilita nuevamente el ejemplar.
- La aplicación utiliza transacciones JDBC para mantener la consistencia de estas operaciones.
- Las credenciales de conexión no se almacenan en el repositorio.

---

## 👨‍💻 Autor

**Leonardo Iglesias Salas**

Proyecto desarrollado como parte de la formación **Desarrollo de Aplicaciones Full Stack Java Trainee**.

GitHub:

```text
https://github.com/Leonardo-Iglesias
```

---

## 📄 Estado del proyecto

Proyecto funcional con módulos de:

- Libros
- Usuarios
- Préstamos
- Devoluciones
- Control de disponibilidad
- Persistencia en MariaDB
- Navegación web mediante JSP y Servlets
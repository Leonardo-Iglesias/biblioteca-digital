package cl.biblioteca.model;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anioPublicacion;
    private String categoria;
    private boolean disponible;

    // Constructor vacío
    public Libro() {
    }

    // Constructor sin ID, utilizado al registrar un libro nuevo
    public Libro(String titulo, String autor, String isbn,
                 Integer anioPublicacion, String categoria,
                 boolean disponible) {

        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    // Constructor completo, utilizado para libros obtenidos desde la BD
    public Libro(int id, String titulo, String autor, String isbn,
                 Integer anioPublicacion, String categoria,
                 boolean disponible) {

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
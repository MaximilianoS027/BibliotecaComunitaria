package logica;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Entidad JPA que representa un libro en el sistema
 */
@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    private String id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String titulo;
    
    @Column(nullable = false)
    private int cantidadPaginas;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaRegistro;
    
    // Constructor por defecto requerido por JPA
    public Libro() {}
    
    // Constructor con parámetros
    public Libro(String id, String titulo, int cantidadPaginas, Date fechaRegistro) {
        this.id = id;
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Constructor sin ID (se genera automáticamente)
    public Libro(String titulo, int cantidadPaginas, Date fechaRegistro) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Constructor simplificado para registro actual
    public Libro(String titulo, int cantidadPaginas) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = new Date();
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public int getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // Métodos de negocio para validaciones
    public boolean tieneTituloValido() {
        return titulo != null && !titulo.trim().isEmpty() && titulo.length() >= 2 && titulo.length() <= 255;
    }
    
    public boolean tieneCantidadPaginasValida() {
        return cantidadPaginas > 0 && cantidadPaginas <= 10000; // Límite razonable
    }
    
    public boolean tieneFechaRegistroValida() {
        return fechaRegistro != null && !fechaRegistro.after(new Date());
    }
    
    public boolean esLibroCorto() {
        return cantidadPaginas <= 100;
    }
    
    public boolean esLibroMedio() {
        return cantidadPaginas > 100 && cantidadPaginas <= 300;
    }
    
    public boolean esLibroLargo() {
        return cantidadPaginas > 300;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", cantidadPaginas=" + cantidadPaginas +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Libro libro = (Libro) obj;
        
        if (id != null) {
            return id.equals(libro.id);
        } else {
            return titulo != null && titulo.equals(libro.titulo);
        }
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : (titulo != null ? titulo.hashCode() : 0);
    }
}

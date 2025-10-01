package logica;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Date;

/**
 * Entidad JPA que representa un libro en el sistema
 * Según el modelo de dominio: Libro hereda de Material
 * Atributos: titulo, cantidadPaginas (int según diagrama)
 */
@Entity
@Table(name = "libros")
public class Libro extends Material {
    
    @Column(nullable = false, length = 255)
    private String titulo;
    
    @Column(nullable = false)
    private Integer cantidadPaginas;
    
    @Column(name = "fecharegistro", nullable = false)
    private Date fechaRegistro;
    
    // Constructor por defecto requerido por JPA
    public Libro() {
        super();
    }
    
    // Constructor con parámetros
    public Libro(String titulo, Integer cantidadPaginas) {
        super();
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = new Date(); // Inicializar con fecha actual
    }
    
    // Constructor con todos los parámetros
    public Libro(String id, Date fechaIngreso, String titulo, Integer cantidadPaginas) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = new Date(); // Inicializar con fecha actual
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Integer getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(Integer cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    public Date getFechaRegistroLocal() {
        return fechaRegistro;
    }
    
    public void setFechaRegistroLocal(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // Métodos de negocio para validaciones
    public boolean tieneTituloValido() {
        return titulo != null && !titulo.trim().isEmpty() && titulo.length() <= 255;
    }
    
    public boolean tieneCantidadPaginasValida() {
        return cantidadPaginas != null && cantidadPaginas > 0;
    }
    
    public boolean tieneFechaRegistroValida() {
        return fechaRegistro != null;
    }
    
    // Método para obtener fecha de registro (alias para compatibilidad)
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    // Método para generar hash lógico para idempotencia
    public String generarHashLogico() {
        return String.valueOf((titulo + cantidadPaginas).hashCode());
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id='" + getId() + '\'' +
                ", fechaIngreso=" + getFechaIngreso() +
                ", titulo='" + titulo + '\'' +
                ", cantidadPaginas=" + cantidadPaginas +
                '}';
    }
}

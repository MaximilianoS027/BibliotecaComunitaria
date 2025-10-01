package logica;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad JPA que representa un préstamo en el sistema
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {
    
    @Id
    private String id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_solicitud", nullable = false)
    private Date fechaSolicitud;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_devolucion")
    private Date fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;
    
    // Relación con Lector
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;
    
    // Relación con Bibliotecario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    private Bibliotecario bibliotecario;
    
    // Relación con Material
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    // Constructor por defecto requerido por JPA
    public Prestamo() {}
    
    // Constructor con parámetros
    public Prestamo(String id, Date fechaSolicitud, EstadoPrestamo estado, 
                   Lector lector, Bibliotecario bibliotecario, Material material) {
        this.id = id;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.material = material;
        this.fechaDevolucion = null; // Inicialmente null
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public Lector getLector() {
        return lector;
    }
    
    public void setLector(Lector lector) {
        this.lector = lector;
    }
    
    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }
    
    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    // Métodos de validación
    public boolean tieneFechaSolicitudValida() {
        return fechaSolicitud != null && !fechaSolicitud.after(new Date());
    }
    
    public boolean puedeSerDevuelto() {
        return estado == EstadoPrestamo.EN_CURSO;
    }
    
    public boolean estaPendiente() {
        return estado == EstadoPrestamo.PENDIENTE;
    }
    
    public boolean estaEnCurso() {
        return estado == EstadoPrestamo.EN_CURSO;
    }
    
    public boolean estaDevuelto() {
        return estado == EstadoPrestamo.DEVUELTO;
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaDevolucion=" + fechaDevolucion +
                ", estado=" + estado +
                ", lector=" + (lector != null ? lector.getNombre() : "null") +
                ", bibliotecario=" + (bibliotecario != null ? bibliotecario.getNombre() : "null") +
                ", material=" + (material != null ? material.getId() : "null") +
                '}';
    }
}

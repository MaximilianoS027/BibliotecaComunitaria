package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/**
 * Clase abstracta que representa un material base en el sistema
 * Según el modelo de dominio: Material (abstracto) con id y fechaIngreso
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "materiales")
public abstract class Material {
    
    @Id
    private String id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    
    // Constructor por defecto requerido por JPA
    public Material() {
        this.id = generarIdMaterial();
        this.fechaIngreso = new Date(); // Fecha actual UTC
    }
    
    // Constructor con parámetros
    public Material(String id, Date fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }
    
    // Método para generar ID único
    private static String generarIdMaterial() {
        return "MAT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    // Métodos de negocio
    public boolean tieneIdValido() {
        return id != null && !id.trim().isEmpty();
    }
    
    public boolean tieneFechaIngresoValida() {
        return fechaIngreso != null;
    }
    
    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}

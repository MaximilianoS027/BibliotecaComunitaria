package logica;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;

/**
 * Entidad JPA que representa un artículo especial en el sistema
 * Según el modelo de dominio: ArticuloEspecial hereda de Material
 * Atributos: descripcion, pesoKg, dimensiones
 */
@Entity
@Table(name = "articulos_especiales")
public class ArticuloEspecial extends Material {
    
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @Column(nullable = false, precision = 10, scale = 3)
    private Float pesoKg;
    
    @Column(nullable = false, length = 100)
    private String dimensiones;
    
    // Constructor por defecto requerido por JPA
    public ArticuloEspecial() {
        super();
    }
    
    // Constructor con parámetros
    public ArticuloEspecial(String descripcion, Float pesoKg, String dimensiones) {
        super();
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }
    
    // Constructor con todos los parámetros
    public ArticuloEspecial(String id, Date fechaIngreso, String descripcion, Float pesoKg, String dimensiones) {
        super(id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }
    
    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Float getPesoKg() {
        return pesoKg;
    }
    
    public void setPesoKg(Float pesoKg) {
        this.pesoKg = pesoKg;
    }
    
    public String getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    // Métodos de negocio para validaciones
    public boolean tieneDescripcionValida() {
        return descripcion != null && !descripcion.trim().isEmpty() && descripcion.length() <= 500;
    }
    
    public boolean tienePesoValido() {
        return pesoKg != null && pesoKg > 0;
    }
    
    public boolean tieneDimensionesValidas() {
        return dimensiones != null && !dimensiones.trim().isEmpty();
    }
    
    public boolean tieneFechaRegistroValida() {
        return getFechaIngreso() != null;
    }
    
    // Método para obtener fecha de registro (alias para compatibilidad)
    public Date getFechaRegistro() {
        return getFechaIngreso();
    }
    
    @Override
    public String getDescripcionMaterial() {
        return descripcion != null ? descripcion : "Artículo sin descripción";
    }
    
    // Método para validar formato de dimensiones (LxAxH cm)
    public boolean tieneDimensionesFormatoValido() {
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            return false;
        }
        
        // Formato flexible: acepta patrones como "10x20x30 cm", "10 x 20 x 30cm", etc.
        String patron = "^\\d+(\\.\\d+)?\\s*[xX]\\s*\\d+(\\.\\d+)?\\s*[xX]\\s*\\d+(\\.\\d+)?\\s*(cm|CM|mm|MM|m|M)?$";
        return dimensiones.matches(patron);
    }
    
    // Método para generar hash lógico para idempotencia
    public String generarHashLogico() {
        return String.valueOf((descripcion + pesoKg + dimensiones).hashCode());
    }
    
    @Override
    public String toString() {
        return "ArticuloEspecial{" +
                "id='" + getId() + '\'' +
                ", fechaIngreso=" + getFechaIngreso() +
                ", descripcion='" + descripcion + '\'' +
                ", pesoKg=" + pesoKg +
                ", dimensiones='" + dimensiones + '\'' +
                '}';
    }
}

package datatypes;

import java.util.Date;

/**
 * DataType para transferir información de libros
 * Contiene los datos básicos de un libro para ser usado en la capa de presentación
 */
public class DtLibro {
    
    private String id;
    private String titulo;
    private int cantidadPaginas;
    private Date fechaRegistro;
    
    /**
     * Constructor completo
     */
    public DtLibro(String id, String titulo, int cantidadPaginas, Date fechaRegistro) {
        this.id = id;
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * Constructor para crear un libro sin ID (para registros nuevos)
     */
    public DtLibro(String titulo, int cantidadPaginas, Date fechaRegistro) {
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public int getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public String toString() {
        return "DtLibro{" +
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
        
        DtLibro dtLibro = (DtLibro) obj;
        
        if (id != null) {
            return id.equals(dtLibro.id);
        } else {
            return titulo != null && titulo.equals(dtLibro.titulo);
        }
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : (titulo != null ? titulo.hashCode() : 0);
    }
}

package datatypes;

/**
 * Data Transfer Object para Bibliotecario
 * Clase inmutable que representa los datos de un bibliotecario
 */
public final class DtBibliotecario {
    final private String numeroEmpleado;
    final private String nombre;
    final private String email;
    
    public DtBibliotecario(final String numeroEmpleado, final String nombre, final String email) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getNumeroEmpleado() { 
        return numeroEmpleado; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    @Override
    public String toString() {
        return "NUMERO EMPLEADO = " + numeroEmpleado + 
               "\nNOMBRE = " + nombre + 
               "\nEMAIL = " + email;
    }
}

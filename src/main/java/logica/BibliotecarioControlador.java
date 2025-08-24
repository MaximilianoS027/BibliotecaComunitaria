package logica;

import interfaces.IBibliotecarioControlador;
import datatypes.DtBibliotecario;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;

import java.util.List;

/**
 * Controlador específico para operaciones con Bibliotecarios
 * Implementa el principio de Single Responsibility
 */
public class BibliotecarioControlador implements IBibliotecarioControlador {
    
    private ManejadorBibliotecario manejadorBibliotecario;
    
    public BibliotecarioControlador() {
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
    }
    
    @Override
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new DatosInvalidosException("El número de empleado es obligatorio");
        }
        
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        // Crear entidad
        Bibliotecario bibliotecario = new Bibliotecario(
            numeroEmpleado.trim(), 
            nombre.trim(), 
            email.trim()
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!bibliotecario.tieneNumeroEmpleadoValido()) {
            throw new DatosInvalidosException("Número de empleado inválido");
        }
        
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        // Delegar al manejador
        manejadorBibliotecario.agregarBibliotecario(bibliotecario);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
            throws BibliotecarioNoExisteException {
        
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new BibliotecarioNoExisteException("Número de empleado inválido");
        }
        
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado.trim());
        
        // Convertir a DTO
        return new DtBibliotecario(
            bibliotecario.getNumeroEmpleado(),
            bibliotecario.getNombre(),
            bibliotecario.getEmail()
        );
    }
    
    @Override
    public String[] listarBibliotecarios() {
        List<Bibliotecario> bibliotecarios = manejadorBibliotecario.listarBibliotecarios();
        
        String[] resultado = new String[bibliotecarios.size()];
        for (int i = 0; i < bibliotecarios.size(); i++) {
            Bibliotecario b = bibliotecarios.get(i);
            resultado[i] = b.getNumeroEmpleado() + " - " + b.getNombre() + " (" + b.getEmail() + ")";
        }
        
        return resultado;
    }
    
    @Override
    public boolean existeBibliotecario(String numeroEmpleado) {
        try {
            manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado);
            return true;
        } catch (BibliotecarioNoExisteException e) {
            return false;
        }
    }
    
    @Override
    public void actualizarBibliotecario(String numeroEmpleado, String nombre, String email)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        
        // Verificar que existe
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado);
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        // Actualizar datos
        bibliotecario.setNombre(nombre.trim());
        bibliotecario.setEmail(email.trim());
        
        // Validaciones adicionales
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        // Delegar al manejador para actualizar en BD
// TODO: Descomentar cuando se implemente actualizarBibliotecario en ManejadorBibliotecario
// manejadorBibliotecario.actualizarBibliotecario(bibliotecario);
    }
}

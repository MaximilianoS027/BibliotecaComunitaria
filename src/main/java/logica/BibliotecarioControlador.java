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
    public void registrarBibliotecario(String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        // Validación de email mejorada
        if (email == null || email.trim().isEmpty()) {
            throw new DatosInvalidosException("El email es obligatorio");
        }
        
        String emailLimpio = email.trim().toLowerCase();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!emailLimpio.matches(emailRegex) || emailLimpio.length() < 5 || emailLimpio.length() > 100) {
            throw new DatosInvalidosException("El email debe ser válido y tener entre 5 y 100 caracteres");
        }
        
        // Crear entidad (ID se autogenera en ManejadorBibliotecario, numeroEmpleado ya no se usa como ID)
        Bibliotecario bibliotecario = new Bibliotecario(
            null, // numeroEmpleado ahora es solo un campo, no un identificador único al crear
            nombre.trim(), 
            emailLimpio
        );
        
        // Validaciones adicionales usando métodos de la entidad
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
    public void registrarBibliotecarioConPassword(String nombre, String email, String password) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        // Validación de email mejorada
        if (email == null || email.trim().isEmpty()) {
            throw new DatosInvalidosException("El email es obligatorio");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DatosInvalidosException("El password es obligatorio");
        }

        // Validaciones del password: (ya no se usa PasswordUtil)
        
        String emailLimpio = email.trim().toLowerCase();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!emailLimpio.matches(emailRegex) || emailLimpio.length() < 5 || emailLimpio.length() > 100) {
            throw new DatosInvalidosException("El email debe ser válido y tener entre 5 y 100 caracteres");
        }
        
        // Crear entidad con password (ID se autogenera en ManejadorBibliotecario, numeroEmpleado ya no se usa como ID)
        Bibliotecario bibliotecario = new Bibliotecario(
            null, // numeroEmpleado ahora es solo un campo, no un identificador único al crear
            nombre.trim(), 
            emailLimpio,
            password.trim()
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        if (!bibliotecario.tienePasswordValido()) {
            throw new DatosInvalidosException("Password inválido");
        }
        
        // Delegar al manejador
        manejadorBibliotecario.agregarBibliotecario(bibliotecario);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String id) 
            throws BibliotecarioNoExisteException {
        
        if (id == null || id.trim().isEmpty()) {
            throw new BibliotecarioNoExisteException("ID de bibliotecario inválido");
        }
        
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(id.trim());
        
        // Convertir a DTO
        return new DtBibliotecario(
            bibliotecario.getId(), // Usar el ID como identificador principal
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
            // Usar ID real (B1, B2...) como identificador principal
            resultado[i] = b.getId() + " - " + b.getNombre() + " (" + b.getEmail() + ")";
        }
        
        return resultado;
    }
    
    @Override
    public void actualizarBibliotecario(String id, String nombre, String email)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        
        // Verificar que existe
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(id);
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        // Validación de email mejorada
        if (email == null || email.trim().isEmpty()) {
            throw new DatosInvalidosException("El email es obligatorio");
        }
        
        String emailLimpio = email.trim().toLowerCase();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!emailLimpio.matches(emailRegex) || emailLimpio.length() < 5 || emailLimpio.length() > 100) {
            throw new DatosInvalidosException("El email debe ser válido y tener entre 5 y 100 caracteres");
        }
        
        // Actualizar datos
        bibliotecario.setNombre(nombre.trim());
        bibliotecario.setEmail(emailLimpio);
        
        // Validaciones adicionales
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        // Delegar al manejador para actualizar en BD
        manejadorBibliotecario.actualizarBibliotecario(bibliotecario);
    }
}

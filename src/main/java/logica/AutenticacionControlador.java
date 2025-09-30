package logica;

import interfaces.IAutenticacionControlador;
import excepciones.DatosInvalidosException;
import excepciones.LectorNoExisteException;
import excepciones.BibliotecarioNoExisteException;

/**
 * Controlador específico para operaciones de autenticación
 * Implementa el principio de Single Responsibility
 */
public class AutenticacionControlador implements IAutenticacionControlador {
    
    private ManejadorLector manejadorLector;
    private ManejadorBibliotecario manejadorBibliotecario;
    
    public AutenticacionControlador() {
        this.manejadorLector = ManejadorLector.getInstancia();
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
    }
    
    @Override
    public String autenticarLector(String email, String password) 
            throws LectorNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (email == null || email.trim().isEmpty()) {
            throw new DatosInvalidosException("El email es obligatorio");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DatosInvalidosException("El password es obligatorio");
        }
        
        // Buscar lector por email
        Lector lector = manejadorLector.obtenerLectorPorEmail(email);
        if (lector == null) {
            throw new LectorNoExisteException("No existe un lector con el email: " + email);
        }
        
        // Verificar password
        if (!lector.verifyPassword(password)) {
            throw new LectorNoExisteException("Credenciales incorrectas");
        }
        
        return lector.getId();
    }
    
    @Override
    public String autenticarBibliotecario(String email, String password) 
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (email == null || email.trim().isEmpty()) {
            throw new DatosInvalidosException("El email es obligatorio");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DatosInvalidosException("El password es obligatorio");
        }
        
        // Buscar bibliotecario por email
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecarioPorEmail(email);
        if (bibliotecario == null) {
            throw new BibliotecarioNoExisteException("No existe un bibliotecario con el email: " + email);
        }
        
        // Verificar password
        if (!bibliotecario.verifyPassword(password)) {
            throw new BibliotecarioNoExisteException("Credenciales incorrectas");
        }
        
        return bibliotecario.getId();
    }
    
    @Override
    public void cambiarPasswordLector(String lectorId, String passwordActual, String passwordNuevo)
            throws LectorNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (lectorId == null || lectorId.trim().isEmpty()) {
            throw new DatosInvalidosException("El ID del lector es obligatorio");
        }
        
        if (passwordActual == null || passwordActual.trim().isEmpty()) {
            throw new DatosInvalidosException("El password actual es obligatorio");
        }
        
        if (passwordNuevo == null || passwordNuevo.trim().isEmpty()) {
            throw new DatosInvalidosException("El password nuevo es obligatorio");
        }
        
        // Buscar lector
        Lector lector = manejadorLector.obtenerLector(lectorId);
        if (lector == null) {
            throw new LectorNoExisteException("No existe un lector con el ID: " + lectorId);
        }
        
        // Verificar password actual
        if (!lector.verifyPassword(passwordActual)) {
            throw new DatosInvalidosException("El password actual es incorrecto");
        }
        
        // Cambiar password
        lector.setPassword(passwordNuevo);
        manejadorLector.actualizarLector(lector);
    }
    
    @Override
    public void cambiarPasswordBibliotecario(String numeroEmpleado, String passwordActual, String passwordNuevo)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new DatosInvalidosException("El número de empleado es obligatorio");
        }
        
        if (passwordActual == null || passwordActual.trim().isEmpty()) {
            throw new DatosInvalidosException("El password actual es obligatorio");
        }
        
        if (passwordNuevo == null || passwordNuevo.trim().isEmpty()) {
            throw new DatosInvalidosException("El password nuevo es obligatorio");
        }
        
        // Buscar bibliotecario
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado);
        if (bibliotecario == null) {
            throw new BibliotecarioNoExisteException("No existe un bibliotecario con el número: " + numeroEmpleado);
        }
        
        // Verificar password actual
        if (!bibliotecario.verifyPassword(passwordActual)) {
            throw new DatosInvalidosException("El password actual es incorrecto");
        }
        
        // Cambiar password
        bibliotecario.setPassword(passwordNuevo);
        manejadorBibliotecario.actualizarBibliotecario(bibliotecario);
    }
    
    @Override
    public void restablecerPasswordLector(String lectorId, String passwordNuevo)
            throws LectorNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (lectorId == null || lectorId.trim().isEmpty()) {
            throw new DatosInvalidosException("El ID del lector es obligatorio");
        }
        
        if (passwordNuevo == null || passwordNuevo.trim().isEmpty()) {
            throw new DatosInvalidosException("El password nuevo es obligatorio");
        }
        
        // Buscar lector
        Lector lector = manejadorLector.obtenerLector(lectorId);
        if (lector == null) {
            throw new LectorNoExisteException("No existe un lector con el ID: " + lectorId);
        }
        
        // Restablecer password
        lector.setPassword(passwordNuevo);
        manejadorLector.actualizarLector(lector);
    }
    
    @Override
    public void restablecerPasswordBibliotecario(String numeroEmpleado, String passwordNuevo)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        
        // Validaciones
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new DatosInvalidosException("El número de empleado es obligatorio");
        }
        
        if (passwordNuevo == null || passwordNuevo.trim().isEmpty()) {
            throw new DatosInvalidosException("El password nuevo es obligatorio");
        }
        
        // Buscar bibliotecario
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado);
        if (bibliotecario == null) {
            throw new BibliotecarioNoExisteException("No existe un bibliotecario con el número: " + numeroEmpleado);
        }
        
        // Restablecer password
        bibliotecario.setPassword(passwordNuevo);
        manejadorBibliotecario.actualizarBibliotecario(bibliotecario);
    }
}

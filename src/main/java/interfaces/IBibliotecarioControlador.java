package interfaces;

import datatypes.DtBibliotecario;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface del controlador para operaciones relacionadas con Bibliotecarios
 * Principio de Single Responsibility: solo maneja bibliotecarios
 */
public interface IBibliotecarioControlador {
    
    /**
     * Registra un nuevo bibliotecario en el sistema
     * @param numeroEmpleado Número único del empleado
     * @param nombre Nombre completo del bibliotecario
     * @param email Email del bibliotecario
     * @throws BibliotecarioRepetidoException Si ya existe un bibliotecario con ese número
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
        throws BibliotecarioRepetidoException, DatosInvalidosException;
    
    /**
     * Registra un nuevo bibliotecario en el sistema con password
     * @param numeroEmpleado Número único del empleado
     * @param nombre Nombre completo del bibliotecario
     * @param email Email del bibliotecario
     * @param password Password del bibliotecario
     * @throws BibliotecarioRepetidoException Si ya existe un bibliotecario con ese número
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void registrarBibliotecarioConPassword(String numeroEmpleado, String nombre, String email, String password) 
        throws BibliotecarioRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un bibliotecario por su número de empleado
     * @param numeroEmpleado Número del empleado a buscar
     * @return Datos del bibliotecario
     * @throws BibliotecarioNoExisteException Si no existe el bibliotecario
     */
    DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
        throws BibliotecarioNoExisteException;
    
    /**
     * Lista todos los bibliotecarios del sistema
     * @return Array con los números de empleado de todos los bibliotecarios
     */
    String[] listarBibliotecarios();
    
    /**
     * Verifica si existe un bibliotecario con el número de empleado dado
     * @param numeroEmpleado Número a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existeBibliotecario(String numeroEmpleado);
    
    /**
     * Actualiza los datos de un bibliotecario existente
     * @param numeroEmpleado Número del empleado a actualizar
     * @param nombre Nuevo nombre
     * @param email Nuevo email
     * @throws BibliotecarioNoExisteException Si no existe el bibliotecario
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void actualizarBibliotecario(String numeroEmpleado, String nombre, String email)
        throws BibliotecarioNoExisteException, DatosInvalidosException;
}

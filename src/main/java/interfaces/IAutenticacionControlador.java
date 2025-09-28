package interfaces;

import excepciones.DatosInvalidosException;
import excepciones.LectorNoExisteException;
import excepciones.BibliotecarioNoExisteException;

/**
 * Interface para operaciones de autenticación de usuarios
 */
public interface IAutenticacionControlador {
    
    /**
     * Autentica un lector con nombre y password
     * @param nombre Nombre del lector
     * @param password Password del lector
     * @return ID del lector si la autenticación es exitosa
     * @throws LectorNoExisteException Si no existe el lector o las credenciales son incorrectas
     * @throws DatosInvalidosException Si los datos son inválidos
     */
    String autenticarLector(String nombre, String password) 
        throws LectorNoExisteException, DatosInvalidosException;
    
    /**
     * Autentica un bibliotecario con nombre y password
     * @param nombre Nombre del bibliotecario
     * @param password Password del bibliotecario
     * @return ID del bibliotecario si la autenticación es exitosa
     * @throws BibliotecarioNoExisteException Si no existe el bibliotecario o las credenciales son incorrectas
     * @throws DatosInvalidosException Si los datos son inválidos
     */
    String autenticarBibliotecario(String nombre, String password) 
        throws BibliotecarioNoExisteException, DatosInvalidosException;
    
    /**
     * Cambia el password de un lector
     * @param lectorId ID del lector
     * @param passwordActual Password actual
     * @param passwordNuevo Nuevo password
     * @throws LectorNoExisteException Si no existe el lector
     * @throws DatosInvalidosException Si los datos son inválidos o el password actual es incorrecto
     */
    void cambiarPasswordLector(String lectorId, String passwordActual, String passwordNuevo)
        throws LectorNoExisteException, DatosInvalidosException;
    
    /**
     * Cambia el password de un bibliotecario
     * @param numeroEmpleado Número de empleado del bibliotecario
     * @param passwordActual Password actual
     * @param passwordNuevo Nuevo password
     * @throws BibliotecarioNoExisteException Si no existe el bibliotecario
     * @throws DatosInvalidosException Si los datos son inválidos o el password actual es incorrecto
     */
    void cambiarPasswordBibliotecario(String numeroEmpleado, String passwordActual, String passwordNuevo)
        throws BibliotecarioNoExisteException, DatosInvalidosException;
    
    /**
     * Restablece el password de un lector (solo para administradores)
     * @param lectorId ID del lector
     * @param passwordNuevo Nuevo password
     * @throws LectorNoExisteException Si no existe el lector
     * @throws DatosInvalidosException Si los datos son inválidos
     */
    void restablecerPasswordLector(String lectorId, String passwordNuevo)
        throws LectorNoExisteException, DatosInvalidosException;
    
    /**
     * Restablece el password de un bibliotecario (solo para administradores)
     * @param numeroEmpleado Número de empleado del bibliotecario
     * @param passwordNuevo Nuevo password
     * @throws BibliotecarioNoExisteException Si no existe el bibliotecario
     * @throws DatosInvalidosException Si los datos son inválidos
     */
    void restablecerPasswordBibliotecario(String numeroEmpleado, String passwordNuevo)
        throws BibliotecarioNoExisteException, DatosInvalidosException;
}

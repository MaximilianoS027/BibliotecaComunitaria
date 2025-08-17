package interfaces;

import datatypes.DtBibliotecario;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface principal del controlador del sistema
 * Define todas las operaciones disponibles
 */
public interface IControlador {
    
    // Operaciones de Bibliotecario
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
        throws BibliotecarioRepetidoException, DatosInvalidosException;
    
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
        throws BibliotecarioNoExisteException;
    
    public String[] listarBibliotecarios();
}

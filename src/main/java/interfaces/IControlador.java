package interfaces;

import datatypes.DtBibliotecario;
import datatypes.DtLector;
import datatypes.DtLibro;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
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
    
    // Operaciones de Lector
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
        throws LectorRepetidoException, DatosInvalidosException;
    
    public DtLector obtenerLector(String id) 
        throws LectorNoExisteException;
    
    public DtLector obtenerLectorPorEmail(String email) 
        throws LectorNoExisteException;
    
    public String[] listarLectores();
    
    public String[] listarLectoresPorEstado(String estado);
    
    public String[] listarLectoresPorZona(String zona);
    
    // Operaciones de Libro
    public void registrarLibro(String titulo, int cantidadPaginas) 
        throws LibroRepetidoException, DatosInvalidosException;
    
    public DtLibro obtenerLibro(String id) 
        throws LibroNoExisteException;
    
    public DtLibro obtenerLibroPorTitulo(String titulo) 
        throws LibroNoExisteException;
    
    public String[] listarLibros();
    
    public String[] listarLibrosPorPaginas(int paginasMin, int paginasMax);
}

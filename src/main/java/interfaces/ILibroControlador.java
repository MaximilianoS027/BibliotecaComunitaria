package interfaces;

import datatypes.DtLibro;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface para el controlador de libros
 * Siguiendo el principio de Single Responsibility
 */
public interface ILibroControlador {
    
    // Operaciones de Libro
    public void registrarLibro(String titulo, int cantidadPaginas)
        throws LibroRepetidoException, DatosInvalidosException;
    
    public DtLibro obtenerLibro(String id) throws LibroNoExisteException;
    
    public DtLibro obtenerLibroPorTitulo(String titulo) throws LibroNoExisteException;
    
    public String[] listarLibros();
    
    public String[] listarLibrosPorPaginas(int paginasMin, int paginasMax);
    
    public boolean existeLibro(String titulo);
    
    public void actualizarLibro(String id, String titulo, int cantidadPaginas)
        throws LibroNoExisteException, DatosInvalidosException;
}

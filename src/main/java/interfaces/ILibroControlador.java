package interfaces;

import datatypes.DtLibro;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface del controlador para operaciones relacionadas con Libros
 * Principio de Single Responsibility: solo maneja libros
 */
public interface ILibroControlador {
    
    /**
     * Registra una nueva donación de libros en el sistema
     * @param titulo Título del libro
     * @param cantidadPaginas Cantidad de páginas del libro
     * @throws LibroRepetidoException Si ya existe un libro con ese título
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void registrarLibro(String titulo, int cantidadPaginas) 
        throws LibroRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un libro por su ID
     * @param id ID del libro a buscar
     * @return Datos del libro
     * @throws LibroNoExisteException Si no existe el libro
     */
    DtLibro obtenerLibro(String id) 
        throws LibroNoExisteException;
    
    /**
     * Obtiene un libro por su título
     * @param titulo Título del libro a buscar
     * @return Datos del libro
     * @throws LibroNoExisteException Si no existe el libro
     */
    DtLibro obtenerLibroPorTitulo(String titulo) 
        throws LibroNoExisteException;
    
    /**
     * Lista todos los libros del sistema
     * @return Array con información básica de todos los libros
     */
    String[] listarLibros();
    
    /**
     * Lista libros por rango de páginas
     * @param paginasMin Cantidad mínima de páginas
     * @param paginasMax Cantidad máxima de páginas
     * @return Array con libros en ese rango
     */
    String[] listarLibrosPorPaginas(int paginasMin, int paginasMax);
    
    /**
     * Verifica si existe un libro con el título dado
     * @param titulo Título a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existeLibro(String titulo);
    
    /**
     * Actualiza los datos de un libro existente
     * @param id ID del libro a actualizar
     * @param titulo Nuevo título
     * @param cantidadPaginas Nueva cantidad de páginas
     * @throws LibroNoExisteException Si no existe el libro
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void actualizarLibro(String id, String titulo, int cantidadPaginas)
        throws LibroNoExisteException, DatosInvalidosException;
}

package interfaces;

import datatypes.DtLector;
import datatypes.DtBibliotecario;
import datatypes.DtLibro;
import datatypes.DtArticuloEspecial;
import datatypes.DtPrestamo;
import excepciones.*;

/**
 * Interface principal del controlador del sistema
 * Define todas las operaciones disponibles en el sistema de biblioteca
 */
public interface IControlador {
    
    // ============= OPERACIONES DE BIBLIOTECARIO =============
    
    /**
     * Registra un nuevo bibliotecario en el sistema
     */
    void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
        throws BibliotecarioRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un bibliotecario por su ID
     */
    DtBibliotecario obtenerBibliotecario(String id) throws BibliotecarioNoExisteException;
    
    /**
     * Lista todos los bibliotecarios del sistema
     */
    String[] listarBibliotecarios();
    
    // ============= OPERACIONES DE LECTOR =============
    
    /**
     * Registra un nuevo lector en el sistema
     */
    void registrarLector(String nombre, String email, String direccion, 
                        String fechaRegistro, String estado, String zona) 
        throws LectorRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un lector por su ID
     */
    DtLector obtenerLector(String id) throws LectorNoExisteException;
    
    /**
     * Obtiene un lector por su email
     */
    DtLector obtenerLectorPorEmail(String email) throws LectorNoExisteException;
    
    /**
     * Lista todos los lectores del sistema
     */
    String[] listarLectores();
    
    /**
     * Lista lectores filtrados por estado
     */
    String[] listarLectoresPorEstado(String estado);
    
    /**
     * Lista lectores filtrados por zona
     */
    String[] listarLectoresPorZona(String zona);
    
    /**
     * Cambia el estado de un lector
     */
    void cambiarEstadoLector(String idLector, String nuevoEstado) 
        throws LectorNoExisteException, DatosInvalidosException;
    
    /**
     * Obtiene el ID de un lector por su nombre y email
     */
    String obtenerIdLectorPorNombreEmail(String nombre, String email) 
        throws LectorNoExisteException;
    
    /**
     * Cambia la zona de un lector
     */
    void cambiarZonaLector(String idLector, String nuevaZona) 
        throws LectorNoExisteException, DatosInvalidosException;
    
    // ============= OPERACIONES DE LIBRO =============
    
    /**
     * Registra un nuevo libro en el sistema
     */
    void registrarLibro(String titulo, String cantidadPaginas, String fechaRegistro) 
        throws LibroRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un libro por su ID
     */
    DtLibro obtenerLibro(String id) throws LibroNoExisteException;
    
    /**
     * Lista todos los libros del sistema
     */
    String[] listarLibros();
    
    // ============= OPERACIONES DE ARTÍCULO ESPECIAL =============
    
    /**
     * Registra un nuevo artículo especial en el sistema
     */
    void registrarArticuloEspecial(String descripcion, String pesoKg, String dimensiones) 
        throws ArticuloEspecialRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un artículo especial por su ID
     */
    DtArticuloEspecial obtenerArticuloEspecial(String id) throws ArticuloEspecialNoExisteException;
    
    /**
     * Lista todos los artículos especiales del sistema
     */
    String[] listarArticulosEspeciales();
    
    // ============= OPERACIONES DE PRÉSTAMO =============
    
    /**
     * Registra un nuevo préstamo en el sistema
     */
    void registrarPrestamo(String lectorId, String bibliotecarioId, String materialId, 
                          String fechaSolicitud, String estado) throws DatosInvalidosException;
    
    /**
     * Obtiene un préstamo por su ID
     */
    DtPrestamo obtenerPrestamo(String id) throws PrestamoNoExisteException;
    
    /**
     * Lista todos los préstamos del sistema
     */
    String[] listarPrestamos();
    
    /**
     * Lista préstamos filtrados por estado
     */
    String[] listarPrestamosPorEstado(String estado);
    
    /**
     * Lista préstamos de un lector específico
     */
    String[] listarPrestamosPorLector(String lectorId);
    
    /**
     * Lista préstamos de un material específico
     */
    String[] listarPrestamosPorMaterial(String materialId);
    
    /**
     * Cambia el estado de un préstamo
     */
    void cambiarEstadoPrestamo(String idPrestamo, String nuevoEstado) 
        throws PrestamoNoExisteException, DatosInvalidosException;
    
    /**
     * Registra la devolución de un préstamo
     */
    void devolverPrestamo(String idPrestamo, String fechaDevolucion) 
        throws PrestamoNoExisteException, DatosInvalidosException;
}

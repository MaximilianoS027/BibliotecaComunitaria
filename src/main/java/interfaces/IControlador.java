package interfaces;

import datatypes.DtBibliotecario;
import datatypes.DtLector;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.DatosInvalidosException;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.LibroRepetidoException;

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
    
    // Operación para cambiar estado de lector
    public void cambiarEstadoLector(String idLector, String nuevoEstado) 
        throws LectorNoExisteException, DatosInvalidosException;
    
    // Obtener ID de lector por nombre y email
    public String obtenerIdLectorPorNombreEmail(String nombre, String email) 
        throws LectorNoExisteException;
    
    // Operación para cambiar zona de lector
    public void cambiarZonaLector(String idLector, String nuevaZona) 
        throws LectorNoExisteException, DatosInvalidosException;
    
    // Operaciones de Material - Artículo Especial
    public void registrarArticuloEspecial(String descripcion, Double pesoKg, String dimensiones)
        throws ArticuloEspecialRepetidoException, DatosInvalidosException;
    
    // Operaciones de Material - Libro
    public void registrarLibro(String titulo, Integer cantidadPaginas)
        throws LibroRepetidoException, DatosInvalidosException;
}

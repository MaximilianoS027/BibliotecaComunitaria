package logica;

import interfaces.IControlador;
// No necesitamos imports adicionales ya que están en el mismo paquete logica
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
 * Controlador principal que actúa como coordinador/facade
 * Delega las operaciones a controladores específicos por funcionalidad
 * Implementa el patrón Facade para mantener compatibilidad con la interfaz existente
 */
public class Controlador implements IControlador {
    
    // Controladores específicos por funcionalidad
    private BibliotecarioControlador bibliotecarioControlador;
    private LectorControlador lectorControlador;
    private LibroControlador libroControlador;
    private ArticuloEspecialControlador articuloEspecialControlador;
    
    public Controlador() {
        this.bibliotecarioControlador = new BibliotecarioControlador();
        this.lectorControlador = new LectorControlador();
        this.libroControlador = new LibroControlador();
        this.articuloEspecialControlador = new ArticuloEspecialControlador();
    }
    
    // ============= OPERACIONES DE BIBLIOTECARIO (delegadas) =============
    
    @Override
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        bibliotecarioControlador.registrarBibliotecario(numeroEmpleado, nombre, email);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
            throws BibliotecarioNoExisteException {
        return bibliotecarioControlador.obtenerBibliotecario(numeroEmpleado);
    }
    
    @Override
    public String[] listarBibliotecarios() {
        return bibliotecarioControlador.listarBibliotecarios();
    }
    
    // ============= OPERACIONES DE LECTOR (delegadas) =============
    
    @Override
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        lectorControlador.registrarLector(nombre, email, direccion, fechaRegistro, estado, zona);
    }
    
    @Override
    public DtLector obtenerLector(String id) 
            throws LectorNoExisteException {
        return lectorControlador.obtenerLector(id);
    }
    
    @Override
    public DtLector obtenerLectorPorEmail(String email) 
            throws LectorNoExisteException {
        return lectorControlador.obtenerLectorPorEmail(email);
    }
    
    @Override
    public String[] listarLectores() {
        return lectorControlador.listarLectores();
    }
    
    @Override
    public String[] listarLectoresPorEstado(String estado) {
        return lectorControlador.listarLectoresPorEstado(estado);
    }
    
    @Override
    public String[] listarLectoresPorZona(String zona) {
        return lectorControlador.listarLectoresPorZona(zona);
    }
    
    @Override
    public void cambiarEstadoLector(String idLector, String nuevoEstado) 
            throws LectorNoExisteException, DatosInvalidosException {
        // Implementación temporal - necesitaría agregarse al LectorControlador
        // Por ahora mantenemos compatibilidad
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new DatosInvalidosException("El nuevo estado es obligatorio");
        }
        
        // TODO: Delegar al lectorControlador cuando se implemente el método
        throw new UnsupportedOperationException("Método pendiente de implementación en LectorControlador");
    }
    
    @Override
    public String obtenerIdLectorPorNombreEmail(String nombre, String email) 
            throws LectorNoExisteException {
        if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Nombre y email son obligatorios");
        }
        
        // TODO: Delegar al lectorControlador cuando se implemente el método
        throw new UnsupportedOperationException("Método pendiente de implementación en LectorControlador");
    }
    
    @Override
    public void cambiarZonaLector(String idLector, String nuevaZona) 
            throws LectorNoExisteException, DatosInvalidosException {
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        
        if (nuevaZona == null || nuevaZona.trim().isEmpty()) {
            throw new DatosInvalidosException("La nueva zona es obligatoria");
        }
        
        // TODO: Delegar al lectorControlador cuando se implemente el método
        throw new UnsupportedOperationException("Método pendiente de implementación en LectorControlador");
    }
    
    // ============= OPERACIONES DE MATERIAL (delegadas) =============
    
    @Override
    public void registrarArticuloEspecial(String descripcion, Double pesoKg, String dimensiones)
            throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        if (pesoKg == null) {
            throw new DatosInvalidosException("El peso es obligatorio");
        }
        articuloEspecialControlador.registrarArticuloEspecial(descripcion, pesoKg.floatValue(), dimensiones);
    }
    
    @Override
    public void registrarLibro(String titulo, Integer cantidadPaginas)
            throws LibroRepetidoException, DatosInvalidosException {
        if (cantidadPaginas == null) {
            throw new DatosInvalidosException("La cantidad de páginas es obligatoria");
        }
        libroControlador.registrarLibro(titulo, cantidadPaginas.intValue());
    }
}
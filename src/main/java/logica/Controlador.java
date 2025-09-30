package logica;

import datatypes.DtLector;
import datatypes.DtBibliotecario;
import datatypes.DtLibro;
import datatypes.DtArticuloEspecial;
import datatypes.DtPrestamo;
import excepciones.*;
import interfaces.IControlador;
import interfaces.IBibliotecarioControlador;
import interfaces.ILectorControlador;
import interfaces.ILibroControlador;
import interfaces.IArticuloEspecialControlador;
import interfaces.IPrestamoControlador;
import interfaces.IAutenticacionControlador;

/**
 * Controlador principal que coordina todas las operaciones del sistema
 * Implementa el patrón Facade para simplificar el acceso a los controladores específicos
 */
public class Controlador implements IControlador {
    
    private IBibliotecarioControlador bibliotecarioControlador;
    private ILectorControlador lectorControlador;
    private ILibroControlador libroControlador;
    private IArticuloEspecialControlador articuloEspecialControlador;
    private IPrestamoControlador prestamoControlador;
    private IAutenticacionControlador autenticacionControlador;
    
    // Instancia única (Singleton)
    private static Controlador instancia;
    
    private Controlador() {
        this.bibliotecarioControlador = new BibliotecarioControlador();
        this.lectorControlador = new LectorControlador();
        this.libroControlador = new LibroControlador();
        this.articuloEspecialControlador = new ArticuloEspecialControlador();
        this.prestamoControlador = new PrestamoControlador();
        this.autenticacionControlador = new AutenticacionControlador();
    }
    
    public static Controlador getInstancia() {
        if (instancia == null) {
            instancia = new Controlador();
        }
        return instancia;
    }
    
    // ============= OPERACIONES DE BIBLIOTECARIO (delegadas) =============
    
    @Override
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        bibliotecarioControlador.registrarBibliotecario(numeroEmpleado, nombre, email);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String id) 
            throws BibliotecarioNoExisteException {
        return bibliotecarioControlador.obtenerBibliotecario(id);
    }
    
    @Override
    public String[] listarBibliotecarios() {
        return bibliotecarioControlador.listarBibliotecarios();
    }
    
    @Override
    public void registrarBibliotecarioConPassword(String numeroEmpleado, String nombre, String email, String password) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        bibliotecarioControlador.registrarBibliotecarioConPassword(numeroEmpleado, nombre, email, password);
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
        
        // Delegar al lectorControlador
        lectorControlador.cambiarZonaLector(idLector, nuevaZona);
    }
    
    @Override
    public void registrarLectorConPassword(String nombre, String email, String password, String direccion, 
                                          String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        lectorControlador.registrarLectorConPassword(nombre, email, password, direccion, fechaRegistro, estado, zona);
    }
    
    // ============= OPERACIONES DE AUTENTICACIÓN (delegadas) =============
    
    @Override
    public String autenticarLector(String email, String password)
            throws LectorNoExisteException, DatosInvalidosException {
        return autenticacionControlador.autenticarLector(email, password);
    }

    @Override
    public String autenticarBibliotecario(String email, String password)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        return autenticacionControlador.autenticarBibliotecario(email, password);
    }
    
    @Override
    public void cambiarPasswordLector(String lectorId, String passwordActual, String passwordNuevo)
            throws LectorNoExisteException, DatosInvalidosException {
        autenticacionControlador.cambiarPasswordLector(lectorId, passwordActual, passwordNuevo);
    }
    
    @Override
    public void cambiarPasswordBibliotecario(String numeroEmpleado, String passwordActual, String passwordNuevo)
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        autenticacionControlador.cambiarPasswordBibliotecario(numeroEmpleado, passwordActual, passwordNuevo);
    }
    
    // ============= OPERACIONES DE LIBRO (delegadas) =============
    
    @Override
    public void registrarLibro(String titulo, String cantidadPaginas, String fechaRegistro) 
            throws LibroRepetidoException, DatosInvalidosException {
        int paginas = Integer.parseInt(cantidadPaginas);
        libroControlador.registrarLibro(titulo, paginas);
    }
    
    @Override
    public DtLibro obtenerLibro(String id) 
            throws LibroNoExisteException {
        return libroControlador.obtenerLibro(id);
    }
    
    @Override
    public String[] listarLibros() {
        return libroControlador.listarLibros();
    }
    
    // ============= OPERACIONES DE ARTÍCULO ESPECIAL (delegadas) =============
    
    @Override
    public void registrarArticuloEspecial(String descripcion, String pesoKg, String dimensiones) 
            throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        float peso = Float.parseFloat(pesoKg);
        articuloEspecialControlador.registrarArticuloEspecial(descripcion, peso, dimensiones);
    }
    
    @Override
    public DtArticuloEspecial obtenerArticuloEspecial(String id) 
            throws ArticuloEspecialNoExisteException {
        return articuloEspecialControlador.obtenerArticuloEspecial(id);
    }
    
    @Override
    public String[] listarArticulosEspeciales() {
        return articuloEspecialControlador.listarArticulosEspeciales();
    }
    
    // ============= OPERACIONES DE PRÉSTAMO (delegadas) =============
    
    @Override
    public void registrarPrestamo(String lectorId, String bibliotecarioId, String materialId, 
                                 String fechaSolicitud, String estado) throws DatosInvalidosException {
        prestamoControlador.registrarPrestamo(lectorId, bibliotecarioId, materialId, fechaSolicitud, estado);
    }
    
    @Override
    public DtPrestamo obtenerPrestamo(String id) 
            throws PrestamoNoExisteException {
        return prestamoControlador.obtenerPrestamo(id);
    }
    
    @Override
    public String[] listarPrestamos() {
        return prestamoControlador.listarPrestamos();
    }
    
    @Override
    public String[] listarPrestamosPorEstado(String estado) {
        return prestamoControlador.listarPrestamosPorEstado(estado);
    }
    
    @Override
    public String[] listarPrestamosPorLector(String lectorId) {
        return prestamoControlador.listarPrestamosPorLector(lectorId);
    }
    
    @Override
    public String[] listarPrestamosPorMaterial(String materialId) {
        return prestamoControlador.listarPrestamosPorMaterial(materialId);
    }
    
    @Override
    public void cambiarEstadoPrestamo(String idPrestamo, String nuevoEstado) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        prestamoControlador.cambiarEstadoPrestamo(idPrestamo, nuevoEstado);
    }
    
    @Override
    public void devolverPrestamo(String idPrestamo, String fechaDevolucion) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        prestamoControlador.devolverPrestamo(idPrestamo, fechaDevolucion);
    }
    
    @Override
    public void modificarPrestamo(String idPrestamo, String lectorId, String bibliotecarioId, 
                                 String materialId, String fechaSolicitud, String estado, 
                                 String fechaDevolucion) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        prestamoControlador.modificarPrestamo(idPrestamo, lectorId, bibliotecarioId, 
                                             materialId, fechaSolicitud, estado, fechaDevolucion);
    }
    
}

package logica;

import interfaces.IControlador;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * Controlador principal que implementa la lógica de negocio
 */
public class Controlador implements IControlador {
    
    private ManejadorBibliotecario manejadorBibliotecario;
    private ManejadorLector manejadorLector;
    private ManejadorLibro manejadorLibro;
    
    public Controlador() {
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
        this.manejadorLector = ManejadorLector.getInstancia();
        this.manejadorLibro = ManejadorLibro.getInstancia();
    }
    
    @Override
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new DatosInvalidosException("El número de empleado es obligatorio");
        }
        
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        // Crear entidad
        Bibliotecario bibliotecario = new Bibliotecario(
            numeroEmpleado.trim(), 
            nombre.trim(), 
            email.trim()
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!bibliotecario.tieneNumeroEmpleadoValido()) {
            throw new DatosInvalidosException("Número de empleado inválido");
        }
        
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        // Delegar al manejador
        manejadorBibliotecario.agregarBibliotecario(bibliotecario);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
            throws BibliotecarioNoExisteException {
        
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new BibliotecarioNoExisteException("Número de empleado inválido");
        }
        
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado.trim());
        
        // Convertir a DTO
        return new DtBibliotecario(
            bibliotecario.getNumeroEmpleado(),
            bibliotecario.getNombre(),
            bibliotecario.getEmail()
        );
    }
    
    @Override
    public String[] listarBibliotecarios() {
        List<Bibliotecario> bibliotecarios = manejadorBibliotecario.listarBibliotecarios();
        
        String[] resultado = new String[bibliotecarios.size()];
        for (int i = 0; i < bibliotecarios.size(); i++) {
            Bibliotecario b = bibliotecarios.get(i);
            resultado[i] = b.getNumeroEmpleado() + " - " + b.getNombre() + " (" + b.getEmail() + ")";
        }
        
        return resultado;
    }
    
    @Override
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        if (direccion == null || direccion.trim().isEmpty() || direccion.length() < 5) {
            throw new DatosInvalidosException("La dirección debe tener al menos 5 caracteres");
        }
        
        if (fechaRegistro == null || fechaRegistro.trim().isEmpty()) {
            throw new DatosInvalidosException("La fecha de registro es obligatoria");
        }
        
        if (estado == null || estado.trim().isEmpty()) {
            throw new DatosInvalidosException("El estado es obligatorio");
        }
        
        if (zona == null || zona.trim().isEmpty()) {
            throw new DatosInvalidosException("La zona es obligatoria");
        }
        
        // Verificar si ya existe un lector con ese email
        if (manejadorLector.existeLectorConEmail(email.trim())) {
            throw new LectorRepetidoException("Ya existe un lector con el email: " + email);
        }
        
        // Parsear fecha
        Date fecha;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.parse(fechaRegistro.trim());
        } catch (ParseException e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Use dd/MM/yyyy");
        }
        
        // Parsear estado
        EstadoLector estadoLector;
        try {
            // Convertir el valor descriptivo a la enumeración
            String estadoStr = estado.trim();
            if (estadoStr.equalsIgnoreCase("Activo")) {
                estadoLector = EstadoLector.ACTIVO;
            } else if (estadoStr.equalsIgnoreCase("Suspendido")) {
                estadoLector = EstadoLector.SUSPENDIDO;
            } else {
                throw new IllegalArgumentException("Estado inválido");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Estado inválido. Valores válidos: Activo, Suspendido");
        }
        
        // Parsear zona
        Zona zonaLector;
        try {
            // Convertir el valor descriptivo a la enumeración
            String zonaStr = zona.trim();
            if (zonaStr.equalsIgnoreCase("Biblioteca Central")) {
                zonaLector = Zona.BIBLIOTECA_CENTRAL;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Este")) {
                zonaLector = Zona.SUCURSAL_ESTE;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Oeste")) {
                zonaLector = Zona.SUCURSAL_OESTE;
            } else if (zonaStr.equalsIgnoreCase("Biblioteca Infantil")) {
                zonaLector = Zona.BIBLIOTECA_INFANTIL;
            } else if (zonaStr.equalsIgnoreCase("Archivo General")) {
                zonaLector = Zona.ARCHIVO_GENERAL;
            } else {
                throw new IllegalArgumentException("Zona inválida");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Zona inválida. Valores válidos: Biblioteca Central, Sucursal Este, Sucursal Oeste, Biblioteca Infantil, Archivo General");
        }
        
        // Crear entidad
        Lector lector = new Lector(
            nombre.trim(), 
            email.trim(), 
            direccion.trim(),
            fecha,
            estadoLector,
            zonaLector
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!lector.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!lector.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        if (!lector.tieneDireccionValida()) {
            throw new DatosInvalidosException("Dirección inválida");
        }
        
        if (!lector.tieneFechaRegistroValida()) {
            throw new DatosInvalidosException("La fecha de registro debe ser anterior a la fecha actual");
        }
        
        // Delegar al manejador
        manejadorLector.agregarLector(lector);
    }
    
    @Override
    public DtLector obtenerLector(String id) 
            throws LectorNoExisteException {
        
        if (id == null || id.trim().isEmpty()) {
            throw new LectorNoExisteException("ID de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLector(id.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con ID: " + id);
        }
        
        // Convertir a DTO
        return new DtLector(
            lector.getId(),
            lector.getNombre(),
            lector.getEmail(),
            lector.getDireccion(),
            lector.getFechaRegistro(),
            lector.getEstado(),
            lector.getZona()
        );
    }
    
    @Override
    public DtLector obtenerLectorPorEmail(String email) 
            throws LectorNoExisteException {
        
        if (email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Email de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLectorPorEmail(email.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con email: " + email);
        }
        
        // Convertir a DTO
        return new DtLector(
            lector.getId(),
            lector.getNombre(),
            lector.getEmail(),
            lector.getDireccion(),
            lector.getFechaRegistro(),
            lector.getEstado(),
            lector.getZona()
        );
    }
    
    @Override
    public String[] listarLectores() {
        List<Lector> lectores = manejadorLector.listarLectores();
        
        String[] resultado = new String[lectores.size()];
        for (int i = 0; i < lectores.size(); i++) {
            Lector l = lectores.get(i);
            resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ") - " + l.getEstado();
        }
        
        return resultado;
    }
    
    @Override
    public String[] listarLectoresPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            EstadoLector estadoLector = EstadoLector.valueOf(estado.trim().toUpperCase());
            List<Lector> lectores = manejadorLector.listarLectoresPorEstado(estadoLector);
            
            String[] resultado = new String[lectores.size()];
            for (int i = 0; i < lectores.size(); i++) {
                Lector l = lectores.get(i);
                resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ")";
            }
            
            return resultado;
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    @Override
    public String[] listarLectoresPorZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            Zona zonaLector = Zona.valueOf(zona.trim().toUpperCase());
            List<Lector> lectores = manejadorLector.listarLectoresPorZona(zonaLector);
            
            String[] resultado = new String[lectores.size()];
            for (int i = 0; i < lectores.size(); i++) {
                Lector l = lectores.get(i);
                resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ") - " + l.getZona();
            }
            
            return resultado;
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    // Implementación de operaciones de Libro
    @Override
    public void registrarLibro(String titulo, int cantidadPaginas) 
            throws LibroRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatosInvalidosException("El título es obligatorio");
        }
        
        if (titulo.trim().length() < 2) {
            throw new DatosInvalidosException("El título debe tener al menos 2 caracteres");
        }
        
        if (titulo.trim().length() > 255) {
            throw new DatosInvalidosException("El título no puede exceder los 255 caracteres");
        }
        
        if (cantidadPaginas <= 0) {
            throw new DatosInvalidosException("La cantidad de páginas debe ser mayor a 0");
        }
        
        if (cantidadPaginas > 10000) {
            throw new DatosInvalidosException("La cantidad de páginas no puede exceder las 10,000 páginas");
        }
        
        // Crear entidad
        Libro libro = new Libro(titulo.trim(), cantidadPaginas);
        
        // Validaciones adicionales usando métodos de la entidad
        if (!libro.tieneTituloValido()) {
            throw new DatosInvalidosException("Título inválido");
        }
        
        if (!libro.tieneCantidadPaginasValida()) {
            throw new DatosInvalidosException("Cantidad de páginas inválida");
        }
        
        if (!libro.tieneFechaRegistroValida()) {
            throw new DatosInvalidosException("Fecha de registro inválida");
        }
        
        // Delegar al manejador
        manejadorLibro.agregarLibro(libro);
    }
    
    @Override
    public DtLibro obtenerLibro(String id) throws LibroNoExisteException {
        
        if (id == null || id.trim().isEmpty()) {
            throw new LibroNoExisteException("ID de libro inválido");
        }
        
        Libro libro = manejadorLibro.obtenerLibro(id.trim());
        
        // Convertir a DTO
        return new DtLibro(
            libro.getId(),
            libro.getTitulo(),
            libro.getCantidadPaginas(),
            libro.getFechaRegistro()
        );
    }
    
    @Override
    public DtLibro obtenerLibroPorTitulo(String titulo) throws LibroNoExisteException {
        
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new LibroNoExisteException("Título de libro inválido");
        }
        
        Libro libro = manejadorLibro.obtenerLibroPorTitulo(titulo.trim());
        
        // Convertir a DTO
        return new DtLibro(
            libro.getId(),
            libro.getTitulo(),
            libro.getCantidadPaginas(),
            libro.getFechaRegistro()
        );
    }
    
    @Override
    public String[] listarLibros() {
        List<Libro> libros = manejadorLibro.listarLibros();
        
        String[] resultado = new String[libros.size()];
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            String categoria = "";
            if (l.esLibroCorto()) {
                categoria = " [Corto]";
            } else if (l.esLibroMedio()) {
                categoria = " [Medio]";
            } else if (l.esLibroLargo()) {
                categoria = " [Largo]";
            }
            resultado[i] = l.getTitulo() + " - " + l.getCantidadPaginas() + " páginas" + categoria;
        }
        
        return resultado;
    }
    
    @Override
    public String[] listarLibrosPorPaginas(int paginasMin, int paginasMax) {
        if (paginasMin < 0 || paginasMax < 0 || paginasMin > paginasMax) {
            return new String[0];
        }
        
        List<Libro> libros = manejadorLibro.listarLibrosPorPaginas(paginasMin, paginasMax);
        
        String[] resultado = new String[libros.size()];
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            resultado[i] = l.getTitulo() + " - " + l.getCantidadPaginas() + " páginas";
        }
        
        return resultado;
    }
}

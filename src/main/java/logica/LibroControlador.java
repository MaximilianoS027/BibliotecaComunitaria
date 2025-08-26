package logica;

import interfaces.ILibroControlador;
import datatypes.DtLibro;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
import excepciones.DatosInvalidosException;

import java.util.List;

/**
 * Controlador específico para operaciones con libros
 * Implementa la lógica de negocio relacionada con libros
 */
public class LibroControlador implements ILibroControlador {
    
    private ManejadorLibro manejadorLibro;
    
    public LibroControlador() {
        this.manejadorLibro = ManejadorLibro.getInstancia();
    }
    
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
        
        if (libro == null) {
            throw new LibroNoExisteException("No existe un libro con ID: " + id);
        }
        
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
            resultado[i] = l.getTitulo() + " - " + l.getCantidadPaginas() + " páginas";
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
    
    @Override
    public boolean existeLibro(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return false;
        }
        return manejadorLibro.existeLibroConTitulo(titulo.trim());
    }
    
    @Override
    public void actualizarLibro(String id, String titulo, int cantidadPaginas)
            throws LibroNoExisteException, DatosInvalidosException {
        
        // Validaciones de datos
        if (id == null || id.trim().isEmpty()) {
            throw new LibroNoExisteException("ID de libro inválido");
        }
        
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
        
        // Obtener el libro existente
        Libro libroExistente = manejadorLibro.obtenerLibro(id.trim());
        
        if (libroExistente == null) {
            throw new LibroNoExisteException("No existe un libro con ID: " + id);
        }
        
        // Actualizar los datos
        libroExistente.setTitulo(titulo.trim());
        libroExistente.setCantidadPaginas(cantidadPaginas);
        
        // Validaciones adicionales usando métodos de la entidad
        if (!libroExistente.tieneTituloValido()) {
            throw new DatosInvalidosException("Título inválido");
        }
        
        if (!libroExistente.tieneCantidadPaginasValida()) {
            throw new DatosInvalidosException("Cantidad de páginas inválida");
        }
        
        // Delegar al manejador
        manejadorLibro.actualizarLibro(libroExistente);
    }
}

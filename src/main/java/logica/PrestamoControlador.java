package logica;

import datatypes.DtPrestamo;
import excepciones.DatosInvalidosException;
import excepciones.PrestamoNoExisteException;
import interfaces.IPrestamoControlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador para operaciones relacionadas con préstamos
 * Implementa la interfaz IPrestamoControlador
 */
public class PrestamoControlador implements IPrestamoControlador {
    
    private ManejadorPrestamo manejadorPrestamo;
    private ManejadorLector manejadorLector;
    private ManejadorBibliotecario manejadorBibliotecario;
    private ManejadorLibro manejadorLibro;
    private ManejadorArticuloEspecial manejadorArticuloEspecial;
    
    public PrestamoControlador() {
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
        this.manejadorLector = ManejadorLector.getInstancia();
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
        this.manejadorLibro = ManejadorLibro.getInstancia();
        this.manejadorArticuloEspecial = ManejadorArticuloEspecial.getInstancia();
    }
    
    @Override
    public void registrarPrestamo(String lectorId, String bibliotecarioId, String materialId, 
                                 String fechaSolicitud, String estado) throws DatosInvalidosException {
        
        // Validaciones de entrada
        if (lectorId == null || lectorId.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        
        if (bibliotecarioId == null || bibliotecarioId.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de bibliotecario es obligatorio");
        }
        
        if (materialId == null || materialId.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de material es obligatorio");
        }
        
        if (fechaSolicitud == null || fechaSolicitud.trim().isEmpty()) {
            throw new DatosInvalidosException("Fecha de solicitud es obligatoria");
        }
        
        if (estado == null || estado.trim().isEmpty()) {
            throw new DatosInvalidosException("Estado es obligatorio");
        }
        
        // Verificar que existen las entidades
        Lector lector;
        Bibliotecario bibliotecario;
        Material material;
        
        try {
            lector = manejadorLector.obtenerLector(lectorId.trim());
        } catch (Exception e) {
            throw new DatosInvalidosException("No existe un lector con ID: " + lectorId);
        }
        
        try {
            bibliotecario = manejadorBibliotecario.obtenerBibliotecarioPorId(bibliotecarioId.trim());
        } catch (Exception e) {
            throw new DatosInvalidosException("No existe un bibliotecario con ID: " + bibliotecarioId);
        }
        
        // Buscar material en libros y artículos especiales
        material = manejadorLibro.obtenerLibro(materialId.trim());
        if (material == null) {
            material = manejadorArticuloEspecial.obtenerArticuloEspecial(materialId.trim());
        }
        
        if (material == null) {
            throw new DatosInvalidosException("No existe un material con ID: " + materialId);
        }
        
        // Parsear fecha
        Date fecha;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.parse(fechaSolicitud.trim());
        } catch (ParseException e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Use dd/MM/yyyy");
        }
        
        // Parsear estado
        EstadoPrestamo estadoPrestamo = parseEstado(estado.trim());
        
        // Crear entidad
        Prestamo prestamo = new Prestamo(
            null, // ID se generará automáticamente
            fecha,
            estadoPrestamo,
            lector,
            bibliotecario,
            material
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!prestamo.tieneFechaSolicitudValida()) {
            throw new DatosInvalidosException("La fecha de solicitud debe ser anterior o igual a la fecha actual");
        }
        
        // Delegar al manejador
        manejadorPrestamo.agregarPrestamo(prestamo);
    }
    
    @Override
    public DtPrestamo obtenerPrestamo(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new PrestamoNoExisteException("ID de préstamo inválido");
        }
        
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamo(id.trim());
        
        if (prestamo == null) {
            throw new PrestamoNoExisteException("No se encontró el préstamo con ID: " + id);
        }
        
        return convertirADto(prestamo);
    }
    
    @Override
    public String[] listarPrestamos() {
        List<Prestamo> prestamos = manejadorPrestamo.listarPrestamos();
        
        String[] resultado = new String[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            resultado[i] = p.getId() + " - " + p.getLector().getNombre() + " (" + p.getLector().getEmail() + 
                          ") - " + p.getMaterial().getId() + " - " + p.getEstado();
        }
        
        return resultado;
    }
    
    @Override
    public String[] listarPrestamosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            EstadoPrestamo estadoPrestamo = EstadoPrestamo.valueOf(estado.trim().toUpperCase());
            List<Prestamo> prestamos = manejadorPrestamo.listarPrestamosPorEstado(estadoPrestamo);
            
            return convertirAArray(prestamos);
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    @Override
    public String[] listarPrestamosPorLector(String lectorId) {
        if (lectorId == null || lectorId.trim().isEmpty()) {
            return new String[0];
        }
        
        List<Prestamo> prestamos = manejadorPrestamo.listarPrestamosPorLector(lectorId.trim());
        return convertirAArray(prestamos);
    }
    
    @Override
    public String[] listarPrestamosPorMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return new String[0];
        }
        
        List<Prestamo> prestamos = manejadorPrestamo.listarPrestamosPorMaterial(materialId.trim());
        return convertirAArray(prestamos);
    }
    
    @Override
    public void cambiarEstadoPrestamo(String idPrestamo, String nuevoEstado) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        if (idPrestamo == null || idPrestamo.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de préstamo es obligatorio");
        }
        
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new DatosInvalidosException("El nuevo estado es obligatorio");
        }
        
        // Obtener préstamo
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamo(idPrestamo.trim());
        
        // Parsear nuevo estado
        EstadoPrestamo estadoPrestamo = parseEstado(nuevoEstado.trim());
        
        // Cambiar estado
        prestamo.setEstado(estadoPrestamo);
        
        // Actualizar en el manejador (esto persistirá en BD)
        manejadorPrestamo.actualizarPrestamo(prestamo);
    }
    
    @Override
    public void devolverPrestamo(String idPrestamo, String fechaDevolucion) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        if (idPrestamo == null || idPrestamo.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de préstamo es obligatorio");
        }
        
        if (fechaDevolucion == null || fechaDevolucion.trim().isEmpty()) {
            throw new DatosInvalidosException("Fecha de devolución es obligatoria");
        }
        
        // Obtener préstamo
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamo(idPrestamo.trim());
        
        // Verificar que puede ser devuelto
        if (!prestamo.puedeSerDevuelto()) {
            throw new DatosInvalidosException("El préstamo no puede ser devuelto en su estado actual: " + prestamo.getEstado());
        }
        
        // Parsear fecha de devolución
        Date fecha;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.parse(fechaDevolucion.trim());
        } catch (ParseException e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Use dd/MM/yyyy");
        }
        
        // Actualizar préstamo
        prestamo.setFechaDevolucion(fecha);
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        
        // Actualizar en el manejador (esto persistirá en BD)
        manejadorPrestamo.actualizarPrestamo(prestamo);
    }
    
    // Métodos auxiliares privados
    
    private EstadoPrestamo parseEstado(String estado) throws DatosInvalidosException {
        try {
            return EstadoPrestamo.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DatosInvalidosException("Estado inválido: " + estado + 
                ". Estados válidos: PENDIENTE, EN_CURSO, DEVUELTO");
        }
    }
    
    private DtPrestamo convertirADto(Prestamo prestamo) {
        String materialTipo = prestamo.getMaterial() instanceof Libro ? "Libro" : "Artículo Especial";
        String materialDescripcion;
        
        if (prestamo.getMaterial() instanceof Libro) {
            materialDescripcion = ((Libro) prestamo.getMaterial()).getTitulo();
        } else {
            materialDescripcion = ((ArticuloEspecial) prestamo.getMaterial()).getDescripcion();
        }
        
        return new DtPrestamo(
            prestamo.getId(),
            prestamo.getFechaSolicitud(),
            prestamo.getFechaDevolucion(),
            prestamo.getEstado().name(),
            prestamo.getLector().getId(),
            prestamo.getLector().getNombre(),
            prestamo.getBibliotecario().getId(),
            prestamo.getBibliotecario().getNombre(),
            prestamo.getMaterial().getId(),
            materialTipo,
            materialDescripcion
        );
    }
    
    private String[] convertirAArray(List<Prestamo> prestamos) {
        String[] resultado = new String[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            resultado[i] = p.getId() + " - " + p.getLector().getNombre() + " (" + p.getLector().getEmail() + 
                          ") - " + p.getMaterial().getId() + " - " + p.getEstado();
        }
        return resultado;
    }
}

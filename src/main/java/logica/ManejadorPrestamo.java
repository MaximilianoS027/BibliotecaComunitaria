package logica;

import persistencia.HibernateUtil;
import persistencia.PrestamoDAO;
import excepciones.PrestamoNoExisteException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manejador para operaciones relacionadas con préstamos
 * Con persistencia real en PostgreSQL
 */
public class ManejadorPrestamo {
    
    private Map<String, Prestamo> prestamos = new HashMap<>();
    private AtomicInteger contadorId = new AtomicInteger(1);
    private PrestamoDAO prestamoDAO;
    
    public ManejadorPrestamo() {
        // Inicializar DAO con SessionFactory de Hibernate
        this.prestamoDAO = new PrestamoDAO(HibernateUtil.getSessionFactory());
        
        // Cargar préstamos existentes de la base de datos
        cargarPrestamosDesdeBaseDatos();
    }
    
    /**
     * Carga préstamos existentes desde la base de datos al inicializar
     */
    private void cargarPrestamosDesdeBaseDatos() {
        try {
            List<Prestamo> prestamosDB = prestamoDAO.listarTodos();
            for (Prestamo prestamo : prestamosDB) {
                prestamos.put(prestamo.getId(), prestamo);
            }
            System.out.println("Cargados " + prestamosDB.size() + " préstamos desde la base de datos");
        } catch (Exception e) {
            System.err.println("Error al cargar préstamos desde BD: " + e.getMessage());
        }
    }
    
    /**
     * Agrega un nuevo préstamo al sistema
     * Ahora persiste en la base de datos
     */
    public void agregarPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser null");
        }
        
        if (prestamo.getId() == null || prestamo.getId().trim().isEmpty()) {
            // Generar ID automático si no tiene uno
            String nuevoId = "P" + contadorId.getAndIncrement();
            prestamo.setId(nuevoId);
        }
        
        // Guardar en memoria
        prestamos.put(prestamo.getId(), prestamo);
        
        // NUEVO: Persistir en base de datos
        try {
            prestamoDAO.guardar(prestamo);
            System.out.println("Préstamo guardado exitosamente en BD: " + prestamo.getId());
        } catch (Exception e) {
            // Si falla la BD, remover de memoria también
            prestamos.remove(prestamo.getId());
            throw new RuntimeException("Error al guardar préstamo en base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un préstamo por su ID
     */
    public Prestamo obtenerPrestamo(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new PrestamoNoExisteException("ID de préstamo inválido");
        }
        
        Prestamo prestamo = prestamos.get(id.trim());
        if (prestamo == null) {
            throw new PrestamoNoExisteException("No existe un préstamo con ID: " + id);
        }
        
        return prestamo;
    }
    
    /**
     * Lista todos los préstamos
     */
    public List<Prestamo> listarTodosLosPrestamos() {
        return new ArrayList<>(prestamos.values());
    }
    
    /**
     * Lista préstamos por estado
     */
    public List<Prestamo> listarPrestamosPorEstado(EstadoPrestamo estado) {
        if (estado == null) {
            return new ArrayList<>();
        }
        
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamos.values()) {
            if (estado.equals(prestamo.getEstado())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }
    
    /**
     * Lista préstamos por lector
     */
    public List<Prestamo> listarPrestamosPorLector(String lectorId) {
        if (lectorId == null || lectorId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamos.values()) {
            if (prestamo.getLector() != null && lectorId.trim().equals(prestamo.getLector().getId())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }
    
    /**
     * Lista préstamos por material
     */
    public List<Prestamo> listarPrestamosPorMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamos.values()) {
            if (prestamo.getMaterial() != null && materialId.trim().equals(prestamo.getMaterial().getId())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }
    
    /**
     * Actualiza un préstamo existente
     */
    public void actualizarPrestamo(Prestamo prestamo) {
        if (prestamo == null || prestamo.getId() == null) {
            throw new IllegalArgumentException("Préstamo o ID inválido");
        }
        
        if (!prestamos.containsKey(prestamo.getId())) {
            throw new IllegalArgumentException("No existe un préstamo con ID: " + prestamo.getId());
        }
        
        // Actualizar en memoria
        prestamos.put(prestamo.getId(), prestamo);
        
        // NUEVO: Actualizar en base de datos
        try {
            prestamoDAO.actualizar(prestamo);
            System.out.println("Préstamo actualizado exitosamente en BD: " + prestamo.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar préstamo en base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un préstamo del sistema
     */
    public void eliminarPrestamo(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new PrestamoNoExisteException("ID de préstamo inválido");
        }
        
        Prestamo prestamo = obtenerPrestamo(id); // Verifica que existe
        
        // Eliminar de memoria
        prestamos.remove(id.trim());
        
        // NUEVO: Eliminar de base de datos
        try {
            prestamoDAO.eliminar(id.trim());
            System.out.println("Préstamo eliminado exitosamente de BD: " + id);
        } catch (Exception e) {
            // Si falla la BD, restaurar en memoria
            prestamos.put(id.trim(), prestamo);
            throw new RuntimeException("Error al eliminar préstamo de base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la cantidad total de préstamos
     */
    public int getCantidadPrestamos() {
        return prestamos.size();
    }
    
    /**
     * Método estático para obtener instancia (patrón Singleton)
     * Para compatibilidad con el código existente
     */
    private static ManejadorPrestamo instancia;
    
    public static ManejadorPrestamo getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorPrestamo();
        }
        return instancia;
    }
    
    /**
     * Lista todos los préstamos
     * Alias para compatibilidad con el código existente
     */
    public List<Prestamo> listarPrestamos() {
        return listarTodosLosPrestamos();
    }
}

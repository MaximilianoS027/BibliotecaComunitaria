package logica;

import persistencia.HibernateUtil;
import persistencia.PrestamoDAO;
import excepciones.PrestamoNoExisteException;
import datatypes.DtPrestamo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Manejador para operaciones relacionadas con préstamos
 * Con persistencia directa en PostgreSQL (sin cache en memoria)
 * Refactorizado para consistencia arquitectónica
 */
public class ManejadorPrestamo {
    
    private AtomicInteger contadorId = new AtomicInteger(1);
    private PrestamoDAO prestamoDAO;
    
    public ManejadorPrestamo() {
        // Inicializar DAO con SessionFactory de Hibernate
        this.prestamoDAO = new PrestamoDAO(HibernateUtil.getSessionFactory());
        
        // Inicializar contador con el último número usado
        inicializarContador();
    }
    

    
    /**
     * Obtiene el último número de préstamo de la base de datos
     * para inicializar correctamente el contador
     */
    private void inicializarContador() {
        try {
            // Consultar el conteo actual en la BD
            long totalPrestamos = prestamoDAO.contarTotal();
            contadorId.set((int) totalPrestamos + 1);
        } catch (Exception e) {
            System.err.println("Error al inicializar contador: " + e.getMessage());
            contadorId.set(1);
        }
    }
    
    /**
     * Agrega un nuevo préstamo al sistema
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
        
        // Persistir directamente en base de datos
        try {
            prestamoDAO.guardar(prestamo);
            System.out.println("Préstamo guardado exitosamente en BD: " + prestamo.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar préstamo en base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un préstamo por su ID
     */
    public Prestamo obtenerPrestamo(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        
        try {
            Prestamo prestamo = prestamoDAO.buscarPorId(id.trim());
            if (prestamo == null) {
                throw new PrestamoNoExisteException("No existe un préstamo con ID: " + id);
            }
            return prestamo;
        } catch (PrestamoNoExisteException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar préstamo: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un préstamo como DtPrestamo con todas las relaciones cargadas
     * Este método evita problemas de lazy loading al cargar todo dentro de una sesión activa
     */
    public DtPrestamo obtenerPrestamoDto(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new PrestamoNoExisteException("ID de préstamo inválido");
        }
        
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Query que carga eagerly todas las relaciones necesarias
            Query<Prestamo> query = session.createQuery(
                "SELECT p FROM Prestamo p " +
                "JOIN FETCH p.lector " +
                "JOIN FETCH p.bibliotecario " +
                "JOIN FETCH p.material " +
                "WHERE p.id = :id", Prestamo.class);
            query.setParameter("id", id.trim());
            
            Prestamo prestamo = query.uniqueResult();
            if (prestamo == null) {
                throw new PrestamoNoExisteException("No existe un préstamo con ID: " + id);
            }
            
            // Convertir a DTO dentro de la sesión activa
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
            
        } catch (Exception e) {
            if (e instanceof PrestamoNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al obtener préstamo: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista todos los préstamos desde la base de datos
     */
    public List<Prestamo> listarTodosLosPrestamos() {
        try {
            return prestamoDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lista préstamos por estado
     */
    public List<Prestamo> listarPrestamosPorEstado(EstadoPrestamo estado) {
        if (estado == null) {
            return new ArrayList<>();
        }
        
        try {
            return prestamoDAO.listarPorEstado(estado);
        } catch (Exception e) {
            System.err.println("Error al listar préstamos por estado: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lista préstamos por lector
     */
    public List<Prestamo> listarPrestamosPorLector(String lectorId) {
        if (lectorId == null || lectorId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return prestamoDAO.listarPorLector(lectorId.trim());
        } catch (Exception e) {
            System.err.println("Error al listar préstamos por lector: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lista préstamos por material
     */
    public List<Prestamo> listarPrestamosPorMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return prestamoDAO.listarPorMaterial(materialId.trim());
        } catch (Exception e) {
            System.err.println("Error al listar préstamos por material: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lista préstamos por bibliotecario
     */
    public List<Prestamo> listarPrestamosPorBibliotecario(String bibliotecarioId) {
        if (bibliotecarioId == null || bibliotecarioId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return prestamoDAO.listarPorBibliotecario(bibliotecarioId.trim());
        } catch (Exception e) {
            System.err.println("Error al listar préstamos por bibliotecario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Actualiza un préstamo existente
     */
    public void actualizarPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser null");
        }
        
        try {
            // Verificar que existe
            obtenerPrestamo(prestamo.getId());
            
            // Actualizar en base de datos
            prestamoDAO.actualizar(prestamo);
            System.out.println("Préstamo actualizado exitosamente: " + prestamo.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar préstamo: " + e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un préstamo del sistema
     */
    public void eliminarPrestamo(String id) throws PrestamoNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        
        try {
            // Verificar que existe antes de eliminar
            obtenerPrestamo(id.trim());
            
            // Eliminar de base de datos
            prestamoDAO.eliminar(id.trim());
            System.out.println("Préstamo eliminado exitosamente: " + id);
        } catch (PrestamoNoExisteException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar préstamo de base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la cantidad total de préstamos
     */
    public int getCantidadPrestamos() {
        try {
            return (int) prestamoDAO.contarTotal();
        } catch (Exception e) {
            System.err.println("Error al contar préstamos: " + e.getMessage());
            return 0;
        }
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

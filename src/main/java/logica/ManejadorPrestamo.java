package logica;

import persistencia.HibernateUtil;
import persistencia.PrestamoDAO;
import excepciones.PrestamoNoExisteException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
        
        // Inicializar contador con el último número usado
        inicializarContador();
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
     * Obtiene el último número de préstamo de la base de datos
     */
    private int obtenerUltimoNumeroPrestamo() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Consultar todos los IDs que empiecen con 'P' y extraer el número más alto
            Query<String> query = session.createQuery(
                "SELECT p.id FROM Prestamo p WHERE p.id LIKE 'P%'", String.class);
            List<String> ids = query.list();
            
            int maxNumero = 0;
            for (String id : ids) {
                if (id != null && id.length() > 1) {
                    try {
                        String numeroStr = id.substring(1); // Quitar 'P'
                        int numero = Integer.parseInt(numeroStr);
                        if (numero > maxNumero) {
                            maxNumero = numero;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar IDs con formato inválido
                    }
                }
            }
            
            return maxNumero;
            
        } catch (Exception e) {
            // En caso de error, retornar 0 para usar contador por defecto
            System.err.println("Error al obtener último número de préstamo: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Inicializa el contador con base en los préstamos existentes
     */
    private void inicializarContador() {
        int ultimoNumero = obtenerUltimoNumeroPrestamo();
        contadorId.set(ultimoNumero + 1);
        System.out.println("Contador de préstamos inicializado en: " + contadorId.get());
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
     * Lista préstamos por bibliotecario
     */
    public List<Prestamo> listarPrestamosPorBibliotecario(String bibliotecarioId) {
        if (bibliotecarioId == null || bibliotecarioId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamos.values()) {
            if (prestamo.getBibliotecario() != null && bibliotecarioId.trim().equals(prestamo.getBibliotecario().getId())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }
    
    /**
     * Lista información de préstamos por bibliotecario usando consulta directa
     */
    public List<Object[]> listarPrestamosPorBibliotecarioConInfo(String bibliotecarioId) {
        if (bibliotecarioId == null || bibliotecarioId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return prestamoDAO.listarPrestamosPorBibliotecarioConInfo(bibliotecarioId.trim());
        } catch (Exception e) {
            System.err.println("Error al obtener información de préstamos: " + e.getMessage());
            return new ArrayList<>();
        }
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

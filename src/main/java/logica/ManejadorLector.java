package logica;

import persistencia.HibernateUtil;
import persistencia.LectorDAO;
import excepciones.LectorNoExisteException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manejador para operaciones relacionadas con lectores
 * Ahora con persistencia real en PostgreSQL
 */
public class ManejadorLector {
    
    private Map<String, Lector> lectores = new HashMap<>();
    private AtomicInteger contadorId = new AtomicInteger(1);
    private LectorDAO lectorDAO;
    
    public ManejadorLector() {
        // Inicializar DAO con SessionFactory de Hibernate
        this.lectorDAO = new LectorDAO(HibernateUtil.getSessionFactory());
        
        // Cargar lectores existentes de la base de datos
        cargarLectoresDesdeBaseDatos();
    }
    
    /**
     * Carga lectores existentes desde la base de datos al inicializar
     */
    private void cargarLectoresDesdeBaseDatos() {
        try {
            List<Lector> lectoresDB = lectorDAO.listarTodos();
            for (Lector lector : lectoresDB) {
                lectores.put(lector.getId(), lector);
            }
            System.out.println("Cargados " + lectoresDB.size() + " lectores desde la base de datos");
        } catch (Exception e) {
            System.err.println("Error al cargar lectores desde BD: " + e.getMessage());
        }
    }
    
    /**
     * Agrega un nuevo lector al sistema
     * Ahora persiste en la base de datos
     */
    public void agregarLector(Lector lector) {
        if (lector == null) {
            throw new IllegalArgumentException("El lector no puede ser null");
        }
        
        if (lector.getId() == null || lector.getId().trim().isEmpty()) {
            // Generar ID automático si no tiene uno
            String nuevoId = "L" + contadorId.getAndIncrement();
            lector.setId(nuevoId);
        }
        
        // Guardar en memoria
        lectores.put(lector.getId(), lector);
        
        // NUEVO: Persistir en base de datos
        try {
            lectorDAO.guardar(lector);
            System.out.println("Lector guardado exitosamente en BD: " + lector.getId());
        } catch (Exception e) {
            // Si falla la BD, remover de memoria también
            lectores.remove(lector.getId());
            throw new RuntimeException("Error al guardar lector en base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un lector por su ID
     */
    public Lector obtenerLector(String id) throws LectorNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new LectorNoExisteException("ID de lector inválido");
        }
        
        Lector lector = lectores.get(id.trim());
        if (lector == null) {
            throw new LectorNoExisteException("No existe un lector con ID: " + id);
        }
        
        return lector;
    }
    
    /**
     * Verifica si existe un lector con el email dado
     */
    public boolean existeLectorConEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String emailBusqueda = email.trim().toLowerCase();
        for (Lector lector : lectores.values()) {
            if (lector.getEmail() != null && 
                lector.getEmail().trim().toLowerCase().equals(emailBusqueda)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Lista todos los lectores
     */
    public List<Lector> listarTodosLosLectores() {
        return new ArrayList<>(lectores.values());
    }
    
    /**
     * Lista lectores por estado
     */
    public List<Lector> listarLectoresPorEstado(EstadoLector estado) {
        if (estado == null) {
            return new ArrayList<>();
        }
        
        List<Lector> resultado = new ArrayList<>();
        for (Lector lector : lectores.values()) {
            if (estado.equals(lector.getEstado())) {
                resultado.add(lector);
            }
        }
        return resultado;
    }
    
    /**
     * Lista lectores por zona
     */
    public List<Lector> listarLectoresPorZona(Zona zona) {
        if (zona == null) {
            return new ArrayList<>();
        }
        
        List<Lector> resultado = new ArrayList<>();
        for (Lector lector : lectores.values()) {
            if (zona.equals(lector.getZona())) {
                resultado.add(lector);
            }
        }
        return resultado;
    }
    
    /**
     * Actualiza un lector existente
     */
    public void actualizarLector(Lector lector) {
        if (lector == null || lector.getId() == null) {
            throw new IllegalArgumentException("Lector o ID inválido");
        }
        
        if (!lectores.containsKey(lector.getId())) {
            throw new IllegalArgumentException("No existe un lector con ID: " + lector.getId());
        }
        
        // Actualizar en memoria
        lectores.put(lector.getId(), lector);
        
        // NUEVO: Actualizar en base de datos
        try {
            lectorDAO.actualizar(lector);
            System.out.println("Lector actualizado exitosamente en BD: " + lector.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar lector en base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un lector del sistema
     */
    public void eliminarLector(String id) throws LectorNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new LectorNoExisteException("ID de lector inválido");
        }
        
        Lector lector = obtenerLector(id); // Verifica que existe
        
        // Eliminar de memoria
        lectores.remove(id.trim());
        
        // NUEVO: Eliminar de base de datos
        try {
            lectorDAO.eliminar(id.trim());
            System.out.println("Lector eliminado exitosamente de BD: " + id);
        } catch (Exception e) {
            // Si falla la BD, restaurar en memoria
            lectores.put(id.trim(), lector);
            throw new RuntimeException("Error al eliminar lector de base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la cantidad total de lectores
     */
    public int getCantidadLectores() {
        return lectores.size();
    }
    
    /**
     * Método estático para obtener instancia (patrón Singleton)
     * Para compatibilidad con el código existente
     */
    private static ManejadorLector instancia;
    
    public static ManejadorLector getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorLector();
        }
        return instancia;
    }
    
    /**
     * Obtiene un lector por email
     * Para compatibilidad con el código existente
     */
    public Lector obtenerLectorPorEmail(String email) {
        try {
            return lectorDAO.buscarPorEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Lista todos los lectores
     * Alias para compatibilidad con el código existente
     */
    public List<Lector> listarLectores() {
        return listarTodosLosLectores();
    }
}
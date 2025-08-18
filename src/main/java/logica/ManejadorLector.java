package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Manejador para la gestión de lectores en el sistema
 * Implementa el patrón Singleton
 */
public class ManejadorLector {
    
    private static ManejadorLector instancia;
    private ConcurrentHashMap<String, Lector> lectores;
    private AtomicLong contadorId;
    
    // Constructor privado para Singleton
    private ManejadorLector() {
        this.lectores = new ConcurrentHashMap<>();
        this.contadorId = new AtomicLong(1);
    }
    
    // Método para obtener la instancia única
    public static synchronized ManejadorLector getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorLector();
        }
        return instancia;
    }
    
    /**
     * Agrega un nuevo lector al sistema
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
        
        lectores.put(lector.getId(), lector);
    }
    
    /**
     * Obtiene un lector por su ID
     */
    public Lector obtenerLector(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        
        return lectores.get(id.trim());
    }
    
    /**
     * Obtiene un lector por su email
     */
    public Lector obtenerLectorPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser null o vacío");
        }
        
        for (Lector lector : lectores.values()) {
            if (email.trim().equalsIgnoreCase(lector.getEmail())) {
                return lector;
            }
        }
        return null;
    }
    
    /**
     * Lista todos los lectores del sistema
     */
    public List<Lector> listarLectores() {
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
        if (lector == null) {
            throw new IllegalArgumentException("El lector no puede ser null");
        }
        
        if (lector.getId() == null || lector.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("El lector debe tener un ID válido");
        }
        
        if (!lectores.containsKey(lector.getId())) {
            throw new IllegalArgumentException("El lector no existe en el sistema");
        }
        
        lectores.put(lector.getId(), lector);
    }
    
    /**
     * Elimina un lector del sistema
     */
    public void eliminarLector(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        
        lectores.remove(id.trim());
    }
    
    /**
     * Verifica si existe un lector con el email especificado
     */
    public boolean existeLectorConEmail(String email) {
        return obtenerLectorPorEmail(email) != null;
    }
    
    /**
     * Obtiene la cantidad total de lectores
     */
    public int getCantidadLectores() {
        return lectores.size();
    }
}

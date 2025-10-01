package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase Singleton para manejar la conexión a la base de datos
 */
public class Conexion {
    private static Conexion instancia = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    private Conexion() {}
    
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
            try {
                emf = Persistence.createEntityManagerFactory("biblioteca-persistence-unit");
                em = emf.createEntityManager();
            } catch (Exception e) {
                System.err.println("Error al inicializar EntityManagerFactory: " + e.getMessage());
                throw new RuntimeException("No se pudo establecer conexión con la base de datos", e);
            }
        }
        return instancia;
    }
    
    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }
    
    public void cerrarConexion() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

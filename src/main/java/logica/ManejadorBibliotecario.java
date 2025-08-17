package logica;

import persistencia.Conexion;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Manejador Singleton para operaciones CRUD de Bibliotecario
 */
public class ManejadorBibliotecario {
    private static ManejadorBibliotecario instancia = null;
    
    private ManejadorBibliotecario() {}
    
    public static ManejadorBibliotecario getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorBibliotecario();
        }
        return instancia;
    }
    
    public void agregarBibliotecario(Bibliotecario bibliotecario) throws BibliotecarioRepetidoException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        EntityTransaction tx = null;
        
        try {
            // Verificar si ya existe
            Bibliotecario existente = em.find(Bibliotecario.class, bibliotecario.getNumeroEmpleado());
            if (existente != null) {
                throw new BibliotecarioRepetidoException("Ya existe un bibliotecario con el número de empleado: " + bibliotecario.getNumeroEmpleado());
            }
            
            tx = em.getTransaction();
            tx.begin();
            em.persist(bibliotecario);
            tx.commit();
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            if (e instanceof BibliotecarioRepetidoException) {
                throw e;
            }
            throw new RuntimeException("Error al agregar bibliotecario: " + e.getMessage(), e);
        }
    }
    
    public Bibliotecario obtenerBibliotecario(String numeroEmpleado) throws BibliotecarioNoExisteException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            Bibliotecario bibliotecario = em.find(Bibliotecario.class, numeroEmpleado);
            if (bibliotecario == null) {
                throw new BibliotecarioNoExisteException("No existe un bibliotecario con el número de empleado: " + numeroEmpleado);
            }
            return bibliotecario;
            
        } catch (Exception e) {
            if (e instanceof BibliotecarioNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al obtener bibliotecario: " + e.getMessage(), e);
        }
    }
    
    public List<Bibliotecario> listarBibliotecarios() {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            TypedQuery<Bibliotecario> query = em.createQuery("SELECT b FROM Bibliotecario b ORDER BY b.nombre", Bibliotecario.class);
            return query.getResultList();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar bibliotecarios: " + e.getMessage(), e);
        }
    }
    
    public boolean existeBibliotecario(String numeroEmpleado) {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            Bibliotecario bibliotecario = em.find(Bibliotecario.class, numeroEmpleado);
            return bibliotecario != null;
            
        } catch (Exception e) {
            return false;
        }
    }
}

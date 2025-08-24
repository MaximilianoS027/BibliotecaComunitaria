package persistencia;

import logica.Lector;
import logica.EstadoLector;
import logica.Zona;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Date;

/**
 * Data Access Object para la entidad Lector
 * Implementa operaciones CRUD usando Hibernate
 */
public class LectorDAO {
    
    private final SessionFactory sessionFactory;
    
    public LectorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Guarda un nuevo lector en la base de datos
     */
    public void guardar(Lector lector) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Generar ID si no tiene uno
            if (lector.getId() == null || lector.getId().trim().isEmpty()) {
                lector.setId(generarIdLector());
            }
            
            session.save(lector);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al guardar el lector: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Actualiza un lector existente en la base de datos
     */
    public void actualizar(Lector lector) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            session.update(lector);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al actualizar el lector: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Elimina un lector de la base de datos
     */
    public void eliminar(String id) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            Lector lector = session.get(Lector.class, id);
            if (lector != null) {
                session.delete(lector);
            }
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al eliminar el lector: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Busca un lector por su ID
     */
    public Lector buscarPorId(String id) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            return session.get(Lector.class, id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el lector por ID: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Busca un lector por su email
     */
    public Lector buscarPorEmail(String email) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Lector> query = session.createQuery(
                "FROM Lector l WHERE l.email = :email", Lector.class);
            query.setParameter("email", email);
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el lector por email: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista todos los lectores
     */
    public List<Lector> listarTodos() {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Lector> query = session.createQuery("FROM Lector", Lector.class);
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los lectores: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista lectores por estado
     */
    public List<Lector> listarPorEstado(EstadoLector estado) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Lector> query = session.createQuery(
                "FROM Lector l WHERE l.estado = :estado", Lector.class);
            query.setParameter("estado", estado);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar lectores por estado: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista lectores por zona
     */
    public List<Lector> listarPorZona(Zona zona) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Lector> query = session.createQuery(
                "FROM Lector l WHERE l.zona = :zona", Lector.class);
            query.setParameter("zona", zona);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar lectores por zona: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Verifica si existe un lector con el email especificado
     */
    public boolean existePorEmail(String email) {
        return buscarPorEmail(email) != null;
    }
    
    /**
     * Cuenta el total de lectores
     */
    public long contarTotal() {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Lector l", Long.class);
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al contar lectores: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Genera un ID único para el lector
     */
    private String generarIdLector() {
        return "L" + System.currentTimeMillis();
    }
}

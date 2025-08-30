package persistencia;

import logica.Prestamo;
import logica.EstadoPrestamo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object para la entidad Prestamo
 * Implementa operaciones CRUD usando Hibernate
 */
public class PrestamoDAO {
    
    private final SessionFactory sessionFactory;
    
    public PrestamoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Guarda un nuevo préstamo en la base de datos
     */
    public void guardar(Prestamo prestamo) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Generar ID si no tiene uno
            if (prestamo.getId() == null || prestamo.getId().trim().isEmpty()) {
                prestamo.setId(generarIdPrestamo());
            }
            
            session.save(prestamo);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al guardar el préstamo: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Actualiza un préstamo existente en la base de datos
     */
    public void actualizar(Prestamo prestamo) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            session.update(prestamo);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al actualizar el préstamo: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Elimina un préstamo de la base de datos
     */
    public void eliminar(String id) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            Prestamo prestamo = session.get(Prestamo.class, id);
            if (prestamo != null) {
                session.delete(prestamo);
            }
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al eliminar el préstamo: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Busca un préstamo por su ID
     */
    public Prestamo buscarPorId(String id) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            return session.get(Prestamo.class, id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el préstamo por ID: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista todos los préstamos
     */
    public List<Prestamo> listarTodos() {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Prestamo> query = session.createQuery("FROM Prestamo", Prestamo.class);
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los préstamos: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista préstamos por estado
     */
    public List<Prestamo> listarPorEstado(EstadoPrestamo estado) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p WHERE p.estado = :estado", Prestamo.class);
            query.setParameter("estado", estado);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos por estado: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista préstamos por lector
     */
    public List<Prestamo> listarPorLector(String lectorId) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p WHERE p.lector.id = :lectorId", Prestamo.class);
            query.setParameter("lectorId", lectorId);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos por lector: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista préstamos por material
     */
    public List<Prestamo> listarPorMaterial(String materialId) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p WHERE p.material.id = :materialId", Prestamo.class);
            query.setParameter("materialId", materialId);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos por material: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista préstamos por bibliotecario
     */
    public List<Prestamo> listarPorBibliotecario(String bibliotecarioId) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p WHERE p.bibliotecario.id = :bibliotecarioId", Prestamo.class);
            query.setParameter("bibliotecarioId", bibliotecarioId);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos por bibliotecario: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Obtiene información de préstamos por bibliotecario con JOIN para evitar lazy-loading
     */
    public List<Object[]> listarPrestamosPorBibliotecarioConInfo(String bibliotecarioId) {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            // Consulta con JOIN para obtener toda la información necesaria
            Query<Object[]> query = session.createQuery(
                "SELECT p.fechaSolicitud, p.fechaDevolucion, l.nombre, " +
                "CASE " +
                "  WHEN lb.titulo IS NOT NULL THEN lb.titulo " +
                "  WHEN ae.descripcion IS NOT NULL THEN ae.descripcion " +
                "  ELSE 'Material sin descripción' " +
                "END " +
                "FROM Prestamo p " +
                "JOIN p.lector l " +
                "JOIN p.material m " +
                "LEFT JOIN Libro lb ON m.id = lb.id " +
                "LEFT JOIN ArticuloEspecial ae ON m.id = ae.id " +
                "WHERE p.bibliotecario.id = :bibliotecarioId", Object[].class);
            query.setParameter("bibliotecarioId", bibliotecarioId);
            
            return query.list();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar préstamos por bibliotecario con info: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Cuenta el total de préstamos
     */
    public long contarTotal() {
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Prestamo p", Long.class);
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al contar préstamos: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Genera un ID único para el préstamo
     */
    private String generarIdPrestamo() {
        return "P" + System.currentTimeMillis();
    }
}

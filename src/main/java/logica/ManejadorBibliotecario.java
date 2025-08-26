package logica;

import persistencia.HibernateUtil;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar si ya existe
            Bibliotecario existente = session.get(Bibliotecario.class, bibliotecario.getNumeroEmpleado());
            if (existente != null) {
                throw new BibliotecarioRepetidoException("Ya existe un bibliotecario con el número de empleado: " + bibliotecario.getNumeroEmpleado());
            }
            
            session.save(bibliotecario);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof BibliotecarioRepetidoException) {
                throw e;
            }
            throw new RuntimeException("Error al agregar bibliotecario: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public Bibliotecario obtenerBibliotecario(String numeroEmpleado) throws BibliotecarioNoExisteException {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Bibliotecario bibliotecario = session.get(Bibliotecario.class, numeroEmpleado);
            if (bibliotecario == null) {
                throw new BibliotecarioNoExisteException("No existe un bibliotecario con el número de empleado: " + numeroEmpleado);
            }
            return bibliotecario;
            
        } catch (Exception e) {
            if (e instanceof BibliotecarioNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al obtener bibliotecario: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public List<Bibliotecario> listarBibliotecarios() {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Bibliotecario> query = session.createQuery("SELECT b FROM Bibliotecario b ORDER BY b.nombre", Bibliotecario.class);
            return query.getResultList();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar bibliotecarios: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public boolean existeBibliotecario(String numeroEmpleado) {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Bibliotecario bibliotecario = session.get(Bibliotecario.class, numeroEmpleado);
            return bibliotecario != null;
            
        } catch (Exception e) {
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

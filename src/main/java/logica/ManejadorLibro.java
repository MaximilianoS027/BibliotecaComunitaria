package logica;

import persistencia.Conexion;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Manejador Singleton para operaciones CRUD de Libro
 */
public class ManejadorLibro {
    private static ManejadorLibro instancia = null;
    
    private ManejadorLibro() {}
    
    public static ManejadorLibro getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorLibro();
        }
        return instancia;
    }
    
    public void agregarLibro(Libro libro) throws LibroRepetidoException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        EntityTransaction tx = null;
        
        try {
            // Verificar si ya existe un libro con el mismo título
            if (existeLibroConTitulo(libro.getTitulo())) {
                throw new LibroRepetidoException("Ya existe un libro con el título: " + libro.getTitulo());
            }
            
            tx = em.getTransaction();
            tx.begin();
            em.persist(libro);
            tx.commit();
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            if (e instanceof LibroRepetidoException) {
                throw e;
            }
            throw new RuntimeException("Error al agregar libro: " + e.getMessage(), e);
        }
    }
    
    public Libro obtenerLibro(String id) throws LibroNoExisteException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            Libro libro = em.find(Libro.class, id);
            if (libro == null) {
                throw new LibroNoExisteException("No existe un libro con el ID: " + id);
            }
            return libro;
            
        } catch (Exception e) {
            if (e instanceof LibroNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al obtener libro: " + e.getMessage(), e);
        }
    }
    
    public Libro obtenerLibroPorTitulo(String titulo) throws LibroNoExisteException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo", Libro.class);
            query.setParameter("titulo", titulo);
            
            List<Libro> resultados = query.getResultList();
            if (resultados.isEmpty()) {
                throw new LibroNoExisteException("No existe un libro con el título: " + titulo);
            }
            
            return resultados.get(0);
            
        } catch (Exception e) {
            if (e instanceof LibroNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al obtener libro por título: " + e.getMessage(), e);
        }
    }
    
    public List<Libro> listarLibros() {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l ORDER BY l.titulo", Libro.class);
            return query.getResultList();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar libros: " + e.getMessage(), e);
        }
    }
    
    public List<Libro> listarLibrosPorPaginas(int paginasMin, int paginasMax) {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.cantidadPaginas >= :min AND l.cantidadPaginas <= :max ORDER BY l.cantidadPaginas", 
                Libro.class);
            query.setParameter("min", paginasMin);
            query.setParameter("max", paginasMax);
            return query.getResultList();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al listar libros por páginas: " + e.getMessage(), e);
        }
    }
    
    public boolean existeLibro(String id) {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            Libro libro = em.find(Libro.class, id);
            return libro != null;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean existeLibroConTitulo(String titulo) {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM Libro l WHERE l.titulo = :titulo", Long.class);
            query.setParameter("titulo", titulo);
            Long count = query.getSingleResult();
            return count > 0;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public void actualizarLibro(Libro libro) throws LibroNoExisteException {
        EntityManager em = Conexion.getInstancia().getEntityManager();
        EntityTransaction tx = null;
        
        try {
            // Verificar que el libro existe
            if (!existeLibro(libro.getId())) {
                throw new LibroNoExisteException("No existe un libro con el ID: " + libro.getId());
            }
            
            tx = em.getTransaction();
            tx.begin();
            em.merge(libro);
            tx.commit();
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            if (e instanceof LibroNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }
}

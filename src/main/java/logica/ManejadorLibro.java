package logica;

import persistencia.HibernateUtil;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
import excepciones.DatosInvalidosException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

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
    
    /**
     * Registra un nuevo libro con validaciones completas
     */
    public void registrarLibro(String titulo, Integer cantidadPaginas) 
            throws LibroRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        validarDatos(titulo, cantidadPaginas);
        
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar si existe libro similar
            if (existeLibroSimilar(session, titulo, cantidadPaginas)) {
                throw new LibroRepetidoException(
                    "Ya existe un libro similar registrado recientemente. " +
                    "Título: " + titulo + ", Páginas: " + cantidadPaginas
                );
            }
            
            // Crear nueva entidad
            Libro libro = new Libro(titulo, cantidadPaginas);
            
            session.save(libro);
            transaction.commit();
            
            System.out.println("Libro registrado exitosamente: " + libro.getId());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof LibroRepetidoException || e instanceof DatosInvalidosException) {
                throw e;
            }
            throw new RuntimeException("Error al registrar libro: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Valida los datos de entrada según las reglas funcionales
     */
    private void validarDatos(String titulo, Integer cantidadPaginas) 
            throws DatosInvalidosException {
        
        // Validar título: no vacío, máx 255 chars
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatosInvalidosException("El título es obligatorio");
        }
        if (titulo.length() > 255) {
            throw new DatosInvalidosException("El título no puede superar los 255 caracteres");
        }
        
        // Validar cantidad de páginas: > 0
        if (cantidadPaginas == null || cantidadPaginas <= 0) {
            throw new DatosInvalidosException("La cantidad de páginas debe ser mayor a 0");
        }
    }
    
    /**
     * Verifica si existe un libro similar en ventana de ±1 día
     */
    private boolean existeLibroSimilar(Session session, String titulo, Integer cantidadPaginas) {
        // Calcular ventana de tiempo: ±1 día desde ahora
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaDesde = cal.getTime();
        
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHasta = cal.getTime();
        
        // Buscar libros con mismos datos en la ventana de tiempo
        Query<Libro> query = session.createQuery(
            "SELECT l FROM Libro l WHERE " +
            "l.titulo = :titulo AND " +
            "l.cantidadPaginas = :paginas AND " +
            "l.fechaIngreso BETWEEN :fechaDesde AND :fechaHasta",
            Libro.class
        );
        
        query.setParameter("titulo", titulo);
        query.setParameter("paginas", cantidadPaginas);
        query.setParameter("fechaDesde", fechaDesde);
        query.setParameter("fechaHasta", fechaHasta);
        
        List<Libro> similares = query.getResultList();
        return !similares.isEmpty();
    }
    
    /**
     * Obtiene un libro por ID
     */
    public Libro obtenerLibro(String id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Libro.class, id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista todos los libros
     */
    public List<Libro> listarLibros() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Libro> query = session.createQuery(
                "SELECT l FROM Libro l ORDER BY l.fechaIngreso DESC", 
                Libro.class
            );
            return query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Obtiene un libro por título
     */
    public Libro obtenerLibroPorTitulo(String titulo) throws LibroNoExisteException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Libro> query = session.createQuery(
                "SELECT l FROM Libro l WHERE l.titulo = :titulo", 
                Libro.class
            );
            query.setParameter("titulo", titulo);
            Libro libro = query.uniqueResult();
            if (libro == null) {
                throw new LibroNoExisteException("No existe un libro con el título: " + titulo);
            }
            return libro;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista libros por rango de páginas
     */
    public List<Libro> listarLibrosPorPaginas(int paginasMin, int paginasMax) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Libro> query = session.createQuery(
                "SELECT l FROM Libro l WHERE l.cantidadPaginas BETWEEN :min AND :max ORDER BY l.cantidadPaginas ASC", 
                Libro.class
            );
            query.setParameter("min", paginasMin);
            query.setParameter("max", paginasMax);
            return query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Verifica si existe un libro con el título dado
     */
    public boolean existeLibroConTitulo(String titulo) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Libro l WHERE l.titulo = :titulo", 
                Long.class
            );
            query.setParameter("titulo", titulo);
            return query.getSingleResult() > 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Actualiza un libro existente
     */
    public void actualizarLibro(Libro libro) throws LibroNoExisteException {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar que el libro existe
            Libro libroExistente = session.get(Libro.class, libro.getId());
            if (libroExistente == null) {
                throw new LibroNoExisteException("No existe un libro con ID: " + libro.getId());
            }
            
            session.update(libro);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof LibroNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Agregar libro (método adicional para compatibilidad)
     */
    public void agregarLibro(Libro libro) throws LibroRepetidoException, DatosInvalidosException {
        registrarLibro(libro.getTitulo(), libro.getCantidadPaginas());
    }
}

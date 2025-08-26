package logica;

import persistencia.HibernateUtil;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.ArticuloEspecialNoExisteException;
import excepciones.DatosInvalidosException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * Manejador Singleton para operaciones CRUD de ArticuloEspecial
 * Implementa validaciones y lógica de idempotencia "suave"
 */
public class ManejadorArticuloEspecial {
    private static ManejadorArticuloEspecial instancia = null;
    
    private ManejadorArticuloEspecial() {}
    
    public static ManejadorArticuloEspecial getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorArticuloEspecial();
        }
        return instancia;
    }
    
    /**
     * Registra un nuevo artículo especial con validaciones completas
     * Implementa idempotencia "suave" verificando duplicados en ventana de ±1 día
     */
    public void registrarArticuloEspecial(String descripcion, Float pesoKg, String dimensiones) 
            throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        validarDatos(descripcion, pesoKg, dimensiones);
        
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar idempotencia "suave" - buscar duplicados en ventana de ±1 día
            if (existeArticuloSimilar(session, descripcion, pesoKg, dimensiones)) {
                throw new ArticuloEspecialRepetidoException(
                    "Ya existe un artículo especial similar registrado recientemente. " +
                    "Descripción: " + descripcion + ", Peso: " + pesoKg + "kg, Dimensiones: " + dimensiones
                );
            }
            
            // Crear nueva entidad
            ArticuloEspecial articulo = new ArticuloEspecial(descripcion, pesoKg, dimensiones);
            
            session.save(articulo);
            transaction.commit();
            
            System.out.println("Artículo especial registrado exitosamente: " + articulo.getId());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof ArticuloEspecialRepetidoException || e instanceof DatosInvalidosException) {
                throw e;
            }
            throw new RuntimeException("Error al registrar artículo especial: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Valida los datos de entrada según las reglas funcionales
     */
    private void validarDatos(String descripcion, Float pesoKg, String dimensiones) 
            throws DatosInvalidosException {
        
        // Validar descripción: no vacía, máx 500 chars
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción es obligatoria");
        }
        if (descripcion.length() > 500) {
            throw new DatosInvalidosException("La descripción no puede superar los 500 caracteres");
        }
        
        // Validar peso: > 0
        if (pesoKg == null || pesoKg <= 0) {
            throw new DatosInvalidosException("El peso debe ser mayor a 0 kg");
        }
        
        // Validar dimensiones: no vacías
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            throw new DatosInvalidosException("Las dimensiones son obligatorias");
        }
        
        // Validar formato de dimensiones si sigue patrón LxAxH
        ArticuloEspecial temp = new ArticuloEspecial(descripcion, pesoKg, dimensiones);
        if (!temp.tieneDimensionesFormatoValido()) {
            // Solo advertencia, no error crítico - formato libre permitido
            System.out.println("ADVERTENCIA: Las dimensiones no siguen el formato recomendado LxAxH cm");
        }
    }
    
    /**
     * Verifica si existe un artículo similar en ventana de ±1 día (idempotencia suave)
     */
    private boolean existeArticuloSimilar(Session session, String descripcion, Float pesoKg, String dimensiones) {
        // Calcular ventana de tiempo: ±1 día desde ahora
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaDesde = cal.getTime();
        
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHasta = cal.getTime();
        
        // Buscar artículos con mismos datos en la ventana de tiempo
        Query<ArticuloEspecial> query = session.createQuery(
            "SELECT a FROM ArticuloEspecial a WHERE " +
            "a.descripcion = :descripcion AND " +
            "a.pesoKg = :peso AND " +
            "a.dimensiones = :dimensiones AND " +
            "a.fechaIngreso BETWEEN :fechaDesde AND :fechaHasta",
            ArticuloEspecial.class
        );
        
        query.setParameter("descripcion", descripcion);
        query.setParameter("peso", pesoKg);
        query.setParameter("dimensiones", dimensiones);
        query.setParameter("fechaDesde", fechaDesde);
        query.setParameter("fechaHasta", fechaHasta);
        
        List<ArticuloEspecial> similares = query.getResultList();
        return !similares.isEmpty();
    }
    
    /**
     * Obtiene un artículo especial por ID
     */
    public ArticuloEspecial obtenerArticuloEspecial(String id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(ArticuloEspecial.class, id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista todos los artículos especiales
     */
    public List<ArticuloEspecial> listarArticulosEspeciales() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<ArticuloEspecial> query = session.createQuery(
                "SELECT a FROM ArticuloEspecial a ORDER BY a.fechaIngreso DESC", 
                ArticuloEspecial.class
            );
            return query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Obtiene un artículo especial por descripción
     */
    public ArticuloEspecial obtenerArticuloEspecialPorDescripcion(String descripcion) throws ArticuloEspecialNoExisteException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<ArticuloEspecial> query = session.createQuery(
                "SELECT a FROM ArticuloEspecial a WHERE a.descripcion = :descripcion", 
                ArticuloEspecial.class
            );
            query.setParameter("descripcion", descripcion);
            ArticuloEspecial articulo = query.uniqueResult();
            if (articulo == null) {
                throw new ArticuloEspecialNoExisteException("No existe un artículo especial con la descripción: " + descripcion);
            }
            return articulo;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Lista artículos especiales por rango de peso
     */
    public List<ArticuloEspecial> listarArticulosEspecialesPorPeso(float pesoMin, float pesoMax) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<ArticuloEspecial> query = session.createQuery(
                "SELECT a FROM ArticuloEspecial a WHERE a.pesoKg BETWEEN :min AND :max ORDER BY a.pesoKg ASC", 
                ArticuloEspecial.class
            );
            query.setParameter("min", pesoMin);
            query.setParameter("max", pesoMax);
            return query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Verifica si existe un artículo especial con la descripción dada
     */
    public boolean existeArticuloEspecialConDescripcion(String descripcion) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(a) FROM ArticuloEspecial a WHERE a.descripcion = :descripcion", 
                Long.class
            );
            query.setParameter("descripcion", descripcion);
            return query.getSingleResult() > 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Actualiza un artículo especial existente
     */
    public void actualizarArticuloEspecial(ArticuloEspecial articulo) throws ArticuloEspecialNoExisteException {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar que el artículo existe
            ArticuloEspecial articuloExistente = session.get(ArticuloEspecial.class, articulo.getId());
            if (articuloExistente == null) {
                throw new ArticuloEspecialNoExisteException("No existe un artículo especial con ID: " + articulo.getId());
            }
            
            session.update(articulo);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof ArticuloEspecialNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al actualizar artículo especial: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Agregar artículo especial (método adicional para compatibilidad)
     */
    public void agregarArticuloEspecial(ArticuloEspecial articulo) throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        registrarArticuloEspecial(articulo.getDescripcion(), articulo.getPesoKg(), articulo.getDimensiones());
    }
}

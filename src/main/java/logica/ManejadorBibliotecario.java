package logica;

import persistencia.HibernateUtil;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manejador Singleton para operaciones CRUD de Bibliotecario
 */
public class ManejadorBibliotecario {
    private static ManejadorBibliotecario instancia = null;
    private static AtomicInteger contadorId = new AtomicInteger(1);
    private static AtomicInteger contadorEmpleado = new AtomicInteger(1000);
    
    private ManejadorBibliotecario() {
        // Inicializar contadores con los últimos números usados
        inicializarContadores();
    }
    
    public static ManejadorBibliotecario getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorBibliotecario();
        }
        return instancia;
    }
    
    /**
     * Genera un ID único secuencial para bibliotecario (B1, B2, B3...)
     */
    private synchronized String generarIdBibliotecario() {
        // Verificar cuál es el último ID en la BD para evitar duplicados
        int ultimoNumero = obtenerUltimoNumeroBibliotecario();
        if (ultimoNumero >= contadorId.get()) {
            contadorId.set(ultimoNumero + 1);
        }
        return "B" + contadorId.getAndIncrement();
    }
    
    /**
     * Genera un número de empleado único secuencial (1000, 1001, 1002...)
     */
    private synchronized String generarNumeroEmpleado() {
        // Verificar cuál es el último número en la BD para evitar duplicados
        int ultimoNumero = obtenerUltimoNumeroEmpleado();
        if (ultimoNumero >= contadorEmpleado.get()) {
            contadorEmpleado.set(ultimoNumero + 1);
        }
        return String.valueOf(contadorEmpleado.getAndIncrement());
    }
    
    /**
     * Obtiene el último número de bibliotecario de la base de datos
     */
    private int obtenerUltimoNumeroBibliotecario() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Consultar todos los IDs que empiecen con 'B' y extraer el número más alto
            Query<String> query = session.createQuery(
                "SELECT u.id FROM Usuario u WHERE u.id LIKE 'B%'", String.class);
            List<String> ids = query.list();
            
            int maxNumero = 0;
            for (String id : ids) {
                if (id != null && id.length() > 1) {
                    try {
                        String numeroStr = id.substring(1); // Quitar 'B'
                        int numero = Integer.parseInt(numeroStr);
                        if (numero > maxNumero) {
                            maxNumero = numero;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar IDs con formato inválido
                    }
                }
            }
            
            return maxNumero;
            
        } catch (Exception e) {
            // En caso de error, retornar 0 para usar contador por defecto
            System.err.println("Error al obtener último número de bibliotecario: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Obtiene el último número de empleado de la base de datos
     */
    private int obtenerUltimoNumeroEmpleado() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Consultar todos los números de empleado y obtener el más alto
            Query<String> query = session.createQuery(
                "SELECT b.numeroEmpleado FROM Bibliotecario b", String.class);
            List<String> numeros = query.list();
            
            int maxNumero = 999; // Empezar en 999 para que el primer número sea 1000
            for (String numero : numeros) {
                if (numero != null) {
                    try {
                        int num = Integer.parseInt(numero);
                        if (num > maxNumero) {
                            maxNumero = num;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar números con formato inválido
                    }
                }
            }
            
            return maxNumero;
            
        } catch (Exception e) {
            // En caso de error, retornar 999 para usar contador por defecto (1000)
            System.err.println("Error al obtener último número de empleado: " + e.getMessage());
            return 999;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Inicializa los contadores con base en los bibliotecarios existentes
     */
    private void inicializarContadores() {
        int ultimoId = obtenerUltimoNumeroBibliotecario();
        int ultimoEmpleado = obtenerUltimoNumeroEmpleado();
        
        contadorId.set(ultimoId + 1);
        contadorEmpleado.set(ultimoEmpleado + 1);
        
        System.out.println("Contador de bibliotecarios inicializado en: B" + contadorId.get());
        System.out.println("Contador de empleados inicializado en: " + contadorEmpleado.get());
    }
    
    public void agregarBibliotecario(Bibliotecario bibliotecario) throws BibliotecarioRepetidoException {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Generar ID automáticamente si no tiene
            if (bibliotecario.getId() == null || bibliotecario.getId().trim().isEmpty()) {
                bibliotecario.setId(generarIdBibliotecario());
            }
            
            // Generar número de empleado automáticamente si no tiene
            if (bibliotecario.getNumeroEmpleado() == null || bibliotecario.getNumeroEmpleado().trim().isEmpty()) {
                bibliotecario.setNumeroEmpleado(generarNumeroEmpleado());
            }
            
            // Verificar si ya existe por ID
            Bibliotecario existente = session.get(Bibliotecario.class, bibliotecario.getId());
            if (existente != null) {
                throw new BibliotecarioRepetidoException("Ya existe un bibliotecario con el ID: " + bibliotecario.getId());
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
    
    public Bibliotecario obtenerBibliotecario(String id) throws BibliotecarioNoExisteException {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Bibliotecario bibliotecario = session.get(Bibliotecario.class, id);
            if (bibliotecario == null) {
                throw new BibliotecarioNoExisteException("No existe un bibliotecario con el ID: " + id);
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
    
    /**
     * Obtiene un bibliotecario por email
     */
    public Bibliotecario obtenerBibliotecarioPorEmail(String email) {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Buscar por email
            Query<Bibliotecario> query = session.createQuery(
                "SELECT b FROM Bibliotecario b WHERE LOWER(b.email) = LOWER(:email)", Bibliotecario.class);
            query.setParameter("email", email.trim());
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Obtiene un bibliotecario por nombre
     */
    public Bibliotecario obtenerBibliotecarioPorNombre(String nombre) {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Buscar por nombre
            Query<Bibliotecario> query = session.createQuery(
                "SELECT b FROM Bibliotecario b WHERE LOWER(b.nombre) = LOWER(:nombre)", Bibliotecario.class);
            query.setParameter("nombre", nombre.trim());
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Obtiene un bibliotecario por número de empleado
     */
    public Bibliotecario obtenerBibliotecarioPorNumeroEmpleado(String numeroEmpleado) {
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Buscar por número de empleado
            Query<Bibliotecario> query = session.createQuery(
                "SELECT b FROM Bibliotecario b WHERE b.numeroEmpleado = :numeroEmpleado", Bibliotecario.class);
            query.setParameter("numeroEmpleado", numeroEmpleado.trim());
            
            return query.uniqueResult();
            
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    /**
     * Actualiza un bibliotecario existente
     */
    public void actualizarBibliotecario(Bibliotecario bibliotecario) throws BibliotecarioNoExisteException {
        Session session = null;
        Transaction transaction = null;
        
        try {
            System.out.println("DEBUG: Iniciando actualización de bibliotecario " + bibliotecario.getId() + " con password: " + bibliotecario.getPassword());
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // Verificar que el bibliotecario existe
            Bibliotecario bibliotecarioExistente = session.get(Bibliotecario.class, bibliotecario.getId());
            if (bibliotecarioExistente == null) {
                throw new BibliotecarioNoExisteException("No existe un bibliotecario con ID: " + bibliotecario.getId());
            }
            
            System.out.println("DEBUG: Bibliotecario encontrado en BD. Password actual: " + bibliotecarioExistente.getPassword());
            
            // Usar consulta SQL nativa para actualizar el password
            Query<?> updateQuery = session.createNativeQuery(
                "UPDATE usuarios SET password = :password WHERE id = :id");
            updateQuery.setParameter("password", bibliotecario.getPassword());
            updateQuery.setParameter("id", bibliotecario.getId());
            int rowsUpdated = updateQuery.executeUpdate();
            System.out.println("DEBUG: Consulta SQL nativa ejecutada. Filas actualizadas: " + rowsUpdated);
            transaction.commit();
            System.out.println("DEBUG: Transacción commitada");
            
        } catch (Exception e) {
            System.out.println("DEBUG: Error en actualizarBibliotecario: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
                System.out.println("DEBUG: Transacción rollback");
            }
            if (e instanceof BibliotecarioNoExisteException) {
                throw e;
            }
            throw new RuntimeException("Error al actualizar bibliotecario: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
                System.out.println("DEBUG: Sesión cerrada");
            }
        }
    }
}

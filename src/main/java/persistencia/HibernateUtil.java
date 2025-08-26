package persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import logica.Lector;
import logica.Usuario;
import logica.Bibliotecario;
import logica.EstadoLector;
import logica.Zona;
import logica.Material;
import logica.ArticuloEspecial;
import logica.Libro;

/**
 * Utilidad para configurar y obtener SessionFactory de Hibernate
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    private HibernateUtil() {}
    
    private static final int MAX_RETRIES = 5;
    private static final long RETRY_DELAY_MILLIS = 5000; // 5 segundos
    
    /**
     * Obtiene la instancia única de SessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            int retries = 0;
            while (retries < MAX_RETRIES) {
                try {
                    // Configuración de Hibernate
                    Configuration configuration = new Configuration();
                    
                    // Configuración de la base de datos (con variables de entorno)
                    String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
                    String dbUrl = "jdbc:postgresql://localhost:" + dbPort + "/biblioteca_bd?sslmode=disable";
                    
                    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                    configuration.setProperty("hibernate.connection.url", dbUrl);
                    configuration.setProperty("hibernate.connection.username", "admin");
                    configuration.setProperty("hibernate.connection.password", "admin");
                    
                    // Configuración de Hibernate
                    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                    configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
                    configuration.setProperty("hibernate.show_sql", "true");
                    configuration.setProperty("hibernate.format_sql", "true");
                    configuration.setProperty("hibernate.use_sql_comments", "true");
                    
                    // Configuración adicional
                    configuration.setProperty("hibernate.connection.pool_size", "10");
                    configuration.setProperty("hibernate.connection.autocommit", "false");
                    configuration.setProperty("hibernate.jdbc.batch_size", "20");
                    
                    // Mapear las entidades
                    configuration.addAnnotatedClass(Usuario.class);
                    configuration.addAnnotatedClass(Lector.class);
                    configuration.addAnnotatedClass(Bibliotecario.class);
                    configuration.addAnnotatedClass(EstadoLector.class);
                    configuration.addAnnotatedClass(Zona.class);
                    configuration.addAnnotatedClass(Material.class);
                    configuration.addAnnotatedClass(ArticuloEspecial.class);
                    configuration.addAnnotatedClass(Libro.class);
                    
                    // Construir SessionFactory
                    sessionFactory = configuration.buildSessionFactory();
                    System.out.println("Hibernate SessionFactory construida correctamente.");
                    break; // Salir del bucle si la conexión es exitosa
                    
                } catch (Exception e) {
                    retries++;
                    System.err.println("Intento " + retries + " de " + MAX_RETRIES + ": Error al configurar Hibernate: " + e.getMessage());
                    if (retries < MAX_RETRIES) {
                        System.err.println("Reintentando en " + RETRY_DELAY_MILLIS / 1000 + " segundos...");
                        try {
                            Thread.sleep(RETRY_DELAY_MILLIS);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException("Interrupción durante el reintento de conexión a la base de datos.", ie);
                        }
                    } else {
                        throw new RuntimeException("Error al configurar Hibernate después de " + MAX_RETRIES + " reintentos: " + e.getMessage(), e);
                    }
                }
            }
        }
        return sessionFactory;
    }
    
    /**
     * Cierra la SessionFactory
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

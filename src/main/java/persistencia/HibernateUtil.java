package persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import logica.Lector;
import logica.Usuario;
import logica.Bibliotecario;
import logica.EstadoLector;
import logica.Zona;

/**
 * Utilidad para configurar y obtener SessionFactory de Hibernate
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    private HibernateUtil() {}
    
    /**
     * Obtiene la instancia única de SessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Configuración de Hibernate
                Configuration configuration = new Configuration();
                
                // Configuración de la base de datos
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/biblioteca_bd");
                configuration.setProperty("hibernate.connection.username", "admin");
                configuration.setProperty("hibernate.connection.password", "admin");
                
                // Configuración de Hibernate
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
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
                
                // Construir SessionFactory
                sessionFactory = configuration.buildSessionFactory();
                
            } catch (Exception e) {
                throw new RuntimeException("Error al configurar Hibernate: " + e.getMessage(), e);
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

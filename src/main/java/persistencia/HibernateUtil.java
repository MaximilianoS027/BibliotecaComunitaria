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
    
    /**
     * Obtiene la instancia única de SessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Cargar configuración desde hibernate.cfg.xml
                sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                System.out.println("Hibernate SessionFactory construida correctamente desde hibernate.cfg.xml.");
            } catch (Exception e) {
                System.err.println("Error al configurar Hibernate desde hibernate.cfg.xml: " + e.getMessage());
                throw new ExceptionInInitializerError(e);
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

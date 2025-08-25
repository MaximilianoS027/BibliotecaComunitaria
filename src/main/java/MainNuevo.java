import presentacion.Principal;
import persistencia.HibernateUtil;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar la nueva versión del sistema
 * con las funcionalidades de registrar bibliotecario y gestionar libros
 */
public class MainNuevo {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" SISTEMA DE BIBLIOTECA - NUEVA VERSION");
        System.out.println(" Funcionalidades: Bibliotecario y Libros");
        System.out.println("========================================");
        
        // Inicializar Hibernate y crear base de datos
        try {
            System.out.println("Inicializando base de datos...");
            HibernateUtil.getSessionFactory();
            System.out.println("Base de datos inicializada correctamente");
            System.out.println("Tablas creadas/verificadas en PostgreSQL");
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
            return; // No continuar si hay error en BD
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Configurar Look and Feel del sistema
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    System.out.println("Look and feel configurado correctamente");
                    
                    // Crear y mostrar la ventana principal
                    Principal ventanaPrincipal = new Principal();
                    ventanaPrincipal.setVisible(true);
                    
                    System.out.println("Aplicación iniciada correctamente");
                    System.out.println("Para probar las nuevas funcionalidades:");
                    System.out.println("BIBLIOTECARIOS:");
                    System.out.println("1. Ir al menú 'Gestión de Usuario'");
                    System.out.println("2. Seleccionar 'Nuevo bibliotecario'");
                    System.out.println("3. Llenar el formulario y hacer clic en 'Aceptar'");
                    System.out.println("");
                    System.out.println("LIBROS (Nueva funcionalidad):");
                    System.out.println("1. Ir al menú 'Gestión de materiales'");
                    System.out.println("2. Seleccionar 'Nuevo libro'");
                    System.out.println("3. Ingresar título y cantidad de páginas");
                    System.out.println("4. Hacer clic en 'Registrar Libro'");
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
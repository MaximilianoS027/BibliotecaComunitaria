package main;

import presentacion.Principal;
import persistencia.HibernateUtil;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar la nueva versión del sistema
 * con la funcionalidad de registrar préstamos
 */
public class MainNuevo {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" SISTEMA DE BIBLIOTECA - NUEVA VERSION");
        System.out.println(" Funcionalidad: Registrar Préstamos");
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
                    System.out.println("Para probar:");
                    System.out.println("1. Ir al menú 'Gestión Prestamo'");
                    System.out.println("2. Seleccionar 'Nuevo prestamo'");
                    System.out.println("3. Llenar el formulario y hacer clic en 'Registrar Préstamo'");
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}

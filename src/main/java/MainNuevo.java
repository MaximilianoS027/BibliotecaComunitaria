import presentacion.Principal;
import persistencia.HibernateUtil;
import publicadores.BibliotecarioPublicador;
import publicadores.LectorPublicador;
import publicadores.LibroPublicador;
import publicadores.ArticuloEspecialPublicador;
import publicadores.PrestamoPublicador;
import publicadores.AutenticacionPublicador;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar la nueva versión del sistema
 * con la funcionalidad de registrar bibliotecario y servicios web
 */
public class MainNuevo {
    
    // Variables para los servicios web
    private static BibliotecarioPublicador bibliotecarioWS;
    private static LectorPublicador lectorWS;
    private static LibroPublicador libroWS;
    private static ArticuloEspecialPublicador articuloEspecialWS;
    private static PrestamoPublicador prestamoWS;
    private static AutenticacionPublicador autenticacionWS;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" SISTEMA DE BIBLIOTECA - NUEVA VERSION");
        System.out.println(" Funcionalidad: GUI + Servicios Web");
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
        
        // Inicializar servicios web
        try {
            System.out.println("\n=== INICIANDO WEB SERVICES ===");
            inicializarServiciosWeb();
            System.out.println("✅ Todos los servicios web iniciados correctamente!");
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar servicios web: " + e.getMessage());
            e.printStackTrace();
            // Continuar con la GUI aunque fallen los servicios web
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
                    System.out.println("1. Ir al menú 'Bibliotecarios'");
                    System.out.println("2. Seleccionar 'Registrar Bibliotecario'");
                    System.out.println("3. Llenar el formulario y hacer clic en 'Aceptar'");
                    System.out.println("\n🌐 Servicios Web disponibles:");
                    mostrarServiciosWeb();
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Inicializa todos los servicios web
     */
    private static void inicializarServiciosWeb() throws Exception {
        // Crear instancias de los publicadores
        bibliotecarioWS = new BibliotecarioPublicador();
        lectorWS = new LectorPublicador();
        libroWS = new LibroPublicador();
        articuloEspecialWS = new ArticuloEspecialPublicador();
        prestamoWS = new PrestamoPublicador();
        autenticacionWS = new AutenticacionPublicador();
        
        // Publicar todos los servicios
        bibliotecarioWS.publicar();
        System.out.println("✅ Servicio Bibliotecario iniciado");
        
        lectorWS.publicar();
        System.out.println("✅ Servicio Lector iniciado");
        
        libroWS.publicar();
        System.out.println("✅ Servicio Libro iniciado");
        
        articuloEspecialWS.publicar();
        System.out.println("✅ Servicio ArticuloEspecial iniciado");
        
        prestamoWS.publicar();
        System.out.println("✅ Servicio Prestamo iniciado");
        
        autenticacionWS.publicar();
        System.out.println("✅ Servicio Autenticacion iniciado");
    }
    
    /**
     * Muestra las URLs de los servicios web disponibles
     */
    private static void mostrarServiciosWeb() {
        if (bibliotecarioWS != null) {
            System.out.println("   - BibliotecarioService: " + bibliotecarioWS.getServiceUrl());
            System.out.println("     WSDL: " + bibliotecarioWS.getServiceUrl() + "?wsdl");
        }
        if (lectorWS != null) {
            System.out.println("   - LectorService: " + lectorWS.getServiceUrl());
            System.out.println("     WSDL: " + lectorWS.getServiceUrl() + "?wsdl");
        }
        if (libroWS != null) {
            System.out.println("   - LibroService: " + libroWS.getServiceUrl());
            System.out.println("     WSDL: " + libroWS.getServiceUrl() + "?wsdl");
        }
        if (articuloEspecialWS != null) {
            System.out.println("   - ArticuloEspecialService: " + articuloEspecialWS.getServiceUrl());
            System.out.println("     WSDL: " + articuloEspecialWS.getServiceUrl() + "?wsdl");
        }
        if (prestamoWS != null) {
            System.out.println("   - PrestamoService: " + prestamoWS.getServiceUrl());
            System.out.println("     WSDL: " + prestamoWS.getServiceUrl() + "?wsdl");
        }
        if (autenticacionWS != null) {
            System.out.println("   - AutenticacionService: " + autenticacionWS.getServiceUrl());
            System.out.println("     WSDL: " + autenticacionWS.getServiceUrl() + "?wsdl");
        }
    }
}
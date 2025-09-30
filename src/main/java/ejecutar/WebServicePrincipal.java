package ejecutar;

import publicadores.BibliotecarioPublicador;
import publicadores.LectorPublicador;
import publicadores.LibroPublicador;
import publicadores.PrestamoPublicador;
import publicadores.AutenticacionPublicador;

/**
 * Punto de entrada principal para los Web Services
 * Inicia todos los publicadores de la Biblioteca Comunitaria
 */
public class WebServicePrincipal {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== INICIANDO WEB SERVICES - BIBLIOTECA COMUNITARIA ===");
            
            // Crear e iniciar todos los publicadores
            BibliotecarioPublicador bibliotecarioWS = new BibliotecarioPublicador();
            LectorPublicador lectorWS = new LectorPublicador();
            LibroPublicador libroWS = new LibroPublicador();
            PrestamoPublicador prestamoWS = new PrestamoPublicador();
            AutenticacionPublicador autenticacionWS = new AutenticacionPublicador();
            
            // Publicar todos los servicios
            bibliotecarioWS.publicar();
            System.out.println("✅ Servicio Bibliotecario iniciado");
            
            lectorWS.publicar();
            System.out.println("✅ Servicio Lector iniciado");
            
            libroWS.publicar();
            System.out.println("✅ Servicio Libro iniciado");
            
            prestamoWS.publicar();
            System.out.println("✅ Servicio Prestamo iniciado");
            
            autenticacionWS.publicar();
            System.out.println("✅ Servicio Autenticacion iniciado");
            
            System.out.println("\n🚀 Todos los Web Services iniciados correctamente!");
            System.out.println("📋 Servicios disponibles:");
            System.out.println("   - BibliotecarioService: " + bibliotecarioWS.getServiceUrl());
            System.out.println("   - LectorService: " + lectorWS.getServiceUrl());
            System.out.println("   - LibroService: " + libroWS.getServiceUrl());
            System.out.println("   - PrestamoService: " + prestamoWS.getServiceUrl());
            System.out.println("   - AutenticacionService: " + autenticacionWS.getServiceUrl());
            System.out.println("\n⏹️  Presiona Ctrl+C para detener todos los servicios.");
            
            // Mantener los servicios corriendo
            while (true) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar los Web Services: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

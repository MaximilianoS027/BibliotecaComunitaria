package configuraciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Configuración para Web Services
 * Lee configuración desde archivo de propiedades externo
 */
public class WebServiceConfiguracion {
    private String path = System.getProperty("user.home") + "/.BibliotecaComunitaria/.properties";
    private HashMap<String, String> configs;
    
    public WebServiceConfiguracion() throws Exception {
        configs = new HashMap<>();
        System.out.println("Leyendo configuración desde: " + path);
        
        try {
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String properties;
            
            while((properties = reader.readLine()) != null) {
                if(properties.startsWith("#")) {
                    String[] div = properties.split("=");
                    if(div.length == 2) {
                        configs.put(div[0], div[1]);
                    }
                }
            }
        } catch(Exception e) {
            // Configuración por defecto si no existe el archivo
            configs.put("#WS_IP", "localhost");
            configs.put("#WS_PORT", "8080");
            System.out.println("Usando configuración por defecto: localhost:8080");
        }
    }
    
    /**
     * Obtiene un valor de configuración
     * @param nombre Nombre del parámetro (ej: "#WS_IP", "#WS_PORT")
     * @return Valor configurado o null si no existe
     */
    public String getConfigOf(String nombre) {
        return configs.get(nombre); 
    }
    
    /**
     * Obtiene la URL base del Web Service
     * @return URL completa (ej: "http://localhost:8080")
     */
    public String getBaseUrl() {
        return "http://" + getConfigOf("#WS_IP") + ":" + getConfigOf("#WS_PORT");
    }
}

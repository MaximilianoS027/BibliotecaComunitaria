package publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import excepciones.LectorNoExisteException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.IControlador;

/**
 * Publicador de Web Service para operaciones de Autenticación
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class AutenticacionPublicador {
    
    private Fabrica fabrica;
    private IControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public AutenticacionPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getIControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/autenticacion";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio Autenticacion publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/autenticacion";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public String autenticarLector(String nombre, String password) 
            throws LectorNoExisteException, DatosInvalidosException {
        return controlador.autenticarLector(nombre, password);
    }
    
    @WebMethod
    public String autenticarBibliotecario(String nombre, String password) 
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        return controlador.autenticarBibliotecario(nombre, password);
    }
    
    @WebMethod
    public void cambiarPasswordLector(String lectorId, String passwordActual, String passwordNuevo) 
            throws LectorNoExisteException, DatosInvalidosException {
        controlador.cambiarPasswordLector(lectorId, passwordActual, passwordNuevo);
    }
    
    @WebMethod
    public void cambiarPasswordBibliotecario(String numeroEmpleado, String passwordActual, String passwordNuevo) 
            throws BibliotecarioNoExisteException, DatosInvalidosException {
        controlador.cambiarPasswordBibliotecario(numeroEmpleado, passwordActual, passwordNuevo);
    }
}

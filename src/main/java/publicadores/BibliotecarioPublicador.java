package publicadores;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtBibliotecario;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.IBibliotecarioControlador;

/**
 * Publicador de Web Service para operaciones de Bibliotecario
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class BibliotecarioPublicador {
    
    private Fabrica fabrica;
    private IBibliotecarioControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public BibliotecarioPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getIBibliotecarioControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/bibliotecario";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio Bibliotecario publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/bibliotecario";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public void registrarBibliotecario(String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        controlador.registrarBibliotecario(nombre, email);
    }
    
    @WebMethod
    public void registrarBibliotecarioConPassword(String nombre, String email, String password) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        controlador.registrarBibliotecarioConPassword(nombre, email, password);
    }
    
    @WebMethod
    public DtBibliotecario obtenerBibliotecario(String id) 
            throws BibliotecarioNoExisteException {
        return controlador.obtenerBibliotecario(id);
    }
    
    @WebMethod
    public String[] listarBibliotecarios() {
        return controlador.listarBibliotecarios();
    }
}

package publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

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
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        controlador.registrarBibliotecario(numeroEmpleado, nombre, email);
    }
    
    @WebMethod
    public void registrarBibliotecarioConPassword(String numeroEmpleado, String nombre, String email, String password) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        controlador.registrarBibliotecarioConPassword(numeroEmpleado, nombre, email, password);
    }
    
    @WebMethod
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
            throws BibliotecarioNoExisteException {
        return controlador.obtenerBibliotecario(numeroEmpleado);
    }
    
    @WebMethod
    public String[] listarBibliotecarios() {
        return controlador.listarBibliotecarios();
    }
    
    @WebMethod
    public boolean existeBibliotecario(String numeroEmpleado) {
        return controlador.existeBibliotecario(numeroEmpleado);
    }
}

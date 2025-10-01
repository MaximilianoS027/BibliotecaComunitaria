package publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtLector;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.ILectorControlador;

/**
 * Publicador de Web Service para operaciones de Lector
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class LectorPublicador {
    
    private Fabrica fabrica;
    private ILectorControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public LectorPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getILectorControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/lector";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio Lector publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/lector";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        controlador.registrarLector(nombre, email, direccion, fechaRegistro, estado, zona);
    }
    
    @WebMethod
    public void registrarLectorConPassword(String nombre, String email, String password, String direccion,
                                          String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        controlador.registrarLectorConPassword(nombre, email, password, direccion, fechaRegistro, estado, zona);
    }
    
    @WebMethod
    public DtLector obtenerLector(String id) throws LectorNoExisteException {
        return controlador.obtenerLector(id);
    }
    
    @WebMethod
    public DtLector obtenerLectorPorEmail(String email) throws LectorNoExisteException {
        return controlador.obtenerLectorPorEmail(email);
    }
    
    @WebMethod
    public String[] listarLectores() {
        return controlador.listarLectores();
    }
    
    @WebMethod
    public String[] listarLectoresPorEstado(String estado) {
        return controlador.listarLectoresPorEstado(estado);
    }
    
    @WebMethod
    public String[] listarLectoresPorZona(String zona) {
        return controlador.listarLectoresPorZona(zona);
    }
    
    @WebMethod
    public boolean existeLectorConEmail(String email) {
        return controlador.existeLectorConEmail(email);
    }
    
    @WebMethod
    public void cambiarEstadoLector(String idLector, String nuevoEstado) 
            throws LectorNoExisteException, DatosInvalidosException {
        controlador.cambiarEstadoLector(idLector, nuevoEstado);
    }
    
    @WebMethod
    public void cambiarZonaLector(String idLector, String nuevaZona) 
            throws LectorNoExisteException, DatosInvalidosException {
        controlador.cambiarZonaLector(idLector, nuevaZona);
    }
}

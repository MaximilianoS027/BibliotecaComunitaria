package publicadores;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtLibro;
import excepciones.LibroRepetidoException;
import excepciones.LibroNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.ILibroControlador;

/**
 * Publicador de Web Service para operaciones de Libro
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class LibroPublicador {
    
    private Fabrica fabrica;
    private ILibroControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public LibroPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getILibroControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/libro";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio Libro publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/libro";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public void registrarLibro(String titulo, int cantidadPaginas) 
            throws LibroRepetidoException, DatosInvalidosException {
        controlador.registrarLibro(titulo, cantidadPaginas);
    }
    
    @WebMethod
    public DtLibro obtenerLibro(String id) throws LibroNoExisteException {
        return controlador.obtenerLibro(id);
    }
    
    @WebMethod
    public DtLibro obtenerLibroPorTitulo(String titulo) throws LibroNoExisteException {
        return controlador.obtenerLibroPorTitulo(titulo);
    }
    
    @WebMethod
    public String[] listarLibros() {
        return controlador.listarLibros();
    }
    
    @WebMethod
    public String[] listarLibrosPorPaginas(int paginasMin, int paginasMax) {
        return controlador.listarLibrosPorPaginas(paginasMin, paginasMax);
    }
    
    @WebMethod
    public boolean existeLibro(String titulo) {
        return controlador.existeLibro(titulo);
    }
    
    @WebMethod
    public void actualizarLibro(String id, String titulo, int cantidadPaginas) 
            throws LibroNoExisteException, DatosInvalidosException {
        controlador.actualizarLibro(id, titulo, cantidadPaginas);
    }
}

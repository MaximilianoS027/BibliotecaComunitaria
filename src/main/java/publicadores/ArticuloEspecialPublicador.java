package publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtArticuloEspecial;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.ArticuloEspecialNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.IArticuloEspecialControlador;

/**
 * Publicador de Web Service para operaciones de Artículo Especial
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ArticuloEspecialPublicador {
    
    private Fabrica fabrica;
    private IArticuloEspecialControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public ArticuloEspecialPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getIArticuloEspecialControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/articuloEspecial";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio ArticuloEspecial publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/articuloEspecial";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public void registrarArticuloEspecial(String descripcion, float pesoKg, String dimensiones)
        throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        controlador.registrarArticuloEspecial(descripcion, pesoKg, dimensiones);
    }
    
    @WebMethod
    public DtArticuloEspecial obtenerArticuloEspecial(String id) throws ArticuloEspecialNoExisteException {
        return controlador.obtenerArticuloEspecial(id);
    }
    
    @WebMethod
    public DtArticuloEspecial obtenerArticuloEspecialPorDescripcion(String descripcion) throws ArticuloEspecialNoExisteException {
        return controlador.obtenerArticuloEspecialPorDescripcion(descripcion);
    }
    
    @WebMethod
    public String[] listarArticulosEspeciales() {
        return controlador.listarArticulosEspeciales();
    }
    
    @WebMethod
    public String[] listarArticulosEspecialesPorPeso(float pesoMin, float pesoMax) {
        return controlador.listarArticulosEspecialesPorPeso(pesoMin, pesoMax);
    }
    
    @WebMethod
    public boolean existeArticuloEspecial(String descripcion) {
        return controlador.existeArticuloEspecial(descripcion);
    }
    
    @WebMethod
    public void actualizarArticuloEspecial(String id, String descripcion, float pesoKg, String dimensiones)
        throws ArticuloEspecialNoExisteException, DatosInvalidosException {
        controlador.actualizarArticuloEspecial(id, descripcion, pesoKg, dimensiones);
    }
}

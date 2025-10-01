package publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import configuraciones.WebServiceConfiguracion;
import datatypes.DtPrestamo;
import excepciones.PrestamoNoExisteException;
import excepciones.DatosInvalidosException;
import interfaces.Fabrica;
import interfaces.IPrestamoControlador;

/**
 * Publicador de Web Service para operaciones de Prestamo
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class PrestamoPublicador {
    
    private Fabrica fabrica;
    private IPrestamoControlador controlador;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;
    
    public PrestamoPublicador() {
        fabrica = Fabrica.getInstancia();
        controlador = fabrica.getIPrestamoControlador();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración WS: " + ex.getMessage());
        }
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        String url = configuracion.getBaseUrl() + "/prestamo";
        endpoint = Endpoint.publish(url, this);
        System.out.println("Servicio Prestamo publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    @WebMethod(exclude = true)
    public String getServiceUrl() {
        return configuracion.getBaseUrl() + "/prestamo";
    }
    
    // ============= MÉTODOS WEB SERVICE =============
    
    @WebMethod
    public void registrarPrestamo(String lectorId, String bibliotecarioId, String materialId, 
                                 String fechaSolicitud, String estado) throws DatosInvalidosException {
        controlador.registrarPrestamo(lectorId, bibliotecarioId, materialId, fechaSolicitud, estado);
    }
    
    @WebMethod
    public DtPrestamo obtenerPrestamo(String id) throws PrestamoNoExisteException {
        return controlador.obtenerPrestamo(id);
    }
    
    @WebMethod
    public String[] listarPrestamos() {
        return controlador.listarPrestamos();
    }
    
    @WebMethod
    public String[] listarPrestamosPorEstado(String estado) {
        return controlador.listarPrestamosPorEstado(estado);
    }
    
    @WebMethod
    public String[] listarPrestamosPorLector(String lectorId) {
        return controlador.listarPrestamosPorLector(lectorId);
    }
    
    @WebMethod
    public String[] listarPrestamosPorMaterial(String materialId) {
        return controlador.listarPrestamosPorMaterial(materialId);
    }
    
    @WebMethod
    public void cambiarEstadoPrestamo(String idPrestamo, String nuevoEstado) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        controlador.cambiarEstadoPrestamo(idPrestamo, nuevoEstado);
    }
    
    @WebMethod
    public void devolverPrestamo(String idPrestamo, String fechaDevolucion) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        controlador.devolverPrestamo(idPrestamo, fechaDevolucion);
    }
    
    @WebMethod
    public void modificarPrestamo(String idPrestamo, String lectorId, String bibliotecarioId, 
                                 String materialId, String fechaSolicitud, String estado, 
                                 String fechaDevolucion) 
            throws PrestamoNoExisteException, DatosInvalidosException {
        controlador.modificarPrestamo(idPrestamo, lectorId, bibliotecarioId, 
                                     materialId, fechaSolicitud, estado, fechaDevolucion);
    }
}

package interfaces;

import datatypes.DtArticuloEspecial;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.ArticuloEspecialNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface para el controlador de artículos especiales
 * Siguiendo el principio de Single Responsibility
 */
public interface IArticuloEspecialControlador {
    
    // Operaciones de Artículo Especial
    public void registrarArticuloEspecial(String descripcion, float pesoKg, String dimensiones)
        throws ArticuloEspecialRepetidoException, DatosInvalidosException;
    
    public DtArticuloEspecial obtenerArticuloEspecial(String id) throws ArticuloEspecialNoExisteException;
    
    public DtArticuloEspecial obtenerArticuloEspecialPorDescripcion(String descripcion) throws ArticuloEspecialNoExisteException;
    
    public String[] listarArticulosEspeciales();
    
    public String[] listarArticulosEspecialesPorPeso(float pesoMin, float pesoMax);
    
    public boolean existeArticuloEspecial(String descripcion);
    
    public void actualizarArticuloEspecial(String id, String descripcion, float pesoKg, String dimensiones)
        throws ArticuloEspecialNoExisteException, DatosInvalidosException;
}

package logica;

import interfaces.IArticuloEspecialControlador;
import datatypes.DtArticuloEspecial;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.ArticuloEspecialNoExisteException;
import excepciones.DatosInvalidosException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Controlador específico para operaciones con artículos especiales
 * Implementa la lógica de negocio relacionada con artículos especiales
 */
public class ArticuloEspecialControlador implements IArticuloEspecialControlador {
    
    private ManejadorArticuloEspecial manejadorArticuloEspecial;
    
    public ArticuloEspecialControlador() {
        this.manejadorArticuloEspecial = ManejadorArticuloEspecial.getInstancia();
    }
    
    @Override
    public void registrarArticuloEspecial(String descripcion, float pesoKg, String dimensiones) 
            throws ArticuloEspecialRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción es obligatoria");
        }
        
        if (descripcion.trim().length() < 2) {
            throw new DatosInvalidosException("La descripción debe tener al menos 2 caracteres");
        }
        
        if (descripcion.trim().length() > 500) {
            throw new DatosInvalidosException("La descripción no puede exceder los 500 caracteres");
        }
        
        if (pesoKg <= 0) {
            throw new DatosInvalidosException("El peso debe ser mayor a 0 kg");
        }
        
        if (pesoKg > 1000) {
            throw new DatosInvalidosException("El peso no puede exceder los 1000 kg");
        }
        
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            throw new DatosInvalidosException("Las dimensiones son obligatorias");
        }
        
        if (dimensiones.trim().length() > 100) {
            throw new DatosInvalidosException("Las dimensiones no pueden exceder los 100 caracteres");
        }
        
        // Crear entidad
        ArticuloEspecial articulo = new ArticuloEspecial(descripcion.trim(), pesoKg, dimensiones.trim());
        
        // Validaciones adicionales usando métodos de la entidad
        if (!articulo.tieneDescripcionValida()) {
            throw new DatosInvalidosException("Descripción inválida");
        }
        
        if (!articulo.tienePesoValido()) {
            throw new DatosInvalidosException("Peso inválido");
        }
        
        if (!articulo.tieneDimensionesValidas()) {
            throw new DatosInvalidosException("Dimensiones inválidas");
        }
        
        if (!articulo.tieneFechaRegistroValida()) {
            throw new DatosInvalidosException("Fecha de registro inválida");
        }
        
        // Delegar al manejador
        manejadorArticuloEspecial.agregarArticuloEspecial(articulo);
    }
    
    @Override
    public DtArticuloEspecial obtenerArticuloEspecial(String id) throws ArticuloEspecialNoExisteException {
        
        if (id == null || id.trim().isEmpty()) {
            throw new ArticuloEspecialNoExisteException("ID de artículo especial inválido");
        }
        
        ArticuloEspecial articulo = manejadorArticuloEspecial.obtenerArticuloEspecial(id.trim());
        
        if (articulo == null) {
            throw new ArticuloEspecialNoExisteException("No existe un artículo especial con ID: " + id);
        }
        
        // Convertir a DTO
        return new DtArticuloEspecial(
            articulo.getId(),
            articulo.getDescripcion(),
            articulo.getPesoKg(),
            articulo.getDimensiones(),
            articulo.getFechaRegistro()
        );
    }
    
    @Override
    public DtArticuloEspecial obtenerArticuloEspecialPorDescripcion(String descripcion) throws ArticuloEspecialNoExisteException {
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ArticuloEspecialNoExisteException("Descripción de artículo especial inválida");
        }
        
        ArticuloEspecial articulo = manejadorArticuloEspecial.obtenerArticuloEspecialPorDescripcion(descripcion.trim());
        
        // Convertir a DTO
        return new DtArticuloEspecial(
            articulo.getId(),
            articulo.getDescripcion(),
            articulo.getPesoKg(),
            articulo.getDimensiones(),
            articulo.getFechaRegistro()
        );
    }
    
    @Override
    public String[] listarArticulosEspeciales() {
        List<ArticuloEspecial> articulos = manejadorArticuloEspecial.listarArticulosEspeciales();
        
        String[] resultado = new String[articulos.size()];
        for (int i = 0; i < articulos.size(); i++) {
            ArticuloEspecial a = articulos.get(i);
            resultado[i] = a.getId() + " | " + a.getDescripcion() + " | " + a.getPesoKg() + " | " + a.getDimensiones() + " | " + new SimpleDateFormat("dd/MM/yyyy").format(a.getFechaRegistro());
        }
        
        return resultado;
    }
    
    @Override
    public String[] listarArticulosEspecialesPorPeso(float pesoMin, float pesoMax) {
        if (pesoMin < 0 || pesoMax < 0 || pesoMin > pesoMax) {
            return new String[0];
        }
        
        List<ArticuloEspecial> articulos = manejadorArticuloEspecial.listarArticulosEspecialesPorPeso(pesoMin, pesoMax);
        
        String[] resultado = new String[articulos.size()];
        for (int i = 0; i < articulos.size(); i++) {
            ArticuloEspecial a = articulos.get(i);
            resultado[i] = a.getDescripcion() + " - " + a.getPesoKg() + " kg - " + a.getDimensiones();
        }
        
        return resultado;
    }
    
    @Override
    public boolean existeArticuloEspecial(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return false;
        }
        return manejadorArticuloEspecial.existeArticuloEspecialConDescripcion(descripcion.trim());
    }
    
    @Override
    public void actualizarArticuloEspecial(String id, String descripcion, float pesoKg, String dimensiones)
            throws ArticuloEspecialNoExisteException, DatosInvalidosException {
        
        // Validaciones de datos
        if (id == null || id.trim().isEmpty()) {
            throw new ArticuloEspecialNoExisteException("ID de artículo especial inválido");
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción es obligatoria");
        }
        
        if (descripcion.trim().length() < 2) {
            throw new DatosInvalidosException("La descripción debe tener al menos 2 caracteres");
        }
        
        if (descripcion.trim().length() > 500) {
            throw new DatosInvalidosException("La descripción no puede exceder los 500 caracteres");
        }
        
        if (pesoKg <= 0) {
            throw new DatosInvalidosException("El peso debe ser mayor a 0 kg");
        }
        
        if (pesoKg > 1000) {
            throw new DatosInvalidosException("El peso no puede exceder los 1000 kg");
        }
        
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            throw new DatosInvalidosException("Las dimensiones son obligatorias");
        }
        
        if (dimensiones.trim().length() > 100) {
            throw new DatosInvalidosException("Las dimensiones no pueden exceder los 100 caracteres");
        }
        
        // Obtener el artículo existente
        ArticuloEspecial articuloExistente = manejadorArticuloEspecial.obtenerArticuloEspecial(id.trim());
        
        if (articuloExistente == null) {
            throw new ArticuloEspecialNoExisteException("No existe un artículo especial con ID: " + id);
        }
        
        // Actualizar los datos
        articuloExistente.setDescripcion(descripcion.trim());
        articuloExistente.setPesoKg(pesoKg);
        articuloExistente.setDimensiones(dimensiones.trim());
        
        // Validaciones adicionales usando métodos de la entidad
        if (!articuloExistente.tieneDescripcionValida()) {
            throw new DatosInvalidosException("Descripción inválida");
        }
        
        if (!articuloExistente.tienePesoValido()) {
            throw new DatosInvalidosException("Peso inválido");
        }
        
        if (!articuloExistente.tieneDimensionesValidas()) {
            throw new DatosInvalidosException("Dimensiones inválidas");
        }
        
        // Delegar al manejador
        manejadorArticuloEspecial.actualizarArticuloEspecial(articuloExistente);
    }
}

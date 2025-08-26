package interfaces;

import datatypes.DtLector;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.DatosInvalidosException;

/**
 * Interface del controlador para operaciones relacionadas con Lectores
 * Principio de Single Responsibility: solo maneja lectores
 */
public interface ILectorControlador {
    
    /**
     * Registra un nuevo lector en el sistema
     * @param nombre Nombre completo del lector
     * @param email Email del lector
     * @param direccion Dirección del lector
     * @param fechaRegistro Fecha de registro en formato dd/MM/yyyy
     * @param estado Estado del lector (Activo, Suspendido)
     * @param zona Zona asignada al lector
     * @throws LectorRepetidoException Si ya existe un lector con ese email
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void registrarLector(String nombre, String email, String direccion, 
                        String fechaRegistro, String estado, String zona) 
        throws LectorRepetidoException, DatosInvalidosException;
    
    /**
     * Obtiene un lector por su ID
     * @param id ID del lector a buscar
     * @return Datos del lector
     * @throws LectorNoExisteException Si no existe el lector
     */
    DtLector obtenerLector(String id) throws LectorNoExisteException;
    
    /**
     * Obtiene un lector por su email
     * @param email Email del lector a buscar
     * @return Datos del lector
     * @throws LectorNoExisteException Si no existe el lector
     */
    DtLector obtenerLectorPorEmail(String email) throws LectorNoExisteException;
    
    /**
     * Lista todos los lectores del sistema
     * @return Array con los IDs de todos los lectores
     */
    String[] listarLectores();
    
    /**
     * Lista lectores filtrados por estado
     * @param estado Estado a filtrar (Activo, Suspendido)
     * @return Array con los IDs de lectores con ese estado
     */
    String[] listarLectoresPorEstado(String estado);
    
    /**
     * Lista lectores filtrados por zona
     * @param zona Zona a filtrar
     * @return Array con los IDs de lectores de esa zona
     */
    String[] listarLectoresPorZona(String zona);
    
    /**
     * Verifica si existe un lector con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existeLectorConEmail(String email);
    
    /**
     * Actualiza los datos de un lector existente
     * @param id ID del lector a actualizar
     * @param nombre Nuevo nombre
     * @param email Nuevo email
     * @param direccion Nueva dirección
     * @param estado Nuevo estado
     * @param zona Nueva zona
     * @throws LectorNoExisteException Si no existe el lector
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void actualizarLector(String id, String nombre, String email, String direccion,
                         String estado, String zona)
        throws LectorNoExisteException, DatosInvalidosException;
    
    /**
     * Elimina un lector del sistema
     * @param id ID del lector a eliminar
     * @throws LectorNoExisteException Si no existe el lector
     */
    void eliminarLector(String id) throws LectorNoExisteException;

    /**
     * Obtiene el ID de un lector por su nombre y email.
     * @param nombre Nombre del lector.
     * @param email Email del lector.
     * @return El ID del lector.
     * @throws LectorNoExisteException Si no se encuentra un lector con el nombre y email dados.
     */
    String obtenerIdLectorPorNombreEmail(String nombre, String email) throws LectorNoExisteException;

    /**
     * Cambia el estado de un lector.
     * @param idLector ID del lector.
     * @param nuevoEstado Nuevo estado del lector.
     * @throws LectorNoExisteException Si el lector no existe.
     * @throws DatosInvalidosException Si el nuevo estado es inválido.
     */
    void cambiarEstadoLector(String idLector, String nuevoEstado) throws LectorNoExisteException, DatosInvalidosException;

    /**
     * Cambia la zona de un lector.
     * @param idLector ID del lector.
     * @param nuevaZona Nueva zona del lector.
     * @throws LectorNoExisteException Si el lector no existe.
     * @throws DatosInvalidosException Si la nueva zona es inválida.
     */
    void cambiarZonaLector(String idLector, String nuevaZona) throws LectorNoExisteException, DatosInvalidosException;
}

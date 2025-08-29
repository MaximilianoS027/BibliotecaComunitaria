package interfaces;

import datatypes.DtPrestamo;
import excepciones.DatosInvalidosException;
import excepciones.PrestamoNoExisteException;

/**
 * Interface del controlador para operaciones relacionadas con Préstamos
 */
public interface IPrestamoControlador {
    
    /**
     * Registra un nuevo préstamo en el sistema
     * @param lectorId ID del lector
     * @param bibliotecarioId ID del bibliotecario
     * @param materialId ID del material
     * @param fechaSolicitud Fecha de solicitud en formato dd/MM/yyyy
     * @param estado Estado inicial del préstamo
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void registrarPrestamo(String lectorId, String bibliotecarioId, String materialId, 
                          String fechaSolicitud, String estado) throws DatosInvalidosException;
    
    /**
     * Obtiene un préstamo por su ID
     * @param id ID del préstamo a buscar
     * @return Datos del préstamo
     * @throws PrestamoNoExisteException Si no existe el préstamo
     */
    DtPrestamo obtenerPrestamo(String id) throws PrestamoNoExisteException;
    
    /**
     * Lista todos los préstamos del sistema
     * @return Array con información de todos los préstamos
     */
    String[] listarPrestamos();
    
    /**
     * Lista préstamos filtrados por estado
     * @param estado Estado a filtrar
     * @return Array con información de préstamos con ese estado
     */
    String[] listarPrestamosPorEstado(String estado);
    
    /**
     * Lista préstamos de un lector específico
     * @param lectorId ID del lector
     * @return Array con información de préstamos del lector
     */
    String[] listarPrestamosPorLector(String lectorId);
    
    /**
     * Lista préstamos de un material específico
     * @param materialId ID del material
     * @return Array con información de préstamos del material
     */
    String[] listarPrestamosPorMaterial(String materialId);
    
    /**
     * Cambia el estado de un préstamo
     * @param idPrestamo ID del préstamo
     * @param nuevoEstado Nuevo estado del préstamo
     * @throws PrestamoNoExisteException Si no existe el préstamo
     * @throws DatosInvalidosException Si el estado no es válido
     */
    void cambiarEstadoPrestamo(String idPrestamo, String nuevoEstado) 
        throws PrestamoNoExisteException, DatosInvalidosException;
    
    /**
     * Registra la devolución de un préstamo
     * @param idPrestamo ID del préstamo
     * @param fechaDevolucion Fecha de devolución en formato dd/MM/yyyy
     * @throws PrestamoNoExisteException Si no existe el préstamo
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void devolverPrestamo(String idPrestamo, String fechaDevolucion) 
        throws PrestamoNoExisteException, DatosInvalidosException;
    
    /**
     * Modifica la información completa de un préstamo
     * @param idPrestamo ID del préstamo a modificar
     * @param lectorId Nuevo ID del lector
     * @param bibliotecarioId Nuevo ID del bibliotecario
     * @param materialId Nuevo ID del material
     * @param fechaSolicitud Nueva fecha de solicitud en formato dd/MM/yyyy
     * @param estado Nuevo estado del préstamo
     * @param fechaDevolucion Nueva fecha de devolución en formato dd/MM/yyyy (puede ser null o vacía)
     * @throws PrestamoNoExisteException Si no existe el préstamo
     * @throws DatosInvalidosException Si los datos no son válidos
     */
    void modificarPrestamo(String idPrestamo, String lectorId, String bibliotecarioId, 
                          String materialId, String fechaSolicitud, String estado, 
                          String fechaDevolucion) 
        throws PrestamoNoExisteException, DatosInvalidosException;
}
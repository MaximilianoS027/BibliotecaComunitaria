package interfaces;

import java.util.List;

/**
 * Interface para el controlador de materiales con préstamos pendientes
 * Permite identificar materiales que tienen más de un préstamo pendiente
 */
public interface IMaterialesConPrestamosPendientesControlador {

    /**
     * Obtiene una lista de materiales que tienen más de un préstamo pendiente
     * Solo se consideran préstamos con estado PENDIENTE
     * @return Lista de Object[] donde cada array contiene [materialId (String), tipo (String), nombre (String), cantidadPrestamos (Integer)]
     */
    List<Object[]> obtenerMaterialesConMuchosPrestamos();

    /**
     * Obtiene la cantidad de préstamos pendientes para un material específico
     * @param materialId ID del material
     * @return Cantidad de préstamos pendientes (solo estado PENDIENTE)
     */
    int contarPrestamosPendientesPorMaterial(String materialId);

    /**
     * Verifica si un material tiene más de un préstamo pendiente
     * @param materialId ID del material
     * @return true si tiene más de 1 préstamo pendiente, false en caso contrario
     */
    boolean tieneMuchosPrestamos(String materialId);

    /**
     * Obtiene información detallada de un material específico
     * @param materialId ID del material
     * @return Array con información detallada del material, o null si no existe
     */
    String[] obtenerDetallesMaterial(String materialId);

    /**
     * Obtiene la lista de préstamos pendientes para un material específico
     * @param materialId ID del material
     * @return Lista de Object[] donde cada array contiene [lector, bibliotecario, fechaSolicitud]
     */
    List<Object[]> obtenerPrestamosPendientesPorMaterial(String materialId);
}

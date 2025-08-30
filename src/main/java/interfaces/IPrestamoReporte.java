package interfaces;

import datatypes.DtPrestamo;
import excepciones.DatosInvalidosException;

/**
 * Interface para generar reportes de préstamos
 * Controlador independiente para análisis y reportes
 */
public interface IPrestamoReporte {
    
    /**
     * Obtiene todos los préstamos agrupados por zona para generar reporte
     * @return Array de préstamos ordenados por zona
     */
    DtPrestamo[] obtenerReportePorZona();
    
    /**
     * Obtiene préstamos por zona específica con todos los filtros posibles
     * @param zona Zona específica (puede ser null para todas)
     * @param estado Estado del préstamo (puede ser null para todos)
     * @param fechaDesde Fecha desde (formato yyyy-MM-dd, puede ser null)
     * @param fechaHasta Fecha hasta (formato yyyy-MM-dd, puede ser null)
     * @return Array de préstamos que cumplen los criterios
     * @throws DatosInvalidosException si los parámetros de fecha son inválidos
     */
    DtPrestamo[] obtenerPrestamosFiltrados(String zona, String estado, 
                                          String fechaDesde, String fechaHasta) 
            throws DatosInvalidosException;
    
    /**
     * Obtiene estadísticas resumidas por zona
     * @return Array de strings con formato "ZONA: X préstamos (Y pendientes, Z en curso, W devueltos)"
     */
    String[] obtenerEstadisticasPorZona();
    
    /**
     * Obtiene todas las zonas disponibles en el sistema
     * @return Array con los nombres de todas las zonas
     */
    String[] obtenerZonasDisponibles();
}

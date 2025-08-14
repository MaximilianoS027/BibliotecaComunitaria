package com.pap.domain.repository;

import com.pap.domain.model.concrete.Prestamo;
import com.pap.domain.model.abstract.Usuario;
import com.pap.domain.model.abstract.Material;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de repositorio para préstamos
 */
public interface PrestamoRepository {
    
    /**
     * Guarda un préstamo en el repositorio
     * @param prestamo Préstamo a guardar
     * @return Préstamo guardado
     */
    Prestamo guardar(Prestamo prestamo);
    
    /**
     * Busca un préstamo por su ID
     * @param id ID del préstamo
     * @return Optional con el préstamo encontrado
     */
    Optional<Prestamo> buscarPorId(String id);
    
    /**
     * Obtiene todos los préstamos
     * @return Lista de todos los préstamos
     */
    List<Prestamo> obtenerTodos();
    
    /**
     * Busca préstamos por lector
     * @param lector Lector del cual buscar préstamos
     * @return Lista de préstamos del lector
     */
    List<Prestamo> buscarPorLector(Usuario lector);
    
    /**
     * Busca préstamos por bibliotecario
     * @param bibliotecario Bibliotecario del cual buscar préstamos
     * @return Lista de préstamos del bibliotecario
     */
    List<Prestamo> buscarPorBibliotecario(Usuario bibliotecario);
    
    /**
     * Busca préstamos por material
     * @param material Material del cual buscar préstamos
     * @return Lista de préstamos del material
     */
    List<Prestamo> buscarPorMaterial(Material material);
    
    /**
     * Busca préstamos por estado
     * @param estado Estado del préstamo a buscar
     * @return Lista de préstamos con el estado especificado
     */
    List<Prestamo> buscarPorEstado(String estado);
    
    /**
     * Busca préstamos por rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de préstamos en el rango de fechas
     */
    List<Prestamo> buscarPorRangoFechas(Date fechaInicio, Date fechaFin);
    
    /**
     * Busca préstamos vencidos
     * @param fechaActual Fecha actual para comparar
     * @return Lista de préstamos vencidos
     */
    List<Prestamo> buscarPrestamosVencidos(Date fechaActual);
    
    /**
     * Actualiza un préstamo existente
     * @param prestamo Préstamo a actualizar
     * @return Préstamo actualizado
     */
    Prestamo actualizar(Prestamo prestamo);
    
    /**
     * Actualiza el estado de un préstamo
     * @param idPrestamo ID del préstamo
     * @param nuevoEstado Nuevo estado del préstamo
     * @return true si se actualizó correctamente
     */
    boolean actualizarEstado(String idPrestamo, String nuevoEstado);
    
    /**
     * Elimina un préstamo por su ID
     * @param id ID del préstamo a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminarPorId(String id);
    
    /**
     * Verifica si existe un préstamo con el ID especificado
     * @param id ID a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorId(String id);
}

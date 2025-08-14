package com.pap.domain.repository;

import com.pap.domain.model.abstract.Material;
import com.pap.domain.model.concrete.Libro;
import com.pap.domain.model.concrete.Articulo;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de repositorio para materiales
 */
public interface MaterialRepository {
    
    /**
     * Guarda un material en el repositorio
     * @param material Material a guardar
     * @return Material guardado
     */
    Material guardar(Material material);
    
    /**
     * Busca un material por su ID
     * @param id ID del material
     * @return Optional con el material encontrado
     */
    Optional<Material> buscarPorId(String id);
    
    /**
     * Obtiene todos los materiales
     * @return Lista de todos los materiales
     */
    List<Material> obtenerTodos();
    
    /**
     * Obtiene todos los libros
     * @return Lista de todos los libros
     */
    List<Libro> obtenerTodosLosLibros();
    
    /**
     * Obtiene todos los artículos
     * @return Lista de todos los artículos
     */
    List<Articulo> obtenerTodosLosArticulos();
    
    /**
     * Busca materiales por tipo
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     */
    List<Material> buscarPorTipo(String tipo);
    
    /**
     * Busca libros por título
     * @param titulo Título del libro a buscar
     * @return Lista de libros que coinciden con el título
     */
    List<Libro> buscarLibrosPorTitulo(String titulo);
    
    /**
     * Busca artículos por descripción
     * @param descripcion Descripción del artículo a buscar
     * @return Lista de artículos que coinciden con la descripción
     */
    List<Articulo> buscarArticulosPorDescripcion(String descripcion);
    
    /**
     * Actualiza un material existente
     * @param material Material a actualizar
     * @return Material actualizado
     */
    Material actualizar(Material material);
    
    /**
     * Elimina un material por su ID
     * @param id ID del material a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminarPorId(String id);
    
    /**
     * Verifica si existe un material con el ID especificado
     * @param id ID a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorId(String id);
}

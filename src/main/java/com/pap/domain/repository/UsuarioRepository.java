package com.pap.domain.repository;

import com.pap.domain.model.abst.Usuario;
import com.pap.domain.model.concrete.Lector;
import com.pap.domain.model.concrete.Bibliotecario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de repositorio para usuarios
 */
public interface UsuarioRepository {
    
    /**
     * Guarda un usuario en el repositorio
     * @param usuario Usuario a guardar
     * @return Usuario guardado
     */
    Usuario guardar(Usuario usuario);
    
    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario encontrado
     */
    Optional<Usuario> buscarPorId(String id);
    
    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Optional con el usuario encontrado
     */
    Optional<Usuario> buscarPorEmail(String email);
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<Usuario> obtenerTodos();
    
    /**
     * Obtiene todos los lectores
     * @return Lista de todos los lectores
     */
    List<Lector> obtenerTodosLosLectores();
    
    /**
     * Obtiene todos los bibliotecarios
     * @return Lista de todos los bibliotecarios
     */
    List<Bibliotecario> obtenerTodosLosBibliotecarios();
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario a actualizar
     * @return Usuario actualizado
     */
    Usuario actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminarPorId(String id);
    
    /**
     * Verifica si existe un usuario con el email especificado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorEmail(String email);
}

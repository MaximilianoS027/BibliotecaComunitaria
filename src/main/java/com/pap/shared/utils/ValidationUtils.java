package com.pap.shared.utils;

import java.util.regex.Pattern;

/**
 * Clase de utilidades para validaciones
 */
public class ValidationUtils {
    
    // Patrón para validar email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Patrón para validar que solo contenga letras y espacios
    private static final Pattern NOMBRE_PATTERN = Pattern.compile(
        "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"
    );
    
    /**
     * Valida si un String no es null ni está vacío
     * @param valor String a validar
     * @return true si es válido
     */
    public static boolean esStringValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
    
    /**
     * Valida si un String tiene la longitud mínima especificada
     * @param valor String a validar
     * @param longitudMinima Longitud mínima requerida
     * @return true si cumple con la longitud mínima
     */
    public static boolean tieneLongitudMinima(String valor, int longitudMinima) {
        return esStringValido(valor) && valor.trim().length() >= longitudMinima;
    }
    
    /**
     * Valida si un String tiene la longitud máxima especificada
     * @param valor String a validar
     * @param longitudMaxima Longitud máxima permitida
     * @return true si cumple con la longitud máxima
     */
    public static boolean tieneLongitudMaxima(String valor, int longitudMaxima) {
        return esStringValido(valor) && valor.trim().length() <= longitudMaxima;
    }
    
    /**
     * Valida si un String está dentro del rango de longitudes especificado
     * @param valor String a validar
     * @param longitudMinima Longitud mínima requerida
     * @param longitudMaxima Longitud máxima permitida
     * @return true si está dentro del rango
     */
    public static boolean tieneLongitudEnRango(String valor, int longitudMinima, int longitudMaxima) {
        return tieneLongitudMinima(valor, longitudMinima) && 
               tieneLongitudMaxima(valor, longitudMaxima);
    }
    
    /**
     * Valida si un String tiene formato de email válido
     * @param email String a validar como email
     * @return true si es un email válido
     */
    public static boolean esEmailValido(String email) {
        return esStringValido(email) && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Valida si un String contiene solo letras y espacios
     * @param nombre String a validar
     * @return true si solo contiene letras y espacios
     */
    public static boolean esNombreValido(String nombre) {
        return esStringValido(nombre) && NOMBRE_PATTERN.matcher(nombre).matches();
    }
    
    /**
     * Valida si un número entero está dentro del rango especificado
     * @param valor Número a validar
     * @param minimo Valor mínimo del rango
     * @param maximo Valor máximo del rango
     * @return true si está dentro del rango
     */
    public static boolean estaEnRango(int valor, int minimo, int maximo) {
        return valor >= minimo && valor <= maximo;
    }
    
    /**
     * Valida si un número float está dentro del rango especificado
     * @param valor Número a validar
     * @param minimo Valor mínimo del rango
     * @param maximo Valor máximo del rango
     * @return true si está dentro del rango
     */
    public static boolean estaEnRango(float valor, float minimo, float maximo) {
        return valor >= minimo && valor <= maximo;
    }
    
    /**
     * Valida si un número es positivo
     * @param valor Número a validar
     * @return true si es positivo
     */
    public static boolean esPositivo(int valor) {
        return valor > 0;
    }
    
    /**
     * Valida si un número float es positivo
     * @param valor Número a validar
     * @return true si es positivo
     */
    public static boolean esPositivo(float valor) {
        return valor > 0;
    }
    
    /**
     * Valida si un objeto no es null
     * @param objeto Objeto a validar
     * @return true si no es null
     */
    public static boolean noEsNull(Object objeto) {
        return objeto != null;
    }
    
    /**
     * Valida si un objeto es null
     * @param objeto Objeto a validar
     * @return true si es null
     */
    public static boolean esNull(Object objeto) {
        return objeto == null;
    }
}

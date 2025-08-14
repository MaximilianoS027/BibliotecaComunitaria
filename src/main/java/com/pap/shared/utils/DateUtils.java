package com.pap.shared.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Clase de utilidades para el manejo de fechas
 */
public class DateUtils {
    
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
    
    /**
     * Convierte una fecha a String en formato dd/MM/yyyy
     * @param fecha Fecha a convertir
     * @return String con la fecha formateada
     */
    public static String formatearFecha(Date fecha) {
        if (fecha == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(fecha);
    }
    
    /**
     * Convierte una fecha a String en formato dd/MM/yyyy HH:mm:ss
     * @param fecha Fecha a convertir
     * @return String con la fecha y hora formateada
     */
    public static String formatearFechaHora(Date fecha) {
        if (fecha == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_HORA);
        return sdf.format(fecha);
    }
    
    /**
     * Convierte un String a Date
     * @param fechaString String con la fecha en formato dd/MM/yyyy
     * @return Date correspondiente
     * @throws ParseException Si el formato no es válido
     */
    public static Date parsearFecha(String fechaString) throws ParseException {
        if (fechaString == null || fechaString.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.parse(fechaString);
    }
    
    /**
     * Obtiene la fecha actual
     * @return Date con la fecha actual
     */
    public static Date obtenerFechaActual() {
        return new Date();
    }
    
    /**
     * Calcula la diferencia en días entre dos fechas
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Diferencia en días
     */
    public static long calcularDiferenciaDias(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) return 0;
        
        long diferenciaMillis = fechaFin.getTime() - fechaInicio.getTime();
        return diferenciaMillis / (24 * 60 * 60 * 1000);
    }
    
    /**
     * Agrega días a una fecha
     * @param fecha Fecha base
     * @param dias Días a agregar
     * @return Nueva fecha
     */
    public static Date agregarDias(Date fecha, int dias) {
        if (fecha == null) return null;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }
    
    /**
     * Verifica si una fecha está en el pasado
     * @param fecha Fecha a verificar
     * @return true si está en el pasado
     */
    public static boolean esFechaPasada(Date fecha) {
        if (fecha == null) return false;
        return fecha.before(obtenerFechaActual());
    }
    
    /**
     * Verifica si una fecha está en el futuro
     * @param fecha Fecha a verificar
     * @return true si está en el futuro
     */
    public static boolean esFechaFutura(Date fecha) {
        if (fecha == null) return false;
        return fecha.after(obtenerFechaActual());
    }
}

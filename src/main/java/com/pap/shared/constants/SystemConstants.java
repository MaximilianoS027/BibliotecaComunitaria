package com.pap.shared.constants;

/**
 * Clase que contiene las constantes del sistema
 */
public class SystemConstants {
    
    // Constantes para la aplicación
    public static final String NOMBRE_APLICACION = "Sistema de Gestión de Biblioteca";
    public static final String VERSION_APLICACION = "1.0.0";
    public static final String AUTOR = "PAP Proyect";
    
    // Constantes para la base de datos
    public static final String CONFIG_DB_URL = "jdbc:h2:mem:testdb";
    public static final String CONFIG_DB_USER = "sa";
    public static final String CONFIG_DB_PASSWORD = "";
    public static final String CONFIG_DB_DRIVER = "org.h2.Driver";
    
    // Constantes para Hibernate
    public static final String HIBERNATE_DIALECT = "org.hibernate.dialect.H2Dialect";
    public static final String HIBERNATE_SHOW_SQL = "true";
    public static final String HIBERNATE_FORMAT_SQL = "true";
    public static final String HIBERNATE_HBM2DDL_AUTO = "create-drop";
    
    // Constantes para validaciones
    public static final int NOMBRE_MIN_LENGTH = 2;
    public static final int NOMBRE_MAX_LENGTH = 50;
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int DIRECCION_MIN_LENGTH = 10;
    public static final int DIRECCION_MAX_LENGTH = 200;
    public static final int TITULO_MIN_LENGTH = 1;
    public static final int TITULO_MAX_LENGTH = 200;
    public static final int DESCRIPCION_MIN_LENGTH = 5;
    public static final int DESCRIPCION_MAX_LENGTH = 500;
    
    // Constantes para préstamos
    public static final int DIAS_PRESTAMO_DEFAULT = 15;
    public static final int DIAS_PRESTAMO_MAX = 30;
    public static final int MATERIALES_MAX_POR_PRESTAMO = 5;
    
    // Constantes para mensajes
    public static final String MSG_OPERACION_EXITOSA = "Operación realizada con éxito";
    public static final String MSG_ERROR_GENERICO = "Ha ocurrido un error inesperado";
    public static final String MSG_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String MSG_MATERIAL_NO_ENCONTRADO = "Material no encontrado";
    public static final String MSG_PRESTAMO_NO_ENCONTRADO = "Préstamo no encontrado";
    public static final String MSG_MATERIAL_NO_DISPONIBLE = "Material no disponible para préstamo";
    public static final String MSG_LECTOR_SUSPENDIDO = "El lector está suspendido";
    public static final String MSG_LIMITE_MATERIALES = "Se ha alcanzado el límite de materiales por préstamo";
    
    // Constantes para archivos
    public static final String CONFIG_FILE = "config.properties";
    public static final String LOG_FILE = "biblioteca.log";
    
    // Constantes para la interfaz gráfica
    public static final int VENTANA_WIDTH = 800;
    public static final int VENTANA_HEIGHT = 600;
    public static final String ICONO_APLICACION = "/images/icon.png";
    
    // Constantes para logging
    public static final String LOG_LEVEL = "INFO";
    public static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
    
    // Constructor privado para evitar instanciación
    private SystemConstants() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }
}

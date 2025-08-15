package com.pap;

import com.pap.shared.constants.SystemConstants;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.io.File;

/**
 * Clase principal que inicia la aplicación del Sistema de Gestión de Biblioteca
 */
public class Main {
    
    private static final Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
        try {
            // Configurar logging
            configurarLogging();
            
            logger.info("Iniciando " + SystemConstants.NOMBRE_APLICACION + " v" + SystemConstants.VERSION_APLICACION);
            
            // Configurar look and feel del sistema
            configurarLookAndFeel();
            
            // Mostrar ventana principal
            mostrarVentanaPrincipal();
            
            logger.info("Aplicación iniciada correctamente");
            
        } catch (Exception e) {
            logger.error("Error al iniciar la aplicación: " + e.getMessage(), e);
            mostrarError("Error al iniciar la aplicación", e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Configura el sistema de logging
     */
    private static void configurarLogging() {
        try {
            // Buscar el archivo de configuración en el classpath
            String logConfigPath = "src/main/resources/log4j.properties";
            if (new File(logConfigPath).exists()) {
                PropertyConfigurator.configure(logConfigPath);
                logger.info("Logging configurado desde archivo: " + logConfigPath);
            } else {
                // Configuración por defecto si no se encuentra el archivo
                logger.warn("Archivo de configuración de logging no encontrado, usando configuración por defecto");
            }
        } catch (Exception e) {
            System.err.println("Error al configurar logging: " + e.getMessage());
        }
    }
    
    /**
     * Configura el look and feel del sistema
     */
    private static void configurarLookAndFeel() {
        try {
            // Usar el look and feel del sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            logger.info("Look and feel configurado: " + UIManager.getLookAndFeel().getName());
        } catch (Exception e) {
            logger.warn("No se pudo configurar el look and feel del sistema: " + e.getMessage());
        }
    }
    
    /**
     * Muestra la ventana principal de la aplicación
     */
    private static void mostrarVentanaPrincipal() {
        try {
            // Crear y mostrar la ventana principal
            SwingUtilities.invokeLater(() -> {
                try {
                    // TODO: Implementar la ventana principal
                    JFrame frame = new JFrame(SystemConstants.NOMBRE_APLICACION);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(SystemConstants.VENTANA_WIDTH, SystemConstants.VENTANA_HEIGHT);
                    frame.setLocationRelativeTo(null);
                    
                    // Panel temporal con información de la aplicación
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    
                    JLabel tituloLabel = new JLabel(SystemConstants.NOMBRE_APLICACION);
                    tituloLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    tituloLabel.setFont(tituloLabel.getFont().deriveFont(24.0f));
                    
                    JLabel versionLabel = new JLabel("Versión: " + SystemConstants.VERSION_APLICACION);
                    versionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    
                    JLabel autorLabel = new JLabel("Autor: " + SystemConstants.AUTOR);
                    autorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    
                    JLabel estadoLabel = new JLabel("Sistema iniciado correctamente");
                    estadoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    
                    panel.add(Box.createVerticalGlue());
                    panel.add(tituloLabel);
                    panel.add(Box.createVerticalStrut(20));
                    panel.add(versionLabel);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(autorLabel);
                    panel.add(Box.createVerticalStrut(20));
                    panel.add(estadoLabel);
                    panel.add(Box.createVerticalGlue());
                    
                    frame.add(panel);
                    frame.setVisible(true);
                    
                    logger.info("Ventana principal mostrada correctamente");
                    
                } catch (Exception e) {
                    logger.error("Error al mostrar la ventana principal: " + e.getMessage(), e);
                    mostrarError("Error al mostrar la interfaz", e.getMessage());
                }
            });
            
        } catch (Exception e) {
            logger.error("Error al crear la ventana principal: " + e.getMessage(), e);
            throw new RuntimeException("No se pudo crear la ventana principal", e);
        }
    }
    
    /**
     * Muestra un diálogo de error
     * @param titulo Título del diálogo
     * @param mensaje Mensaje de error
     */
    private static void mostrarError(String titulo, String mensaje) {
        try {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                    null,
                    mensaje,
                    titulo,
                    JOptionPane.ERROR_MESSAGE
                );
            });
        } catch (Exception e) {
            System.err.println("Error al mostrar diálogo de error: " + e.getMessage());
        }
    }
}

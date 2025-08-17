package presentacion;

import interfaces.Fabrica;
import interfaces.IControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del sistema con menú
 */
public class Principal extends JFrame {
    
    private IControlador controlador;
    private JDesktopPane desktopPane;
    
    public Principal() {
        this.controlador = Fabrica.getInstancia().getIControlador();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Biblioteca - Lectores.uy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Desktop pane para internal frames
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);
        
        // Crear menú
        crearMenu();
        
        // Centrar ventana
        setLocationRelativeTo(null);
    }
    
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Bibliotecarios
        JMenu menuBibliotecarios = new JMenu("Bibliotecarios");
        
        JMenuItem itemRegistrarBibliotecario = new JMenuItem("Registrar Bibliotecario");
        itemRegistrarBibliotecario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarBibliotecario();
            }
        });
        
        JMenuItem itemConsultarBibliotecarios = new JMenuItem("Consultar Bibliotecarios");
        itemConsultarBibliotecarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirConsultarBibliotecarios();
            }
        });
        
        menuBibliotecarios.add(itemRegistrarBibliotecario);
        menuBibliotecarios.add(itemConsultarBibliotecarios);
        
        menuBar.add(menuBibliotecarios);
        
        setJMenuBar(menuBar);
    }
    
    private void abrirRegistrarBibliotecario() {
        RegistrarBibliotecario ventana = new RegistrarBibliotecario(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        
        // Centrar internal frame
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    private void abrirConsultarBibliotecarios() {
        // TODO: Implementar consulta de bibliotecarios
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                new Principal().setVisible(true);
            }
        });
    }
}

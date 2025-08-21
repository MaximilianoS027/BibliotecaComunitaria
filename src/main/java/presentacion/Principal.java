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
    
    // Crear menú
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Gestión de Usuario
        JMenu menuGestionUsuario = new JMenu("Gestión de Usuario");

        JMenuItem itemNuevoLector = new JMenuItem("Nuevo lector");
        itemNuevoLector.addActionListener(e -> abrirRegistrarLector());
        menuGestionUsuario.add(itemNuevoLector);

        JMenuItem itemNuevoBibliotecario = new JMenuItem("Nuevo bibliotecario");
        itemNuevoBibliotecario.addActionListener(e -> abrirRegistrarBibliotecario());
        menuGestionUsuario.add(itemNuevoBibliotecario);

        JMenuItem itemEstadoLector = new JMenuItem("Estado lector");
        itemEstadoLector.addActionListener(e -> abrirEstadoLector());
        menuGestionUsuario.add(itemEstadoLector);

        JMenuItem itemModificarZonaLector = new JMenuItem("Modificar zona lector");
        itemModificarZonaLector.addActionListener(e -> abrirModificarZonaLector());
        menuGestionUsuario.add(itemModificarZonaLector);

        // Menú Gestión de Materiales
        JMenu menuGestionMateriales = new JMenu("Gestión de materiales");

        JMenuItem itemNuevoLibro = new JMenuItem("Nuevo libro");
        itemNuevoLibro.addActionListener(e -> abrirNuevoLibro());
        menuGestionMateriales.add(itemNuevoLibro);

        JMenuItem itemNuevoArticulo = new JMenuItem("Nuevo articulo");
        itemNuevoArticulo.addActionListener(e -> abrirNuevoArticulo());
        menuGestionMateriales.add(itemNuevoArticulo);

        JMenuItem itemVerDonaciones = new JMenuItem("Ver todas las donaciones");
        itemVerDonaciones.addActionListener(e -> abrirVerDonaciones());
        menuGestionMateriales.add(itemVerDonaciones);

        JMenuItem itemDonacionesPorFecha = new JMenuItem("Donaciones por fecha");
        itemDonacionesPorFecha.addActionListener(e -> abrirDonacionesPorFecha());
        menuGestionMateriales.add(itemDonacionesPorFecha);

        // Menú Gestión de Préstamo
        JMenu menuGestionPrestamo = new JMenu("Gestión Prestamo");

        JMenuItem itemNuevoPrestamo = new JMenuItem("Nuevo prestamo");
        itemNuevoPrestamo.addActionListener(e -> abrirNuevoPrestamo());
        menuGestionPrestamo.add(itemNuevoPrestamo);

        JMenuItem itemEstadoPrestamo = new JMenuItem("Estado de un prestamo");
        itemEstadoPrestamo.addActionListener(e -> abrirEstadoPrestamo());
        menuGestionPrestamo.add(itemEstadoPrestamo);

        JMenuItem itemModificarInfoPrestamo = new JMenuItem("Modificar información de prestamo");
        itemModificarInfoPrestamo.addActionListener(e -> abrirModificarInfoPrestamo());
        menuGestionPrestamo.add(itemModificarInfoPrestamo);

        JMenuItem itemListarPrestamos = new JMenuItem("Listar prestamos");
        itemListarPrestamos.addActionListener(e -> abrirListarPrestamos());
        menuGestionPrestamo.add(itemListarPrestamos);

        // Menú Consultas
        JMenu menuConsultas = new JMenu("Consultas");

        JMenuItem itemHistorialPrestamos = new JMenuItem("Historial de Prestamos");
        itemHistorialPrestamos.addActionListener(e -> abrirHistorialPrestamos());
        menuConsultas.add(itemHistorialPrestamos);

        JMenuItem itemReportePrestamosPorZona = new JMenuItem("Reporte de prestamos por zona");
        itemReportePrestamosPorZona.addActionListener(e -> abrirReportePrestamosPorZona());
        menuConsultas.add(itemReportePrestamosPorZona);

        JMenuItem itemMaterialesConPrestamos = new JMenuItem("Materiales con prestamos");
        itemMaterialesConPrestamos.addActionListener(e -> abrirMaterialesConPrestamos());
        menuConsultas.add(itemMaterialesConPrestamos);

        // Añadir menús a la barra de menú
        menuBar.add(menuGestionUsuario);
        menuBar.add(menuGestionMateriales);
        menuBar.add(menuGestionPrestamo);
        menuBar.add(menuConsultas);

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
    
    private void abrirRegistrarLector() {
        RegistrarLector ventana = new RegistrarLector(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        
        // Centrar internal frame
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirEstadoLector() {
        EstadoLector ventana = new EstadoLector(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirModificarZonaLector() {
        ModificarZonaLector ventana = new ModificarZonaLector(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevoLibro() {
        NuevoLibro ventana = new NuevoLibro(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevoArticulo() {
        NuevoArticulo ventana = new NuevoArticulo(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirVerDonaciones() {
        VerDonaciones ventana = new VerDonaciones(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirDonacionesPorFecha() {
        DonacionesPorFecha ventana = new DonacionesPorFecha(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevoPrestamo() {
        NuevoPrestamo ventana = new NuevoPrestamo(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirEstadoPrestamo() {
        EstadoPrestamo ventana = new EstadoPrestamo(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirModificarInfoPrestamo() {
        ModificarInfoPrestamo ventana = new ModificarInfoPrestamo(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirListarPrestamos() {
        ListarPrestamos ventana = new ListarPrestamos(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirHistorialPrestamos() {
        HistorialPrestamos ventana = new HistorialPrestamos(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirReportePrestamosPorZona() {
        ReportePrestamosPorZona ventana = new ReportePrestamosPorZona(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirMaterialesConPrestamos() {
        MaterialesConPrestamos ventana = new MaterialesConPrestamos(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                new Principal().setVisible(true);
            }
        });
    }
}

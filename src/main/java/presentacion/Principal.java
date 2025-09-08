package presentacion;

import interfaces.Fabrica;
import interfaces.IControlador;
import interfaces.IBibliotecarioControlador;
import interfaces.ILectorControlador;
import interfaces.ILibroControlador;
import interfaces.IArticuloEspecialControlador;
import interfaces.IMaterialesConPrestamosPendientesControlador;
// import logica.ControladorPrincipal;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema con menú
 * Actualizada para usar controladores específicos
 */
public class Principal extends JFrame {
    
    private IControlador controlador; // Controlador principal
    private IBibliotecarioControlador bibliotecarioControlador;
    private ILectorControlador lectorControlador;
    private ILibroControlador libroControlador;
    private IArticuloEspecialControlador articuloEspecialControlador;
    private IMaterialesConPrestamosPendientesControlador materialesConPrestamosPendientesControlador;

    private JDesktopPane desktopPane;
    
    public Principal() {
        // Obtener controladores de la Fábrica
        Fabrica fabrica = Fabrica.getInstancia();
        this.controlador = fabrica.getIControlador();
        this.bibliotecarioControlador = fabrica.getIBibliotecarioControlador();
        this.lectorControlador = fabrica.getILectorControlador();
        this.libroControlador = fabrica.getILibroControlador();
        this.articuloEspecialControlador = fabrica.getIArticuloEspecialControlador();
        this.materialesConPrestamosPendientesControlador = fabrica.getIMaterialesConPrestamosPendientesControlador();

        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Biblioteca - Lectores.uy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Desktop pane para internal frames con fondo de la Biblioteca Nacional
        desktopPane = new FondoBibliotecaPanel();
        // Configurar para que la imagen se estire y cubra toda la ventana
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

        JMenuItem itemNuevoArticulo = new JMenuItem("Nuevo artículo especial");
        itemNuevoArticulo.addActionListener(e -> abrirNuevoArticulo());
        menuGestionMateriales.add(itemNuevoArticulo);

        JMenuItem itemConsultarDonaciones = new JMenuItem("Consultar Donaciones");
        itemConsultarDonaciones.addActionListener(e -> abrirConsultarDonaciones());
        menuGestionMateriales.add(itemConsultarDonaciones);

        // Menú Gestión de Préstamo
        JMenu menuGestionPrestamo = new JMenu("Gestión Prestamo");

        JMenuItem itemNuevoPrestamo = new JMenuItem("Nuevo prestamo");
        itemNuevoPrestamo.addActionListener(e -> abrirNuevoPrestamo());
        menuGestionPrestamo.add(itemNuevoPrestamo);

        JMenuItem itemModificarPrestamo = new JMenuItem("Modificar Prestamo");
        itemModificarPrestamo.addActionListener(e -> abrirModificarPrestamo());
        menuGestionPrestamo.add(itemModificarPrestamo);

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

        JMenuItem itemMaterialesConPrestamosPendientes = new JMenuItem("Materiales con préstamos pendientes");
        itemMaterialesConPrestamosPendientes.addActionListener(e -> abrirMaterialesConPrestamosPendientes());
        menuConsultas.add(itemMaterialesConPrestamosPendientes);

        // Añadir menús a la barra de menú
        menuBar.add(menuGestionUsuario);
        menuBar.add(menuGestionMateriales);
        menuBar.add(menuGestionPrestamo);
        menuBar.add(menuConsultas);

        setJMenuBar(menuBar);
    }
    
    private void abrirRegistrarBibliotecario() {
        RegistrarBibliotecario ventana = new RegistrarBibliotecario(bibliotecarioControlador);
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
        RegistrarLector ventana = new RegistrarLector(lectorControlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirEstadoLector() {
        CambiarEstadoLector ventana = new CambiarEstadoLector(lectorControlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirModificarZonaLector() {
        ModificarZonaLector ventana = new ModificarZonaLector(lectorControlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevoLibro() {
        NuevoLibro ventana = new NuevoLibro(libroControlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevoArticulo() {
        NuevoArticuloEspecial ventana = new NuevoArticuloEspecial(articuloEspecialControlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
            }
        }

    private void abrirConsultarDonaciones() {
        ConsultarDonaciones ventana = new ConsultarDonaciones(); // Se necesitará un controlador
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

    private void abrirModificarPrestamo() {
        ModificarPrestamo ventana = new ModificarPrestamo(controlador);
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirListarPrestamos() {
        ListarPrestamos ventana = new ListarPrestamos(controlador); // Ahora necesita controlador
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirHistorialPrestamos() {
        HistorialPrestamos ventana = new HistorialPrestamos(controlador); // Ahora necesita controlador        
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirReportePrestamosPorZona() {
        ReportePrestamosPorZona ventana = new ReportePrestamosPorZona(); // No necesita controlador
        desktopPane.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void abrirMaterialesConPrestamosPendientes() {
        MaterialesConPrestamosPendientes ventana = new MaterialesConPrestamosPendientes(materialesConPrestamosPendientesControlador);
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

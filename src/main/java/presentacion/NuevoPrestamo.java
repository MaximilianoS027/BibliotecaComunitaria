package presentacion;

import interfaces.IControlador;
import logica.EstadoPrestamo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NuevoPrestamo extends JInternalFrame {

    private IControlador controlador;
    private JComboBox<String> comboLectores;
    private JComboBox<String> comboBibliotecarios;
    private JComboBox<String> comboMateriales;
    private JComboBox<EstadoPrestamo> comboEstados;
    private JTextField txtFechaSolicitud;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JLabel lblResultado;

    public NuevoPrestamo(IControlador controlador) {
        super("Nuevo Préstamo", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Registrar Nuevo Préstamo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, gbc);

        // Seleccionar Lector
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblLector = new JLabel("Seleccionar Lector:");
        panelPrincipal.add(lblLector, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        comboLectores = new JComboBox<>();
        comboLectores.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboLectores, gbc);

        // Seleccionar Bibliotecario
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblBibliotecario = new JLabel("Seleccionar Bibliotecario:");
        panelPrincipal.add(lblBibliotecario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        comboBibliotecarios = new JComboBox<>();
        comboBibliotecarios.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboBibliotecarios, gbc);

        // Seleccionar Material
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblMaterial = new JLabel("Seleccionar Material:");
        panelPrincipal.add(lblMaterial, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        comboMateriales = new JComboBox<>();
        comboMateriales.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboMateriales, gbc);

        // Fecha de Solicitud
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblFecha = new JLabel("Fecha de Solicitud:");
        panelPrincipal.add(lblFecha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        txtFechaSolicitud = new JTextField();
        txtFechaSolicitud.setPreferredSize(new Dimension(300, 25));
        // Establecer fecha actual por defecto
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaSolicitud.setText(sdf.format(new Date()));
        panelPrincipal.add(txtFechaSolicitud, gbc);

        // Estado del Préstamo
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblEstado = new JLabel("Estado del Préstamo:");
        panelPrincipal.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        comboEstados = new JComboBox<>(EstadoPrestamo.values());
        comboEstados.setPreferredSize(new Dimension(300, 25));
        comboEstados.setSelectedItem(EstadoPrestamo.PENDIENTE); // Estado por defecto
        panelPrincipal.add(comboEstados, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.setPreferredSize(new Dimension(150, 35));
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPrestamo();
            }
        });
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 35));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelPrincipal.add(panelBotones, gbc);

        // Label para mostrar resultado
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        lblResultado = new JLabel("");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblResultado, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        try {
            // Cargar lectores
            String[] lectores = controlador.listarLectores();
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                // Formato: "ID - Nombre (Email) - Estado"
                String[] partes = lector.split(" - ");
                if (partes.length >= 2) {
                    String nombreEmail = partes[1]; // "Nombre (Email)"
                    comboLectores.addItem(nombreEmail);
                }
            }
            
            // Cargar bibliotecarios
            String[] bibliotecarios = controlador.listarBibliotecarios();
            comboBibliotecarios.removeAllItems();
            comboBibliotecarios.addItem("-- Seleccionar Bibliotecario --");
            
            for (String bibliotecario : bibliotecarios) {
                // Formato: "ID - Nombre (Email)"
                String[] partes = bibliotecario.split(" - ");
                if (partes.length >= 2) {
                    String nombreEmail = partes[1]; // "Nombre (Email)"
                    comboBibliotecarios.addItem(nombreEmail);
                }
            }
            
            // Cargar materiales (libros y artículos especiales)
            comboMateriales.removeAllItems();
            comboMateriales.addItem("-- Seleccionar Material --");
            
            // Cargar libros
            String[] libros = controlador.listarLibros();
            for (String libro : libros) {
                // Formato: "ID - Título"
                String[] partes = libro.split(" - ");
                if (partes.length >= 2) {
                    String titulo = partes[1];
                    comboMateriales.addItem("Libro: " + titulo);
                }
            }
            
            // Cargar artículos especiales
            String[] articulos = controlador.listarArticulosEspeciales();
            for (String articulo : articulos) {
                // Formato: "ID - Descripción"
                String[] partes = articulo.split(" - ");
                if (partes.length >= 2) {
                    String descripcion = partes[1];
                    comboMateriales.addItem("Artículo: " + descripcion);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPrestamo() {
        try {
            // Validar selecciones
            if (comboLectores.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un lector del combo desplegable");
                return;
            }
            
            if (comboBibliotecarios.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un bibliotecario del combo desplegable");
                return;
            }
            
            if (comboMateriales.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un material (libro o artículo) del combo desplegable");
                return;
            }
            
            // Obtener IDs de las selecciones
            String lectorId = obtenerIdDeSeleccion(comboLectores.getSelectedItem().toString(), controlador.listarLectores());
            String bibliotecarioId = obtenerIdDeSeleccion(comboBibliotecarios.getSelectedItem().toString(), controlador.listarBibliotecarios());
            String materialId = obtenerIdDeMaterial(comboMateriales.getSelectedItem().toString());
            
            // Validar fecha
            String fechaSolicitud = txtFechaSolicitud.getText().trim();
            if (fechaSolicitud.isEmpty()) {
                mostrarError("⚠️ La fecha de solicitud es obligatoria. Use el formato dd/MM/yyyy");
                return;
            }
            
            // Obtener estado
            EstadoPrestamo estado = (EstadoPrestamo) comboEstados.getSelectedItem();
            
            // Registrar préstamo
            controlador.registrarPrestamo(lectorId, bibliotecarioId, materialId, fechaSolicitud, estado.name());
            
            // Mostrar éxito
            mostrarExito("Préstamo agregado exitosamente");
            
            // Limpiar formulario después de un breve delay
            Timer timer = new Timer(3000, e -> limpiarFormulario());
            timer.setRepeats(false);
            timer.start();
            
        } catch (Exception e) {
            mostrarError("Error al registrar préstamo: " + e.getMessage());
        }
    }
    
    private String obtenerIdDeSeleccion(String seleccion, String[] lista) {
        for (String item : lista) {
            String[] partes = item.split(" - ");
            if (partes.length >= 2 && partes[1].equals(seleccion)) {
                return partes[0]; // Retornar el ID
            }
        }
        throw new IllegalArgumentException("No se encontró el ID para: " + seleccion);
    }
    
    private String obtenerIdDeMaterial(String seleccion) {
        // Formato: "Libro: Título (páginas)" o "Artículo: Descripción (peso, dimensiones)"
        String tipo = seleccion.startsWith("Libro:") ? "Libro" : "Artículo";
        String descripcion = seleccion.substring(seleccion.indexOf(":") + 1).trim();
        
        if (tipo.equals("Libro")) {
            String[] libros = controlador.listarLibros();
            for (String libro : libros) {
                // Formato: "ID - Título (páginas)"
                String[] partes = libro.split(" - ");
                if (partes.length >= 2) {
                    String tituloConPaginas = partes[1]; // "Título (páginas)"
                    if (tituloConPaginas.equals(descripcion)) {
                        return partes[0]; // Retornar el ID
                    }
                }
            }
        } else {
            String[] articulos = controlador.listarArticulosEspeciales();
            for (String articulo : articulos) {
                // Formato: "ID - Descripción (peso, dimensiones)"
                String[] partes = articulo.split(" - ");
                if (partes.length >= 2) {
                    String descripcionCompleta = partes[1]; // "Descripción (peso, dimensiones)"
                    if (descripcionCompleta.equals(descripcion)) {
                        return partes[0]; // Retornar el ID
                    }
                }
            }
        }
        
        throw new IllegalArgumentException("No se encontró el ID para el material: " + seleccion);
    }
    
    private void limpiarFormulario() {
        comboLectores.setSelectedIndex(0);
        comboBibliotecarios.setSelectedIndex(0);
        comboMateriales.setSelectedIndex(0);
        comboEstados.setSelectedItem(EstadoPrestamo.PENDIENTE);
        
        // Restablecer fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaSolicitud.setText(sdf.format(new Date()));
        
        lblResultado.setText("");
    }
    
    private void mostrarError(String mensaje) {
        lblResultado.setText("❌ " + mensaje);
        lblResultado.setForeground(Color.RED);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Mostrar también un popup de error
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error al Registrar Préstamo", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        lblResultado.setText("✅ " + mensaje);
        lblResultado.setForeground(new Color(0, 128, 0)); // Verde oscuro
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
    }
}

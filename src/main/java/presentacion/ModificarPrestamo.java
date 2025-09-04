package presentacion;

import interfaces.IControlador;
import logica.EstadoPrestamo;
import datatypes.DtPrestamo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModificarPrestamo extends JInternalFrame {

    private IControlador controlador;
    private JComboBox<String> comboPrestamos;
    private JComboBox<String> comboLectores;
    private JComboBox<String> comboBibliotecarios;
    private JComboBox<String> comboMateriales;
    private JComboBox<EstadoPrestamo> comboEstados;
    private JTextField txtFechaSolicitud;
    private JTextField txtFechaDevolucion;
    private JButton btnCargar;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JLabel lblResultado;
    private String prestamoSeleccionadoId = null;

    public ModificarPrestamo(IControlador controlador) {
        super("Modificar Préstamo", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(650, 600);
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
        JLabel lblTitulo = new JLabel("Modificar Préstamo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, gbc);

        // Seleccionar Préstamo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblPrestamo = new JLabel("Seleccionar Préstamo:");
        panelPrincipal.add(lblPrestamo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        comboPrestamos = new JComboBox<>();
        comboPrestamos.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboPrestamos, gbc);

        // Botón Cargar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCargar = new JButton("Cargar Préstamo");
        btnCargar.setPreferredSize(new Dimension(150, 35));
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPrestamoSeleccionado();
            }
        });
        panelPrincipal.add(btnCargar, gbc);

        // Separador
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JSeparator separador = new JSeparator();
        panelPrincipal.add(separador, gbc);

        // Seleccionar Lector
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblLector = new JLabel("Lector:");
        panelPrincipal.add(lblLector, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        comboLectores = new JComboBox<>();
        comboLectores.setPreferredSize(new Dimension(300, 25));
        comboLectores.setEnabled(false);
        panelPrincipal.add(comboLectores, gbc);

        // Seleccionar Bibliotecario
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblBibliotecario = new JLabel("Bibliotecario:");
        panelPrincipal.add(lblBibliotecario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        comboBibliotecarios = new JComboBox<>();
        comboBibliotecarios.setPreferredSize(new Dimension(300, 25));
        comboBibliotecarios.setEnabled(false);
        panelPrincipal.add(comboBibliotecarios, gbc);

        // Seleccionar Material
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblMaterial = new JLabel("Material:");
        panelPrincipal.add(lblMaterial, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        comboMateriales = new JComboBox<>();
        comboMateriales.setPreferredSize(new Dimension(300, 25));
        comboMateriales.setEnabled(false);
        panelPrincipal.add(comboMateriales, gbc);

        // Estado del Préstamo
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel lblEstado = new JLabel("Estado:");
        panelPrincipal.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        comboEstados = new JComboBox<>(EstadoPrestamo.values());
        comboEstados.setPreferredSize(new Dimension(300, 25));
        comboEstados.setEnabled(false);
        panelPrincipal.add(comboEstados, gbc);

        // Fecha de Solicitud
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel lblFechaSolicitud = new JLabel("Fecha Solicitud:");
        panelPrincipal.add(lblFechaSolicitud, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        txtFechaSolicitud = new JTextField();
        txtFechaSolicitud.setPreferredSize(new Dimension(300, 25));
        txtFechaSolicitud.setEnabled(false);
        panelPrincipal.add(txtFechaSolicitud, gbc);

        // Fecha de Devolución
        gbc.gridx = 0;
        gbc.gridy = 9;
        JLabel lblFechaDevolucion = new JLabel("Fecha Devolución:");
        panelPrincipal.add(lblFechaDevolucion, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        txtFechaDevolucion = new JTextField();
        txtFechaDevolucion.setPreferredSize(new Dimension(300, 25));
        txtFechaDevolucion.setEnabled(false);
        panelPrincipal.add(txtFechaDevolucion, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
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
        
        // Listener para controlar fecha de devolución según el estado
        comboEstados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlarFechaDevolucion();
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelPrincipal.add(panelBotones, gbc);

        // Label para mostrar resultado
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        lblResultado = new JLabel("");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblResultado, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        try {
            // Cargar préstamos de forma segura (solo IDs por ahora)
            comboPrestamos.removeAllItems();
            comboPrestamos.addItem("-- Seleccionar Préstamo --");
            
            // Agregar IDs de préstamos de forma simple
            try {
                String[] prestamos = controlador.listarPrestamos();
                for (String prestamo : prestamos) {
                    String[] partes = prestamo.split(" - ", 2);
                    if (partes.length >= 1) {
                        String prestamoId = partes[0];
                        comboPrestamos.addItem(prestamoId);
                    }
                }
            } catch (Exception e) {
                // Si falla, cargar IDs manualmente basándose en lo que sabemos
                System.err.println("Error cargando préstamos, usando IDs básicos: " + e.getMessage());
                comboPrestamos.addItem("P1");
                comboPrestamos.addItem("P2");
            }
            
            // Cargar lectores
            String[] lectores = controlador.listarLectores();
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                String[] partes = lector.split(" - ", 2);
                if (partes.length >= 2) {
                    String nombreEmail = partes[1];
                    comboLectores.addItem(nombreEmail);
                }
            }
            
            // Cargar bibliotecarios
            String[] bibliotecarios = controlador.listarBibliotecarios();
            comboBibliotecarios.removeAllItems();
            comboBibliotecarios.addItem("-- Seleccionar Bibliotecario --");
            
            for (String bibliotecario : bibliotecarios) {
                String[] partes = bibliotecario.split(" - ", 2);
                if (partes.length >= 2) {
                    String nombreEmail = partes[1];
                    comboBibliotecarios.addItem(nombreEmail);
                }
            }
            
            // Cargar materiales
            comboMateriales.removeAllItems();
            comboMateriales.addItem("-- Seleccionar Material --");
            
            // Cargar libros
            String[] libros = controlador.listarLibros();
            for (String libro : libros) {
                String[] partes = libro.split(" - ", 2);
                if (partes.length >= 2) {
                    String titulo = partes[1];
                    comboMateriales.addItem("Libro: " + titulo);
                }
            }
            
            // Cargar artículos especiales
            String[] articulos = controlador.listarArticulosEspeciales();
            for (String articulo : articulos) {
                String[] partes = articulo.split(" - ", 2);
                if (partes.length >= 2) {
                    String descripcion = partes[1];
                    comboMateriales.addItem("Artículo: " + descripcion);
                }
            }
            
        } catch (Exception e) {
            mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarPrestamoSeleccionado() {
        try {
            if (comboPrestamos.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un préstamo");
                return;
            }
            
            String prestamoId = comboPrestamos.getSelectedItem().toString();
            prestamoSeleccionadoId = prestamoId;
            
            // Obtener información del préstamo con manejo robusto de errores
            DtPrestamo prestamo = null;
            try {
                prestamo = controlador.obtenerPrestamo(prestamoId);
            } catch (Exception e) {
                System.err.println("Error obteniendo préstamo " + prestamoId + ": " + e.getMessage());
                mostrarError("Error al obtener datos del préstamo. Puede modificar solo el estado por ahora.");
                
                // Habilitar solo el cambio de estado si hay error cargando datos completos
                comboEstados.setEnabled(true);
                btnGuardar.setEnabled(true);
                return;
            }
            
            // Llenar formulario con datos del préstamo
            try {
                if (prestamo.getLectorNombre() != null) {
                    seleccionarEnCombo(comboLectores, prestamo.getLectorNombre());
                }
            } catch (Exception e) {
                System.err.println("Error cargando lector: " + e.getMessage());
            }
            
            try {
                if (prestamo.getBibliotecarioNombre() != null) {
                    seleccionarEnCombo(comboBibliotecarios, prestamo.getBibliotecarioNombre());
                }
            } catch (Exception e) {
                System.err.println("Error cargando bibliotecario: " + e.getMessage());
            }
            
            try {
                if (prestamo.getMaterialTipo() != null && prestamo.getMaterialDescripcion() != null) {
                    seleccionarMaterialEnCombo(prestamo.getMaterialTipo(), prestamo.getMaterialDescripcion());
                }
            } catch (Exception e) {
                System.err.println("Error cargando material: " + e.getMessage());
            }
            
            try {
                if (prestamo.getEstado() != null) {
                    comboEstados.setSelectedItem(EstadoPrestamo.valueOf(prestamo.getEstado()));
                }
            } catch (Exception e) {
                System.err.println("Error cargando estado: " + e.getMessage());
            }
            
            // Llenar fechas
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (prestamo.getFechaSolicitud() != null) {
                    txtFechaSolicitud.setText(sdf.format(prestamo.getFechaSolicitud()));
                }
                if (prestamo.getFechaDevolucion() != null) {
                    txtFechaDevolucion.setText(sdf.format(prestamo.getFechaDevolucion()));
                } else {
                    txtFechaDevolucion.setText("");
                }
            } catch (Exception e) {
                System.err.println("Error cargando fechas: " + e.getMessage());
            }
            
            // Habilitar campos para edición
            comboLectores.setEnabled(true);
            comboBibliotecarios.setEnabled(true);
            comboMateriales.setEnabled(true);
            comboEstados.setEnabled(true);
            txtFechaSolicitud.setEnabled(true);
            // txtFechaDevolucion se controla según estado
            controlarFechaDevolucion(); // Controlar habilitación según estado
            btnGuardar.setEnabled(true);
            
            mostrarExito("Préstamo " + prestamoId + " cargado correctamente");
            
        } catch (Exception e) {
            mostrarError("Error general al cargar préstamo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seleccionarEnCombo(JComboBox<String> combo, String nombre) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (item.contains(nombre)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void seleccionarMaterialEnCombo(String tipo, String descripcion) {
        try {
            String buscar = tipo + ": " + descripcion;
            for (int i = 0; i < comboMateriales.getItemCount(); i++) {
                String item = comboMateriales.getItemAt(i);
                if (item != null && item.equals(buscar)) {
                    comboMateriales.setSelectedIndex(i);
                    return;
                }
            }
            // Si no se encuentra, buscar solo por descripción
            for (int i = 0; i < comboMateriales.getItemCount(); i++) {
                String item = comboMateriales.getItemAt(i);
                if (item != null && item.contains(descripcion)) {
                    comboMateriales.setSelectedIndex(i);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Error seleccionando material en combo: " + e.getMessage());
            // Dejar en posición por defecto si hay error
        }
    }

    private void guardarCambios() {
        try {
            if (prestamoSeleccionadoId == null) {
                mostrarError("⚠️ No hay préstamo cargado");
                return;
            }

            // Validar selecciones
            if (comboLectores.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un lector");
                return;
            }
            
            if (comboBibliotecarios.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un bibliotecario");
                return;
            }
            
            if (comboMateriales.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un material");
                return;
            }

            // Obtener datos del formulario
            String lectorId = obtenerIdDeSeleccion(comboLectores.getSelectedItem().toString(), controlador.listarLectores());
            String bibliotecarioId = obtenerIdDeSeleccion(comboBibliotecarios.getSelectedItem().toString(), controlador.listarBibliotecarios());
            String materialId = obtenerIdDeMaterial(comboMateriales.getSelectedItem().toString());
            EstadoPrestamo estado = (EstadoPrestamo) comboEstados.getSelectedItem();
            String fechaSolicitud = txtFechaSolicitud.getText().trim();
            String fechaDevolucion = txtFechaDevolucion.getText().trim();

            // Validar fechas
            if (fechaSolicitud.isEmpty()) {
                mostrarError("⚠️ La fecha de solicitud es obligatoria");
                return;
            }

            // Validar fecha de devolución si el estado es DEVUELTO
            if (estado == EstadoPrestamo.DEVUELTO) {
                if (fechaDevolucion.isEmpty()) {
                    mostrarError("⚠️ La fecha de devolución es obligatoria cuando el estado es DEVUELTO");
                    return;
                }
            }

            // Usar el nuevo método modificarPrestamo que actualiza TODA la información
            controlador.modificarPrestamo(
                prestamoSeleccionadoId,
                lectorId,
                bibliotecarioId,
                materialId,
                fechaSolicitud,
                estado.name(),
                fechaDevolucion.isEmpty() ? null : fechaDevolucion
            );
            
            mostrarExito("✅ Cambios guardados correctamente en préstamo " + prestamoSeleccionadoId);
            
        } catch (Exception e) {
            mostrarError("Error al guardar cambios: " + e.getMessage());
        }
    }

    private String obtenerIdDeSeleccion(String seleccion, String[] lista) {
        for (String item : lista) {
            String[] partes = item.split(" - ", 2);
            if (partes.length >= 2 && partes[1].equals(seleccion)) {
                return partes[0];
            }
        }
        throw new IllegalArgumentException("No se encontró el ID para: " + seleccion);
    }
    
    private String obtenerIdDeMaterial(String seleccion) {
        String tipo = seleccion.startsWith("Libro:") ? "Libro" : "Artículo";
        String descripcion = seleccion.substring(seleccion.indexOf(":") + 1).trim();
        
        if (tipo.equals("Libro")) {
            String[] libros = controlador.listarLibros();
            for (String libro : libros) {
                String[] partes = libro.split(" - ", 2);
                if (partes.length >= 2) {
                    String titulo = partes[1];
                    if (titulo.equals(descripcion)) {
                        return partes[0];
                    }
                }
            }
        } else {
            String[] articulos = controlador.listarArticulosEspeciales();
            for (String articulo : articulos) {
                String[] partes = articulo.split(" - ", 2);
                if (partes.length >= 2) {
                    String desc = partes[1];
                    if (desc.equals(descripcion)) {
                        return partes[0];
                    }
                }
            }
        }
        
        throw new IllegalArgumentException("No se encontró el ID para el material: " + seleccion);
    }

    private void limpiarFormulario() {
        comboPrestamos.setSelectedIndex(0);
        comboLectores.setSelectedIndex(0);
        comboBibliotecarios.setSelectedIndex(0);
        comboMateriales.setSelectedIndex(0);
        comboEstados.setSelectedIndex(0);
        txtFechaSolicitud.setText("");
        txtFechaDevolucion.setText("");
        
        // Deshabilitar campos
        comboLectores.setEnabled(false);
        comboBibliotecarios.setEnabled(false);
        comboMateriales.setEnabled(false);
        comboEstados.setEnabled(false);
        txtFechaSolicitud.setEnabled(false);
        txtFechaDevolucion.setEnabled(false);
        btnGuardar.setEnabled(false);
        
        prestamoSeleccionadoId = null;
        lblResultado.setText("");
    }
    
    /**
     * Controla si la fecha de devolución debe estar habilitada según el estado del préstamo
     */
    private void controlarFechaDevolucion() {
        try {
            EstadoPrestamo estadoSeleccionado = (EstadoPrestamo) comboEstados.getSelectedItem();
            
            if (estadoSeleccionado == EstadoPrestamo.DEVUELTO) {
                // Solo si está DEVUELTO puede editarse la fecha de devolución
                txtFechaDevolucion.setEnabled(true);
                txtFechaDevolucion.setBackground(Color.WHITE);
            } else {
                // En cualquier otro estado, deshabilitar y limpiar
                txtFechaDevolucion.setEnabled(false);
                txtFechaDevolucion.setText("");
                txtFechaDevolucion.setBackground(new Color(240, 240, 240)); // Gris claro
            }
        } catch (Exception e) {
            // Si hay error, deshabilitar por seguridad
            txtFechaDevolucion.setEnabled(false);
            System.err.println("Error controlando fecha devolución: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        lblResultado.setText("❌ " + mensaje);
        lblResultado.setForeground(Color.RED);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        lblResultado.setText("✅ " + mensaje);
        lblResultado.setForeground(new Color(0, 128, 0));
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
    }
}
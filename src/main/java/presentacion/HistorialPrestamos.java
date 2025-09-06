package presentacion;

import interfaces.IControlador;
import datatypes.DtPrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Ventana para auditar la actividad de préstamos gestionados por bibliotecarios
 * Permite a los administradores revisar el historial de préstamos por bibliotecario
 */
public class HistorialPrestamos extends JInternalFrame {

    private IControlador controlador;
    
    // Componentes de la interfaz
    private JComboBox<String> comboBibliotecarios;
    private JComboBox<String> comboEstados;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnExportar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblResultado;
    private JLabel lblEstadisticas;
    private TableRowSorter<DefaultTableModel> sorter;

    public HistorialPrestamos(IControlador controlador) {
        super("Historial de Préstamos por Bibliotecario", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel superior - Controles de búsqueda
        JPanel panelSuperior = crearPanelFiltros();
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel central - Tabla de resultados
        JPanel panelCentral = crearPanelTabla();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior - Estadísticas y mensajes
        JPanel panelInferior = crearPanelInferior();
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));
        panel.setPreferredSize(new Dimension(0, 120));

        // Título
        JLabel lblTitulo = new JLabel("Auditoría de Préstamos por Bibliotecario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Seleccionar Bibliotecario
        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(new JLabel("Bibliotecario:"), gbc);
        gbc.gridx = 1;
        comboBibliotecarios = new JComboBox<>();
        comboBibliotecarios.setPreferredSize(new Dimension(300, 25));
        panelFiltros.add(comboBibliotecarios, gbc);

        // Estado del Préstamo
        gbc.gridx = 2; gbc.gridy = 0; // Cambiar a fila 0
        panelFiltros.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3;
        comboEstados = new JComboBox<>();
        comboEstados.addItem("Todos los estados");
        comboEstados.addItem("PENDIENTE");
        comboEstados.addItem("EN_CURSO");
        comboEstados.addItem("DEVUELTO");
        panelFiltros.add(comboEstados, gbc);

        // Panel para fechas con FlowLayout
        JPanel panelFechas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFechas.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Fecha desde
        panelFechas.add(new JLabel("Fecha desde:"));
        txtFechaDesde = new JTextField(10);
        txtFechaDesde.setColumns(10);
        txtFechaDesde.setToolTipText("Formato: dd/MM/yyyy");
        panelFechas.add(txtFechaDesde);

        // Fecha hasta
        panelFechas.add(new JLabel("Fecha hasta:"));
        txtFechaHasta = new JTextField(10);
        txtFechaHasta.setColumns(10);
        txtFechaHasta.setToolTipText("Formato: dd/MM/yyyy");
        panelFechas.add(txtFechaHasta);

        // Agregar el panel de fechas al GridBagLayout
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 4; // Ocupar 4 columnas (0, 1, 2, 3)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;
        panelFiltros.add(panelFechas, gbc);
        gbc.gridwidth = 1; // Resetear
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        // Botones
        gbc.gridx = 4; gbc.gridy = 1; // Fila 1, columna 4
        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(100, 30));
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPrestamos();
            }
        });
        panelFiltros.add(btnBuscar, gbc);

        gbc.gridx = 5; gbc.gridy = 1; // Fila 1, columna 5
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 30));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
            }
        });
        panelFiltros.add(btnLimpiar, gbc);

        gbc.gridx = 4; gbc.gridy = 1;
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 30));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
            }
        });
        panelFiltros.add(btnLimpiar, gbc);

        panel.add(panelFiltros, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Historial de Préstamos"));

        // Crear modelo de tabla
        String[] columnas = {"ID Préstamo", "Lector", "Material", "Tipo", 
                           "Fecha Solicitud", "Fecha Devolución", "Estado", "Días"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };

        // Crear tabla
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaResultados.setRowHeight(25);
        
        // Configurar ancho de columnas
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(200); // Lector
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(200); // Material
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(80);  // Tipo
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha Sol
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(100); // Fecha Dev
        tablaResultados.getColumnModel().getColumn(6).setPreferredWidth(100); // Estado
        tablaResultados.getColumnModel().getColumn(7).setPreferredWidth(60);  // Días

        // Agregar capacidad de ordenamiento
        sorter = new TableRowSorter<>(modeloTabla);
        tablaResultados.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 80));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnExportar = new JButton("Exportar Reporte");
        btnExportar.setPreferredSize(new Dimension(150, 35));
        btnExportar.setEnabled(false);
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarReporte();
            }
        });
        panelBotones.add(btnExportar);

        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        
        lblResultado = new JLabel("Seleccione un bibliotecario para ver su historial de préstamos");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        lblResultado.setForeground(new Color(0, 100, 200));
        
        lblEstadisticas = new JLabel("Total de préstamos: 0");
        lblEstadisticas.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 11));
        
        panelInfo.add(lblResultado);
        panelInfo.add(lblEstadisticas);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(panelInfo, BorderLayout.CENTER);

        return panel;
    }

    private void cargarDatos() {
        try {
            // Cargar bibliotecarios
            String[] bibliotecarios = controlador.listarBibliotecarios();
            comboBibliotecarios.removeAllItems();
            comboBibliotecarios.addItem("-- Seleccionar Bibliotecario --");
            
            for (String bibliotecario : bibliotecarios) {
                // Formato: "ID - Nombre (Email)"
                String[] partes = bibliotecario.split(" - ", 2);
                if (partes.length >= 2) {
                    String nombreEmail = partes[1]; // "Nombre (Email)"
                    comboBibliotecarios.addItem(nombreEmail);
                }
            }
            
        } catch (Exception e) {
            mostrarError("Error al cargar bibliotecarios: " + e.getMessage());
        }
    }

    private void buscarPrestamos() {
        try {
            if (comboBibliotecarios.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un bibliotecario");
                return;
            }

            String bibliotecarioSeleccionado = comboBibliotecarios.getSelectedItem().toString();
            
            // Extraer ID del bibliotecario
            String bibliotecarioId = obtenerIdDeSeleccion(bibliotecarioSeleccionado, controlador.listarBibliotecarios());
            
            // Obtener todos los préstamos
            String[] todosLosPrestamos = controlador.listarPrestamos();
            
            // Filtrar por bibliotecario y otros criterios
            java.util.List<Object[]> prestamosFiltrados = filtrarPrestamos(todosLosPrestamos, bibliotecarioId);
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            if (prestamosFiltrados.isEmpty()) {
                mostrarInfo("No se encontraron préstamos para el bibliotecario seleccionado");
                btnExportar.setEnabled(false);
                return;
            }
            
            // Agregar préstamos a la tabla
            for (Object[] prestamo : prestamosFiltrados) {
                modeloTabla.addRow(prestamo);
            }
            
            // Mostrar estadísticas
            mostrarEstadisticas(prestamosFiltrados, bibliotecarioSeleccionado);
            btnExportar.setEnabled(true);
            
        } catch (Exception e) {
            mostrarError("Error al buscar préstamos: " + e.getMessage());
        }
    }

    private java.util.List<Object[]> filtrarPrestamos(String[] todosLosPrestamos, String bibliotecarioId) {
        java.util.List<Object[]> resultado = new java.util.ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        // Obtener filtros
        String estadoFiltro = null;
        if (comboEstados.getSelectedIndex() > 0) {
            estadoFiltro = comboEstados.getSelectedItem().toString();
        }
        
        Date fechaDesde = null;
        Date fechaHasta = null;
        
        try {
            if (!txtFechaDesde.getText().trim().isEmpty()) {
                fechaDesde = sdf.parse(txtFechaDesde.getText().trim());
            }
            if (!txtFechaHasta.getText().trim().isEmpty()) {
                fechaHasta = sdf.parse(txtFechaHasta.getText().trim());
            }
        } catch (Exception e) {
            mostrarError("Formato de fecha inválido. Use dd/MM/yyyy");
            return resultado;
        }
        
        for (String prestamoStr : todosLosPrestamos) {
            try {
                // Extraer solo el ID del préstamo del string
                String prestamoId = prestamoStr.split(" - ")[0];
                
                // Obtener detalles completos del préstamo
                DtPrestamo dtPrestamo = controlador.obtenerPrestamo(prestamoId);
                
                // Verificar si el préstamo pertenece al bibliotecario seleccionado
                if (!dtPrestamo.getBibliotecarioId().equals(bibliotecarioId)) {
                    continue;
                }
                
                // Aplicar filtros usando los datos del DtPrestamo
                if (estadoFiltro != null && !dtPrestamo.getEstado().equals(estadoFiltro)) {
                    continue;
                }
                
                if (fechaDesde != null && dtPrestamo.getFechaSolicitud() != null) {
                    if (dtPrestamo.getFechaSolicitud().before(fechaDesde)) {
                        continue;
                    }
                }
                
                if (fechaHasta != null && dtPrestamo.getFechaSolicitud() != null) {
                    if (dtPrestamo.getFechaSolicitud().after(fechaHasta)) {
                        continue;
                    }
                }
                
                // Crear fila para la tabla
                Object[] fila = crearFilaPrestamo(dtPrestamo);
                resultado.add(fila);
                
            } catch (Exception e) {
                System.err.println("Error procesando préstamo: " + prestamoStr + " - " + e.getMessage());
            }
        }
        
        return resultado;
    }

    private Object[] crearFilaPrestamo(DtPrestamo dtPrestamo) {
        try {
            String tipoMaterial = "Material";
            String descripcionMaterial = dtPrestamo.getMaterialDescripcion();
            
            // Determinar tipo de material y mejorar la descripción
            if (dtPrestamo.getMaterialTipo().toLowerCase().contains("libro")) {
                tipoMaterial = "Libro";
                // Para libros, mostrar: "Título | ID"
                descripcionMaterial = dtPrestamo.getMaterialDescripcion() + " | " + dtPrestamo.getMaterialId();
            } else if (dtPrestamo.getMaterialTipo().toLowerCase().contains("artículo") || 
                    dtPrestamo.getMaterialTipo().toLowerCase().contains("articulo")) {
                tipoMaterial = "Artículo";
                // Para artículos, mostrar: "Descripción | ID"
                descripcionMaterial = dtPrestamo.getMaterialDescripcion() + " | " + dtPrestamo.getMaterialId();
            }
            
            String fechaSolicitudStr = "N/A";
            String diasTranscurridos = "N/A";

            // Obtener y formatear la fecha de solicitud
            if (dtPrestamo.getFechaSolicitud() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaSolicitudStr = sdf.format(dtPrestamo.getFechaSolicitud());

                // Calcular días transcurridos
                long diffInMillies = Math.abs(new Date().getTime() - dtPrestamo.getFechaSolicitud().getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                diasTranscurridos = String.valueOf(diff);
            }

            String fechaDevolucionStr = "";
            if (dtPrestamo.getFechaDevolucion() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaDevolucionStr = sdf.format(dtPrestamo.getFechaDevolucion());
            }

            return new Object[]{
                dtPrestamo.getId(),
                dtPrestamo.getLectorNombre(),
                descripcionMaterial,
                tipoMaterial,
                fechaSolicitudStr,
                fechaDevolucionStr,
                dtPrestamo.getEstado(),
                diasTranscurridos
            };
            
        } catch (Exception e) {
            System.err.println("Error creando fila de préstamo " + dtPrestamo.getId() + ": " + e.getMessage());
            return new Object[]{dtPrestamo.getId(), "Error", "Error", "Error", "N/A", "", "N/A", "N/A"};
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

    private void mostrarEstadisticas(java.util.List<Object[]> prestamos, String bibliotecario) {
        int totalPrestamos = prestamos.size();
        int prestamosActivos = 0;
        int prestamosDevueltos = 0;
        
        for (Object[] prestamo : prestamos) {
            String estado = (String) prestamo[6]; // Columna estado
            if (estado.equals("PENDIENTE") || estado.equals("EN_CURSO")) {
                prestamosActivos++;
            } else if (estado.equals("DEVUELTO")) {
                prestamosDevueltos++;
            }
        }
        
        lblEstadisticas.setText(String.format(
            "Total: %d | Activos: %d | Devueltos: %d", 
            totalPrestamos, prestamosActivos, prestamosDevueltos
        ));
        
        mostrarExito("Historial cargado para: " + bibliotecario + " (" + totalPrestamos + " préstamos)");
    }

    private void limpiarFiltros() {
        comboBibliotecarios.setSelectedIndex(0);
        comboEstados.setSelectedIndex(0);
        txtFechaDesde.setText("");
        txtFechaHasta.setText("");
        modeloTabla.setRowCount(0);
        lblResultado.setText("Seleccione un bibliotecario para ver su historial de préstamos");
        lblEstadisticas.setText("Total de préstamos: 0");
        btnExportar.setEnabled(false);
    }

    private void exportarReporte() {
        try {
            if (modeloTabla.getRowCount() == 0) {
                mostrarInfo("No hay datos para exportar");
                return;
            }
            
            // Crear contenido para exportar
            StringBuilder contenido = new StringBuilder();
            contenido.append("REPORTE DE AUDITORÍA DE PRÉSTAMOS POR BIBLIOTECARIO\n");
            contenido.append("================================================\n");
            contenido.append("Bibliotecario: ").append(comboBibliotecarios.getSelectedItem()).append("\n");
            contenido.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n");
            contenido.append("Filtros aplicados:\n");
            contenido.append("- Estado: ").append(comboEstados.getSelectedItem()).append("\n");
            contenido.append("- Fecha desde: ").append(txtFechaDesde.getText()).append("\n");
            contenido.append("- Fecha hasta: ").append(txtFechaHasta.getText()).append("\n\n");
            
            // Encabezados
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                contenido.append(modeloTabla.getColumnName(i)).append("\t");
            }
            contenido.append("\n");
            
            // Datos
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    contenido.append(modeloTabla.getValueAt(i, j)).append("\t");
                }
                contenido.append("\n");
            }
            
            // Mostrar en ventana
            JTextArea areaTexto = new JTextArea(contenido.toString());
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(800, 500));
            
            JOptionPane.showMessageDialog(this, scroll, "Reporte de Auditoría", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            mostrarError("Error al exportar: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        lblResultado.setText("❌ " + mensaje);
        lblResultado.setForeground(Color.RED);
        
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        lblResultado.setText("✅ " + mensaje);
        lblResultado.setForeground(new Color(0, 128, 0));
    }
    
    private void mostrarInfo(String mensaje) {
        lblResultado.setText("ℹ️ " + mensaje);
        lblResultado.setForeground(new Color(0, 100, 200));
    }
}

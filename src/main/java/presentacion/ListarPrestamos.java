package presentacion;

import interfaces.IControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import datatypes.DtPrestamo;

public class ListarPrestamos extends JInternalFrame {

    private IControlador controlador;
    private JComboBox<String> comboLectores;
    private JRadioButton rbSoloActivos;
    private JRadioButton rbHistorialCompleto;
    private ButtonGroup grupoTipoConsulta;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnExportar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblResultado;
    private JLabel lblLectorSeleccionado;

    public ListarPrestamos(IControlador controlador) {
        super("Listar Préstamos por Lector", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
        cargarLectores();
    }

    private void inicializarComponentes() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel superior - Controles de búsqueda
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel lblTitulo = new JLabel("Consultar Préstamos de Lector");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo, gbc);

        // Seleccionar Lector
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblLector = new JLabel("Seleccionar Lector:");
        panelSuperior.add(lblLector, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        comboLectores = new JComboBox<>();
        comboLectores.setPreferredSize(new Dimension(400, 25));
        panelSuperior.add(comboLectores, gbc);

        // Mostrar lector seleccionado
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        lblLectorSeleccionado = new JLabel(" ");
        lblLectorSeleccionado.setFont(new Font("Arial", Font.BOLD, 12));
        lblLectorSeleccionado.setForeground(new Color(0, 100, 0));
        panelSuperior.add(lblLectorSeleccionado, gbc);

        // Tipo de consulta
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel lblTipo = new JLabel("Tipo de consulta:");
        panelSuperior.add(lblTipo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbSoloActivos = new JRadioButton("Solo préstamos activos", true);
        rbHistorialCompleto = new JRadioButton("Historial completo");
        
        grupoTipoConsulta = new ButtonGroup();
        grupoTipoConsulta.add(rbSoloActivos);
        grupoTipoConsulta.add(rbHistorialCompleto);
        
        panelRadios.add(rbSoloActivos);
        panelRadios.add(rbHistorialCompleto);
        panelSuperior.add(panelRadios, gbc);

        // Botones de acción
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnBuscar = new JButton("Buscar Préstamos");
        btnBuscar.setPreferredSize(new Dimension(150, 35));
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPrestamos();
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

        btnExportar = new JButton("Exportar");
        btnExportar.setPreferredSize(new Dimension(100, 35));
        btnExportar.setEnabled(false);
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarResultados();
            }
        });
        
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnExportar);
        panelSuperior.add(panelBotones, gbc);

        // Panel central - Tabla de resultados
        String[] columnas = {"ID Préstamo", "Material", "Tipo", "Fecha Solicitud", "Fecha Devolución", "Estado", "Días"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla solo lectura
            }
        };
        
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.getTableHeader().setReorderingAllowed(false);
        
        // Configurar anchos de columnas
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(200); // Material
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(80);  // Tipo
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(100); // Fecha Solicitud
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha Devolución
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(100); // Estado
        tablaResultados.getColumnModel().getColumn(6).setPreferredWidth(60);  // Días
        
        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        // Panel inferior - Mensajes
        lblResultado = new JLabel(" ");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Ensamblar componentes
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(lblResultado, BorderLayout.SOUTH);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void cargarLectores() {
        try {
            // Reutilizar la lógica de ModificarPrestamo
            String[] lectores = controlador.listarLectores();
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                comboLectores.addItem(lector); // Formato: "ID - Nombre (email)"
            }
            
        } catch (Exception e) {
            mostrarError("Error al cargar lectores: " + e.getMessage());
        }
    }

    private void buscarPrestamos() {
        try {
            if (comboLectores.getSelectedIndex() == 0) {
                mostrarError("⚠️ Debe seleccionar un lector");
                return;
            }

            String lectorSeleccionado = comboLectores.getSelectedItem().toString();
            
            // Extraer ID del lector (reutilizar lógica de ModificarPrestamo)
            String lectorId = obtenerIdDeSeleccion(lectorSeleccionado, controlador.listarLectores());
            
            // Mostrar lector seleccionado
            lblLectorSeleccionado.setText("Consultando: " + lectorSeleccionado);
            
            // Obtener préstamos del lector
            String[] prestamos = controlador.listarPrestamosPorLector(lectorId);
            
            // Debug: mostrar qué préstamos encontramos
            System.out.println("DEBUG: Préstamos encontrados para lector " + lectorId + ":");
            for (String prestamo : prestamos) {
                System.out.println("  - " + prestamo);
            }
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            if (prestamos.length == 0) {
                mostrarInfo("No se encontraron préstamos para el lector seleccionado");
                btnExportar.setEnabled(false);
                return;
            }
            
            // Procesar y filtrar préstamos
            int prestamosAñadidos = 0;
            for (String prestamo : prestamos) {
                try {
                    // Formato esperado: "ID - Lector (email) - Material - Estado"
                    String[] partes = prestamo.split(" - ");
                    if (partes.length >= 4) {
                        String prestamoId = partes[0];
                        String estado = partes[3];
                        
                        // Obtener el DtPrestamo completo
                        DtPrestamo dtPrestamo = controlador.obtenerPrestamo(prestamoId);

                        // Filtrar según tipo de consulta
                        if (rbSoloActivos.isSelected()) {
                            // Los préstamos activos son: PENDIENTE y EN_CURSO (o "En Curso")
                            String estadoLimpio = estado.trim().toUpperCase().replace(" ", "_");
                            System.out.println("DEBUG: Evaluando préstamo " + prestamoId + " con estado: '" + estadoLimpio + "'");
                            
                            if (!estadoLimpio.equals("PENDIENTE") && !estadoLimpio.equals("EN_CURSO")) {
                                System.out.println("DEBUG: Préstamo " + prestamoId + " filtrado (no activo)");
                                continue; // Saltar préstamos no activos
                            }
                            System.out.println("DEBUG: Préstamo " + prestamoId + " incluido (activo)");
                        }
                        
                        // Obtener información detallada del préstamo
                        String[] datosPrestamo = obtenerDatosPrestamo(dtPrestamo);
                        modeloTabla.addRow(datosPrestamo);
                        prestamosAñadidos++;
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando préstamo: " + prestamo + " - " + e.getMessage());
                }
            }
            
            String tipoConsulta = rbSoloActivos.isSelected() ? "activos" : "total";
            mostrarExito("Se encontraron " + prestamosAñadidos + " préstamos " + tipoConsulta);
            btnExportar.setEnabled(prestamosAñadidos > 0);
            
        } catch (Exception e) {
            mostrarError("Error al buscar préstamos: " + e.getMessage());
        }
    }

    private String[] obtenerDatosPrestamo(DtPrestamo dtPrestamo) {
        try {
            // Intentar obtener información completa del préstamo
            // Por ahora usamos información básica, pero podríamos llamar a obtenerPrestamo() para más detalles
            
            String tipoMaterial = "Material";
            String descripcionMaterial = dtPrestamo.getMaterialDescripcion();
            
            // Determinar tipo de material
            if (dtPrestamo.getMaterialTipo().toLowerCase().contains("libro")) {
                tipoMaterial = "Libro";
            } else if (dtPrestamo.getMaterialTipo().toLowerCase().contains("artículo") || dtPrestamo.getMaterialTipo().toLowerCase().contains("articulo")) {
                tipoMaterial = "Artículo";
            }
            
            String fechaSolicitudStr = "N/A";
            String diasTranscurridos = "N/A";

            // Obtener y formatear la fecha real de solicitud
            if (dtPrestamo.getFechaSolicitud() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaSolicitudStr = sdf.format(dtPrestamo.getFechaSolicitud());

                // Calcular días transcurridos
                long diffInMillies = Math.abs(new Date().getTime() - dtPrestamo.getFechaSolicitud().getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                diasTranscurridos = String.valueOf(diff);
            }

            String fechaDevolucionStr = ""; // Valor por defecto vacío
            if (dtPrestamo.getFechaDevolucion() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaDevolucionStr = sdf.format(dtPrestamo.getFechaDevolucion());
            }

            return new String[]{
                dtPrestamo.getId(),
                descripcionMaterial,
                tipoMaterial,
                fechaSolicitudStr,
                fechaDevolucionStr, // Añadir fecha de devolución
                dtPrestamo.getEstado(),
                diasTranscurridos
            };
            
        } catch (Exception e) {
            System.err.println("Error obteniendo datos del préstamo " + dtPrestamo.getId() + ": " + e.getMessage());
            return new String[]{dtPrestamo.getId(), dtPrestamo.getMaterialDescripcion(), "N/A", "N/A", "", dtPrestamo.getEstado(), "N/A"};
        }
    }

    private String obtenerIdDeSeleccion(String seleccion, String[] lista) {
        // Reutilizar la lógica exacta de ModificarPrestamo
        for (String item : lista) {
            if (item.equals(seleccion)) {
                String[] partes = item.split(" - ");
                if (partes.length >= 1) {
                    return partes[0]; // Retornar el ID (primera parte)
                }
            }
        }
        throw new IllegalArgumentException("No se encontró el ID para: " + seleccion);
    }

    private void limpiarFormulario() {
        comboLectores.setSelectedIndex(0);
        rbSoloActivos.setSelected(true);
        modeloTabla.setRowCount(0);
        lblResultado.setText(" ");
        lblLectorSeleccionado.setText(" ");
        btnExportar.setEnabled(false);
    }

    private void exportarResultados() {
        try {
            if (modeloTabla.getRowCount() == 0) {
                mostrarInfo("No hay datos para exportar");
                return;
            }
            
            // Crear contenido para exportar (formato simple)
            StringBuilder contenido = new StringBuilder();
            contenido.append("REPORTE DE PRÉSTAMOS POR LECTOR\n");
            contenido.append("================================\n");
            contenido.append("Lector: ").append(lblLectorSeleccionado.getText()).append("\n");
            contenido.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n");
            contenido.append("Tipo: ").append(rbSoloActivos.isSelected() ? "Solo Activos" : "Historial Completo").append("\n\n");
            
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
            
            // Mostrar en ventana (en una versión completa se guardaría en archivo)
            JTextArea areaTexto = new JTextArea(contenido.toString());
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scroll, "Exportar Resultados", JOptionPane.INFORMATION_MESSAGE);
            
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

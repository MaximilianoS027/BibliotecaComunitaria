package presentacion;

import interfaces.IPrestamoReporte;
import logica.PrestamoReporte;
import datatypes.DtPrestamo;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ReportePrestamosPorZona extends JInternalFrame {

    private IPrestamoReporte prestamoReporte;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboZona;
    private JComboBox<String> comboEstado;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnFiltrar;
    private JButton btnLimpiar;
    private JButton btnEstadisticas;
    private JTextArea areaEstadisticas;
    private JComboBox<String> comboZonaEstadisticas;
    private SimpleDateFormat dateFormat;

    public ReportePrestamosPorZona() {
        super("Reporte de Préstamos por Zona", true, true, true, true);
        this.prestamoReporte = new PrestamoReporte();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(1200, 700);
        setLayout(new BorderLayout());

        // Panel superior con título y filtros
        JPanel panelSuperior = crearPanelFiltros();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con tabla
        JPanel panelCentral = crearPanelTabla();
        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con estadísticas
        JPanel panelInferior = crearPanelEstadisticas();
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));
        panel.setPreferredSize(new Dimension(0, 120));

        // Título
        JLabel lblTitulo = new JLabel("Reporte de Préstamos por Zona", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Zona
        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(new JLabel("Zona:"), gbc);
        gbc.gridx = 1;
        comboZona = new JComboBox<>();
        comboZona.addItem("Todas las zonas");
        String[] zonas = prestamoReporte.obtenerZonasDisponibles();
        for (String zona : zonas) {
            comboZona.addItem(zona);
        }
        panelFiltros.add(comboZona, gbc);

        // Estado
        gbc.gridx = 2; gbc.gridy = 0;
        panelFiltros.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3;
        comboEstado = new JComboBox<>();
        comboEstado.addItem("Todos los estados");
        comboEstado.addItem("PENDIENTE");
        comboEstado.addItem("EN_CURSO");
        comboEstado.addItem("DEVUELTO");
        panelFiltros.add(comboEstado, gbc);

        // Fecha desde
        gbc.gridx = 0; gbc.gridy = 1;
        panelFiltros.add(new JLabel("Fecha desde:"), gbc);
        gbc.gridx = 1;
        txtFechaDesde = new JTextField(10);
        txtFechaDesde.setToolTipText("Ingrese solo números (ddmmaaaa). Las barras se agregan automáticamente");
        configurarCampoFecha(txtFechaDesde);
        panelFiltros.add(txtFechaDesde, gbc);

        // Fecha hasta
        gbc.gridx = 2; gbc.gridy = 1;
        panelFiltros.add(new JLabel("Fecha hasta:"), gbc);
        gbc.gridx = 3;
        txtFechaHasta = new JTextField(10);
        txtFechaHasta.setToolTipText("Ingrese solo números (ddmmaaaa). Las barras se agregan automáticamente");
        configurarCampoFecha(txtFechaHasta);
        panelFiltros.add(txtFechaHasta, gbc);

        // Botones
        gbc.gridx = 4; gbc.gridy = 0;
        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });
        panelFiltros.add(btnFiltrar, gbc);

        gbc.gridx = 4; gbc.gridy = 1;
        btnLimpiar = new JButton("Limpiar");
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
        panel.setBorder(BorderFactory.createTitledBorder("Préstamos"));

        // Crear modelo de tabla
        String[] columnas = {"Lector", "Bibliotecario", "Material", 
                           "Fecha Solicitud", "Fecha Devolución", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };

        // Crear tabla
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        // Configurar ancho de columnas
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(200); // Lector
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(150); // Bibliotecario
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(200); // Material
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(100); // Fecha Sol
        tablaPrestamos.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha Dev
        tablaPrestamos.getColumnModel().getColumn(5).setPreferredWidth(80);  // Estado

        // Agregar capacidad de ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaPrestamos.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Estadísticas por Zona"));
        panel.setPreferredSize(new Dimension(0, 150));

        // Panel superior con filtro de zona y botón
        JPanel panelSuperiorEstadisticas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelSuperiorEstadisticas.add(new JLabel("Zona:"));
        comboZonaEstadisticas = new JComboBox<>();
        comboZonaEstadisticas.addItem("Todas las zonas");
        String[] zonas = prestamoReporte.obtenerZonasDisponibles();
        for (String zona : zonas) {
            comboZonaEstadisticas.addItem(zona);
        }
        panelSuperiorEstadisticas.add(comboZonaEstadisticas);
        
        btnEstadisticas = new JButton("Estadísticas");
        btnEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadisticasFiltradas();
            }
        });
        panelSuperiorEstadisticas.add(btnEstadisticas);
        
        panel.add(panelSuperiorEstadisticas, BorderLayout.NORTH);

        // Área de texto para estadísticas
        areaEstadisticas = new JTextArea(5, 0);
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollEstadisticas = new JScrollPane(areaEstadisticas);
        panel.add(scrollEstadisticas, BorderLayout.CENTER);

        return panel;
    }

    private void cargarDatos() {
        try {
            // Inicializar con pantalla limpia - no cargar datos automáticamente
            // Solo mostrar mensaje en estadísticas
            areaEstadisticas.setText("Seleccione una zona específica para ver las estadísticas.");
            System.out.println("DEBUG: Pantalla inicializada vacía - Use filtros para mostrar datos");
        } catch (Exception e) {
            mostrarError("Error al inicializar pantalla: " + e.getMessage());
        }
    }

    private void aplicarFiltros() {
        try {
            String zona = null;
            String estado = null;
            String fechaDesde = null;
            String fechaHasta = null;

            // Obtener valores de filtros
            if (comboZona.getSelectedIndex() > 0) {
                zona = (String) comboZona.getSelectedItem();
                // Convertir descripción a nombre de enum
                zona = zona.toUpperCase().replace(" ", "_");
            }

            if (comboEstado.getSelectedIndex() > 0) {
                estado = (String) comboEstado.getSelectedItem();
            }

            if (!txtFechaDesde.getText().trim().isEmpty()) {
                fechaDesde = convertirFechaAFormatoISO(txtFechaDesde.getText().trim());
            }

            if (!txtFechaHasta.getText().trim().isEmpty()) {
                fechaHasta = convertirFechaAFormatoISO(txtFechaHasta.getText().trim());
            }

            // Aplicar filtros
            DtPrestamo[] prestamos = prestamoReporte.obtenerPrestamosFiltrados(
                zona, estado, fechaDesde, fechaHasta);
            
            actualizarTabla(prestamos);

        } catch (DatosInvalidosException e) {
            mostrarError("Error en filtros: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al aplicar filtros: " + e.getMessage());
        }
    }

    private void limpiarFiltros() {
        try {
            System.out.println("DEBUG: Iniciando limpiarFiltros() - Limpiando inputs y outputs");
            
            // Limpiar filtros de entrada (inputs)
            comboZona.setSelectedIndex(0);
            comboEstado.setSelectedIndex(0);
            txtFechaDesde.setText("");
            txtFechaHasta.setText("");
            comboZonaEstadisticas.setSelectedIndex(0);
            
            // Limpiar salidas (outputs) - Dejar pantalla vacía
            System.out.println("DEBUG: Limpiando tabla y estadísticas...");
            
            // Limpiar tabla completamente
            modeloTabla.setRowCount(0);
            
            // Limpiar estadísticas
            areaEstadisticas.setText("Seleccione una zona específica para ver las estadísticas.");
            
            System.out.println("DEBUG: Pantalla limpiada exitosamente - Sin datos desplegados");
            
        } catch (Exception e) {
            System.err.println("ERROR en limpiarFiltros(): " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al limpiar filtros: " + e.getMessage());
        }
    }

    private void actualizarTabla(DtPrestamo[] prestamos) {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        // Agregar filas
        for (DtPrestamo prestamo : prestamos) {
            Object[] fila = new Object[6];
            
            // Extraer solo el nombre del lector (sin la zona)
            String nombreLector = prestamo.getLectorNombre();
            if (nombreLector != null && nombreLector.contains(" [")) {
                nombreLector = nombreLector.substring(0, nombreLector.indexOf(" ["));
            }
            
            fila[0] = nombreLector;
            fila[1] = prestamo.getBibliotecarioNombre();
            // Unificar Material y Tipo
            String materialInfo = prestamo.getMaterialDescripcion() + " (" + prestamo.getMaterialTipo() + ")";
            fila[2] = materialInfo;
            fila[3] = prestamo.getFechaSolicitud() != null ? 
                     dateFormat.format(prestamo.getFechaSolicitud()) : "";
            fila[4] = prestamo.getFechaDevolucion() != null ? 
                     dateFormat.format(prestamo.getFechaDevolucion()) : "";
            fila[5] = prestamo.getEstado();
            
            modeloTabla.addRow(fila);
        }
    }

    private void actualizarEstadisticas() {
        // Método original para compatibilidad - ahora llama al nuevo método
        actualizarEstadisticasFiltradas();
    }
    
    private void actualizarEstadisticasFiltradas() {
        try {
            String zonaSeleccionada = null;
            if (comboZonaEstadisticas.getSelectedIndex() > 0) {
                zonaSeleccionada = (String) comboZonaEstadisticas.getSelectedItem();
            }
            
            StringBuilder texto = new StringBuilder();
            
            if (zonaSeleccionada == null) {
                // No mostrar nada si no hay zona seleccionada
                texto.append("Seleccione una zona específica para ver las estadísticas.");
            } else {
                // Mostrar solo la zona seleccionada
                String[] todasEstadisticas = prestamoReporte.obtenerEstadisticasPorZona();
                texto.append("ESTADÍSTICAS DE: ").append(zonaSeleccionada.toUpperCase()).append("\n");
                texto.append("=" .repeat(50)).append("\n\n");
                
                boolean encontrada = false;
                for (String estadistica : todasEstadisticas) {
                    if (estadistica.startsWith(zonaSeleccionada + ":")) {
                        texto.append(estadistica).append("\n");
                        encontrada = true;
                        break;
                    }
                }
                
                if (!encontrada) {
                    texto.append("No se encontraron datos para la zona seleccionada.\n");
                }
            }
            
            areaEstadisticas.setText(texto.toString());
            
        } catch (Exception e) {
            areaEstadisticas.setText("Error al cargar estadísticas: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Configura un campo de texto para formato automático de fecha dd/MM/yyyy
     */
    private void configurarCampoFecha(JTextField campoFecha) {
        // Filtro de documento para controlar lo que se puede escribir
        ((AbstractDocument) campoFecha.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nuevoTexto = textoActual.substring(0, offset) + string + textoActual.substring(offset);
                
                if (esValidoParaFecha(nuevoTexto)) {
                    super.insertString(fb, offset, formatearFecha(string, offset, textoActual), attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nuevoTexto = textoActual.substring(0, offset) + text + textoActual.substring(offset + length);
                
                if (esValidoParaFecha(nuevoTexto)) {
                    super.replace(fb, offset, length, formatearFecha(text, offset, textoActual), attrs);
                }
            }
        });
    }
    
    /**
     * Valida si el texto es válido para un campo de fecha
     */
    private boolean esValidoParaFecha(String texto) {
        // Remover barras para contar solo dígitos
        String soloNumeros = texto.replace("/", "");
        
        // Solo permitir números y máximo 8 dígitos
        return soloNumeros.matches("\\d*") && soloNumeros.length() <= 8;
    }
    
    /**
     * Formatea automáticamente el texto agregando barras en las posiciones correctas
     */
    private String formatearFecha(String textoIngresado, int offset, String textoActual) {
        // Si se está insertando una barra, no hacer nada
        if (textoIngresado.equals("/")) {
            return "";
        }
        
        // Solo procesar si son números
        if (!textoIngresado.matches("\\d+")) {
            return "";
        }
        
        StringBuilder resultado = new StringBuilder();
        String textoCompleto = textoActual.substring(0, offset) + textoIngresado + textoActual.substring(offset);
        String soloNumeros = textoCompleto.replace("/", "");
        
        // Formatear según la cantidad de dígitos
        for (int i = 0; i < soloNumeros.length() && i < 8; i++) {
            if (i == 2 || i == 4) {
                resultado.append("/");
            }
            resultado.append(soloNumeros.charAt(i));
        }
        
        // Calcular qué parte es la nueva
        String textoFormateado = resultado.toString();
        String textoAnteriorFormateado = formatearTextoCompleto(textoActual);
        
        // Retornar solo la parte nueva
        if (textoFormateado.length() > textoAnteriorFormateado.length()) {
            return textoFormateado.substring(textoAnteriorFormateado.length());
        }
        
        return textoIngresado;
    }
    
    /**
     * Formatea un texto completo agregando barras
     */
    private String formatearTextoCompleto(String texto) {
        String soloNumeros = texto.replace("/", "");
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < soloNumeros.length() && i < 8; i++) {
            if (i == 2 || i == 4) {
                resultado.append("/");
            }
            resultado.append(soloNumeros.charAt(i));
        }
        
        return resultado.toString();
    }
    
    /**
     * Convierte fecha de formato dd/MM/yyyy al formato yyyy-MM-dd requerido por el backend
     */
    private String convertirFechaAFormatoISO(String fechaInput) throws DatosInvalidosException {
        if (fechaInput == null || fechaInput.trim().isEmpty()) {
            return null;
        }
        
        fechaInput = fechaInput.trim();
        
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            formatoEntrada.setLenient(false); // No permitir fechas inválidas como 32/01/2024
            
            // Si no tiene barras pero tiene 8 dígitos, formatear primero
            if (!fechaInput.contains("/") && fechaInput.length() == 8 && fechaInput.matches("\\d{8}")) {
                fechaInput = formatearTextoCompleto(fechaInput);
            }
            
            // Validar que tenga el formato correcto
            if (!fechaInput.matches("\\d{2}/\\d{2}/\\d{4}")) {
                throw new DatosInvalidosException("Formato de fecha incompleto. Ingrese dd/MM/yyyy");
            }
            
            java.util.Date fecha = formatoEntrada.parse(fechaInput);
            
            // Convertir al formato ISO requerido por el backend
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
            return formatoSalida.format(fecha);
            
        } catch (java.text.ParseException e) {
            throw new DatosInvalidosException("Fecha inválida: " + fechaInput + ". Verifique día, mes y año");
        }
    }
}

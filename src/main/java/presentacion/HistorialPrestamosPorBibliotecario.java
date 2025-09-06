package presentacion;

import interfaces.IPrestamoControlador;
import interfaces.IBibliotecarioControlador;
import datatypes.DtPrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

/**
 * Ventana para consultar el historial de préstamos por bibliotecario
 * Muestra una grilla con todos los préstamos realizados por un bibliotecario específico
 */
public class HistorialPrestamosPorBibliotecario extends JInternalFrame {
    
    private IPrestamoControlador prestamoControlador;
    private IBibliotecarioControlador bibliotecarioControlador;
    
    private JComboBox<String> comboBibliotecarios;
    private JButton btnConsultar;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JLabel lblResultado;
    
    public HistorialPrestamosPorBibliotecario(IPrestamoControlador prestamoControlador, 
                                            IBibliotecarioControlador bibliotecarioControlador) {
        super("Historial de Préstamos por Bibliotecario", true, true, true, true);
        this.prestamoControlador = prestamoControlador;
        this.bibliotecarioControlador = bibliotecarioControlador;
        
        inicializarComponentes();
        cargarBibliotecarios();
    }
    
    private void inicializarComponentes() {
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // Panel superior con controles
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con la tabla
        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con resultado
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Label para seleccionar bibliotecario
        JLabel lblSeleccionar = new JLabel("Seleccionar Bibliotecario:");
        panel.add(lblSeleccionar);
        
        // ComboBox para bibliotecarios
        comboBibliotecarios = new JComboBox<>();
        comboBibliotecarios.setPreferredSize(new Dimension(300, 25));
        panel.add(comboBibliotecarios);
        
        // Botón consultar
        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarPrestamos();
            }
        });
        panel.add(btnConsultar);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear tabla
        String[] columnas = {"Material", "Fecha de Solicitud", "Fecha de Devolución", "Lector Solic."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };
        
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar ancho de columnas
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(200); // Material
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha Solicitud
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(120); // Fecha Devolución
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(150); // Lector
        
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        lblResultado = new JLabel("Seleccione un bibliotecario para consultar sus préstamos");
        lblResultado.setForeground(new Color(0, 0, 139)); // Azul más oscuro para mejor legibilidad
        panel.add(lblResultado);
        
        return panel;
    }
    
    private void cargarBibliotecarios() {
        try {
            String[] bibliotecarios = bibliotecarioControlador.listarBibliotecarios();
            
            // Limpiar combo box
            comboBibliotecarios.removeAllItems();
            
            // Agregar opción por defecto
            comboBibliotecarios.addItem("Seleccione un bibliotecario...");
            
            // Agregar bibliotecarios
            for (String bibliotecario : bibliotecarios) {
                if (bibliotecario != null && !bibliotecario.trim().isEmpty()) {
                    comboBibliotecarios.addItem(bibliotecario);
                }
            }
            
            // Seleccionar la opción por defecto
            comboBibliotecarios.setSelectedIndex(0);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar bibliotecarios: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarPrestamos() {
        String bibliotecarioSeleccionado = (String) comboBibliotecarios.getSelectedItem();
        
        // Validar selección
        if (bibliotecarioSeleccionado == null || 
            bibliotecarioSeleccionado.equals("Seleccione un bibliotecario...")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un bibliotecario", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Extraer ID del bibliotecario del formato "ID - Nombre (email)"
            String bibliotecarioId = extraerIdBibliotecario(bibliotecarioSeleccionado);
            
            // Obtener préstamos del bibliotecario
            String[] prestamosArray = prestamoControlador.listarPrestamosPorBibliotecario(bibliotecarioId);
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            // Procesar préstamos y agregar a la tabla
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            int contador = 0;
            
            for (String prestamoStr : prestamosArray) {
                if (prestamoStr != null && !prestamoStr.trim().isEmpty()) {
                    // Parsear el string del préstamo
                    String[] partes = prestamoStr.split(" - ");
                    if (partes.length >= 4) {
                        String material = partes[2]; // Material
                        String lectorInfo = partes[1]; // Lector info
                        
                        // Para obtener fechas, necesitamos obtener el DTO completo
                        try {
                            DtPrestamo prestamoDto = prestamoControlador.obtenerPrestamo(partes[0]);
                            
                            String fechaSolicitud = prestamoDto.getFechaSolicitud() != null ? 
                                sdf.format(prestamoDto.getFechaSolicitud()) : "N/A";
                            
                            String fechaDevolucion = prestamoDto.getFechaDevolucion() != null ? 
                                sdf.format(prestamoDto.getFechaDevolucion()) : "N/A";
                            
                            String lectorNombre = prestamoDto.getLectorNombre() != null ? 
                                prestamoDto.getLectorNombre() : "N/A";
                            
                            // Agregar fila a la tabla
                            Object[] fila = {
                                material,
                                fechaSolicitud,
                                fechaDevolucion,
                                lectorNombre
                            };
                            
                            modeloTabla.addRow(fila);
                            contador++;
                            
                        } catch (Exception e) {
                            // Si no se puede obtener el DTO, usar información básica
                            Object[] fila = {
                                material,
                                "N/A",
                                "N/A",
                                lectorInfo
                            };
                            modeloTabla.addRow(fila);
                            contador++;
                        }
                    }
                }
            }
            
            // Actualizar mensaje de resultado
            lblResultado.setText("Se encontraron " + contador + " préstamos para este bibliotecario");
            lblResultado.setForeground(contador > 0 ? new Color(0, 100, 0) : Color.RED); // Verde más oscuro
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al consultar préstamos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            
            lblResultado.setText("Error al consultar préstamos");
            lblResultado.setForeground(Color.RED);
        }
    }
    
    private String extraerIdBibliotecario(String bibliotecarioStr) {
        // El formato es "ID - Nombre (email)"
        // Necesitamos extraer solo el ID
        if (bibliotecarioStr.contains(" - ")) {
            return bibliotecarioStr.split(" - ")[0].trim();
        }
        return bibliotecarioStr.trim();
    }
}

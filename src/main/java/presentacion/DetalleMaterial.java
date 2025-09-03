package presentacion;

import interfaces.IMaterialesConPrestamosPendientesControlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para mostrar los detalles de un material específico
 * y sus préstamos pendientes.
 */
public class DetalleMaterial extends JFrame {

    private IMaterialesConPrestamosPendientesControlador controlador;
    private String materialId;

    private JTextArea txtDetallesMaterial;
    private JTable tablaPrestamosPendientes;
    private DefaultTableModel modeloTablaPrestamos;

    public DetalleMaterial(IMaterialesConPrestamosPendientesControlador controlador, String materialId) {
        this.controlador = controlador;
        this.materialId = materialId;
        inicializarComponentes();
        cargarDetalles();
    }

    private void inicializarComponentes() {
        setTitle("Detalles del Material: " + materialId);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(700, 600)); // Tamaño preferido

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel para detalles del material
        JPanel panelDetallesMaterial = new JPanel(new BorderLayout());
        panelDetallesMaterial.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Información del Material",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.DARK_GRAY
        ));

        txtDetallesMaterial = new JTextArea();
        txtDetallesMaterial.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDetallesMaterial.setEditable(false);
        txtDetallesMaterial.setLineWrap(true);
        txtDetallesMaterial.setWrapStyleWord(true);
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesMaterial);
        scrollDetalles.setPreferredSize(new Dimension(600, 200)); // Altura fija para detalles
        panelDetallesMaterial.add(scrollDetalles, BorderLayout.CENTER);
        panelPrincipal.add(panelDetallesMaterial, BorderLayout.NORTH);

        // Panel para préstamos pendientes con tabla
        JPanel panelPrestamosPendientes = new JPanel(new BorderLayout());
        panelPrestamosPendientes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Préstamos Pendientes",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.DARK_GRAY
        ));

        // Configurar tabla de préstamos
        String[] columnasPrestamos = {"Lector", "Bibliotecario", "Fecha Solicitud"};
        modeloTablaPrestamos = new DefaultTableModel(columnasPrestamos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaPrestamosPendientes = new JTable(modeloTablaPrestamos);
        tablaPrestamosPendientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPrestamosPendientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamosPendientes.setRowHeight(25);
        tablaPrestamosPendientes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamosPendientes);
        panelPrestamosPendientes.add(scrollPrestamos, BorderLayout.CENTER);
        panelPrincipal.add(panelPrestamosPendientes, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void cargarDetalles() {
        // Cargar detalles del material
        String[] detalles = controlador.obtenerDetallesMaterial(materialId);
        if (detalles != null) {
            txtDetallesMaterial.setText(String.join("\n", detalles));
        } else {
            txtDetallesMaterial.setText("No se pudieron cargar los detalles del material.");
        }

        // Cargar préstamos pendientes en la tabla
        modeloTablaPrestamos.setRowCount(0); // Limpiar tabla
        List<Object[]> prestamos = controlador.obtenerPrestamosPendientesPorMaterial(materialId);
        if (prestamos != null && !prestamos.isEmpty()) {
            for (Object[] prestamo : prestamos) {
                modeloTablaPrestamos.addRow(prestamo);
            }
        } else {
            // Si no hay préstamos, mostrar mensaje en la tabla
            modeloTablaPrestamos.addRow(new Object[]{"No hay préstamos pendientes", "", ""});
        }
    }
}

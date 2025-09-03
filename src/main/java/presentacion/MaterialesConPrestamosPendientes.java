package presentacion;

import interfaces.IMaterialesConPrestamosPendientesControlador;
import logica.MaterialesConPrestamosPendientesControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Ventana para mostrar materiales con muchos préstamos pendientes
 * Permite al administrador identificar materiales que requieren atención prioritaria
 */
public class MaterialesConPrestamosPendientes extends JFrame {

    private IMaterialesConPrestamosPendientesControlador controlador;

    private JTable tablaMateriales;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnCerrar;
    private JLabel lblEstado;
    private JLabel lblTotalMateriales;
    private JScrollPane scrollPane;

    public MaterialesConPrestamosPendientes(IMaterialesConPrestamosPendientesControlador controlador) {
        this.controlador = controlador;
        inicializarComponentes();
        cargarMateriales(); // Cargar datos al iniciar
    }

    private void inicializarComponentes() {
        setTitle("Materiales con Préstamos Pendientes");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Más espaciado

        // Panel central con la tabla
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Configurar tabla
        String[] columnas = {"ID", "Nombre", "Tipo", "Préstamos Pendientes"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaMateriales = new JTable(modeloTabla);
        tablaMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaMateriales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMateriales.setRowHeight(25);
        tablaMateriales.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Ocultar la columna ID (primera columna)
        tablaMateriales.getColumnModel().getColumn(0).setMinWidth(0);
        tablaMateriales.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaMateriales.getColumnModel().getColumn(0).setWidth(0);
        tablaMateriales.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tablaMateriales.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        // Configurar el header de la tabla
        tablaMateriales.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Agregar listener para doble clic
        tablaMateriales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDetalleMaterial();
                }
            }
        });

        // Configurar tooltip para la tabla
        tablaMateriales.setToolTipText("Haz doble clic sobre un material para ver sus detalles");
        
        // Habilitar tooltips explícitamente
        ToolTipManager.sharedInstance().setEnabled(true);
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(3000);

        // Scroll pane para la tabla
        scrollPane = new JScrollPane(tablaMateriales);
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Materiales Identificados",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.DARK_GRAY
        ));
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior para botones y estado
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMateriales();
            }
        });
        panelInferior.add(btnActualizar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelInferior.add(btnCerrar);

        lblTotalMateriales = new JLabel("Total de materiales: 0");
        lblTotalMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelInferior.add(lblTotalMateriales);

        lblEstado = new JLabel("Listo");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        panelInferior.add(lblEstado);

        add(panelInferior, BorderLayout.SOUTH);

        pack(); // Ajustar tamaño de la ventana a los componentes
        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void cargarMateriales() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        lblEstado.setText("Cargando materiales...");
        btnActualizar.setEnabled(false);

        SwingWorker<List<Object[]>, Void> worker = new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return controlador.obtenerMaterialesConMuchosPrestamos();
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> materiales = get();
                    if (materiales != null && !materiales.isEmpty()) {
                        for (Object[] material : materiales) {
                            modeloTabla.addRow(material);
                        }
                        lblEstado.setText("Datos cargados correctamente - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                        lblTotalMateriales.setText("Total de materiales: " + materiales.size());
                    } else {
                        lblEstado.setText("No hay datos.");
                        lblTotalMateriales.setText("Total de materiales: 0");
                    }
                } catch (Exception e) {
                    lblEstado.setText("Error.");
                    lblTotalMateriales.setText("Total de materiales: 0");
                    JOptionPane.showMessageDialog(
                        MaterialesConPrestamosPendientes.this,
                        "Error al cargar materiales: " + e.getMessage(),
                        "Error de Carga",
                        JOptionPane.ERROR_MESSAGE
                    );
                    e.printStackTrace();
                } finally {
                    btnActualizar.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    /**
     * Abre la ventana de detalle para el material seleccionado
     */
    private void abrirDetalleMaterial() {
        int filaSeleccionada = tablaMateriales.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un material de la tabla",
                "Selección requerida",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        try {
            // Obtener el ID del material de la primera columna (oculta)
            String materialId = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

            if (materialId == null || materialId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo identificar el ID del material seleccionado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Crear y mostrar ventana de detalle
            DetalleMaterial ventanaDetalle = new DetalleMaterial(controlador, materialId);
            ventanaDetalle.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al abrir el detalle del material:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}

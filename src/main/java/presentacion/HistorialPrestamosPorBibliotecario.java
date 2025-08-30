package presentacion;

import interfaces.IBibliotecarioControlador;
import interfaces.IControlador;
import datatypes.DtBibliotecario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class HistorialPrestamosPorBibliotecario extends JInternalFrame {

    private IControlador controlador;
    private IBibliotecarioControlador bibliotecarioControlador;
    private JComboBox<String> comboBibliotecarios;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;
    private JLabel lblResultado;

    public HistorialPrestamosPorBibliotecario(IControlador controlador, IBibliotecarioControlador bibliotecarioControlador) {
        super("Historial de Préstamos por Bibliotecario", true, true, true, true);
        this.controlador = controlador;
        this.bibliotecarioControlador = bibliotecarioControlador;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel lblTitulo = new JLabel("Historial de Préstamos por Bibliotecario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, gbc);

        // Seleccionar Bibliotecario
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblBibliotecario = new JLabel("Seleccionar Bibliotecario:");
        panelPrincipal.add(lblBibliotecario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        comboBibliotecarios = new JComboBox<>();
        comboBibliotecarios.setPreferredSize(new Dimension(400, 25));
        panelPrincipal.add(comboBibliotecarios, gbc);

        // Botón Consultar
        gbc.gridx = 2;
        gbc.gridy = 1;
        btnConsultar = new JButton("Consultar");
        btnConsultar.setPreferredSize(new Dimension(100, 25));
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarPrestamos();
            }
        });
        panelPrincipal.add(btnConsultar, gbc);

        // Tabla de préstamos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Crear tabla
        String[] columnas = {"Material", "Fecha de Solicitud", "Fecha de Devolución", "Lector Solic."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar scroll pane
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        panelPrincipal.add(scrollPane, gbc);

        // Label para mostrar resultado
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        lblResultado = new JLabel("");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblResultado, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        try {
            String[] bibliotecarios = bibliotecarioControlador.listarBibliotecarios();
            comboBibliotecarios.removeAllItems();
            comboBibliotecarios.addItem("Seleccione un bibliotecario...");
            
            for (String bibliotecario : bibliotecarios) {
                comboBibliotecarios.addItem(bibliotecario);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar bibliotecarios: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarPrestamos() {
        String seleccion = (String) comboBibliotecarios.getSelectedItem();
        
        if (seleccion == null || seleccion.equals("Seleccione un bibliotecario...")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un bibliotecario", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Extraer el ID del bibliotecario del formato "ID - Nombre (email)"
            String bibliotecarioId = seleccion.split(" - ")[0];
            
            String[] prestamos = controlador.listarPrestamosPorBibliotecario(bibliotecarioId);
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            if (prestamos.length == 0) {
                lblResultado.setText("No se encontraron préstamos para este bibliotecario");
                lblResultado.setForeground(Color.BLUE);
            } else {
                // Procesar cada préstamo y agregarlo a la tabla
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                for (String prestamoStr : prestamos) {
                    // El nuevo formato es: "Material|FechaSolicitud|FechaDevolucion|Lector"
                    String[] partes = prestamoStr.split("\\|");
                    if (partes.length >= 4) {
                        String material = partes[0];
                        String fechaSolicitud = partes[1];
                        String fechaDevolucion = partes[2];
                        String lector = partes[3];
                        
                        // Agregar fila a la tabla
                        modeloTabla.addRow(new Object[]{
                            material,
                            fechaSolicitud,
                            fechaDevolucion,
                            lector
                        });
                    }
                }
                
                lblResultado.setText("Se encontraron " + prestamos.length + " préstamos para este bibliotecario");
                lblResultado.setForeground(new Color(0, 100, 0)); // Verde oscuro
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al consultar préstamos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            lblResultado.setText("Error al consultar préstamos");
            lblResultado.setForeground(Color.RED);
        }
    }
}

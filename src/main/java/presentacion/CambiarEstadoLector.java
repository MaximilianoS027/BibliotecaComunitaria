package presentacion;

// import interfaces.IControlador;
import interfaces.ILectorControlador;
import logica.EstadoLector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CambiarEstadoLector extends JInternalFrame {

    private ILectorControlador lectorControlador;
    private JComboBox<String> comboLectores;
    private JComboBox<EstadoLector> comboEstados;
    private JButton btnCambiarEstado;
    private JLabel lblResultado;

    public CambiarEstadoLector(ILectorControlador lectorControlador) {
        super("Cambiar Estado de Lector", true, true, true, true);
        this.lectorControlador = lectorControlador;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setSize(500, 400);
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
        JLabel lblTitulo = new JLabel("Cambiar Estado de Lector");
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
        comboLectores.setRenderer(new LectorListCellRenderer()); // Establecer el renderizador personalizado
        panelPrincipal.add(comboLectores, gbc);

        // Seleccionar Estado
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblEstado = new JLabel("Nuevo Estado:");
        panelPrincipal.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        comboEstados = new JComboBox<>(EstadoLector.values());
        comboEstados.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboEstados, gbc);

        // Botón Cambiar Estado
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.setPreferredSize(new Dimension(150, 35));
        btnCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstadoLector();
            }
        });
        panelPrincipal.add(btnCambiarEstado, gbc);

        // Label para mostrar resultado
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        lblResultado = new JLabel("");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblResultado, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        try {
            // Cargar lista de lectores solo con nombre y email (sin zona)
            String[] lectores = lectorControlador.listarLectores();
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                // Añadir la cadena completa al JComboBox
                comboLectores.addItem(lector);
            }
            
            // NO precargar estado - dejar que el usuario seleccione
            // comboEstados.setSelectedIndex(0);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar lectores: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstadoLector() {
        try {
            // Validar selección de lector
            if (comboLectores.getSelectedIndex() == 0) {
                lblResultado.setText("Debe seleccionar un lector");
                lblResultado.setForeground(Color.RED);
                return;
            }

            // Obtener lector seleccionado
            String lectorSeleccionado = (String) comboLectores.getSelectedItem();
            
            // El formato es "ID - Nombre (Email) - Estado"
            // Extraer solo el ID del lector
            String idLector = lectorSeleccionado.split(" - ", 2)[0];
            
            // Obtener estado seleccionado
            EstadoLector nuevoEstado = (EstadoLector) comboEstados.getSelectedItem();
            
            // Validar que se haya seleccionado un estado
            if (nuevoEstado == null) {
                lblResultado.setText("Debe seleccionar un estado");
                lblResultado.setForeground(Color.RED);
                return;
            }
            
            // Cambiar estado - usar el nombre del enum, no la descripción
            lectorControlador.cambiarEstadoLector(idLector, nuevoEstado.name());
            
            // Mostrar éxito
            lblResultado.setText("Estado cambiado exitosamente a: " + nuevoEstado.getDescripcion());
            lblResultado.setForeground(new Color(0, 128, 0)); // Verde
            
            // Recargar datos para mostrar el cambio
            cargarDatos();
            
        } catch (Exception e) {
            lblResultado.setText("Error: " + e.getMessage());
            lblResultado.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, 
                "Error al cambiar estado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clase interna para el renderizado personalizado del JComboBox
    class LectorListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof String) {
                String item = (String) value;
                if (item.equals("-- Seleccionar Lector --")) {
                    setText(item);
                } else {
                    // Formato esperado: "ID - Nombre (Email) - Estado - Zona"
                    String[] partes = item.split(" - ", 2);
                    if (partes.length >= 3) {
                        String nombreEmail = partes[1]; // "Nombre (Email)"
                        String estado = partes[2];      // "Estado"
                        setText(nombreEmail + " - " + estado);
                    } else {
                        setText(item); // En caso de que el formato no sea el esperado
                    }
                }
            }
            return this;
        }
    }
}

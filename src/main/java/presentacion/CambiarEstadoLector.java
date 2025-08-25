package presentacion;

import interfaces.IControlador;
import logica.EstadoLector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CambiarEstadoLector extends JInternalFrame {

    private IControlador controlador;
    private JComboBox<String> comboLectores;
    private JComboBox<EstadoLector> comboEstados;
    private JButton btnCambiarEstado;
    private JLabel lblResultado;

    public CambiarEstadoLector(IControlador controlador) {
        super("Cambiar Estado de Lector", true, true, true, true);
        this.controlador = controlador;
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
            // Cargar lista de lectores
            String[] lectores = controlador.listarLectores();
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                comboLectores.addItem(lector);
            }
            
            // Seleccionar primer estado por defecto
            comboEstados.setSelectedIndex(0);
            
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
            
            // Extraer nombre y email del formato "Nombre (Email) - Estado"
            String[] partes = lectorSeleccionado.split(" - ");
            if (partes.length < 2) {
                lblResultado.setText("Formato de lector inválido");
                lblResultado.setForeground(Color.RED);
                return;
            }
            
            String nombreEmail = partes[0]; // "Nombre (Email)"
            String nombre = nombreEmail.substring(0, nombreEmail.lastIndexOf("(")).trim();
            String email = nombreEmail.substring(nombreEmail.lastIndexOf("(") + 1, nombreEmail.lastIndexOf(")")).trim();
            
            // Obtener ID del lector usando nombre y email
            String idLector = controlador.obtenerIdLectorPorNombreEmail(nombre, email);
            
            // Obtener estado seleccionado
            EstadoLector nuevoEstado = (EstadoLector) comboEstados.getSelectedItem();
            
            // Cambiar estado
            controlador.cambiarEstadoLector(idLector, nuevoEstado.toString());
            
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
}

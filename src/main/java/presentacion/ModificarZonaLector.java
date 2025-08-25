package presentacion;

import interfaces.IControlador;
import logica.Zona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarZonaLector extends JInternalFrame {

    private IControlador controlador;
    private JComboBox<String> comboLectores;
    private JComboBox<Zona> comboZonas;
    private JButton btnCambiarZona;
    private JLabel lblResultado;

    public ModificarZonaLector(IControlador controlador) {
        super("Modificar Zona de Lector", true, true, true, true);
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
        JLabel lblTitulo = new JLabel("Modificar Zona de Lector");
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

        // Seleccionar Zona
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblZona = new JLabel("Nueva Zona:");
        panelPrincipal.add(lblZona, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        comboZonas = new JComboBox<>(Zona.values());
        comboZonas.setPreferredSize(new Dimension(300, 25));
        panelPrincipal.add(comboZonas, gbc);

        // Botón Cambiar Zona
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCambiarZona = new JButton("Cambiar Zona");
        btnCambiarZona.setPreferredSize(new Dimension(150, 35));
        btnCambiarZona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarZonaLector();
            }
        });
        panelPrincipal.add(btnCambiarZona, gbc);

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
            System.out.println("=== DEBUG: Cargando datos ===");
            
            // Cargar lista de lectores solo con nombre y email (sin zona)
            String[] lectores = controlador.listarLectores();
            System.out.println("DEBUG: Total de lectores obtenidos: " + lectores.length);
            
            comboLectores.removeAllItems();
            comboLectores.addItem("-- Seleccionar Lector --");
            
            for (String lector : lectores) {
                System.out.println("DEBUG: Lector original: '" + lector + "'");
                // Extraer solo "Nombre (Email)" sin la zona
                String[] partes = lector.split(" - ");
                System.out.println("DEBUG: Partes del lector: " + partes.length);
                if (partes.length >= 2) {
                    String nombreEmail = partes[0]; // "Nombre (Email)"
                    System.out.println("DEBUG: Agregando al combo: '" + nombreEmail + "'");
                    comboLectores.addItem(nombreEmail);
                }
            }
            
            System.out.println("DEBUG: Total de items en combo: " + comboLectores.getItemCount());
            
            // NO precargar zona - dejar que el usuario seleccione
            // comboZonas.setSelectedIndex(0);
            
        } catch (Exception e) {
            System.out.println("DEBUG: ERROR al cargar datos: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar lectores: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarZonaLector() {
        try {
            System.out.println("=== DEBUG: Iniciando cambio de zona ===");
            
            // Validar selección de lector
            if (comboLectores.getSelectedIndex() == 0) {
                lblResultado.setText("Debe seleccionar un lector");
                lblResultado.setForeground(Color.RED);
                System.out.println("DEBUG: No se seleccionó lector");
                return;
            }

            // Obtener lector seleccionado
            String lectorSeleccionado = (String) comboLectores.getSelectedItem();
            System.out.println("DEBUG: Lector seleccionado: '" + lectorSeleccionado + "'");
            
            // El formato ahora es solo "Nombre (Email)" sin zona
            String nombreEmail = lectorSeleccionado; // "Nombre (Email)"
            String nombre = nombreEmail.substring(0, nombreEmail.lastIndexOf("(")).trim();
            String email = nombreEmail.substring(nombreEmail.lastIndexOf("(") + 1, nombreEmail.lastIndexOf(")")).trim();
            
            System.out.println("DEBUG: Nombre extraído: '" + nombre + "'");
            System.out.println("DEBUG: Email extraído: '" + email + "'");
            
            // Obtener ID del lector usando nombre y email
            System.out.println("DEBUG: Llamando a obtenerIdLectorPorNombreEmail...");
            String idLector = controlador.obtenerIdLectorPorNombreEmail(nombre, email);
            System.out.println("DEBUG: ID del lector obtenido: '" + idLector + "'");
            
            // Obtener zona seleccionada
            Zona nuevaZona = (Zona) comboZonas.getSelectedItem();
            System.out.println("DEBUG: Zona seleccionada: " + nuevaZona);
            
            // Validar que se haya seleccionado una zona
            if (nuevaZona == null) {
                lblResultado.setText("Debe seleccionar una zona");
                lblResultado.setForeground(Color.RED);
                System.out.println("DEBUG: No se seleccionó zona");
                return;
            }
            
            // Cambiar zona - usar el nombre del enum, no la descripción
            System.out.println("DEBUG: Llamando a cambiarZonaLector con ID: '" + idLector + "' y zona: '" + nuevaZona.name() + "'");
            controlador.cambiarZonaLector(idLector, nuevaZona.name());
            
            // Mostrar éxito
            lblResultado.setText("Zona cambiada exitosamente a: " + nuevaZona.getDescripcion());
            lblResultado.setForeground(new Color(0, 128, 0)); // Verde
            System.out.println("DEBUG: Zona cambiada exitosamente");
            
            // Recargar datos para mostrar el cambio
            cargarDatos();
            
        } catch (Exception e) {
            System.out.println("DEBUG: ERROR CAPTURADO: " + e.getMessage());
            System.out.println("DEBUG: STACK TRACE:");
            e.printStackTrace();
            lblResultado.setText("Error: " + e.getMessage());
            lblResultado.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, 
                "Error al cambiar zona: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package presentacion;

import interfaces.ILectorControlador;
import logica.EstadoLector;
import logica.Zona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ventana para registrar nuevos lectores en el sistema
 * Actualizada para usar controlador específico
 */
public class RegistrarLector extends JInternalFrame {
    
    private ILectorControlador controlador;
    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JTextField txtDireccion;
    private JTextField txtFechaRegistro;
    private JComboBox<EstadoLector> cmbEstado;
    private JComboBox<Zona> cmbZona;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public RegistrarLector(ILectorControlador controlador) {
        super("Registrar Lector", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
    }
    
    // Constructor de compatibilidad para la transición
    /*
    public RegistrarLector() {
        this.controlador = new LectorControlador();
        inicializarComponentes();
    }
    */
    
    private void inicializarComponentes() {
        setSize(600, 600);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblTitulo = new JLabel("Registrar Lector");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, gbc);
        
        // Instrucciones
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblInstrucciones = new JLabel("Ingrese los siguientes datos:");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(lblInstrucciones, gbc);
        
        // Nombre
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblNombre, gbc);
        
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNombre.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtNombre, gbc);
        
        // Email
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblEmail = new JLabel("Correo electrónico:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        txtEmail.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtEmail, gbc);
        
        // Password
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPassword.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtPassword, gbc);
        
        // Confirmar Password
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblConfirmarPassword = new JLabel("Confirmar contraseña:");
        lblConfirmarPassword.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblConfirmarPassword, gbc);
        
        txtConfirmarPassword = new JPasswordField(20);
        txtConfirmarPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtConfirmarPassword.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtConfirmarPassword, gbc);
        
        // Dirección
        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblDireccion, gbc);
        
        txtDireccion = new JTextField(20);
        txtDireccion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDireccion.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtDireccion, gbc);
        
        // Fecha de Registro
        gbc.gridy = 7;
        gbc.gridx = 0;
        JLabel lblFecha = new JLabel("Fecha de registro:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        txtFechaRegistro = new JTextField();
        txtFechaRegistro.setPreferredSize(new Dimension(300, 25));
        txtFechaRegistro.setFont(new Font("Arial", Font.PLAIN, 12));
        // Establecer fecha actual por defecto
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaRegistro.setText(sdf.format(new Date()));
        panelPrincipal.add(txtFechaRegistro, gbc);
        
        // Estado
        gbc.gridy = 8;
        gbc.gridx = 0;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblEstado, gbc);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbEstado.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(cmbEstado, gbc);
        
        // Zona
        gbc.gridy = 9;
        gbc.gridx = 0;
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblZona, gbc);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbZona.setPreferredSize(new Dimension(300, 25));
        gbc.gridx = 1;
        panelPrincipal.add(cmbZona, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAceptar.setPreferredSize(new Dimension(150, 35));
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLector();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Establecer fecha actual por defecto
        establecerFechaActual();
    }
    
    private void establecerFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fechaActual);
        txtFechaRegistro.setText(fechaFormateada);
    }
    
    private void registrarLector() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());
            String confirmarPassword = new String(txtConfirmarPassword.getPassword());
            String direccion = txtDireccion.getText();
            String fechaStr = txtFechaRegistro.getText();
            String estado = cmbEstado.getSelectedItem().toString();
            String zona = cmbZona.getSelectedItem().toString();
            
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmarPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "Las contraseñas no coinciden", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que la contraseña no esté vacía
            if (password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La contraseña es obligatoria", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Llamar al controlador con password
            controlador.registrarLectorConPassword(nombre, email, password, direccion, fechaStr, estado, zona);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Lector registrado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar lector: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
        txtDireccion.setText("");
        establecerFechaActual();
        cmbEstado.setSelectedIndex(0);
        cmbZona.setSelectedIndex(0);
        txtNombre.requestFocus();
    }
}

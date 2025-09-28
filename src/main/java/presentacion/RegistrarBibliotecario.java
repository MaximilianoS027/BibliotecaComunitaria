package presentacion;

import interfaces.IBibliotecarioControlador;
import logica.BibliotecarioControlador;
import excepciones.BibliotecarioRepetidoException;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para registrar un nuevo bibliotecario
 * Actualizada para usar controlador específico
 */
public class RegistrarBibliotecario extends JInternalFrame {
    
    private IBibliotecarioControlador controlador;
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public RegistrarBibliotecario(IBibliotecarioControlador controlador) {
        super("Registrar Bibliotecario", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
    }
    
    // Constructor de compatibilidad para la transición
    public RegistrarBibliotecario() {
        super("Registrar Bibliotecario", true, true, true, true);
        this.controlador = new BibliotecarioControlador();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSize(450, 320);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel lblTitulo = new JLabel("Registrar Bibliotecario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Instrucciones
        JLabel lblInstrucciones = new JLabel("Ingrese los siguientes datos (número de empleado se genera automáticamente):");
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(lblInstrucciones, gbc);
        
        // Nombre
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Nombre:"), gbc);
        
        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtNombre, gbc);
        
        // Email
        gbc.gridy = 3;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Correo electrónico:"), gbc);
        
        txtEmail = new JTextField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtEmail, gbc);
        
        // Password
        gbc.gridy = 4;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtPassword, gbc);
        
        // Confirmar Password
        gbc.gridy = 5;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Confirmar contraseña:"), gbc);
        
        txtConfirmarPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtConfirmarPassword, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarBibliotecario();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Centrar ventana
        setLocation(50, 50);
    }
    
    private void registrarBibliotecario() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());
            String confirmarPassword = new String(txtConfirmarPassword.getPassword());
            
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
            
            // Llamar al controlador con password (numeroEmpleado se autogenera, por eso pasamos null)
            controlador.registrarBibliotecarioConPassword(null, nombre, email, password);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Bibliotecario registrado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
            
        } catch (BibliotecarioRepetidoException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Bibliotecario Repetido", 
                JOptionPane.ERROR_MESSAGE);
                
        } catch (DatosInvalidosException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Datos Inválidos", 
                JOptionPane.ERROR_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
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
        txtNombre.requestFocus();
    }
}

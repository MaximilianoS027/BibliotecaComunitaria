package presentacion;

import interfaces.IControlador;
import interfaces.Fabrica;
import excepciones.LectorNoExisteException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de login para autenticación de usuarios
 */
public class Login extends JInternalFrame {
    
    private IControlador controlador;
    
    // Componentes de la interfaz
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipoUsuario;
    private JButton btnLogin;
    private JButton btnCancelar;
    
    public Login() {
        super("Iniciar Sesión", true, true, true, true);
        
        // Obtener controlador de la Fábrica
        Fabrica fabrica = Fabrica.getInstancia();
        this.controlador = fabrica.getIControlador();
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSize(500, 350);
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
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, gbc);
        
        // Instrucciones
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblInstrucciones = new JLabel("Ingrese sus credenciales:");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(lblInstrucciones, gbc);
        
        // Tipo de usuario
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblTipoUsuario = new JLabel("Tipo de usuario:");
        lblTipoUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblTipoUsuario, gbc);
        
        cmbTipoUsuario = new JComboBox<>(new String[]{"Lector", "Bibliotecario"});
        cmbTipoUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbTipoUsuario.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 1;
        panelPrincipal.add(cmbTipoUsuario, gbc);
        
        // Nombre
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblUsuario = new JLabel("Nombre:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        txtUsuario.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtUsuario, gbc);
        
        // Password
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPassword.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 1;
        panelPrincipal.add(txtPassword, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
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
        
        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Centrar ventana
        setLocation(100, 100);
    }
    
    private void autenticar() {
        try {
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword());
            String tipoUsuario = (String) cmbTipoUsuario.getSelectedItem();
            
            // Validaciones básicas
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El nombre es obligatorio", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La contraseña es obligatoria", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String userId = null;
            
            // Autenticar según el tipo de usuario
            if ("Lector".equals(tipoUsuario)) {
                userId = controlador.autenticarLector(usuario, password);
                JOptionPane.showMessageDialog(this, 
                    "¡Bienvenido Lector!", 
                    "Login Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else if ("Bibliotecario".equals(tipoUsuario)) {
                userId = controlador.autenticarBibliotecario(usuario, password);
                JOptionPane.showMessageDialog(this, 
                    "¡Bienvenido Bibliotecario!", 
                    "Login Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Limpiar formulario después del login exitoso
            limpiarFormulario();
            
        } catch (LectorNoExisteException | BibliotecarioNoExisteException e) {
            JOptionPane.showMessageDialog(this, 
                "Credenciales incorrectas: " + e.getMessage(), 
                "Error de Autenticación", 
                JOptionPane.ERROR_MESSAGE);
        } catch (DatosInvalidosException e) {
            JOptionPane.showMessageDialog(this, 
                "Datos inválidos: " + e.getMessage(), 
                "Error", 
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
        txtUsuario.setText("");
        txtPassword.setText("");
        cmbTipoUsuario.setSelectedIndex(0);
        txtUsuario.requestFocus();
    }
}

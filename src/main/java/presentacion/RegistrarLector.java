package presentacion;

import interfaces.IControlador;
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
 */
public class RegistrarLector extends JFrame {
    
    private IControlador controlador;
    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JComboBox<EstadoLector> cmbEstado;
    private JComboBox<Zona> cmbZona;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public RegistrarLector(IControlador controlador) {
        this.controlador = controlador;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Registrar Lector");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // No establecer tamaño aquí - pack() lo calculará automáticamente
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Más padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Más espaciado entre elementos
        
        // Título
        JLabel lblTitulo = new JLabel("Registrar Lector");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20)); // Título más grande
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Instrucciones
        JLabel lblInstrucciones = new JLabel("Ingrese los siguientes datos:");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 14)); // Texto más grande
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(lblInstrucciones, gbc);
        
        // Nombre
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14)); // Labels más grandes
        panelPrincipal.add(lblNombre, gbc);
        
        txtNombre = new JTextField(30); // Campo más ancho
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14)); // Texto más grande
        txtNombre.setPreferredSize(new Dimension(300, 35)); // Campo más alto
        gbc.gridx = 1;
        panelPrincipal.add(txtNombre, gbc);
        
        // Email
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblEmail = new JLabel("Correo electrónico:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblEmail, gbc);
        
        txtEmail = new JTextField(30);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        panelPrincipal.add(txtEmail, gbc);
        
        // Dirección
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblDireccion, gbc);
        
        txtDireccion = new JTextField(30);
        txtDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDireccion.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        panelPrincipal.add(txtDireccion, gbc);
        
        // Fecha de Registro
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblFecha = new JLabel("Fecha de registro:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblFecha, gbc);
        
        // Panel para la fecha
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFecha.setBackground(panelPrincipal.getBackground());
        
        txtDia = new JTextField(3);
        txtDia.setPreferredSize(new Dimension(60, 35)); // Campos más altos
        txtDia.setHorizontalAlignment(JTextField.CENTER);
        txtDia.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtMes = new JTextField(3);
        txtMes.setPreferredSize(new Dimension(60, 35));
        txtMes.setHorizontalAlignment(JTextField.CENTER);
        txtMes.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtAnio = new JTextField(5);
        txtAnio.setPreferredSize(new Dimension(80, 35));
        txtAnio.setHorizontalAlignment(JTextField.CENTER);
        txtAnio.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Agregar bordes para hacer los campos más visibles
        txtDia.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        txtMes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        txtAnio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        // Labels de separación más grandes
        JLabel lblSlash1 = new JLabel("/");
        lblSlash1.setFont(new Font("Arial", Font.BOLD, 18));
        lblSlash1.setForeground(Color.GRAY);
        
        JLabel lblSlash2 = new JLabel("/");
        lblSlash2.setFont(new Font("Arial", Font.BOLD, 18));
        lblSlash2.setForeground(Color.GRAY);
        
        panelFecha.add(txtDia);
        panelFecha.add(lblSlash1);
        panelFecha.add(txtMes);
        panelFecha.add(lblSlash2);
        panelFecha.add(txtAnio);
        
        gbc.gridx = 1;
        panelPrincipal.add(panelFecha, gbc);
        
        // Estado
        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblEstado, gbc);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbEstado.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        panelPrincipal.add(cmbEstado, gbc);
        
        // Zona
        gbc.gridy = 7;
        gbc.gridx = 0;
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblZona, gbc);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbZona.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        panelPrincipal.add(cmbZona, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAceptar.setPreferredSize(new Dimension(120, 40));
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLector();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Establecer fecha actual por defecto
        establecerFechaActual();
        
        // Forzar el cálculo del layout ANTES de posicionar
        pack();
        
        // Centrar ventana en la pantalla DESPUÉS del pack
        setLocationRelativeTo(null);
        
        // pack() ya calculó el tamaño perfecto - no necesitamos setSize()
    }
    
    private void establecerFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaStr = sdf.format(fechaActual);
        String[] partes = fechaStr.split("/");
        
        System.out.println("Fecha actual: " + fechaStr);
        System.out.println("Partes: " + partes[0] + ", " + partes[1] + ", " + partes[2]);
        
        txtDia.setText(partes[0]);
        txtMes.setText(partes[1]);
        txtAnio.setText(partes[2]);
        
        // Verificar que los campos se hayan establecido correctamente
        System.out.println("Campo día: '" + txtDia.getText() + "'");
        System.out.println("Campo mes: '" + txtMes.getText() + "'");
        System.out.println("Campo año: '" + txtAnio.getText() + "'");
        
        // Forzar la actualización de la interfaz
        txtDia.revalidate();
        txtMes.revalidate();
        txtAnio.revalidate();
        txtDia.repaint();
        txtMes.repaint();
        txtAnio.repaint();
    }
    
    private void registrarLector() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            String fechaStr = txtDia.getText() + "/" + txtMes.getText() + "/" + txtAnio.getText();
            String estado = cmbEstado.getSelectedItem().toString();
            String zona = cmbZona.getSelectedItem().toString();
            
            // Llamar al controlador
            controlador.registrarLector(nombre, email, direccion, fechaStr, estado, zona);
            
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
        txtDireccion.setText("");
        establecerFechaActual();
        cmbEstado.setSelectedIndex(0);
        cmbZona.setSelectedIndex(0);
        txtNombre.requestFocus();
    }
}

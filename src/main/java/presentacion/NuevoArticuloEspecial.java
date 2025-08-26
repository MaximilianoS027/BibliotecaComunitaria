package presentacion;

// import interfaces.IControlador;
import interfaces.IArticuloEspecialControlador;
import excepciones.ArticuloEspecialRepetidoException;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para registrar un nuevo artículo especial
 * Sigue el formato estándar del proyecto
 */
public class NuevoArticuloEspecial extends JInternalFrame {
    
    private IArticuloEspecialControlador articuloEspecialControlador;
    private JTextField txtDescripcion;
    private JTextField txtPesoKg;
    private JTextField txtDimensiones;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public NuevoArticuloEspecial(IArticuloEspecialControlador articuloEspecialControlador) {
        super("Nuevo Artículo Especial", true, true, true, true);
        this.articuloEspecialControlador = articuloEspecialControlador;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSize(450, 350);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel lblTitulo = new JLabel("Registrar Nuevo Artículo Especial");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Instrucciones
        JLabel lblInstrucciones = new JLabel("Ingrese los siguientes datos:");
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(lblInstrucciones, gbc);
        
        // Descripción
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Descripción:"), gbc);
        
        txtDescripcion = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtDescripcion, gbc);
        
        // Peso en kg
        gbc.gridy = 3;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Peso (kg):"), gbc);
        
        txtPesoKg = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtPesoKg, gbc);
        
        // Dimensiones
        gbc.gridy = 4;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Dimensiones:"), gbc);
        
        txtDimensiones = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtDimensiones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarArticuloEspecial();
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
    
    private void registrarArticuloEspecial() {
        try {
            // Obtener datos del formulario
            String descripcion = txtDescripcion.getText();
            String pesoKgStr = txtPesoKg.getText();
            String dimensiones = txtDimensiones.getText();
            
            // Validar que el peso sea un número
            double pesoKg;
            try {
                pesoKg = Double.parseDouble(pesoKgStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "El peso debe ser un número válido", 
                    "Datos Inválidos", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Llamar al controlador
            articuloEspecialControlador.registrarArticuloEspecial(descripcion, (float) pesoKg, dimensiones);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Artículo especial ingresado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
            
        } catch (ArticuloEspecialRepetidoException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Artículo Especial Repetido", 
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
        txtDescripcion.setText("");
        txtPesoKg.setText("");
        txtDimensiones.setText("");
        txtDescripcion.requestFocus();
    }
}

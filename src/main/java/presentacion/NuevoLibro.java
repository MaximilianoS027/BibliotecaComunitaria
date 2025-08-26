package presentacion;

// import interfaces.IControlador;
import interfaces.ILibroControlador;
import excepciones.LibroRepetidoException;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para registrar una nueva donación de libros
 * Sigue el formato estándar del proyecto
 */
public class NuevoLibro extends JInternalFrame {
    
    private ILibroControlador libroControlador;
    private JTextField txtTitulo;
    private JTextField txtCantidadPaginas;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public NuevoLibro(ILibroControlador libroControlador) {
        super("Nuevo Libro", true, true, true, true);
        this.libroControlador = libroControlador;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSize(400, 300);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel lblTitulo = new JLabel("Registrar Nuevo Libro");
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
        
        // Título del libro
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Título del libro:"), gbc);
        
        txtTitulo = new JTextField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtTitulo, gbc);
        
        // Cantidad de páginas
        gbc.gridy = 3;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Cantidad de páginas:"), gbc);
        
        txtCantidadPaginas = new JTextField(15);
        gbc.gridx = 1;
        panelPrincipal.add(txtCantidadPaginas, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLibro();
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
    
    private void registrarLibro() {
        try {
            // Obtener datos del formulario
            String titulo = txtTitulo.getText();
            String cantidadPaginasStr = txtCantidadPaginas.getText();
            
            // Validar que la cantidad de páginas sea un número
            int cantidadPaginas;
            try {
                cantidadPaginas = Integer.parseInt(cantidadPaginasStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "La cantidad de páginas debe ser un número válido", 
                    "Datos Inválidos", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Llamar al controlador
            libroControlador.registrarLibro(titulo, cantidadPaginas);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Libro ingresado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
            
        } catch (LibroRepetidoException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Libro Repetido", 
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
        txtTitulo.setText("");
        txtCantidadPaginas.setText("");
        txtTitulo.requestFocus();
    }
}

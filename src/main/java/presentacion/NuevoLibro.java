package presentacion;

import interfaces.IControlador;
import excepciones.LibroRepetidoException;
import excepciones.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuevoLibro extends JInternalFrame {

    private IControlador controlador;
    private JTextField txtTitulo;
    private JSpinner spnCantidadPaginas;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnCancelar;
    private JTextArea txtResultado;

    public NuevoLibro(IControlador controlador) {
        super("Registro de Nueva Donación de Libros", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setSize(550, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Panel principal con formulario
        JPanel panelFormulario = crearPanelFormulario();
        
        // Panel de botones
        JPanel panelBotones = crearPanelBotones();
        
        // Panel de resultados
        JPanel panelResultados = crearPanelResultados();

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelResultados, BorderLayout.SOUTH);
        
        // Centrar el internal frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información del Libro"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título del Libro:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtTitulo = new JTextField(20);
        txtTitulo.setToolTipText("Ingrese el título del libro (mínimo 2 caracteres)");
        panel.add(txtTitulo, gbc);
        
        // Cantidad de páginas
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Cantidad de Páginas:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        spnCantidadPaginas = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        spnCantidadPaginas.setToolTipText("Ingrese la cantidad de páginas (1 a 10,000)");
        panel.add(spnCantidadPaginas, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnRegistrar = new JButton("Registrar Libro");
        btnRegistrar.setPreferredSize(new Dimension(150, 35));
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLibro();
            }
        });
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 35));
        btnLimpiar.setBackground(new Color(255, 165, 0));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        panel.add(btnRegistrar);
        panel.add(btnLimpiar);
        panel.add(btnCancelar);
        
        return panel;
    }
    
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resultado"));
        
        txtResultado = new JTextArea(4, 40);
        txtResultado.setEditable(false);
        txtResultado.setBackground(new Color(245, 245, 245));
        txtResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void registrarLibro() {
        try {
            // Obtener datos del formulario
            String titulo = txtTitulo.getText();
            int cantidadPaginas = (Integer) spnCantidadPaginas.getValue();
            
            // Validaciones básicas
            if (titulo == null || titulo.trim().isEmpty()) {
                mostrarError("El título es obligatorio.");
                txtTitulo.requestFocus();
                return;
            }
            
            if (cantidadPaginas <= 0) {
                mostrarError("La cantidad de páginas debe ser mayor a 0.");
                spnCantidadPaginas.requestFocus();
                return;
            }
            
            // Llamar al controlador
            controlador.registrarLibro(titulo, cantidadPaginas);
            
            // Mostrar éxito
            String categoria = "";
            if (cantidadPaginas <= 100) {
                categoria = " [Libro Corto]";
            } else if (cantidadPaginas <= 300) {
                categoria = " [Libro Medio]";
            } else {
                categoria = " [Libro Largo]";
            }
            
            String mensaje = "✓ LIBRO REGISTRADO EXITOSAMENTE\n" +
                           "Título: " + titulo + "\n" +
                           "Páginas: " + cantidadPaginas + categoria + "\n" +
                           "Fecha: " + new java.util.Date().toString();
            
            mostrarExito(mensaje);
            limpiarFormulario();
            
        } catch (LibroRepetidoException e) {
            mostrarError("Error: " + e.getMessage());
            txtTitulo.selectAll();
            txtTitulo.requestFocus();
        } catch (DatosInvalidosException e) {
            mostrarError("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        txtTitulo.setText("");
        spnCantidadPaginas.setValue(1);
        txtResultado.setText("");
        txtTitulo.requestFocus();
    }
    
    private void mostrarError(String mensaje) {
        txtResultado.setForeground(Color.RED);
        txtResultado.setText("✗ " + mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        txtResultado.setForeground(new Color(34, 139, 34));
        txtResultado.setText(mensaje);
        JOptionPane.showMessageDialog(this, "Libro registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}

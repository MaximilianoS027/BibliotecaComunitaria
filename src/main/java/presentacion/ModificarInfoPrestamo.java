package presentacion;

import interfaces.IControlador;

import javax.swing.*;
import java.awt.*;

public class ModificarInfoPrestamo extends JInternalFrame {

    private IControlador controlador;

    public ModificarInfoPrestamo(IControlador controlador) {
        super("Modificar Información de Préstamo", true, true, true, true);
        this.controlador = controlador;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("Modificar Información de Préstamo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }
}

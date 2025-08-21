package presentacion;

import interfaces.IControlador;

import javax.swing.*;
import java.awt.*;

public class ReportePrestamosPorZona extends JInternalFrame {

    private IControlador controlador;

    public ReportePrestamosPorZona(IControlador controlador) {
        super("Reporte de Préstamos por Zona", true, true, true, true);
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

        JLabel lblTitulo = new JLabel("Reporte de Préstamos por Zona");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }
}

package presentacion;

import interfaces.Fabrica;
import interfaces.IControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ConsultarDonaciones extends JInternalFrame {

    private IControlador controlador;

    private DefaultTableModel modeloTabla;
    private JTable tablaDonaciones;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnConsultar;
    private JButton btnLimpiar;

    public ConsultarDonaciones() {
        super("Consultar Donaciones", true, true, true, true);
        this.controlador = Fabrica.getInstancia().getIControlador();
        inicializarComponentes();
        cargarTodasLasDonaciones();
    }

    private void inicializarComponentes() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel superior para filtros de fecha
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar por Fecha de Donación"));

        panelFiltro.add(new JLabel("Desde (dd/MM/yyyy):"));
        txtFechaDesde = new JTextField(10);
        panelFiltro.add(txtFechaDesde);

        panelFiltro.add(new JLabel("Hasta (dd/MM/yyyy):"));
        txtFechaHasta = new JTextField(10);
        panelFiltro.add(txtFechaHasta);

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> filtrarDonaciones());
        panelFiltro.add(btnConsultar);

        btnLimpiar = new JButton("Limpiar Filtro");
        btnLimpiar.addActionListener(e -> limpiarFiltro());
        panelFiltro.add(btnLimpiar);

        add(panelFiltro, BorderLayout.NORTH);

        // Tabla para mostrar donaciones
        String[] columnas = {"ID Material", "Nombre Material", "Fecha Donación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };
        tablaDonaciones = new JTable(modeloTabla);
        tablaDonaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDonaciones.setRowHeight(25);
        tablaDonaciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaDonaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaDonaciones);
        add(scrollPane, BorderLayout.CENTER);

        // Centrar la ventana
//        setLocationRelativeTo(null); // Eliminado porque JInternalFrame no tiene este método
    }

    private void cargarTodasLasDonaciones() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Obtener libros
        String[] librosStr = controlador.listarLibros();
        System.out.println("Libros devueltos por listarLibros(): " + Arrays.toString(librosStr));
        if (librosStr != null) {
            for (String libroStr : librosStr) {
                // Asumo que el formato de libroStr es ID - Título - Páginas - FechaRegistro
                // Necesitamos parsear esto para obtener el ID, Título y FechaRegistro
                String[] partes = libroStr.split("[|]");
                System.out.println("Partes del libro: " + Arrays.toString(partes));
                if (partes.length >= 4) {
                    String id = partes[0];
                    String titulo = partes[1];
                    String fechaStr = partes[3];
                    try {
                        Date fechaDonacion = sdf.parse(fechaStr);
                        System.out.println("Fecha de donación parseada (libro): " + fechaDonacion);
                        modeloTabla.addRow(new Object[]{id, titulo, sdf.format(fechaDonacion)});
                    } catch (ParseException e) {
                        System.err.println("Error al parsear fecha de libro: " + fechaStr + " - " + e.getMessage());
                    }
                }
            }
        }

        // Obtener artículos especiales
        String[] articulosStr = controlador.listarArticulosEspeciales();
        System.out.println("Artículos devueltos por listarArticulosEspeciales(): " + Arrays.toString(articulosStr));
        if (articulosStr != null) {
            for (String articuloStr : articulosStr) {
                // Asumo que el formato de articuloStr es ID - Descripción - Peso - Dimensiones - FechaRegistro
                // Necesitamos parsear esto para obtener el ID, Descripción y FechaRegistro
                String[] partes = articuloStr.split("[|]");
                System.out.println("Partes del artículo: " + Arrays.toString(partes));
                if (partes.length >= 5) { // Suponiendo que FechaRegistro es el quinto elemento
                    String id = partes[0];
                    String descripcion = partes[1];
                    String fechaStr = partes[4]; // FechaRegistro es el quinto elemento
                    try {
                        Date fechaDonacion = sdf.parse(fechaStr);
                        System.out.println("Fecha de donación parseada (artículo): " + fechaDonacion);
                        modeloTabla.addRow(new Object[]{id, descripcion, sdf.format(fechaDonacion)});
                    } catch (ParseException e) {
                        System.err.println("Error al parsear fecha de artículo: " + fechaStr + " - " + e.getMessage());
                    }
                }
            }
        }
    }


    private void filtrarDonaciones() {
        modeloTabla.setRowCount(0); // Limpiar tabla para nuevos resultados
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDesde = null;
        Date fechaHasta = null;

        try {
            if (!txtFechaDesde.getText().trim().isEmpty()) {
                fechaDesde = sdf.parse(txtFechaDesde.getText().trim());
            }
            if (!txtFechaHasta.getText().trim().isEmpty()) {
                fechaHasta = sdf.parse(txtFechaHasta.getText().trim());
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Object[]> donacionesFiltradas = new ArrayList<>();

        // Obtener libros y filtrar
        String[] librosStr = controlador.listarLibros();
        System.out.println("Libros devueltos por listarLibros() para filtrar: " + Arrays.toString(librosStr));
        if (librosStr != null) {
            for (String libroStr : librosStr) {
                String[] partes = libroStr.split("[|]");
                System.out.println("Partes del libro (filtrar): " + Arrays.toString(partes));
                if (partes.length >= 4) {
                    String id = partes[0];
                    String titulo = partes[1];
                    String fechaStr = partes[3];
                    try {
                        Date fechaDonacion = sdf.parse(fechaStr);
                        System.out.println("Fecha de donación parseada (libro, filtrar): " + fechaDonacion);
                        if ( (fechaDesde == null || !fechaDonacion.before(fechaDesde)) &&
                             (fechaHasta == null || !fechaDonacion.after(fechaHasta)) ) {
                            donacionesFiltradas.add(new Object[]{id, titulo, sdf.format(fechaDonacion)});
                        }
                    } catch (ParseException e) {
                        System.err.println("Error al parsear fecha de libro: " + fechaStr + " - " + e.getMessage());
                    }
                }
            }
        }

        // Obtener artículos especiales y filtrar
        String[] articulosStr = controlador.listarArticulosEspeciales();
        System.out.println("Artículos devueltos por listarArticulosEspeciales() para filtrar: " + Arrays.toString(articulosStr));
        if (articulosStr != null) {
            for (String articuloStr : articulosStr) {
                String[] partes = articuloStr.split("[|]");
                System.out.println("Partes del artículo (filtrar): " + Arrays.toString(partes));
                if (partes.length >= 5) {
                    String id = partes[0];
                    String descripcion = partes[1];
                    String fechaStr = partes[4];
                    try {
                        Date fechaDonacion = sdf.parse(fechaStr);
                        System.out.println("Fecha de donación parseada (artículo, filtrar): " + fechaDonacion);
                        if ( (fechaDesde == null || !fechaDonacion.before(fechaDesde)) &&
                             (fechaHasta == null || !fechaDonacion.after(fechaHasta)) ) {
                            donacionesFiltradas.add(new Object[]{id, descripcion, sdf.format(fechaDonacion)});
                        }
                    } catch (ParseException e) {
                        System.err.println("Error al parsear fecha de artículo: " + fechaStr + " - " + e.getMessage());
                    }
                }
            }
        }

        // Añadir las donaciones filtradas a la tabla
        for (Object[] rowData : donacionesFiltradas) {
            modeloTabla.addRow(rowData);
        }
    }

    private void limpiarFiltro() {
        txtFechaDesde.setText("");
        txtFechaHasta.setText("");
        cargarTodasLasDonaciones(); // Recargar todas las donaciones sin filtro
    }
}

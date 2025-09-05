package logica;

import interfaces.IMaterialesConPrestamosPendientesControlador;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

import datatypes.DtArticuloEspecial;
import datatypes.DtLibro;
import excepciones.ArticuloEspecialNoExisteException;
import excepciones.LibroNoExisteException;

// Importar Hibernate para manejar sesiones correctamente
import org.hibernate.Session;
import org.hibernate.query.Query;
import persistencia.HibernateUtil;

public class MaterialesConPrestamosPendientesControlador implements IMaterialesConPrestamosPendientesControlador {

    private ManejadorPrestamo manejadorPrestamo;
    private ManejadorLibro manejadorLibro;
    private ManejadorArticuloEspecial manejadorArticuloEspecial;

    public MaterialesConPrestamosPendientesControlador() {
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
        this.manejadorLibro = ManejadorLibro.getInstancia();
        this.manejadorArticuloEspecial = ManejadorArticuloEspecial.getInstancia();
    }

    @Override
    public List<Object[]> obtenerMaterialesConMuchosPrestamos() {
        List<Object[]> resultado = new ArrayList<>();

        // Obtener solo los préstamos con estado PENDIENTE
        List<Prestamo> prestamosPendientes = manejadorPrestamo.listarPrestamosPorEstado(EstadoPrestamo.PENDIENTE);

        // Usar directamente la lista de préstamos pendientes
        List<Prestamo> todosLosPendientes = prestamosPendientes;

        // Contar préstamos por material
        Map<String, Integer> contadorPorMaterial = new HashMap<>();

        for (Prestamo prestamo : todosLosPendientes) {
            String materialId = prestamo.getMaterial().getId();
            contadorPorMaterial.put(materialId, contadorPorMaterial.getOrDefault(materialId, 0) + 1);
        }

        // Filtrar materiales con más de 1 préstamo pendiente y ordenar
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(contadorPorMaterial.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // Orden descendente

        for (Map.Entry<String, Integer> entry : listaOrdenada) {
            String materialId = entry.getKey();
            Integer cantidad = entry.getValue();

            if (cantidad > 1) { // "muchos" significa "mas de 1"
                Object[] datosMaterial = obtenerDatosMaterialParaTabla(materialId, cantidad);
                if (datosMaterial != null) {
                    resultado.add(datosMaterial);
                }
            }
        }

        return resultado;
    }

    @Override
    public int contarPrestamosPendientesPorMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return 0;
        }

        int contador = 0;
        String idBusqueda = materialId.trim();

        // Contar solo préstamos con estado PENDIENTE
        List<Prestamo> prestamosPendientes = manejadorPrestamo.listarPrestamosPorEstado(EstadoPrestamo.PENDIENTE);
        for (Prestamo prestamo : prestamosPendientes) {
            if (idBusqueda.equals(prestamo.getMaterial().getId())) {
                contador++;
            }
        }

        return contador;
    }

    @Override
    public boolean tieneMuchosPrestamos(String materialId) {
        return contarPrestamosPendientesPorMaterial(materialId) > 1;
    }

    @Override
    public String[] obtenerDetallesMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return null;
        }

        String idBusqueda = materialId.trim();

        try {
            // Intentar obtener como Libro
            Libro libro = manejadorLibro.obtenerLibro(idBusqueda);
            if (libro != null) {
                return new String[] {
                    "Tipo: Libro",
                    "Título: " + libro.getTitulo(),
                    "Cantidad de páginas: " + libro.getCantidadPaginas(),
                    "Préstamos pendientes: " + contarPrestamosPendientesPorMaterial(idBusqueda)
                };
            }
        } catch (Exception e) {
            // No es un libro, continuar
        }

        try {
            // Intentar obtener como Artículo Especial
            ArticuloEspecial articulo = manejadorArticuloEspecial.obtenerArticuloEspecial(idBusqueda);
            if (articulo != null) {
                return new String[] {
                    "Tipo: Artículo Especial",
                    "Descripción: " + articulo.getDescripcion(),
                    "Peso (kg): " + articulo.getPesoKg(),
                    "Dimensiones: " + articulo.getDimensiones(),
                    "Préstamos pendientes: " + contarPrestamosPendientesPorMaterial(idBusqueda)
                };
            }
        } catch (Exception e) {
            // No es un artículo especial
        }

        return new String[] {
            "Material no encontrado o tipo desconocido",
            "Préstamos pendientes: " + contarPrestamosPendientesPorMaterial(idBusqueda)
        };
    }

    @Override
    public List<Object[]> obtenerPrestamosPendientesPorMaterial(String materialId) {
        if (materialId == null || materialId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Object[]> resultado = new ArrayList<>();
        String idBusqueda = materialId.trim();
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Query HQL que carga eagerly todas las relaciones necesarias
            Query<Prestamo> query = session.createQuery(
                "SELECT p FROM Prestamo p " +
                "JOIN FETCH p.lector " +
                "JOIN FETCH p.bibliotecario " +
                "JOIN FETCH p.material " +
                "WHERE p.material.id = :materialId AND p.estado = :estado", 
                Prestamo.class);
            
            query.setParameter("materialId", idBusqueda);
            query.setParameter("estado", EstadoPrestamo.PENDIENTE);
            
            List<Prestamo> prestamos = query.list();
            
            for (Prestamo prestamo : prestamos) {
                // Formatear fecha como dd/mm/yyyy
                String fechaFormateada = formatearFecha(prestamo.getFechaSolicitud());
                
                Object[] datosPrestamo = {
                    prestamo.getLector().getNombre(),           // lector
                    prestamo.getBibliotecario().getNombre(),   // bibliotecario
                    fechaFormateada                            // fecha solicitud formateada
                };
                resultado.add(datosPrestamo);
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener préstamos pendientes: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return resultado;
    }

    /**
     * Formatea una fecha al formato dd/mm/yyyy
     */
    private String formatearFecha(java.util.Date fecha) {
        if (fecha == null) {
            return "No disponible";
        }
        
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } catch (Exception e) {
            return "Error en fecha";
        }
    }

    /**
     * Obtiene los datos del material en formato para la tabla
     * @param materialId ID del material
     * @param cantidadPrestamos Cantidad de préstamos pendientes
     * @return Object[] con [materialId, nombre, tipo, cantidadPrestamos] o null si no se encuentra
     */
    private Object[] obtenerDatosMaterialParaTabla(String materialId, Integer cantidadPrestamos) {
        try {
            // Intentar obtener como Libro
            Libro libro = manejadorLibro.obtenerLibro(materialId);
            if (libro != null) {
                return new Object[] {
                    materialId,                    // materialId (invisible en la tabla)
                    libro.getTitulo(),             // nombre
                    "Libro",                       // tipo
                    cantidadPrestamos              // cantidad de préstamos pendientes
                };
            }
        } catch (Exception e) {
            // No es un libro, continuar
        }

        try {
            // Intentar obtener como Artículo Especial
            ArticuloEspecial articulo = manejadorArticuloEspecial.obtenerArticuloEspecial(materialId);
            if (articulo != null) {
                return new Object[] {
                    materialId,                    // materialId (invisible en la tabla)
                    articulo.getDescripcion(),     // nombre
                    "Artículo Especial",           // tipo
                    cantidadPrestamos              // cantidad de préstamos pendientes
                };
            }
        } catch (Exception e) {
            // No es un artículo especial
        }

        return null;
    }
}

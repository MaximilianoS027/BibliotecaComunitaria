package logica;

import datatypes.DtPrestamo;
import excepciones.DatosInvalidosException;
import interfaces.IPrestamoReporte;
import persistencia.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controlador independiente para generar reportes de préstamos
 * Se enfoca específicamente en análisis y reportes por zona
 */
public class PrestamoReporte implements IPrestamoReporte {
    
    private SimpleDateFormat dateFormat;
    
    public PrestamoReporte() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Override
    public DtPrestamo[] obtenerReportePorZona() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Query que carga todas las relaciones y ordena por zona del lector
            Query<Prestamo> query = session.createQuery(
                "SELECT p FROM Prestamo p " +
                "JOIN FETCH p.lector l " +
                "JOIN FETCH p.bibliotecario " +
                "JOIN FETCH p.material " +
                "ORDER BY l.zona, p.fechaSolicitud DESC", Prestamo.class);
            
            List<Prestamo> prestamos = query.getResultList();
            
            // Convertir a DTOs dentro de la sesión activa
            List<DtPrestamo> dtos = new ArrayList<>();
            for (Prestamo prestamo : prestamos) {
                dtos.add(convertirADto(prestamo));
            }
            
            return dtos.toArray(new DtPrestamo[0]);
            
        } catch (Exception e) {
            System.err.println("Error al obtener reporte por zona: " + e.getMessage());
            e.printStackTrace();
            return new DtPrestamo[0];
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public DtPrestamo[] obtenerPrestamosFiltrados(String zona, String estado, 
                                                 String fechaDesde, String fechaHasta) 
            throws DatosInvalidosException {
        
        // Validar y parsear fechas si se proporcionan
        Date fechaDesdeDate = null;
        Date fechaHastaDate = null;
        
        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            try {
                fechaDesdeDate = dateFormat.parse(fechaDesde.trim());
            } catch (ParseException e) {
                throw new DatosInvalidosException("Formato de fecha desde inválido. Use yyyy-MM-dd");
            }
        }
        
        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            try {
                fechaHastaDate = dateFormat.parse(fechaHasta.trim());
            } catch (ParseException e) {
                throw new DatosInvalidosException("Formato de fecha hasta inválido. Use yyyy-MM-dd");
            }
        }
        
        // Validar rango de fechas
        if (fechaDesdeDate != null && fechaHastaDate != null && 
            fechaDesdeDate.after(fechaHastaDate)) {
            throw new DatosInvalidosException("La fecha desde no puede ser posterior a la fecha hasta");
        }
        
        // Validar zona si se proporciona
        Zona zonaEnum = null;
        if (zona != null && !zona.trim().isEmpty()) {
            try {
                zonaEnum = Zona.valueOf(zona.trim().toUpperCase().replace(" ", "_"));
            } catch (IllegalArgumentException e) {
                throw new DatosInvalidosException("Zona inválida: " + zona + 
                    ". Zonas válidas: " + Arrays.toString(Zona.values()));
            }
        }
        
        // Validar estado si se proporciona
        EstadoPrestamo estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            try {
                estadoEnum = EstadoPrestamo.valueOf(estado.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new DatosInvalidosException("Estado inválido: " + estado + 
                    ". Estados válidos: " + Arrays.toString(EstadoPrestamo.values()));
            }
        }
        
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Construir query dinámico basado en filtros
            StringBuilder hql = new StringBuilder(
                "SELECT p FROM Prestamo p " +
                "JOIN FETCH p.lector l " +
                "JOIN FETCH p.bibliotecario " +
                "JOIN FETCH p.material " +
                "WHERE 1=1 ");
            
            Map<String, Object> parametros = new HashMap<>();
            
            if (zonaEnum != null) {
                hql.append("AND l.zona = :zona ");
                parametros.put("zona", zonaEnum);
            }
            
            if (estadoEnum != null) {
                hql.append("AND p.estado = :estado ");
                parametros.put("estado", estadoEnum);
            }
            
            if (fechaDesdeDate != null) {
                hql.append("AND p.fechaSolicitud >= :fechaDesde ");
                parametros.put("fechaDesde", fechaDesdeDate);
            }
            
            if (fechaHastaDate != null) {
                hql.append("AND p.fechaSolicitud <= :fechaHasta ");
                parametros.put("fechaHasta", fechaHastaDate);
            }
            
            hql.append("ORDER BY l.zona, p.fechaSolicitud DESC");
            
            Query<Prestamo> query = session.createQuery(hql.toString(), Prestamo.class);
            
            // Establecer parámetros
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            
            List<Prestamo> prestamos = query.getResultList();
            
            // Convertir a DTOs dentro de la sesión activa
            List<DtPrestamo> dtos = new ArrayList<>();
            for (Prestamo prestamo : prestamos) {
                dtos.add(convertirADto(prestamo));
            }
            
            return dtos.toArray(new DtPrestamo[0]);
            
        } catch (Exception e) {
            System.err.println("Error al obtener préstamos filtrados: " + e.getMessage());
            e.printStackTrace();
            return new DtPrestamo[0];
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public String[] obtenerEstadisticasPorZona() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Query para obtener estadísticas agrupadas por zona
            Query<Object[]> query = session.createQuery(
                "SELECT l.zona, p.estado, COUNT(p) FROM Prestamo p " +
                "JOIN p.lector l " +
                "GROUP BY l.zona, p.estado " +
                "ORDER BY l.zona, p.estado", Object[].class);
            
            List<Object[]> resultados = query.getResultList();
            
            // Procesar resultados y agrupar por zona
            Map<Zona, Map<EstadoPrestamo, Long>> estadisticasPorZona = new HashMap<>();
            
            for (Object[] resultado : resultados) {
                Zona zona = (Zona) resultado[0];
                EstadoPrestamo estado = (EstadoPrestamo) resultado[1];
                Long cantidad = (Long) resultado[2];
                
                estadisticasPorZona.computeIfAbsent(zona, k -> new HashMap<>())
                                 .put(estado, cantidad);
            }
            
            // Generar strings de estadísticas
            List<String> estadisticas = new ArrayList<>();
            
            // Asegurar que todas las zonas aparezcan, incluso sin préstamos
            for (Zona zona : Zona.values()) {
                Map<EstadoPrestamo, Long> estadosPorZona = estadisticasPorZona.getOrDefault(zona, new HashMap<>());
                
                long pendientes = estadosPorZona.getOrDefault(EstadoPrestamo.PENDIENTE, 0L);
                long enCurso = estadosPorZona.getOrDefault(EstadoPrestamo.EN_CURSO, 0L);
                long devueltos = estadosPorZona.getOrDefault(EstadoPrestamo.DEVUELTO, 0L);
                long total = pendientes + enCurso + devueltos;
                
                String estadistica = String.format("%s: %d préstamos (%d pendientes, %d en curso, %d devueltos)",
                    zona.getDescripcion(), total, pendientes, enCurso, devueltos);
                
                estadisticas.add(estadistica);
            }
            
            return estadisticas.toArray(new String[0]);
            
        } catch (Exception e) {
            System.err.println("Error al obtener estadísticas por zona: " + e.getMessage());
            e.printStackTrace();
            return new String[]{"Error al cargar estadísticas"};
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public String[] obtenerZonasDisponibles() {
        List<String> zonas = new ArrayList<>();
        for (Zona zona : Zona.values()) {
            zonas.add(zona.getDescripcion());
        }
        return zonas.toArray(new String[0]);
    }
    
    /**
     * Método auxiliar para convertir Prestamo a DtPrestamo
     * Debe llamarse dentro de una sesión de Hibernate activa
     */
    private DtPrestamo convertirADto(Prestamo prestamo) {
        // Determinar tipo y descripción del material
        String materialTipo;
        String materialDescripcion;
        
        if (prestamo.getMaterial() instanceof Libro) {
            materialTipo = "Libro";
            materialDescripcion = ((Libro) prestamo.getMaterial()).getTitulo();
        } else if (prestamo.getMaterial() instanceof ArticuloEspecial) {
            materialTipo = "Artículo Especial";
            materialDescripcion = ((ArticuloEspecial) prestamo.getMaterial()).getDescripcion();
        } else {
            materialTipo = "Material";
            materialDescripcion = "Descripción no disponible";
        }
        
        // Crear DTO con información adicional de la zona
        DtPrestamo dto = new DtPrestamo(
            prestamo.getId(),
            prestamo.getFechaSolicitud(),
            prestamo.getFechaDevolucion(),
            prestamo.getEstado().name(),
            prestamo.getLector().getId(),
            prestamo.getLector().getNombre() + " [" + prestamo.getLector().getZona().getDescripcion() + "]",
            prestamo.getBibliotecario().getId(),
            prestamo.getBibliotecario().getNombre(),
            prestamo.getMaterial().getId(),
            materialTipo,
            materialDescripcion
        );
        
        return dto;
    }
    
    /**
     * Método auxiliar para obtener zona del lector de un préstamo
     * Útil para agrupaciones
     */
    public String obtenerZonaDelPrestamo(DtPrestamo prestamo) {
        String lectorInfo = prestamo.getLectorNombre();
        if (lectorInfo != null && lectorInfo.contains("[") && lectorInfo.contains("]")) {
            int inicio = lectorInfo.lastIndexOf("[");
            int fin = lectorInfo.lastIndexOf("]");
            if (inicio != -1 && fin != -1 && fin > inicio) {
                return lectorInfo.substring(inicio + 1, fin);
            }
        }
        return "Zona no disponible";
    }
}

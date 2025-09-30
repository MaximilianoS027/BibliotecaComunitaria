package logica;

import interfaces.ILectorControlador;
import datatypes.DtLector;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.DatosInvalidosException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * Controlador específico para operaciones con Lectores
 * Implementa el principio de Single Responsibility
 */
public class LectorControlador implements ILectorControlador {
    
    private ManejadorLector manejadorLector;
    
    public LectorControlador() {
        this.manejadorLector = ManejadorLector.getInstancia();
    }
    
    @Override
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre es obligatorio");
        }
        
        if (email == null || !email.contains("@")) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new DatosInvalidosException("La dirección es obligatoria");
        }
        
        if (fechaRegistro == null || fechaRegistro.trim().isEmpty()) {
            throw new DatosInvalidosException("La fecha de registro es obligatoria");
        }
        
        if (estado == null || estado.trim().isEmpty()) {
            throw new DatosInvalidosException("El estado es obligatorio");
        }
        
        if (zona == null || zona.trim().isEmpty()) {
            throw new DatosInvalidosException("La zona es obligatoria");
        }
        
        // Verificar si ya existe un lector con ese email
        if (manejadorLector.existeLectorConEmail(email.trim())) {
            throw new LectorRepetidoException("Ya existe un lector con el email: " + email);
        }
        
        // Parsear fecha
        Date fecha;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.parse(fechaRegistro.trim());
        } catch (ParseException e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Use dd/MM/yyyy");
        }
        
        // Parsear estado
        EstadoLector estadoLector = parseEstado(estado);
        
        // Parsear zona
        Zona zonaLector = parseZona(zona);
        
        // Crear entidad
        Lector lector = new Lector(
            nombre.trim(), 
            email.trim(), 
            direccion.trim(),
            fecha,
            estadoLector,
            zonaLector
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!lector.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!lector.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        if (!lector.tieneDireccionValida()) {
            throw new DatosInvalidosException("Dirección inválida");
        }
        
        if (!lector.tieneFechaRegistroValida()) {
            throw new DatosInvalidosException("La fecha de registro debe ser anterior a la fecha actual");
        }
        
        // Delegar al manejador
        manejadorLector.agregarLector(lector);
    }
    
    @Override
    public void registrarLectorConPassword(String nombre, String email, String password, String direccion, 
                                          String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre es obligatorio");
        }
        
        if (email == null || !email.contains("@")) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DatosInvalidosException("El password es obligatorio");
        }

        // No se necesita PasswordUtil.isValidPassword(password) con el nuevo enfoque

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new DatosInvalidosException("La dirección es obligatoria");
        }

        if (fechaRegistro == null || fechaRegistro.trim().isEmpty()) {
            throw new DatosInvalidosException("La fecha de registro es obligatoria");
        }

        if (estado == null || estado.trim().isEmpty()) {
            throw new DatosInvalidosException("El estado es obligatorio");
        }

        if (zona == null || zona.trim().isEmpty()) {
            throw new DatosInvalidosException("La zona es obligatoria");
        }

        // Verificar si ya existe un lector con ese email
        if (manejadorLector.existeLectorConEmail(email.trim())) {
            throw new LectorRepetidoException("Ya existe un lector con el email: " + email);
        }

        // Parsear fecha
        Date fecha;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.parse(fechaRegistro.trim());
        } catch (ParseException e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Use dd/MM/yyyy");
        }

        // Parsear estado
        EstadoLector estadoLector = parseEstado(estado);

        // Parsear zona
        Zona zonaLector = parseZona(zona);

        // Crear entidad con password
        Lector lector = new Lector(
            nombre.trim(), 
            email.trim(), 
            direccion.trim(),
            password.trim(),
            fecha,
            estadoLector,
            zonaLector
        );

        // Validaciones adicionales usando métodos de la entidad
        if (!lector.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }

        if (!lector.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }

        if (!lector.tieneDireccionValida()) {
            throw new DatosInvalidosException("Dirección inválida");
        }

        if (!lector.tieneFechaRegistroValida()) {
            throw new DatosInvalidosException("La fecha de registro debe ser anterior a la fecha actual");
        }
        
        if (!lector.tienePasswordValido()) {
            throw new DatosInvalidosException("Password inválido");
        }

        // Delegar al manejador
        manejadorLector.agregarLector(lector);
    }
    
    @Override
    public DtLector obtenerLector(String id) throws LectorNoExisteException {
        if (id == null || id.trim().isEmpty()) {
            throw new LectorNoExisteException("ID de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLector(id.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con ID: " + id);
        }
        
        return convertirADto(lector);
    }
    
    @Override
    public DtLector obtenerLectorPorEmail(String email) throws LectorNoExisteException {
        if (email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Email de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLectorPorEmail(email.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con email: " + email);
        }
        
        return convertirADto(lector);
    }
    
    @Override
    public String[] listarLectores() {
        List<Lector> lectores = manejadorLector.listarLectores();
        
        String[] resultado = new String[lectores.size()];
        for (int i = 0; i < lectores.size(); i++) {
            Lector l = lectores.get(i);
            resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ") - " + l.getEstado().getDescripcion() + " - " + l.getZona().getDescripcion();
        }
        
        return resultado;
    }
    
    @Override
    public String[] listarLectoresPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            EstadoLector estadoLector = EstadoLector.valueOf(estado.trim().toUpperCase());
            List<Lector> lectores = manejadorLector.listarLectoresPorEstado(estadoLector);
            
            return convertirAArray(lectores);
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    @Override
    public String[] listarLectoresPorZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            Zona zonaLector = Zona.valueOf(zona.trim().toUpperCase());
            List<Lector> lectores = manejadorLector.listarLectoresPorZona(zonaLector);
            
            return convertirAArray(lectores);
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    @Override
    public boolean existeLectorConEmail(String email) {
        return manejadorLector.existeLectorConEmail(email);
    }
    
    @Override
    public String obtenerIdLectorPorNombreEmail(String nombre, String email) throws LectorNoExisteException {
        if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Nombre y email son obligatorios para obtener el ID del lector.");
        }
        Lector lector = manejadorLector.obtenerLectorPorNombreEmail(nombre.trim(), email.trim());
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró un lector con el nombre: " + nombre + " y email: " + email);
        }
        return lector.getId();
    }

    @Override
    public void cambiarEstadoLector(String idLector, String nuevoEstado) throws LectorNoExisteException, DatosInvalidosException {
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new DatosInvalidosException("El nuevo estado es obligatorio");
        }
        Lector lector = manejadorLector.obtenerLector(idLector.trim());
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con ID: " + idLector);
        }
        EstadoLector estadoLector = parseEstado(nuevoEstado);
        lector.setEstado(estadoLector);
        manejadorLector.actualizarLector(lector);
    }

    @Override
    public void cambiarZonaLector(String idLector, String nuevaZona) throws LectorNoExisteException, DatosInvalidosException {
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        if (nuevaZona == null || nuevaZona.trim().isEmpty()) {
            throw new DatosInvalidosException("La nueva zona es obligatoria");
        }
        Lector lector = manejadorLector.obtenerLector(idLector.trim());
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con ID: " + idLector);
        }
        Zona zonaLector = parseZona(nuevaZona);
        lector.setZona(zonaLector);
        manejadorLector.actualizarLector(lector);
    }

    @Override
    public void actualizarLector(String id, String nombre, String email, String direccion,
                                String estado, String zona)
            throws LectorNoExisteException, DatosInvalidosException {
        
        // Verificar que existe
        Lector lector = manejadorLector.obtenerLector(id);
        
        // Validaciones de datos
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        if (direccion == null || direccion.trim().isEmpty() || direccion.length() < 5) {
            throw new DatosInvalidosException("La dirección debe tener al menos 5 caracteres");
        }
        
        // Parsear estado y zona
        EstadoLector estadoLector = parseEstado(estado);
        Zona zonaLector = parseZona(zona);
        
        // Actualizar datos
        lector.setNombre(nombre.trim());
        lector.setEmail(email.trim());
        lector.setDireccion(direccion.trim());
        lector.setEstado(estadoLector);
        lector.setZona(zonaLector);
        
        // Validaciones adicionales
        if (!lector.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!lector.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        if (!lector.tieneDireccionValida()) {
            throw new DatosInvalidosException("Dirección inválida");
        }
        
        // Delegar al manejador para actualizar en BD
        manejadorLector.actualizarLector(lector);
    }
    
    @Override
    public void eliminarLector(String id) throws LectorNoExisteException {
        manejadorLector.eliminarLector(id);
    }
    
    // Métodos auxiliares
    
    private EstadoLector parseEstado(String estado) throws DatosInvalidosException {
        try {
            String estadoStr = estado.trim();
            if (estadoStr.equalsIgnoreCase("Activo")) {
                return EstadoLector.ACTIVO;
            } else if (estadoStr.equalsIgnoreCase("Suspendido")) {
                return EstadoLector.SUSPENDIDO;
            } else {
                throw new IllegalArgumentException("Estado inválido");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Estado inválido. Valores válidos: Activo, Suspendido");
        }
    }
    
    private Zona parseZona(String zona) throws DatosInvalidosException {
        try {
            String zonaStr = zona.trim();
            if (zonaStr.equalsIgnoreCase("Biblioteca Central")) {
                return Zona.BIBLIOTECA_CENTRAL;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Este")) {
                return Zona.SUCURSAL_ESTE;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Oeste")) {
                return Zona.SUCURSAL_OESTE;
            } else if (zonaStr.equalsIgnoreCase("Biblioteca Infantil")) {
                return Zona.BIBLIOTECA_INFANTIL;
            } else if (zonaStr.equalsIgnoreCase("Archivo General")) {
                return Zona.ARCHIVO_GENERAL;
            } else {
                throw new IllegalArgumentException("Zona inválida");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Zona inválida. Valores válidos: Biblioteca Central, Sucursal Este, Sucursal Oeste, Biblioteca Infantil, Archivo General");
        }
    }
    
    private DtLector convertirADto(Lector lector) {
        return new DtLector(
            lector.getId(),
            lector.getNombre(),
            lector.getEmail(),
            lector.getDireccion(),
            lector.getFechaRegistro(),
            lector.getEstado(),
            lector.getZona()
        );
    }
    
    private String[] convertirAArray(List<Lector> lectores) {
        String[] resultado = new String[lectores.size()];
        for (int i = 0; i < lectores.size(); i++) {
            Lector l = lectores.get(i);
            resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ")";
        }
        return resultado;
    }
}

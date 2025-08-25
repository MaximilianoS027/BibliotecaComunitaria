package logica;

import interfaces.IControlador;
import datatypes.DtBibliotecario;
import datatypes.DtLector;
import excepciones.BibliotecarioRepetidoException;
import excepciones.BibliotecarioNoExisteException;
import excepciones.LectorRepetidoException;
import excepciones.LectorNoExisteException;
import excepciones.DatosInvalidosException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * Controlador principal que implementa la lógica de negocio
 */
public class Controlador implements IControlador {
    
    private ManejadorBibliotecario manejadorBibliotecario;
    private ManejadorLector manejadorLector;
    
    public Controlador() {
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
        this.manejadorLector = ManejadorLector.getInstancia();
    }
    
    @Override
    public void registrarBibliotecario(String numeroEmpleado, String nombre, String email) 
            throws BibliotecarioRepetidoException, DatosInvalidosException {
        
        // Validaciones de datos
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new DatosInvalidosException("El número de empleado es obligatorio");
        }
        
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 2) {
            throw new DatosInvalidosException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (email == null || !email.contains("@") || email.length() < 5) {
            throw new DatosInvalidosException("El email debe ser válido y contener @");
        }
        
        // Crear entidad
        Bibliotecario bibliotecario = new Bibliotecario(
            numeroEmpleado.trim(), 
            nombre.trim(), 
            email.trim()
        );
        
        // Validaciones adicionales usando métodos de la entidad
        if (!bibliotecario.tieneNumeroEmpleadoValido()) {
            throw new DatosInvalidosException("Número de empleado inválido");
        }
        
        if (!bibliotecario.tieneNombreValido()) {
            throw new DatosInvalidosException("Nombre inválido");
        }
        
        if (!bibliotecario.tieneEmailValido()) {
            throw new DatosInvalidosException("Email inválido");
        }
        
        // Delegar al manejador
        manejadorBibliotecario.agregarBibliotecario(bibliotecario);
    }
    
    @Override
    public DtBibliotecario obtenerBibliotecario(String numeroEmpleado) 
            throws BibliotecarioNoExisteException {
        
        if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
            throw new BibliotecarioNoExisteException("Número de empleado inválido");
        }
        
        Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecario(numeroEmpleado.trim());
        
        // Convertir a DTO
        return new DtBibliotecario(
            bibliotecario.getNumeroEmpleado(),
            bibliotecario.getNombre(),
            bibliotecario.getEmail()
        );
    }
    
    @Override
    public String[] listarBibliotecarios() {
        List<Bibliotecario> bibliotecarios = manejadorBibliotecario.listarBibliotecarios();
        
        String[] resultado = new String[bibliotecarios.size()];
        for (int i = 0; i < bibliotecarios.size(); i++) {
            Bibliotecario b = bibliotecarios.get(i);
            resultado[i] = b.getNumeroEmpleado() + " - " + b.getNombre() + " (" + b.getEmail() + ")";
        }
        
        return resultado;
    }
    
    @Override
    public void registrarLector(String nombre, String email, String direccion, 
                               String fechaRegistro, String estado, String zona) 
            throws LectorRepetidoException, DatosInvalidosException {
        
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
        EstadoLector estadoLector;
        try {
            // Convertir el valor descriptivo a la enumeración
            String estadoStr = estado.trim();
            if (estadoStr.equalsIgnoreCase("Activo")) {
                estadoLector = EstadoLector.ACTIVO;
            } else if (estadoStr.equalsIgnoreCase("Suspendido")) {
                estadoLector = EstadoLector.SUSPENDIDO;
            } else {
                throw new IllegalArgumentException("Estado inválido");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Estado inválido. Valores válidos: Activo, Suspendido");
        }
        
        // Parsear zona
        Zona zonaLector;
        try {
            // Convertir el valor descriptivo a la enumeración
            String zonaStr = zona.trim();
            if (zonaStr.equalsIgnoreCase("Biblioteca Central")) {
                zonaLector = Zona.BIBLIOTECA_CENTRAL;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Este")) {
                zonaLector = Zona.SUCURSAL_ESTE;
            } else if (zonaStr.equalsIgnoreCase("Sucursal Oeste")) {
                zonaLector = Zona.SUCURSAL_OESTE;
            } else if (zonaStr.equalsIgnoreCase("Biblioteca Infantil")) {
                zonaLector = Zona.BIBLIOTECA_INFANTIL;
            } else if (zonaStr.equalsIgnoreCase("Archivo General")) {
                zonaLector = Zona.ARCHIVO_GENERAL;
            } else {
                throw new IllegalArgumentException("Zona inválida");
            }
        } catch (Exception e) {
            throw new DatosInvalidosException("Zona inválida. Valores válidos: Biblioteca Central, Sucursal Este, Sucursal Oeste, Biblioteca Infantil, Archivo General");
        }
        
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
    public DtLector obtenerLector(String id) 
            throws LectorNoExisteException {
        
        if (id == null || id.trim().isEmpty()) {
            throw new LectorNoExisteException("ID de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLector(id.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con ID: " + id);
        }
        
        // Convertir a DTO
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
    
    @Override
    public DtLector obtenerLectorPorEmail(String email) 
            throws LectorNoExisteException {
        
        if (email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Email de lector inválido");
        }
        
        Lector lector = manejadorLector.obtenerLectorPorEmail(email.trim());
        
        if (lector == null) {
            throw new LectorNoExisteException("No se encontró el lector con email: " + email);
        }
        
        // Convertir a DTO
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
    
    @Override
    public String[] listarLectores() {
        List<Lector> lectores = manejadorLector.listarLectores();
        
        String[] resultado = new String[lectores.size()];
        for (int i = 0; i < lectores.size(); i++) {
            Lector l = lectores.get(i);
            resultado[i] = l.getNombre() + " (" + l.getEmail() + ") - " + l.getZona();
        }
        
        return resultado;
    }
    
    /**
     * Obtiene el ID de un lector por su nombre y email
     * Útil para la interfaz de usuario
     */
    public String obtenerIdLectorPorNombreEmail(String nombre, String email) throws LectorNoExisteException {
        if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new LectorNoExisteException("Nombre y email son obligatorios");
        }
        
        List<Lector> lectores = manejadorLector.listarLectores();
        for (Lector l : lectores) {
            if (l.getNombre().trim().equals(nombre.trim()) && 
                l.getEmail().trim().equals(email.trim())) {
                return l.getId();
            }
        }
        
        throw new LectorNoExisteException("No se encontró lector con nombre: " + nombre + " y email: " + email);
    }
    
    @Override
    public String[] listarLectoresPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return new String[0];
        }
        
        try {
            EstadoLector estadoLector = EstadoLector.valueOf(estado.trim().toUpperCase());
            List<Lector> lectores = manejadorLector.listarLectoresPorEstado(estadoLector);
            
            String[] resultado = new String[lectores.size()];
            for (int i = 0; i < lectores.size(); i++) {
                Lector l = lectores.get(i);
                resultado[i] = l.getNombre() + " (" + l.getEmail() + ") - " + l.getZona();
            }
            
            return resultado;
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
            
            String[] resultado = new String[lectores.size()];
            for (int i = 0; i < lectores.size(); i++) {
                Lector l = lectores.get(i);
                resultado[i] = l.getId() + " - " + l.getNombre() + " (" + l.getEmail() + ") - " + l.getZona();
            }
            
            return resultado;
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
    
    @Override
    public void cambiarEstadoLector(String idLector, String nuevoEstado) 
            throws LectorNoExisteException, DatosInvalidosException {
        
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new DatosInvalidosException("El nuevo estado es obligatorio");
        }
        
        try {
            EstadoLector estado = EstadoLector.valueOf(nuevoEstado.trim().toUpperCase());
            manejadorLector.cambiarEstadoLector(idLector.trim(), estado);
        } catch (IllegalArgumentException e) {
            throw new DatosInvalidosException("Estado inválido: " + nuevoEstado);
        }
    }
    
    @Override
    public void cambiarZonaLector(String idLector, String nuevaZona) 
            throws LectorNoExisteException, DatosInvalidosException {
        
        if (idLector == null || idLector.trim().isEmpty()) {
            throw new DatosInvalidosException("ID de lector es obligatorio");
        }
        
        if (nuevaZona == null || nuevaZona.trim().isEmpty()) {
            throw new DatosInvalidosException("La nueva zona es obligatoria");
        }
        
        try {
            Zona zona = Zona.valueOf(nuevaZona.trim().toUpperCase());
            manejadorLector.cambiarZonaLector(idLector.trim(), zona);
        } catch (IllegalArgumentException e) {
            throw new DatosInvalidosException("Zona inválida: " + nuevaZona);
        }
    }
}

package logica;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para el manejo seguro de passwords
 * Incluye funciones de hash, salt y verificación
 */
public class PasswordUtil {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    
    /**
     * Genera un salt aleatorio
     * @return Salt en formato Base64
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Genera el hash de un password con salt
     * @param password Password en texto plano
     * @param salt Salt a utilizar
     * @return Hash del password en formato Base64
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            
            // Combinar password y salt
            String saltedPassword = password + salt;
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());
            
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash del password", e);
        }
    }
    
    /**
     * Verifica si un password coincide con el hash almacenado
     * @param password Password en texto plano
     * @param storedHash Hash almacenado
     * @param salt Salt utilizado
     * @return true si el password es correcto
     */
    public static boolean verifyPassword(String password, String storedHash, String salt) {
        String hashedInput = hashPassword(password, salt);
        return hashedInput.equals(storedHash);
    }
    
    /**
     * Valida que un password cumpla con los requisitos mínimos
     * @param password Password a validar
     * @return true si es válido
     */
    public static boolean isValidPassword(String password) {
        // Sin restricciones, solo que no sea null o vacío
        return password != null && !password.trim().isEmpty();
    }
    
    /**
     * Obtiene el mensaje de error para passwords inválidos
     * @return Mensaje descriptivo de los requisitos
     */
    public static String getPasswordRequirements() {
        return "El password no puede estar vacío.";
    }
}

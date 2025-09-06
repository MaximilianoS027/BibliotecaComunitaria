package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * Panel personalizado que muestra la imagen de la Biblioteca Nacional del Uruguay como fondo
 */
public class FondoBibliotecaPanel extends JDesktopPane {
    
    private Image imagenFondo;
    private boolean imagenCargada = false;
    
    private boolean estirarImagen = false; // Flag para controlar si estirar o mantener proporciones
    
    public FondoBibliotecaPanel() {
        super();
        cargarImagenFondo();
    }
    
    /**
     * Establece si la imagen debe estirarse para cubrir toda la ventana
     * @param estirar true para estirar, false para mantener proporciones
     */
    public void setEstirarImagen(boolean estirar) {
        this.estirarImagen = estirar;
        repaint();
    }
    
    private void cargarImagenFondo() {
        try {
            // Cargar la imagen Portada.png desde recursos
            imagenFondo = new ImageIcon(getClass().getResource("/images/Portada.png")).getImage();
            imagenCargada = true;
            System.out.println("Imagen Portada.png cargada exitosamente");
        } catch (Exception e) {
            // Si no se puede cargar la imagen, mostrar error
            System.out.println("Error al cargar la imagen Portada.png: " + e.getMessage());
            imagenCargada = false;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        if (imagenCargada && imagenFondo != null) {
            // Dibujar la imagen de fondo escalada
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Escalar la imagen según el modo seleccionado
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = imagenFondo.getWidth(this);
            int imageHeight = imagenFondo.getHeight(this);
            
            int scaledWidth, scaledHeight, x, y;
            
            if (estirarImagen) {
                // Estirar la imagen para cubrir exactamente toda la ventana
                scaledWidth = panelWidth;
                scaledHeight = panelHeight;
                x = 0;
                y = 0;
            } else {
                // Mantener proporciones y cubrir toda la ventana
                double scaleX = (double) panelWidth / imageWidth;
                double scaleY = (double) panelHeight / imageHeight;
                double scale = Math.max(scaleX, scaleY); // Usar el mayor para cubrir todo
                
                scaledWidth = (int) (imageWidth * scale);
                scaledHeight = (int) (imageHeight * scale);
                
                // Centrar la imagen
                x = (panelWidth - scaledWidth) / 2;
                y = (panelHeight - scaledHeight) / 2;
            }
            
            // Dibujar la imagen con transparencia para que no interfiera con el contenido
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.drawImage(imagenFondo, x, y, scaledWidth, scaledHeight, this);
        } else {
            // Fondo azul como fallback si no se puede cargar la imagen
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Mensaje de error
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String mensaje = "No se pudo cargar la imagen Portada.png";
            int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(mensaje, x, y);
        }
        
        g2d.dispose();
    }
    
    /**
     * Método para recargar la imagen de fondo
     */
    public void recargarImagen() {
        cargarImagenFondo();
        repaint();
    }
    
    /**
     * Método para establecer una imagen de fondo personalizada
     */
    public void establecerImagenFondo(Image imagen) {
        this.imagenFondo = imagen;
        this.imagenCargada = true;
        repaint();
    }
}

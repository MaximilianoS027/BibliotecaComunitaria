package interfaces;

import logica.ArticuloEspecialControlador;
import logica.BibliotecarioControlador;
import logica.LectorControlador;
import logica.LibroControlador;

/**
 * Fábrica Singleton para crear instancias de controladores específicos
 */
public class Fabrica {
    private static Fabrica instancia = null;
    private static ILectorControlador lectorControlador = null;
    private static IBibliotecarioControlador bibliotecarioControlador = null;
    private static ILibroControlador libroControlador = null;
    private static IArticuloEspecialControlador articuloEspecialControlador = null;

    private Fabrica() {}

    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public ILectorControlador getILectorControlador() {
        if (lectorControlador == null) {
            lectorControlador = new LectorControlador();
        }
        return lectorControlador;
    }

    public IBibliotecarioControlador getIBibliotecarioControlador() {
        if (bibliotecarioControlador == null) {
            bibliotecarioControlador = new BibliotecarioControlador();
        }
        return bibliotecarioControlador;
    }

    public ILibroControlador getILibroControlador() {
        if (libroControlador == null) {
            libroControlador = new LibroControlador();
        }
        return libroControlador;
    }

    public IArticuloEspecialControlador getIArticuloEspecialControlador() {
        if (articuloEspecialControlador == null) {
            articuloEspecialControlador = new ArticuloEspecialControlador();
        }
        return articuloEspecialControlador;
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LibroDeRecetas {

    public static final int EXITO = 0;
    public static final int ERROR_RECETA_NULL = 1;
    public static final int ERROR_DEMASIADAS = 2;

    // @todo: atributos privados

    public LibroDeRecetas(int maxRecetasEnLibro) {
        // @todo
    }

    public int agregarReceta(Receta receta) {
        // @todo
    }

    public Receta[] buscarRecetaPorNombre(String texto) {
        // @todo
    }

    public void guardarRecetasEnArchivo(String nombreArchivo) throws IOException {
        // @todo
    }

    public void cargarRecetasDeArchivo(String nombreArchivo, int maxIngredientes, int maxInstrucciones) throws IOException {
        // @todo
    }

    public boolean recetasCompletas() {
        // @todo
    }

    public int numRecetas() {
        // @todo
    }

    public boolean eliminarReceta(Receta seleccionada) {
        // @todo
    }
}

import java.io.BufferedReader;
import java.io.IOException;

public class Receta {

    // Estados de operación
    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_INGREDIENTES_COMPLETOS = 2;
    public static final int ERROR_INSTRUCCIONES_COMPLETAS = 3;

    public static final String SEPARADOR_INSTRUCCIONES = "INSTRUCCIONES";
    public static final String SEPARADOR_FIN = "-----";

    // @todo: atributos privados

    public Receta(String nombre, int maxIngredientes, int maxInstrucciones) {
        // @todo
    }

    public String getNombre() {
        // @todo
    }

    public String[] getIngredientes() {
        // @todo
    }

    public String[] getInstrucciones() {
        // @todo
    }

    public int agregarIngrediente(String ingrediente) {
        // @todo
    }

    public int agregarInstruccion(String instruccion) {
        // @todo
    }

    public boolean ingredientesCompletos() {
        // @todo
    }

    public boolean instruccionesCompletas() {
        // @todo
    }

    public int numIngredientes() {
        // @todo
    }

    public int numInstrucciones() {
        // @todo
    }

    @Override
    public String toString() {
        // @todo
    }

    public String toRawString() {
        // @todo
    }

    public int getMaxIngredientes() {
        // @todo
    }

    public int getMaxInstrucciones() {
        // @todo
    }

    public static Receta fromBufferedReader(BufferedReader reader, int maxIngredientes, int maxInstrucciones) throws IOException {
        // @todo
    }
}

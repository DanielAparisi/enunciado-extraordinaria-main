import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class PlanificadorSemanal {

    // Constantes para los días de la semana
    public static final int LUNES = 0;
    public static final int MARTES = 1;
    public static final int MIERCOLES = 2;
    public static final int JUEVES = 3;
    public static final int VIERNES = 4;
    public static final int SABADO = 5;
    public static final int DOMINGO = 6;

    // Nombres de los días para mostrar por pantalla / guardar en fichero
    public static final String[] NOMBRES_DIAS = new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    // Ancho fijo por columna (según la especificación): 12 caracteres
    public static final int ANCHO_COLUMNA = 12;
    // número de caracteres mostrados antes del punto cuando truncamos
    public static final int MAX_RECETA_NOMBRE = ANCHO_COLUMNA - 2;

    // @todo: atributos privados

    public PlanificadorSemanal() {
        // @todo
    }

    public void agregarComida(int dia, Receta receta) {
        // @todo
    }

    @Override
    public String toString() {
        // @todo
    }

    public void guardarPlanEnArchivo(String nombreArchivo) throws IOException {
        // @todo
    }
}

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gestiona la planificación de una receta por cada día de la semana.
 * <p>
 * Permite asignar recetas a días concretos, obtener una representación
 * tabular del plan y persistirlo en un fichero de texto.
 * </p>
 */
public class PlanificadorSemanal {

    /** Índice del lunes en el plan semanal. */
    public static final int LUNES = 0;
    /** Índice del martes en el plan semanal. */
    public static final int MARTES = 1;
    /** Índice del miércoles en el plan semanal. */
    public static final int MIERCOLES = 2;
    /** Índice del jueves en el plan semanal. */
    public static final int JUEVES = 3;
    /** Índice del viernes en el plan semanal. */
    public static final int VIERNES = 4;
    /** Índice del sábado en el plan semanal. */
    public static final int SABADO = 5;
    /** Índice del domingo en el plan semanal. */
    public static final int DOMINGO = 6;

    /** Nombres completos de los días de la semana, en orden de {@link #LUNES} a {@link #DOMINGO}. */
    public static final String[] NOMBRES_DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    /** Ancho fijo de cada columna en la representación tabular (12 caracteres). */
    public static final int ANCHO_COLUMNA = 12;
    /** Número máximo de caracteres del nombre de la receta antes de truncar con un punto. */
    public static final int MAX_RECETA_NOMBRE = ANCHO_COLUMNA - 2;

    private Receta[] planSemanal;

    /**
     * Crea un planificador semanal sin ninguna comida asignada.
     */
    public PlanificadorSemanal() {
        this.planSemanal= new Receta[NOMBRES_DIAS.length];
    }

    /**
     * Asigna una receta a un día de la semana. Si ya había una receta para ese día, se reemplaza.
     *
     * @param dia    índice del día (usar las constantes {@link #LUNES}–{@link #DOMINGO})
     * @param receta receta a planificar para ese día
     */
    public void agregarComida(int dia, Receta receta) {
        if(dia>=LUNES && dia<=DOMINGO){
            this.planSemanal[dia]= receta;
        }
    }

    /**
     * Devuelve la representación tabular del plan semanal para mostrar en consola.
     * Cada columna tiene un ancho fijo de {@link #ANCHO_COLUMNA} caracteres; los nombres
     * que superen {@link #MAX_RECETA_NOMBRE} caracteres se truncan y se añade un punto.
     * Los días sin receta asignada se muestran en blanco.
     *
     * @return cadena de texto con la tabla del plan semanal
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------------------------------------\n");
        for(int i = 0; i < NOMBRES_DIAS.length; i++) {
            sb.append(formatearCelda(NOMBRES_DIAS[i]));
        }

        sb.append("\n");
        sb.append("------------------------------------------------------------------------------------\n");
        for(int i = 0; i < planSemanal.length; i++) {
            if (planSemanal[i] != null && planSemanal[i].getNombre() != null) {
                sb.append(formatearCelda(planSemanal[i].getNombre()));
            } else {
                sb.append(formatearCelda(""));
            }
        }

        sb.append("\n");
        sb.append("------------------------------------------------------------------------------------\n");
        return sb.toString();
    }

    /**
     * Formatea un texto para que ocupe exactamente {@link #ANCHO_COLUMNA} caracteres.
     * Si el texto supera los {@link #MAX_RECETA_NOMBRE} caracteres, se trunca y se añade un punto.
     *
     * @param texto texto a formatear
     * @return cadena de exactamente {@link #ANCHO_COLUMNA} caracteres
     */
    private String formatearCelda(String texto) {
        if (texto.length() > 11) {
            String textoTruncado = texto.substring(0, MAX_RECETA_NOMBRE) + ".";
            return String.format("%-" + ANCHO_COLUMNA + "s", textoTruncado);
        } else {
            return String.format("%-" + ANCHO_COLUMNA + "s", texto);
        }
    }

    /**
     * Guarda el plan semanal en un fichero de texto con el formato:
     * <pre>
     * Lunes: &lt;nombre receta o ---&gt;
     * Martes: &lt;nombre receta o ---&gt;
     * ...
     * </pre>
     * Si el fichero ya existe, su contenido se sobreescribe.
     *
     * @param nombreArchivo ruta del fichero donde se guardará el plan
     * @throws IOException si ocurre un error al escribir el fichero
     */
    public void guardarPlanEnArchivo(String nombreArchivo) throws IOException {
        Path path = Path.of(nombreArchivo);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < NOMBRES_DIAS.length; i++) {
            String nombreDia = NOMBRES_DIAS[i];
            String textoReceta = "---";
            if (planSemanal[i] != null && planSemanal[i].getNombre() != null) {
                textoReceta = planSemanal[i].getNombre();
            }
            sb.append(nombreDia).append(": ").append(textoReceta).append("\n");
        }

        Files.writeString(path, sb.toString());
    }
}
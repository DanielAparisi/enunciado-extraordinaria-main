import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
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
    private Receta [] planSemanal;


    public PlanificadorSemanal() {
        this.planSemanal= new Receta[NOMBRES_DIAS.length];
    }

    public void agregarComida(int dia, Receta receta) {
        if(dia>=LUNES && dia<=DOMINGO){
            this.planSemanal[dia]= receta;
        }
    }

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

    private String formatearCelda( String texto){
        if (texto.length() > 11) {
            String textoTruncado = texto.substring(0, MAX_RECETA_NOMBRE) + ".";
            return String.format("%-" + ANCHO_COLUMNA + "s", textoTruncado);
        } else {
            return String.format("%-" + ANCHO_COLUMNA + "s", texto);
        }
    }

    public void guardarPlanEnArchivo(String nombreArchivo) throws IOException {
        Path path = Path.of(nombreArchivo);

        try( BufferedWriter bufferedWriter= Files.newBufferedWriter(path)){
            for ( int i=0; i<NOMBRES_DIAS.length; i++){
                String nombreDia = NOMBRES_DIAS[i];
                String textoReceta = "---";
                if (planSemanal[i] != null && planSemanal[i].getNombre() != null) {
                    textoReceta = planSemanal[i].getNombre();
                }
                bufferedWriter.write(nombreDia + ": " + textoReceta);
                bufferedWriter.newLine();
            }
        }
    }
}

/**
 * Punto de entrada de la aplicación de recetario y planificador semanal.
 * <p>
 * Recibe los parámetros de configuración por línea de comandos, inicializa
 * la {@link InterfazUsuario} y arranca el bucle principal del programa.
 * </p>
 * @author Luis Salinero y Daniel Aparisi
 */
public class Main {

    /**
     * Método principal de la aplicación.
     * <p>Argumentos esperados (en orden):</p>
     * <ol>
     *   <li>{@code maxIngredientesPorReceta} – número máximo de ingredientes por receta</li>
     *   <li>{@code maxInstruccionesPorReceta} – número máximo de instrucciones por receta</li>
     *   <li>{@code maxRecetasEnLibro} – número máximo de recetas en el libro</li>
     *   <li>{@code nombreArchivoRecetas} (opcional) – fichero del que cargar recetas al inicio</li>
     * </ol>
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Uso: Main <maxIngredientes> <maxInstrucciones> <maxRecetas> [nombreArchivoRecetas]");
            return;
        }

        try {
            int maxIngredientes = Integer.parseInt(args[0]);
            int maxInstrucciones = Integer.parseInt(args[1]);
            int maxRecetas = Integer.parseInt(args[2]);
            InterfazUsuario interfaz;

            if (args.length >= 4) {
                String nombreArchivoRecetas = args[3];
                interfaz = new InterfazUsuario(maxIngredientes, maxInstrucciones, maxRecetas, nombreArchivoRecetas);
            } else {
                interfaz = new InterfazUsuario(maxIngredientes, maxInstrucciones, maxRecetas);
            }
            interfaz.iniciar();

        } catch (NumberFormatException e) {
            System.out.println("Error: Los argumentos maxIngredientes, maxInstrucciones y maxRecetas deben ser números enteros.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}

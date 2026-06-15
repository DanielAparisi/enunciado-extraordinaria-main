import java.io.IOException;
import java.util.Scanner;


/**
 * Gestiona la interacción con el usuario a través de un menú en consola.
 * <p>
 * Actúa como controlador principal: muestra el menú, recoge la entrada del
 * usuario y delega las operaciones en {@link LibroDeRecetas} y {@link PlanificadorSemanal}.
 * </p>
 * @author Luis Salinero y Daniel Aparisi
 */
public class InterfazUsuario {

    private final LibroDeRecetas libroRecetas;
    private final int maxIngredientes;
    private final int maxInstrucciones;
    private PlanificadorSemanal planificador;

    /**
     * Crea la interfaz de usuario inicializando el libro de recetas y el planificador vacíos.
     *
     * @param maxIngredientes  número máximo de ingredientes por receta
     * @param maxInstrucciones número máximo de instrucciones por receta
     * @param maxRecetasEnLibro número máximo de recetas en el libro
     */
    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
        this.planificador = new PlanificadorSemanal();
    }

    /**
     * Crea la interfaz de usuario indicando el fichero de recetas a cargar al inicio.
     *
     * @param maxIngredientes   número máximo de ingredientes por receta
     * @param maxInstrucciones  número máximo de instrucciones por receta
     * @param maxRecetasEnLibro número máximo de recetas en el libro
     * @param archivoRecetas    ruta del fichero de recetas a cargar al iniciar
     */
    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
    }

    /**
     * Comprueba si el texto introducido por el usuario es una señal de cancelación
     * ({@code "fin"} o {@code "-FIN-"}, sin distinción de mayúsculas).
     *
     * @param texto texto a comprobar
     * @return {@code true} si el texto indica cancelación
     */
    private boolean esCancelacion(String texto) {
        return texto.equalsIgnoreCase("fin") || texto.equalsIgnoreCase("-FIN-");
    }

    /**
     * Solicita al usuario el nombre de un fichero y garantiza que termine en {@code .txt}.
     *
     * @param scanner lector de entrada estándar
     * @param mensaje mensaje a mostrar antes de leer
     * @return nombre del fichero con extensión {@code .txt}
     */
    private String leerNombreArchivoTxt(Scanner scanner, String mensaje) {
        String nombreArchivo = Utilidades.leerCadena(scanner, mensaje);
        if (!nombreArchivo.toLowerCase().endsWith(".txt")) {
            nombreArchivo += ".txt";
        }
        return nombreArchivo;
    }

    /**
     * Abre el canal de entrada y lanza el bucle principal del programa.
     * El método retorna cuando el usuario selecciona la opción de salir.
     */
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        menuPrincipal(scanner);
        scanner.close();
    }

    /**
     * Muestra el menú principal y gestiona la selección del usuario en bucle
     * hasta que se elija la opción de salir.
     *
     * @param scanner lector de entrada estándar
     */
    private void menuPrincipal(Scanner scanner) {
        String cadena = "";
        cadena += "--- Menú Principal ---\n";
        cadena += "1. Agregar Receta\n";
        cadena += "2. Consultar/Editar Receta\n";
        cadena += "3. Planificar Comidas\n";
        cadena += "4. Guardar Recetas\n";
        cadena += "5. Cargar Recetas\n";
        cadena += "6. Guardar Plan Semanal\n";
        cadena += "7. Salir\n\n";

        cadena += "Elige una opción:";

        int opcion;
        do{
            opcion = Utilidades.leerNumero(scanner, cadena, 1, 7);
            switch (opcion) {
                case 1 -> agregarReceta(scanner);
                case 2 -> consultarReceta(scanner);
                case 3 -> planificarComidas(scanner);
                case 4 -> guardarRecetas(scanner);
                case 5 -> cargarRecetas(scanner);
                case 6 -> guardarPlanSemanal(scanner);
            }
        } while (opcion != 7);
    }

    /**
     * Solicita al usuario los datos de una nueva receta (nombre, ingredientes e instrucciones)
     * y la añade al libro de recetas.
     *
     * @param scanner lector de entrada estándar
     */
    private void agregarReceta(Scanner scanner) {
        boolean fin = false;
        String nombre = Utilidades.leerCadena(scanner, "Nombre de la receta: ");
        Receta receta = new Receta(nombre, maxIngredientes, maxInstrucciones);

        String ingrediente = Utilidades.leerCadena(scanner, "Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar): ");
        while (!esCancelacion(ingrediente) && !fin) {
            receta.agregarIngrediente(ingrediente);
            if (!receta.ingredientesCompletos()) {
                ingrediente = Utilidades.leerCadena(scanner, "Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar): ");
            } else {
                System.out.println("No se pueden añadir más ingredientes.");
                fin = true;
            }
        }

        fin = false;
        String instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
        while (!esCancelacion(instruccion) && !fin) {
            receta.agregarInstruccion(instruccion);
            if (!receta.instruccionesCompletas()) {
                instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
            } else {
                System.out.println("Ha añadido el número máximo de instrucciones posible.");
                fin = true;
            }
        }

        if (!libroRecetas.recetasCompletas()) {
            libroRecetas.agregarReceta(receta);
            System.out.println("¡Receta agregada exitosamente!");
        } else {
            System.out.println("No se pudo añadir la receta.");
        }
    }

    /**
     * Permite al usuario buscar una receta por nombre y, tras seleccionarla,
     * acceder al menú de edición.
     *
     * @param scanner lector de entrada estándar
     */
    private void consultarReceta(Scanner scanner) {
        Receta busqueda = buscarRecetaPorNombre(scanner);
        if (busqueda != null) {
            System.out.println(busqueda);
            System.out.println();
            editarReceta(scanner, busqueda);
        }
    }

    /**
     * Solicita al usuario un texto de búsqueda y devuelve la receta que seleccione
     * de entre las coincidencias encontradas. Repite la búsqueda si no hay resultados.
     * Devuelve {@code null} si el usuario cancela con {@code -FIN-}.
     *
     * @param scanner lector de entrada estándar
     * @return receta seleccionada por el usuario, o {@code null} si cancela
     */
    private Receta buscarRecetaPorNombre(Scanner scanner) {
        String textoBusqueda = Utilidades.leerCadena(scanner, "Introduce el texto de la receta a buscar (-FIN- para volver): ");
        Receta respuesta;

        if (esCancelacion(textoBusqueda)) {
            respuesta = null;
        } else {
            Receta[] recetasEncontradas = libroRecetas.buscarRecetaPorNombre(textoBusqueda);

            while (recetasEncontradas[0] == null && !esCancelacion(textoBusqueda)) {
                System.out.println("No se han encontrado recetas con ese nombre. Prueba otra vez.");
                textoBusqueda = Utilidades.leerCadena(scanner, "Introduce el texto de la receta a buscar (-FIN- para volver): ");
                recetasEncontradas = libroRecetas.buscarRecetaPorNombre(textoBusqueda);
            }

            if (!esCancelacion(textoBusqueda)) {
                System.out.println("Recetas encontradas:");
                respuesta = seleccionarReceta(scanner, recetasEncontradas);
            } else {
                respuesta = null;
            }
        }

        return respuesta;
    }

    /**
     * Muestra el menú de edición de la receta seleccionada y ejecuta la acción elegida
     * (añadir ingrediente, añadir instrucción, eliminar receta o volver).
     *
     * @param scanner     lector de entrada estándar
     * @param seleccionada receta sobre la que se realizará la edición
     */
    private void editarReceta(Scanner scanner, Receta seleccionada) {
        if (seleccionada != null) {
            System.out.println(seleccionada);
            System.out.println();

            String cadena = "";
            cadena += "1. Añadir ingrediente\n";
            cadena += "2. Añadir instrucción\n";
            cadena += "3. Eliminar receta\n";
            cadena += "4. Volver\n";

            System.out.println();
            int opcion = Utilidades.leerNumero(scanner, cadena, 1, 4);
            switch (opcion) {
                case 1 -> {
                    String nuevoIngrediente = Utilidades.leerCadena(scanner, "Introduce el ingrediente a añadir: ");
                    seleccionada.agregarIngrediente(nuevoIngrediente);
                }
                case 2 -> {
                    String nuevaInstruccion = Utilidades.leerCadena(scanner, "Introduce la instrucción a añadir: ");
                    seleccionada.agregarInstruccion(nuevaInstruccion);
                }
                case 3 -> {
                    libroRecetas.eliminarReceta(seleccionada);
                    System.out.println("Receta eliminada.");
                }
                case 4 -> System.out.println("Volver");
            }
        }
    }

    /**
     * Muestra el listado de recetas proporcionado y devuelve la que elija el usuario.
     *
     * @param scanner lector de entrada estándar
     * @param recetas array de recetas entre las que el usuario debe elegir
     * @return receta seleccionada, o {@code null} si el array es vacío o nulo
     */
    private Receta seleccionarReceta(Scanner scanner, Receta[] recetas) {
        if (recetas == null || recetas.length == 0) {
            return null;
        }

        int totalRecetas = 0;
        for (int i = 0; i < recetas.length; i++) {
            if (recetas[i] != null) {
                totalRecetas++;
                System.out.println((i + 1) + ". " + recetas[i].getNombre());
            }
        }

        if (totalRecetas == 0) {
            System.out.println("No hay recetas disponibles.");
            return null;
        }

        int recetaAElegir = Utilidades.leerNumero(scanner, "Elige una receta: ", 1, recetas.length);
        return recetas[recetaAElegir - 1];
    }

    /**
     * Muestra el plan semanal actual y permite al usuario asignar una receta a un día concreto.
     *
     * @param scanner lector de entrada estándar
     */
    private void planificarComidas(Scanner scanner) {
        System.out.println("Planificación de comidas para la semana:");
        System.out.println(planificador.toString());
        System.out.println();

        int valorDia = Utilidades.leerDiaDeLaSemana(scanner, "Introduce el día de la semana (L, M, X, J, V, S, D): ");
        String nombreDia = Utilidades.posicionADiaSemana(valorDia);

        Receta receta = buscarRecetaPorNombre(scanner);
        if (receta != null) {
            planificador.agregarComida(valorDia, receta);
            System.out.println("Receta planificada para " + nombreDia + ".");
        } else {
            System.out.println("No se pudo planificar porque no se encontró la receta.");
        }
    }

    /**
     * Solicita al usuario el nombre del fichero y guarda en él todas las recetas del libro.
     *
     * @param scanner lector de entrada estándar
     */
    private void guardarRecetas(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce el nombre del archivo donde guardar las recetas: ");
        try {
            libroRecetas.guardarRecetasEnArchivo(nombreArchivo);
            System.out.println("Recetas guardadas en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario la ruta del fichero y carga desde él las recetas al libro.
     *
     * @param scanner lector de entrada estándar
     */
    private void cargarRecetas(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce la ruta del archivo de donde cargar las recetas: ");
        try {
            libroRecetas.cargarRecetasDeArchivo(nombreArchivo, maxIngredientes, maxInstrucciones);
            System.out.println("Recetas cargadas de " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario el nombre del fichero y guarda en él el plan semanal.
     *
     * @param scanner lector de entrada estándar
     */
    private void guardarPlanSemanal(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce el nombre del archivo donde guardar el plan semanal: ");
        try {
            planificador.guardarPlanEnArchivo(nombreArchivo);
            System.out.println("Plan semanal guardado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    /**
     * Muestra un mensaje de error por la salida estándar.
     *
     * @param mensaje texto del error a mostrar
     */
    private void mostrarError(String mensaje) {
        System.out.println(mensaje);
    }
}

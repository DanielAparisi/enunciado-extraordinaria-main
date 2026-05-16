import java.io.IOException;
import java.util.Scanner;

public class InterfazUsuario {

    private final LibroDeRecetas libroRecetas;
    private final int maxIngredientes;
    private final int maxInstrucciones;
    private PlanificadorSemanal planificador;

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
        this.planificador = new PlanificadorSemanal();
    }

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
    }

    private boolean esCancelacion(String texto) {
        return texto.equalsIgnoreCase("fin") || texto.equalsIgnoreCase("-FIN-");
    }

    private String leerNombreArchivoTxt(Scanner scanner, String mensaje) {
        String nombreArchivo = Utilidades.leerCadena(scanner, mensaje);
        if (!nombreArchivo.toLowerCase().endsWith(".txt")) {
            nombreArchivo += ".txt";
        }
        return nombreArchivo;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        menuPrincipal(scanner);
        scanner.close();
    }

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

    private void consultarReceta(Scanner scanner) {
        Receta busqueda = buscarRecetaPorNombre(scanner);
        if (busqueda != null) {
            System.out.println(busqueda);
            System.out.println();
            editarReceta(scanner, busqueda);
        }
    }

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

    private void guardarRecetas(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce el nombre del archivo donde guardar las recetas: ");
        try {
            libroRecetas.guardarRecetasEnArchivo(nombreArchivo);
            System.out.println("Recetas guardadas en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private void cargarRecetas(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce la ruta del archivo de donde cargar las recetas: ");
        try {
            libroRecetas.cargarRecetasDeArchivo(nombreArchivo, maxIngredientes, maxInstrucciones);
            System.out.println("Recetas cargadas de " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    private void guardarPlanSemanal(Scanner scanner) {
        String nombreArchivo = leerNombreArchivoTxt(scanner, "Introduce el nombre del archivo donde guardar el plan semanal: ");
        try {
            planificador.guardarPlanEnArchivo(nombreArchivo);
            System.out.println("Plan semanal guardado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    private void mostrarError(String mensaje) {
        // @todo
    }
}

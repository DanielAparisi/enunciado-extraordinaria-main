import java.util.Scanner;

public class InterfazUsuario {
    // @todo: atributos privado
        private LibroDeRecetas libroRecetas;
        private int maxIngredientes;
        private int maxInstrucciones;
        private PlanificadorSemanal planificaccionSemanal;



        public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
            // @todo
            this.maxIngredientes = maxIngredientes;
            this.maxInstrucciones = maxInstrucciones;
            this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
            this.planificaccionSemanal = new PlanificadorSemanal();
        }

        public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
            // @todo
            this.maxIngredientes = maxIngredientes;
            this.maxInstrucciones = maxInstrucciones;
            this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);

            
        }
        private boolean esCancelacion(String texto) {
            return texto.equalsIgnoreCase("fin") || texto.equalsIgnoreCase("-FIN-");
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

            cadena += "Elige una opción: ";

            int opcion;
            do{
                System.out.println();
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
            System.out.println("¡Has salido del recetario con éxito!");
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
                } else respuesta = null;
            }

            return respuesta;
        }

        private void editarReceta(Scanner scanner, Receta seleccionada) {
            /*
            recibe como parámetros un objeto de la clase Scanner pa-
            ra leer la entrada del usuario y la receta seleccionada por el
            usuario. El método debe mostrar la información de la rece-
            ta seleccionada y el menú de edición de receta, que permi-
            te añadir un ingrediente, una instrucción o eliminar la receta
            */
            if(seleccionada != null){
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
        // 1. Validación de seguridad (incluyendo que el array no sea null)
        if (recetas == null || recetas.length == 0) {
            return null;
        }

        int totalRecetas = 0; // Esta variable será nuestro contador real

        // 2. Empezamos en i = 0, porque el primer elemento siempre es el 0
        for (int i = 0; i < recetas.length; i++) {
            if (recetas[i] != null) {
                // Contamos que hemos encontrado una receta real
                totalRecetas++;

                // Mostramos el índice empezando por 1 visualmente para el usuario (i + 1)
                System.out.println((i + 1) + ". " + recetas[i].getNombre());
            }
        }

        // Si resulta que el array tenía longitud pero todos eran 'null'
        if (totalRecetas == 0) {
            System.out.println("No hay recetas disponibles.");
            return null;
        }

        // Si tus recetas se guardan siempre seguidas, puedes usar 'totalRecetas'.
        int recetaAelegir = Utilidades.leerNumero(scanner, "Elige una receta: ", 1, recetas.length);


        // Como el usuario elige del 1 en adelante, debemos restarle 1 para que encaje con el índice del array (0 en adelante)
        return recetas[recetaAelegir - 1];
    }
        @SuppressWarnings("ConvertToStringSwitch")
        private void planificarComidas(Scanner scanner) {
            /*recibe como parámetro un objeto de la clase Scanner para
            leer la entrada del usuario. El método debe, en primer lugar,
            imprimir por pantalla la planificación actual de la semana.
            Después, debe solicitar al usuario el día de la semana en el
            que desea planificar la comida y la receta que desea añadir.
            Una vez introducidos los datos, el método debe planificar la
            comida para el día especificado y mostrar un mensaje de éxi-
            to */
            String planificaccion = planificaccionSemanal.toString();

            System.out.println("Planificación de comidas para la semana:");
            System.out.println(planificaccion);
            System.out.println("");
           
           String diaRequerido = "";
            boolean esValido = false; //estado
            Receta receta;

            do { 
                // Leemos la entrada y usamos .toUpperCase() por si el usuario la escribe en minúscula
                diaRequerido = Utilidades.leerCadena(scanner, "Introduce el día de la semana (L, M, X, J, V, S, D)").toUpperCase();
                int ValorDiaRequerido;
                // Comprobamos si la entrada coincide con una sola letra válida usando una expresión regular
                if (diaRequerido.matches("[LMXJVSD]")) {
                    esValido = true; // La entrada es correcta, cambiamos a true para salir del bucle
                    //cojo el diaRequerido y depues lo comparo con la primera letra delas constante de libroderecetas y si coincidan devueove ese valor  
                    if("L".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.LUNES;
                    }else if("M".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.MARTES;
                    }else if("X".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.MIERCOLES;
                    }else if("J".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.JUEVES;
                    }else if("V".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.VIERNES;
                    }else if("S".equals(diaRequerido)){
                        ValorDiaRequerido = planificaccionSemanal.SABADO;
                    }else {
                        ValorDiaRequerido = planificaccionSemanal.DOMINGO;
                    }
                    receta = buscarRecetaPorNombre(scanner);
                    if(receta != null){
                        planificaccionSemanal.agregarComida(ValorDiaRequerido, receta);
                        System.out.println("Receta Planificada para el" + diaRequerido);
                    }
                } else {
                    // La entrada es incorrecta, mostramos el mensaje de error
                    System.out.println("Error: Letra no válida. Debes introducir exclusivamente: L, M, X, J, V, S o D.");
                }
                
            } while (!esValido); // El bucle se repetirá mientras esValido sea falso

        }

        private void guardarRecetas(Scanner scanner) {
            // @todo

        }

        private void cargarRecetas(Scanner scanner) {
            // @todo
        }

        private void guardarPlanSemanal(Scanner scanner) {
            // @todo
        }

        private void mostrarError(String mensaje) {
            // @todo
        }
    }

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
        }

        public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
            // @todo
            this.maxIngredientes = maxIngredientes;
            this.maxInstrucciones = maxInstrucciones;
            this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
            
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
            // @todo
            /* El método debe solicitar al usua-
            rio el nombre de la receta. A continuación, debe solicitar al
            usuario que introduzca los ingredientes e instrucciones de la
            receta. Una vez introducidos los datos, el método debe crear
            un objeto de la clase Receta con la información proporciona-
            da y añadir la receta al libro de recetas. Si la receta se añade
            correctamente, se debe mostrar un mensaje de éxito */



            if(!libroRecetas.recetasCompletas()){

            String nombre;
            nombre = Utilidades.leerCadena(scanner,"nombre de la cadena ");
            //aqui crea el una receta nueva con ese nombre
            Receta receta = new Receta(nombre, maxIngredientes, maxInstrucciones);

            String ingrediente ;
            ingrediente = Utilidades.leerCadena(scanner,"Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar");


            }else{
                System.out.println("No se pudo añadir la receta");
            }

        }

        private void consultarReceta(Scanner scanner) {
            Receta busqueda = buscarRecetaPorNombre(scanner);
            if(!busqueda.getNombre().equalsIgnoreCase("ELIMINAR")){
                System.out.println(busqueda);
                System.out.println();
                editarReceta(scanner, busqueda);
            }

        }

        private Receta buscarRecetaPorNombre(Scanner scanner) {
            String textoBusqueda = Utilidades.leerCadena(scanner,"Introduce el texto de la receta a buscar (-FIN- para volver):" );
            Receta recetaEncontrada;
            boolean fin = false;
            while (textoBusqueda.equalsIgnoreCase("fin") || textoBusqueda.equalsIgnoreCase("-FIN-")){
                Receta[] recetasEncontradas = libroRecetas.buscarRecetaPorNombre(textoBusqueda);
                //verificamos si el array esta vacio
                while (recetasEncontradas[0] == null && !textoBusqueda.equalsIgnoreCase("fin") && !textoBusqueda.equalsIgnoreCase("-FIN-")){
                    System.out.println("No se han encontrado recetas con ese nombre. Prueba otra vez.");
                    textoBusqueda = Utilidades.leerCadena(scanner, "Introduce el texto de la receta a buscar (-FIN- para volver): ");
                    recetasEncontradas = libroRecetas.buscarRecetaPorNombre(textoBusqueda);
                }

                //si no esta vacio mostramos las recetas
              if (!textoBusqueda.equalsIgnoreCase("fin") && !textoBusqueda.equalsIgnoreCase("-FIN-")){

                  System.out.println("Recetas encontradas");
                  recetaEncontrada = seleccionarReceta(scanner, recetasEncontradas);
              } else{
                  recetaEncontrada = new Receta("ELIMINAR", maxIngredientes, maxInstrucciones);
              }
            }
            return null;
        }

        private void editarReceta(Scanner scanner, Receta seleccionada) {
            /*
            recibe como parámetros un objeto de la clase Scanner pa-
            ra leer la entrada del usuario y la receta seleccionada por el
            usuario. El método debe mostrar la información de la rece-
            ta seleccionada y el menú de edición de receta, que permi-
            te añadir un ingrediente, una instrucción o eliminar la receta
            */
            if(seleccionada !=null){
                System.out.println(seleccionada.toString());
            }
          
            System.out.println();
          
            String cadena = "";
                cadena += "1. Añadir ingrediente\n";
                cadena += "2. Añadir instrucción\n";
                cadena += "3. Eliminar receta\n";
                cadena += "4. Volver\n";

            int opcion;
            do{
                System.out.println();
                opcion = Utilidades.leerNumero(scanner, cadena, 1, 4);
                String nuevoIngrediente = Utilidades.leerCadena(scanner, "Introduce el ingrediente a añadir:");
                String nuevaInstruccion = Utilidades.leerCadena(scanner, "Introduce la Instruccion  a añadir:");

                switch (opcion) {
                    case 1 -> seleccionada.agregarIngrediente(nuevoIngrediente);
                    case 2 -> seleccionada.agregarInstruccion(nuevaInstruccion);
                    case 3 -> libroRecetas.eliminarReceta(seleccionada);
    
                }
            } while (opcion != 4);

           

        }        
        private Receta seleccionarReceta(Scanner scanner, Receta[] recetas) {
            /*que recibe como parámetro un objeto de la clase Scanner pa-
            ra leer la entrada del usuario y un array de recetas. El méto-
            do debe mostrar al usuario el listado de recetas y solicitar al
            usuario que seleccione una de ellas, mediante el número de
            orden en el listado (ver Listado 12). A continuación, debe de-
            volver la receta seleccionada por el usuario. */
            
            int totalRecetas = 1;
            if(recetas.length == 0){
                return null;
            }else{

                for(int i = 0; i< recetas.length; i++){
                    //MOSTRAMOS EL NOMBRE CON EL INDICE
                    if (recetas[i] != null) {
                        System.out.println((i + 1) + ". " + recetas[i].getNombre());//emepzamos el indice por el 1 no por el 0, 
                    }

                    if(i == recetas.length -1){
                        totalRecetas = recetas.length;
                    }else{
                        //si no ¿como consigo el numero totalderecetas si no ha llegado al maximo del array??
                    }
                }
            }
            int recetaAelegir = Utilidades.leerNumero(scanner, "Elige una receta: ", 1, totalRecetas);

            return recetas[recetaAelegir];
        }
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
                    if(diaRequerido == "L"){
                        ValorDiaRequerido = planificaccionSemanal.LUNES;
                    }else if(diaRequerido == "M"){
                        ValorDiaRequerido = planificaccionSemanal.MARTES;
                    }else if(diaRequerido == "X"){
                        ValorDiaRequerido = planificaccionSemanal.MIERCOLES;
                    }else if(diaRequerido == "J"){
                        ValorDiaRequerido = planificaccionSemanal.JUEVES;
                    }else if(diaRequerido == "V"){
                        ValorDiaRequerido = planificaccionSemanal.VIERNES;
                    }else if(diaRequerido == "S"){
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

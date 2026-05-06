import java.util.Scanner;

public class InterfazUsuario {
    // @todo: atributos privado
        private LibroDeRecetas libroRecetas;
        private int maxIngredientes;
        private int maxInstrucciones;



    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
            // @todo
            this.maxIngredientes = maxIngredientes;
            this.maxInstrucciones = maxInstrucciones;
            this.libroRecetas = new LibroDeRecetas(maxRecetasEnLibro);
        }

        public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
            // @todo
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
            String[] recetas;
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
             return null;
        }

        private void planificarComidas(Scanner scanner) {

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

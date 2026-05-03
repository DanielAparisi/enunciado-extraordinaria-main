import java.io.IOException;
import java.util.Scanner;

public class InterfazUsuario {
    // @todo: atributos privado
        private LibroDeRecetas libroRecetas;
        private final int maxIngredientes;
        private final int maxInstrucciones;



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
                boolean fin = false;

                String ingrediente ;
                ingrediente = Utilidades.leerCadena(scanner,"Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar");
                //mientras no lea fin o FIN .equalsIgnoraCase()
                while(!ingrediente.equalsIgnoreCase("fin") && !fin){
                    receta.agregarIngrediente(ingrediente);

                    if(!receta.ingredientesCompletos()){
                        ingrediente = Utilidades.leerCadena(scanner,"Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar");
                    } else{
                        System.out.println("Ha añadido el número máximo de ingredientes posible.");
                        fin = true;
                    }
                }


                fin = false;
                String instruccion;
                instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
                while (!instruccion.equalsIgnoreCase("fin") && !fin) {
                    receta.agregarInstruccion(instruccion);
                    if (!receta.instruccionesCompletas()) {
                        instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
                    } else {
                        System.out.println("Ha añadido el número máximo de instrucciones posible.");
                        fin = true;
                    }
                }

                libroRecetas.agregarReceta(receta);
                System.out.println("!Receta agregada exitosamente");
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
            // @todo
        }

        private Receta seleccionarReceta(Scanner scanner, Receta[] recetas) {
            // @todo
            return null;
        }

        private void planificarComidas(Scanner scanner) {
            // @todo
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

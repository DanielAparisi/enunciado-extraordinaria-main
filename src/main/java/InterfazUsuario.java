import java.io.IOException;
import java.util.Scanner;

public class InterfazUsuario {
    // @todo: atributos privado

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
        // @todo
        

    }

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
        // @todo
    }

    public void iniciar() {
        // @todo
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
        if(LibroDeRecetas.rece)


        System.out.println("Nombre de la recets: ");

    }

    private void consultarReceta(Scanner scanner) {
        // @todo
    }

    private Receta buscarRecetaPorNombre(Scanner scanner) {
        // @todo

    }

    private void editarReceta(Scanner scanner, Receta seleccionada) {
        // @todo
    }

    //private Receta seleccionarReceta(Scanner scanner, Receta[] recetas) {
        // @todo
    //}

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


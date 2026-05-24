public class Main {
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

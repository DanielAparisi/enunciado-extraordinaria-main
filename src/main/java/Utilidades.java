import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado.
 */
public class Utilidades {
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        String cadena = teclado.nextLine();
        return cadena;
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        boolean esValido = false;
        int num = 0;
        while (!esValido) {
            System.out.print(mensaje);
            if (teclado.hasNextInt()) { //¿es un entero?
                num = teclado.nextInt();
                if (num >= minimo && num <= maximo) esValido = true;//¿esta entre minimo y maximo?
                else teclado.nextLine();
            } else teclado.nextLine();
        }
        teclado.nextLine(); // Limpia el buffer de la entrada
        return num;
    }


    public static int leerDiaDeLaSemana(Scanner teclado, String mensaje) {
        // @todo
        String dia;
        int posicion;
        do {
            System.out.print(mensaje);
            dia = teclado.nextLine().toUpperCase();
            switch (dia) {
                case "L" -> posicion = 0;
                case "M" -> posicion = 1;
                case "X" -> posicion = 2;
                case "J" -> posicion = 3;
                case "V" -> posicion = 4;
                case "S" -> posicion = 5;
                case "D" -> posicion = 6;
                default -> posicion = -1;
            }
        } while (posicion == -1);
        return posicion;
    }

    public static int diaSemanaAPosicion(String dia) {
        int posicion;
        String dia1 = dia.substring(0, 1).toUpperCase();//Extraemos Su inicial en Mayuscula
        switch (dia1) {
            case "L" -> posicion = 0;
            case "M" -> posicion = 1;
            case "X" -> posicion = 2;
            case "J" -> posicion = 3;
            case "V" -> posicion = 4;
            case "S" -> posicion = 5;
            case "D" -> posicion = 6;
            default -> posicion = -1;
        }
        return posicion;
    }

    public static String posicionADiaSemana(int pos) {
        // @todo
        String dia = "Desconocido";
        switch (pos) {
            case 0 -> dia = "Lunes";
            case 1 -> dia = "Martes";
            case 2 -> dia = "Miércoles";
            case 3 -> dia = "Jueves";
            case 4 -> dia = "Viernes";
            case 5 -> dia = "Sábado";
            case 6 -> dia = "Domingo";
        }
        return dia; //si no se ha encontrado ----> desconocido
    }
}

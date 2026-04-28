import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado.
 */
public class Utilidades {
    public static String leerCadena(Scanner teclado, String s) {
        String cadena;
        do {
            System.out.print(s);
            cadena = teclado.nextLine();
        } while (cadena.isEmpty());
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
        int posicion;
        do {
            System.out.print(mensaje);
            String dia = teclado.nextLine().toUpperCase();
            posicion = diaSemanaAPosicion(dia);
        } while (posicion == -1);
        return posicion;
    }

    public static int diaSemanaAPosicion(String dia) {
        if (dia == null || dia.isEmpty()) return -1;
        switch (dia.substring(0, 1).toUpperCase()) {
            case "L": return PlanificadorSemanal.LUNES;
            case "M": return PlanificadorSemanal.MARTES;
            case "X": return PlanificadorSemanal.MIERCOLES;
            case "J": return PlanificadorSemanal.JUEVES;
            case "V": return PlanificadorSemanal.VIERNES;
            case "S": return PlanificadorSemanal.SABADO;
            case "D": return PlanificadorSemanal.DOMINGO;
            default:  return -1;
        }
    }

    public static String posicionADiaSemana(int pos) {
        if (pos >= PlanificadorSemanal.LUNES && pos <= PlanificadorSemanal.DOMINGO) {
            return PlanificadorSemanal.NOMBRES_DIAS[pos];
        }
        return "Desconocido";
    }
}

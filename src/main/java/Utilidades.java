import java.util.Scanner;

/**
 * Métodos de utilidad para la lectura de datos por teclado.
 * <p>
 * Todos los métodos garantizan que el valor devuelto es válido:
 * no se retorna hasta que el usuario introduce un dato correcto.
 * </p>
 */
public class Utilidades {

    /**
     * Muestra un mensaje y lee una cadena de texto no vacía introducida por el usuario.
     *
     * @param teclado lector de entrada estándar
     * @param s       mensaje a mostrar antes de leer
     * @return cadena no vacía introducida por el usuario
     */
    public static String leerCadena(Scanner teclado, String s) {
        String cadena;
        do {
            System.out.print(s);
            cadena = teclado.nextLine();
        } while (cadena.isEmpty());
        return cadena;
    }

    /**
     * Muestra un mensaje y lee un número entero en el rango [{@code minimo}, {@code maximo}].
     * Repite la solicitud hasta que el usuario introduzca un valor válido.
     *
     * @param teclado lector de entrada estándar
     * @param mensaje mensaje a mostrar antes de leer
     * @param minimo  valor mínimo aceptado (inclusive)
     * @param maximo  valor máximo aceptado (inclusive)
     * @return número entero introducido por el usuario dentro del rango indicado
     */
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


    /**
     * Muestra un mensaje y lee un carácter que representa un día de la semana
     * (L, M, X, J, V, S, D). Repite la solicitud hasta recibir un valor válido.
     *
     * @param teclado lector de entrada estándar
     * @param mensaje mensaje a mostrar antes de leer
     * @return índice del día de la semana según las constantes de {@link PlanificadorSemanal}
     */
    public static int leerDiaDeLaSemana(Scanner teclado, String mensaje) {
        int posicion;
        do {
            System.out.print(mensaje);
            String dia = teclado.nextLine().toUpperCase();
            posicion = diaSemanaAPosicion(dia);
        } while (posicion == -1);
        return posicion;
    }

    /**
     * Convierte el carácter de un día de la semana en su índice numérico.
     * Solo se considera el primer carácter del parámetro (L, M, X, J, V, S, D).
     *
     * @param dia carácter del día de la semana (mayúsculas o minúsculas)
     * @return índice del día según las constantes de {@link PlanificadorSemanal},
     *         o {@code -1} si el valor no corresponde a ningún día válido
     */
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

    /**
     * Convierte el índice numérico de un día de la semana en su nombre completo.
     *
     * @param pos índice del día según las constantes de {@link PlanificadorSemanal}
     * @return nombre completo del día (p.ej. "Lunes"), o {@code "Desconocido"} si el índice no es válido
     */
    public static String posicionADiaSemana(int pos) {
        if (pos >= PlanificadorSemanal.LUNES && pos <= PlanificadorSemanal.DOMINGO) {
            return PlanificadorSemanal.NOMBRES_DIAS[pos];
        }
        return "Desconocido";
    }
}

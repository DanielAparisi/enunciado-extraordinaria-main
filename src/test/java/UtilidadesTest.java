import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

@DisplayName("Tests para la clase Utilidades")
public class UtilidadesTest {

    @ParameterizedTest
    @DisplayName("Leer cadena de texto")
    @ValueSource(strings = {"Hola Mundo", "1234", "Texto con espacios"})
    void leerCadenaDeTexto(String entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        String resultado = Utilidades.leerCadena(teclado, "Introduce un texto: ");
        assertEquals(entrada, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer número dentro del rango")
    @ValueSource(ints = {1, 2, 3, 4, 5, 10})
    void leerNumeroDentroDelRango(int entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        int resultado = Utilidades.leerNumero(teclado, "Introduce un número entre 1 y 10: ", 1, 10);
        assertEquals(entrada, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer número fuera del rango")
    @CsvSource({"0, 5", "11, 5", "0, 10", "11, 10"})
    void leerNumeroFueraDelRango(int entradaMala, int entradaBuena) {
        Scanner teclado = new Scanner(entradaMala + "\n" + entradaMala + "\n" + entradaBuena + "\n");
        int resultado = Utilidades.leerNumero(teclado, "Introduce un número entre 1 y 10: ", 1, 10);
        assertEquals(entradaBuena, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer día de la semana válido")
    @CsvSource({"L, 0", "M, 1", "X, 2", "J, 3", "V, 4", "S, 5", "D, 6"})
    void leerDiaDeLaSemanaValido(String entrada, int posicion) {
        Scanner teclado = new Scanner(entrada + "\n");
        int resultado = Utilidades.leerDiaDeLaSemana(teclado, "Introduce un día de la semana: ");
        assertEquals(posicion, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer día de la semana inválido")
    @CsvSource({"A, L, 0", "A, M, 1", "A, X, 2", "A, J, 3", "A, V, 4", "A, S, 5", "A, D, 6"})
    void leerDiaDeLaSemanaInvalido(String entradaMala, String entradaBuena, int posicion) {
        Scanner teclado = new Scanner(entradaMala + "\n" + entradaMala + "\n" + entradaBuena + "\n");
        int resultado = Utilidades.leerDiaDeLaSemana(teclado, "Introduce un día de la semana: ");
        assertEquals(posicion, resultado);
    }

    @Test
    @DisplayName("Convertir día de la semana a posición")
    void convertirDiaSemanaAPosicion() {
        assertEquals(0, Utilidades.diaSemanaAPosicion("L"));
        assertEquals(1, Utilidades.diaSemanaAPosicion("M"));
        assertEquals(2, Utilidades.diaSemanaAPosicion("X"));
        assertEquals(3, Utilidades.diaSemanaAPosicion("J"));
        assertEquals(4, Utilidades.diaSemanaAPosicion("V"));
        assertEquals(5, Utilidades.diaSemanaAPosicion("S"));
        assertEquals(6, Utilidades.diaSemanaAPosicion("D"));
        assertEquals(-1, Utilidades.diaSemanaAPosicion("A"));
    }

    @Test
    @DisplayName("Convertir posición a día de la semana")
    void convertirPosicionADiaSemana() {
        assertEquals("Lunes", Utilidades.posicionADiaSemana(0));
        assertEquals("Martes", Utilidades.posicionADiaSemana(1));
        assertEquals("Miércoles", Utilidades.posicionADiaSemana(2));
        assertEquals("Jueves", Utilidades.posicionADiaSemana(3));
        assertEquals("Viernes", Utilidades.posicionADiaSemana(4));
        assertEquals("Sábado", Utilidades.posicionADiaSemana(5));
        assertEquals("Domingo", Utilidades.posicionADiaSemana(6));
        assertEquals("Desconocido", Utilidades.posicionADiaSemana(7));
    }
}
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la clase Receta")
public class RecetaTest {

    @Test
    @DisplayName("Constructor de Receta")
    void constructorReceta() {
        Receta receta = new Receta("Tortilla", 3, 3);
        assertEquals("Tortilla", receta.getNombre(), "El nombre de la receta no es el esperado.");
        assertEquals(3, receta.getMaxIngredientes(), "El número máximo de ingredientes no es el esperado.");
        assertEquals(3, receta.getMaxInstrucciones(), "El número máximo de instrucciones no es el esperado.");
    }

    @Test
    @DisplayName("Añadir ingrediente a receta vacía (código de estado)")
    void agregarIngredienteARecetaVaciaEstado() {
        Receta receta = new Receta("Tortilla", 3, 3);
        int estado = receta.agregarIngrediente("Huevo");
        assertEquals(Receta.EXITO, estado, "Debería poder añadir un ingrediente a una receta vacía.");
        assertEquals(1, receta.numIngredientes(),
                "El número de ingredientes debería ser 1 tras añadir un ingrediente a una receta vacía.");
    }

    @Test
    @DisplayName("Añadir ingrediente a receta completa (código de estado)")
    void agregarIngredienteARecetaCompletaEstado() {
        Receta receta = new Receta("Tortilla", 1, 3);
        receta.agregarIngrediente("Huevo");
        int estado = receta.agregarIngrediente("Sal");
        assertEquals(Receta.ERROR_INGREDIENTES_COMPLETOS, estado,
                "No se debería poder añadir un ingrediente a una receta completa.");
        assertEquals(1, receta.numIngredientes(),
                "El número de ingredientes no debería cambiar si no se añade el ingrediente.");
    }

    @Test
    @DisplayName("Añadir instrucción a receta vacía (código de estado)")
    void agregarInstruccionARecetaVaciaEstado() {
        Receta receta = new Receta("Tortilla", 3, 3);
        int estado = receta.agregarInstruccion("Batir los huevos");
        assertEquals(Receta.EXITO, estado,
                "Debería poder añadir una instrucción a una receta vacía.");
        assertEquals(1, receta.numInstrucciones(),
                "El número de instrucciones debería ser 1 tras añadir una instrucción a una receta vacía.");
    }

    @Test
    @DisplayName("Añadir instrucción a receta completa (código de estado)")
    void agregarInstruccionARecetaCompletaEstado() {
        Receta receta = new Receta("Tortilla", 3, 1);
        receta.agregarInstruccion("Batir los huevos");
        int estado = receta.agregarInstruccion("Añadir sal");
        assertEquals(Receta.ERROR_INSTRUCCIONES_COMPLETAS, estado,
                "No se debería poder añadir una instrucción a una receta completa.");
        assertEquals(1, receta.numInstrucciones(),
                "El número de instrucciones no debería cambiar si no se añade la instrucción.");
    }

    @Test
    @DisplayName("Comprobar receta con ingredientes completos")
    void ingredientesCompletos() {
        Receta receta = new Receta("Tortilla", 1, 3);
        receta.agregarIngrediente("Huevo");
        assertTrue(receta.ingredientesCompletos(), "Debería decir que los ingredientes están completos.");
    }

    @Test
    @DisplayName("Comprobar receta con ingredientes no completos")
    void ingredientesNoCompletos() {
        Receta receta = new Receta("Tortilla", 2, 3);
        receta.agregarIngrediente("Huevo");
        assertFalse(receta.ingredientesCompletos(), "Debería decir que los ingredientes no están completos.");
    }

    @Test
    @DisplayName("Comprobar receta con instrucciones completas")
    void instruccionesCompletas() {
        Receta receta = new Receta("Tortilla", 3, 1);
        receta.agregarInstruccion("Batir los huevos");
        assertTrue(receta.instruccionesCompletas(), "Debería decir que las instrucciones están completas.");
    }

    @Test
    @DisplayName("Comprobar receta con instrucciones no completas")
    void instruccionesNoCompletas() {
        Receta receta = new Receta("Tortilla", 3, 2);
        receta.agregarInstruccion("Batir los huevos");
        assertFalse(receta.instruccionesCompletas(), "Debería decir que las instrucciones no están completas.");
    }

    @ParameterizedTest
    @CsvSource({
            "Tortilla, Huevo, Patata, Batir los huevos, Freír la patata",
            "Tarta de queso, Queso, Huevo, Mezclar los ingredientes, Hornear a 180ºC",
            "Ensalada, Lechuga, Tomate, Lavar los ingredientes, Cortar los ingredientes"
    })
    @DisplayName("Formato textual de la receta para representar en pantalla")
    void toStringFormatoCorrecto(String nombre, String ingrediente1, String ingrediente2, String instruccion1, String instruccion2) {
        Receta receta = new Receta(nombre, 3, 3);
        receta.agregarIngrediente(ingrediente1);
        receta.agregarIngrediente(ingrediente2);
        receta.agregarInstruccion(instruccion1);
        receta.agregarInstruccion(instruccion2);
        String expected = String.format("Receta: %s\nIngredientes:\n- %s\n- %s\nInstrucciones:\n1. %s\n2. %s\n", nombre, ingrediente1, ingrediente2, instruccion1, instruccion2);
        assertEquals(expected, receta.toString(), "El formato devuelto por el método toString() no es el correcto.");
    }

    @ParameterizedTest
    @DisplayName("Formato textual de la receta para guardar en archivo (raw)")
    @CsvSource({
            "Tortilla, Huevo, Patata, Batir los huevos, Freír la patata",
            "Tarta de queso, Queso, Huevo, Mezclar los ingredientes, Hornear a 180ºC",
            "Ensalada, Lechuga, Tomate, Lavar los ingredientes, Cortar los ingredientes"
    })
    void toRawStringFormatoCorrecto(String nombre, String ingrediente1, String ingrediente2, String instruccion1, String instruccion2) {
        Receta receta = new Receta(nombre, 3, 3);
        receta.agregarIngrediente(ingrediente1);
        receta.agregarIngrediente(ingrediente2);
        receta.agregarInstruccion(instruccion1);
        receta.agregarInstruccion(instruccion2);
        String expected = String.format("%s\n%s\n%s\nINSTRUCCIONES\n%s\n%s\n-----\n", nombre, ingrediente1, ingrediente2, instruccion1, instruccion2);
        assertEquals(expected, receta.toRawString());
    }

    @Test
    @DisplayName("fromBufferedReader: obtiene información de receta correctamente")
    void fromBufferedReaderTrunca() throws IOException {
        String datos = "Tortilla\nHuevo\nPatata\nSal\nINSTRUCCIONES\nBatir los huevos\nFreir las patatas\nAñadir sal\n-----\n";
        BufferedReader reader = new BufferedReader(new StringReader(datos));
        Receta receta = Receta.fromBufferedReader(reader, 2, 2);
        assertEquals("Tortilla", receta.getNombre());
        assertArrayEquals(new String[]{"Huevo", "Patata"}, receta.getIngredientes());
        assertArrayEquals(new String[]{"Batir los huevos", "Freir las patatas"}, receta.getInstrucciones());
    }

    @Test
    @DisplayName("fromBufferedReader: trunca cantidad y mantiene solo hasta el máximo")
    void fromBufferedReaderTruncaCantidad() throws IOException {
        String datos = "Pasta\nIng1\nIng2\nIng3\nIng4\nIng5\nINSTRUCCIONES\nPaso1\nPaso2\nPaso3\n-----\n";
        BufferedReader reader = new BufferedReader(new StringReader(datos));
        // Pedimos solo 3 ingredientes y 2 instrucciones; el resto debe ser ignorado
        Receta receta = Receta.fromBufferedReader(reader, 3, 2);
        assertEquals("Pasta", receta.getNombre());
        assertEquals(3, receta.numIngredientes(), "Debe contener exactamente el número máximo de ingredientes permitidos");
        assertArrayEquals(new String[]{"Ing1", "Ing2", "Ing3"}, receta.getIngredientes());
        assertEquals(2, receta.numInstrucciones(), "Debe contener exactamente el número máximo de instrucciones permitidas");
        assertArrayEquals(new String[]{"Paso1", "Paso2"}, receta.getInstrucciones());
    }

    // Nuevas pruebas para validar valores inválidos de ingrediente (nulo, vacío, solo espacios)
    @Test
    @DisplayName("Agregar ingrediente nulo debe devolver ERROR_VALOR_INVALIDO")
    void agregarIngredienteNulo() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarIngrediente(null);
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para ingrediente nulo.");
        assertEquals(0, receta.numIngredientes(), "No debería incrementarse el contador de ingredientes.");
    }

    @Test
    @DisplayName("Agregar ingrediente vacío debe devolver ERROR_VALOR_INVALIDO")
    void agregarIngredienteVacio() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarIngrediente("");
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para cadena vacía.");
        assertEquals(0, receta.numIngredientes(), "No debería incrementarse el contador de ingredientes.");
    }

    @Test
    @DisplayName("Agregar ingrediente solo espacios debe devolver ERROR_VALOR_INVALIDO")
    void agregarIngredienteSoloEspacios() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarIngrediente("   \t  ");
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para cadena con solo espacios.");
        assertEquals(0, receta.numIngredientes(), "No debería incrementarse el contador de ingredientes.");
    }

    @Test
    @DisplayName("Agregar instrucción nula debe devolver ERROR_VALOR_INVALIDO")
    void agregarInstruccionNula() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarInstruccion(null);
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para instrucción nula.");
        assertEquals(0, receta.numInstrucciones(), "No debería incrementarse el contador de instrucciones.");
    }

    @Test
    @DisplayName("Agregar instrucción vacía debe devolver ERROR_VALOR_INVALIDO")
    void agregarInstruccionVacia() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarInstruccion("");
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para cadena vacía.");
        assertEquals(0, receta.numInstrucciones(), "No debería incrementarse el contador de instrucciones.");
    }

    @Test
    @DisplayName("Agregar instrucción solo espacios debe devolver ERROR_VALOR_INVALIDO")
    void agregarInstruccionSoloEspacios() {
        Receta receta = new Receta("Test", 3, 3);
        int estado = receta.agregarInstruccion("   \t  ");
        assertEquals(Receta.ERROR_VALOR_INVALIDO, estado, "Debería devolver ERROR_VALOR_INVALIDO para cadena con solo espacios.");
        assertEquals(0, receta.numInstrucciones(), "No debería incrementarse el contador de instrucciones.");
    }

}

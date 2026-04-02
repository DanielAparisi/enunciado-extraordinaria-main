import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests de integración")
public class InterfazUsuarioTest {
    @Test
    @DisplayName("Menú principal")
    void menuPrincipal() {
        List<String> salidaEsperada = Arrays.asList(
                "--- Menú Principal ---",
                "1. Agregar Receta",
                "2. Consultar/Editar Receta",
                "3. Planificar Comidas",
                "4. Guardar Recetas",
                "5. Cargar Recetas",
                "6. Guardar Plan Semanal",
                "7. Salir",
                "",
                "Elige una opción:"
        );

        String entrada = "7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
        interfaz.iniciar();

        List<String> salidaActual = Arrays.asList(salidaCapturada.toString().split("\\r?\\n"));

        assertLinesMatch(salidaEsperada, salidaActual, "El menú principal no se corresponde con los requisitos");
    }

    @Test
    @DisplayName("Menu: Agregar receta satisfactoriamente")
    void agregarRecetaSatisfactoriamente() {
        String entrada = "1\nTortilla\nHuevo\nPatata\nfin\nBatir los huevos\nFreir las patatas\nfin\n7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
        interfaz.iniciar();
        assertTrue(salidaCapturada.toString().contains("Nombre de la receta: "), "Hay que solicitar el nombre de la receta");
        assertTrue(salidaCapturada.toString().contains("Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar):"), "Hay que solicitar los ingredientes de la receta");
        assertTrue(salidaCapturada.toString().contains("Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar):"), "Hay que solicitar las instrucciones de la receta");
        assertTrue(salidaCapturada.toString().contains("¡Receta agregada exitosamente!"), "Hay que avisar al usuario de que la receta se ha agregado correctamente");
    }

    @Test
    @DisplayName("Menu: Agregar receta fallida")
    void agregarRecetaFallida() {
        String entrada = "1\nTortilla\nHuevo\nPatata\nfin\nBatir los huevos\nFreir las patatas\nfin\n7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(1, 1, 0);
        interfaz.iniciar();
        assertTrue(salidaCapturada.toString().contains("No se pueden añadir más ingredientes."), "Hay que avisar al usuario de que no se pueden añadir más ingredientes");
        assertTrue(salidaCapturada.toString().contains("No se pudo añadir la receta."), "Hay que avisar al usuario de que la receta no se ha podido agregar");
    }

    @Test
    @DisplayName("Menu: Consultar, seleccionar y editar receta")
    void consultarReceta() {
        String entrada = """
                1
                Tortilla
                Huevo
                Patata
                fin
                Batir los huevos
                Freir las patatas
                fin
                2
                Tortilla
                1
                1
                Cebolla
                2
                Tortilla
                1
                2
                Añadir cebolla
                2
                Tortilla
                1
                3
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
        interfaz.iniciar();
        assertTrue(salidaCapturada.toString().contains("Introduce el texto de la receta a buscar (-FIN- para volver): "), "Hay que solicitar el texto de la receta a buscar");
        assertTrue(salidaCapturada.toString().contains("Recetas encontradas:"), "Hay que mostrar la cabecera Recetas Encontradas:");
        assertTrue(salidaCapturada.toString().contains("1. Tortilla"), "Hay que mostrar la receta encontrada");
        assertTrue(salidaCapturada.toString().contains("1. Añadir ingrediente"), "Hay que mostrar las opciones de edición de la receta");
        assertTrue(salidaCapturada.toString().contains("2. Añadir instrucción"), "Hay que mostrar las opciones de edición de la receta");
        assertTrue(salidaCapturada.toString().contains("3. Eliminar receta"), "Hay que mostrar las opciones de edición de la receta");
        assertTrue(salidaCapturada.toString().contains("4. Volver"), "Hay que mostrar las opciones de edición de la receta");
        assertTrue(salidaCapturada.toString().contains("Introduce el ingrediente a añadir: "), "Hay que solicitar el ingrediente a añadir");
        assertTrue(salidaCapturada.toString().contains("Introduce la instrucción a añadir: "), "Hay que solicitar la instrucción a añadir");
        assertTrue(salidaCapturada.toString().contains("Receta eliminada."), "Hay que avisar al usuario de que la receta se ha eliminado correctamente");
    }

    @Test
    @DisplayName("Menu: Planificar comidas")
    void planificarComidas() {
        String entrada = """
                1
                Tortilla
                Huevo
                Patata
                fin
                Batir los huevos
                Freir las patatas
                fin
                3
                L
                Tort
                1
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
        interfaz.iniciar();
        assertTrue(salidaCapturada.toString().contains("Planificación de comidas para la semana:"), "Hay que mostrar la cabecera de la planificación de comidas");
        assertTrue(salidaCapturada.toString().contains("Introduce el día de la semana (L, M, X, J, V, S, D): "), "Hay que solicitar el día de la semana");
        assertTrue(salidaCapturada.toString().contains("Recetas encontradas:"), "Hay que solicitar la receta a planificar");
        assertTrue(salidaCapturada.toString().contains("Receta planificada para Lunes"), "Hay que avisar al usuario de que la receta se ha planificado correctamente");
    }

    @Test
    @DisplayName("Menu: Guardar recetas")
    void guardarRecetas() {
        try {
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("recetas", ".txt");
            String entrada = "4\n" + tempFile.toString() + "\n7\n";
            ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
            System.setIn(in);
            ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
            PrintStream salidaSimulada = new PrintStream(salidaCapturada);
            System.setOut(salidaSimulada);
            InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
            interfaz.iniciar();
            assertTrue(salidaCapturada.toString().contains("Introduce el nombre del archivo donde guardar las recetas: "), "Hay que solicitar el nombre del archivo donde guardar las recetas");
            assertTrue(salidaCapturada.toString().contains("Recetas guardadas en"), "Hay que avisar al usuario de que las recetas se han guardado correctamente");
            java.nio.file.Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Menu: Cargar recetas")
    void cargarRecetas() {
        String entrada = """
                5
                src/test/resources/recetas_predefinidas.txt
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
        interfaz.iniciar();
        assertTrue(salidaCapturada.toString().contains("Introduce la ruta del archivo de donde cargar las recetas: "), "Hay que solicitar el nombre del archivo donde cargar las recetas");
        assertTrue(salidaCapturada.toString().contains("Recetas cargadas de"), "Hay que avisar al usuario de que las recetas se han cargado correctamente");
    }

    @Test
    @DisplayName("Menu: Guardar plan semanal")
    void guardarPlanSemanal() {
        try {
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("plan_semanal", ".txt");
            String entrada = "6\n" + tempFile.toString() + "\n7\n";
            ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
            System.setIn(in);
            ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
            PrintStream salidaSimulada = new PrintStream(salidaCapturada);
            System.setOut(salidaSimulada);
            InterfazUsuario interfaz = new InterfazUsuario(3, 3, 3);
            interfaz.iniciar();
            assertTrue(salidaCapturada.toString().contains("Introduce el nombre del archivo donde guardar el plan semanal: "), "Hay que solicitar el nombre del archivo donde guardar el plan semanal");
            assertTrue(salidaCapturada.toString().contains("Plan semanal guardado en"), "Hay que avisar al usuario de que el plan semanal se ha guardado correctamente");
            java.nio.file.Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
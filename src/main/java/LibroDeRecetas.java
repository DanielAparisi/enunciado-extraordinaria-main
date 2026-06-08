import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Almacena un conjunto de recetas con capacidad fija.
 * <p>
 * Permite agregar, buscar y eliminar recetas, así como guardar y cargar
 * el catálogo desde un fichero de texto.
 * </p>
 */
public class LibroDeRecetas {

    /** Operación realizada con éxito. */
    public static final int EXITO = 0;
    /** La receta proporcionada es {@code null}. */
    public static final int ERROR_RECETA_NULL = 1;
    /** El libro ya contiene el número máximo de recetas. */
    public static final int ERROR_DEMASIADAS = 2;

    private int maxRecetas;
    private int numRecetas;
    private Receta[] recetas;

    /**
     * Crea un libro de recetas con la capacidad máxima indicada.
     *
     * @param maxRecetasEnLibro número máximo de recetas que puede almacenar el libro
     */
    public LibroDeRecetas(int maxRecetasEnLibro) {
       this.maxRecetas=maxRecetasEnLibro;
       this.recetas= new Receta[maxRecetasEnLibro];
       this.numRecetas= 0;
    }

    /**
     * Añade una receta al libro.
     *
     * @param receta la receta a añadir; no debe ser {@code null}
     * @return {@link #EXITO} si se añadió correctamente,
     *         {@link #ERROR_RECETA_NULL} si la receta es {@code null},
     *         {@link #ERROR_DEMASIADAS} si el libro está lleno
     */
    public int agregarReceta(Receta receta) {
        if (receta == null) {
            return ERROR_RECETA_NULL;
        }
        if (recetasCompletas()) {
            return ERROR_DEMASIADAS;
        }

        this.recetas[numRecetas] = receta;
        this.numRecetas++;
        return EXITO;
    }

    /**
     * Busca recetas cuyo nombre contenga el texto indicado (sin distinción de mayúsculas).
     *
     * @param texto subcadena a buscar en el nombre de las recetas
     * @return array con las recetas encontradas; vacío si no hay coincidencias
     */
    public Receta[] buscarRecetaPorNombre(String texto) {
        int contador= 0;
        for(int i=0; i<numRecetas;i++){
            if(recetas[i].getNombre().toLowerCase().contains(texto.toLowerCase())){  //usamos el toLowercase par ignorar mayuscula y minusculas
                contador++;
            }
        }
        Receta[]resultado= new Receta[contador];

        int indice=0;

        for(int j=0; j<numRecetas;j++){
            if(recetas[j].getNombre().toLowerCase().contains(texto.toLowerCase())){
                resultado[indice]=recetas[j];
                indice++;
            }
        }
        return resultado;
    }

    /**
     * Guarda todas las recetas del libro en un fichero de texto usando el formato compacto
     * de {@link Receta#toRawString()}. Si el fichero ya existe, su contenido se sobreescribe.
     *
     * @param nombreArchivo ruta del fichero donde se guardarán las recetas
     * @throws IOException si ocurre un error al escribir el fichero
     */
    public void guardarRecetasEnArchivo(String nombreArchivo) throws IOException {
        Path path= Path.of(nombreArchivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < numRecetas; i++) {
                writer.write(recetas[i].toRawString());
            }
        }

    }

    /**
     * Carga recetas desde un fichero de texto generado previamente con {@link #guardarRecetasEnArchivo}.
     * Si el fichero contiene más recetas de las que caben en el libro, solo se cargan las primeras.
     *
     * @param nombreArchivo    ruta del fichero del que se cargarán las recetas
     * @param maxIngredientes  número máximo de ingredientes por receta
     * @param maxInstrucciones número máximo de instrucciones por receta
     * @throws IOException si ocurre un error al leer el fichero
     */
    public void cargarRecetasDeArchivo(String nombreArchivo, int maxIngredientes, int maxInstrucciones) throws IOException {
        Path path= Path.of(nombreArchivo);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Receta recetaCargada = Receta.fromBufferedReader(reader, maxIngredientes, maxInstrucciones);

            while (recetaCargada != null && !recetasCompletas()) {
                agregarReceta(recetaCargada);
                recetaCargada = Receta.fromBufferedReader(reader, maxIngredientes, maxInstrucciones);
            }
        }
    }

    /**
     * Indica si el libro ha alcanzado su capacidad máxima de recetas.
     *
     * @return {@code true} si el libro está lleno, {@code false} en caso contrario
     */
    public boolean recetasCompletas() {
        return this.numRecetas>=this.maxRecetas;
    }

    /**
     * Devuelve el número de recetas que contiene el libro actualmente.
     *
     * @return número de recetas almacenadas
     */
    public int numRecetas() {
        return this.numRecetas;
    }

    /**
     * Elimina la receta indicada del libro (comparación por identidad de objeto).
     *
     * @param seleccionada la receta a eliminar
     * @return {@code true} si la receta se encontró y eliminó, {@code false} en caso contrario
     */
    public boolean eliminarReceta(Receta seleccionada) {
        if(seleccionada==null){
            return false;
        }
        for (int i=0; i<numRecetas;i++){
            if(recetas[i]== seleccionada){
                for(int j=i; j<numRecetas-1; j++){
                    recetas[j]=recetas[j+1];
                }
                recetas[numRecetas-1]=null;
                numRecetas--;
                return true;
            }
        }
        return false;
    }
}

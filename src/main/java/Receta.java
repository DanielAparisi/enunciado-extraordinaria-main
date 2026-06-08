import java.io.BufferedReader;
import java.io.IOException;

/**
 * Representa una receta de cocina con nombre, ingredientes e instrucciones.
 * <p>
 * Los métodos de modificación devuelven códigos de resultado que permiten
 * separar la lógica de negocio de la presentación al usuario.
 * </p>
 */
public class Receta {

    /** Operación realizada con éxito. */
    public static final int EXITO = 0;
    /** El parámetro recibido es {@code null} o está vacío. */
    public static final int ERROR_VALOR_INVALIDO = 1;
    /** La receta ya contiene el número máximo de ingredientes. */
    public static final int ERROR_INGREDIENTES_COMPLETOS = 2;
    /** La receta ya contiene el número máximo de instrucciones. */
    public static final int ERROR_INSTRUCCIONES_COMPLETAS = 3;

    /** Cadena que separa los ingredientes de las instrucciones en el formato de fichero. */
    public static final String SEPARADOR_INSTRUCCIONES = "INSTRUCCIONES";
    /** Cadena que marca el final de una receta en el formato de fichero. */
    public static final String SEPARADOR_FIN = "----";

    private String nombre;
    private String[] ingredientes;
    private String[] instrucciones;
    private int numIngredientes;
    private int numInstrucciones;
    private int maxIngredientes;
    private int maxInstrucciones;

    /**
     * Crea una receta con el nombre y los límites de capacidad indicados.
     *
     * @param nombre           nombre de la receta; no debe ser {@code null}
     * @param maxIngredientes  número máximo de ingredientes que puede tener la receta
     * @param maxInstrucciones número máximo de instrucciones que puede tener la receta
     */
    public Receta(String nombre, int maxIngredientes, int maxInstrucciones) {
        this.nombre= nombre;
        this.maxIngredientes= maxIngredientes;
        this.maxInstrucciones= maxInstrucciones;

        this.ingredientes= new String[maxIngredientes];
        this.instrucciones= new String[maxInstrucciones];

        this.numIngredientes= 0;
        this.numInstrucciones= 0;
    }

    /**
     * Devuelve el nombre de la receta.
     *
     * @return nombre de la receta
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve una copia de la lista de ingredientes que contiene la receta actualmente.
     *
     * @return array con los ingredientes añadidos hasta el momento
     */
    public String[] getIngredientes() {
        String[] copiaIngredientes = new String[numIngredientes];
        for (int i = 0; i < numIngredientes; i++) {
            copiaIngredientes[i] = ingredientes[i];
        }
        return copiaIngredientes;
    }

    /**
     * Devuelve una copia de la lista de instrucciones que contiene la receta actualmente.
     *
     * @return array con las instrucciones añadidas hasta el momento
     */
    public String[] getInstrucciones() {
        String[] copiaInstrucciones = new String[numInstrucciones];
        for (int i = 0; i < numInstrucciones; i++) {
            copiaInstrucciones[i] = instrucciones[i];
        }
        return copiaInstrucciones;
    }

    /**
     * Añade un ingrediente a la receta.
     *
     * @param ingrediente texto del ingrediente a añadir; no debe ser {@code null} ni vacío
     * @return {@link #EXITO} si se añadió correctamente,
     *         {@link #ERROR_VALOR_INVALIDO} si el parámetro es {@code null} o vacío,
     *         {@link #ERROR_INGREDIENTES_COMPLETOS} si ya se alcanzó el máximo
     */
    public int agregarIngrediente(String ingrediente) {
        if(ingrediente == null || ingrediente.trim().isEmpty()){
            return ERROR_VALOR_INVALIDO;
        }
        if(ingredientesCompletos()){
            return ERROR_INGREDIENTES_COMPLETOS;
        }
        ingredientes[numIngredientes]= ingrediente;
        numIngredientes++;
        return EXITO;
    }

    /**
     * Añade una instrucción a la receta.
     *
     * @param instruccion texto de la instrucción a añadir; no debe ser {@code null} ni vacío
     * @return {@link #EXITO} si se añadió correctamente,
     *         {@link #ERROR_VALOR_INVALIDO} si el parámetro es {@code null} o vacío,
     *         {@link #ERROR_INSTRUCCIONES_COMPLETAS} si ya se alcanzó el máximo
     */
    public int agregarInstruccion(String instruccion) {
        if(instruccion==null || instruccion.trim().isEmpty()){
            return ERROR_VALOR_INVALIDO;
        }
        if(instruccionesCompletas()){
            return ERROR_INSTRUCCIONES_COMPLETAS;
        }

        instrucciones[numInstrucciones]= instruccion;
        numInstrucciones++;
        return EXITO;
    }

    /**
     * Indica si la receta ha alcanzado su límite de ingredientes.
     *
     * @return {@code true} si no se pueden añadir más ingredientes, {@code false} en caso contrario
     */
    public boolean ingredientesCompletos() {
        return numIngredientes() >= maxIngredientes;
    }

    /**
     * Indica si la receta ha alcanzado su límite de instrucciones.
     *
     * @return {@code true} si no se pueden añadir más instrucciones, {@code false} en caso contrario
     */
    public boolean instruccionesCompletas() {
        return numInstrucciones() >= maxInstrucciones;
    }

    /**
     * Devuelve el número de ingredientes que tiene la receta actualmente.
     *
     * @return número de ingredientes añadidos
     */
    public int numIngredientes() {
        return numIngredientes;
    }

    /**
     * Devuelve el número de instrucciones que tiene la receta actualmente.
     *
     * @return número de instrucciones añadidas
     */
    public int numInstrucciones() {
        return numInstrucciones;
    }

    /**
     * Devuelve la representación textual completa de la receta, formateada para mostrar al usuario.
     * <p>Formato:</p>
     * <pre>
     * Receta: &lt;nombre&gt;
     * Ingredientes:
     * - Ingrediente 1
     * Instrucciones:
     * 1. Instrucción 1
     * </pre>
     *
     * @return cadena de texto con la información de la receta
     */
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("Receta: ").append(this.nombre).append("\n");
        sb.append("Ingredientes:").append("\n");
        for(int i=0; i<numIngredientes;i++){
            sb.append("- ").append(ingredientes[i]).append("\n");
        }
        sb.append("Instrucciones:").append("\n");
        for(int j=0; j<numInstrucciones;j++){
            sb.append(j+1+". ").append(instrucciones[j]).append("\n");
        }
        return sb.toString();
    }

    /**
     * Devuelve la representación compacta de la receta para su almacenamiento en fichero.
     * <p>Formato:</p>
     * <pre>
     * &lt;nombre&gt;
     * Ingrediente 1
     * INSTRUCCIONES
     * Instrucción 1
     * -----
     * </pre>
     *
     * @return cadena de texto en formato de fichero
     */
    public String toRawString() {
        StringBuilder sb= new StringBuilder();
        sb.append(this.nombre).append("\n");
        for(int i=0; i<numIngredientes;i++){
            sb.append(ingredientes[i]).append("\n");
        }
        sb.append("INSTRUCCIONES").append("\n");
        for(int j=0; j<numInstrucciones;j++){
            sb.append(instrucciones[j]).append("\n");
        }
        sb.append("-----").append("\n");
        return sb.toString();
    }

    /**
     * Devuelve el número máximo de ingredientes que puede contener la receta.
     *
     * @return capacidad máxima de ingredientes
     */
    public int getMaxIngredientes() {
        return ingredientes.length;
    }

    /**
     * Devuelve el número máximo de instrucciones que puede contener la receta.
     *
     * @return capacidad máxima de instrucciones
     */
    public int getMaxInstrucciones() {
        return instrucciones.length;
    }

    /**
     * Lee una receta desde un {@link BufferedReader} abierto en el formato generado por {@link #toRawString()}.
     * <p>
     * Si el lector está al final del fichero, devuelve {@code null}. Si el fichero contiene
     * más ingredientes o instrucciones de los permitidos, la información sobrante se descarta.
     * </p>
     *
     * @param reader           lector ya abierto y posicionado al inicio de la receta
     * @param maxIngredientes  número máximo de ingredientes a leer
     * @param maxInstrucciones número máximo de instrucciones a leer
     * @return la {@link Receta} leída, o {@code null} si no hay más datos
     * @throws IOException si ocurre un error de lectura
     */
    public static Receta fromBufferedReader(BufferedReader reader, int maxIngredientes, int maxInstrucciones) throws IOException {
        String nombre= reader.readLine();
        if(nombre==null){
            return null;
        }

        Receta nuevaReceta= new Receta(nombre, maxIngredientes, maxInstrucciones);

        String linea= reader.readLine();
        while(linea!= null && !linea.equals("INSTRUCCIONES")){
            nuevaReceta.agregarIngrediente(linea);
            linea=reader.readLine();
        }

        linea= reader.readLine();
        while(linea!= null && !linea.equals("-----")){
            nuevaReceta.agregarInstruccion(linea);
            linea= reader.readLine();
        }
        return nuevaReceta;
    }
}
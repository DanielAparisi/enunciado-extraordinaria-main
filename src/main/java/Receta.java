import java.io.BufferedReader;
import java.io.IOException;

public class Receta {

    // Estados de operación
    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_INGREDIENTES_COMPLETOS = 2;
    public static final int ERROR_INSTRUCCIONES_COMPLETAS = 3;

    public static final String SEPARADOR_INSTRUCCIONES = "INSTRUCCIONES";
    public static final String SEPARADOR_FIN = "-----";

    // @todo: atributos privados

    private String nombre;
    private String [] ingredientes;
    private String [] instrucciones;
    private int numIngredientes;
    private int numInstrucciones;
    private int maxIngredientes;
    private int maxInstrucciones;

    public Receta(String nombre, int maxIngredientes, int maxInstrucciones) {
        this.nombre= nombre;
        this.maxIngredientes= maxIngredientes;
        this.maxInstrucciones= maxInstrucciones;

        this.ingredientes= new String[maxIngredientes];
        this.instrucciones= new String[maxInstrucciones];

        this.numIngredientes= 0;
        this.numInstrucciones= 0;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String[] getIngredientes() {
        String [] copiaIngredientes= new String[numIngredientes];
        for( int i= 0; i< numIngredientes; i++){
            copiaIngredientes[i]= ingredientes[i];
        }
        return copiaIngredientes;
    }

    public String[] getInstrucciones() {
        String [ ] copiaInstrucciones= new String[numInstrucciones];
        for ( int i = 0; i<maxInstrucciones; i++){
            copiaInstrucciones[i]= instrucciones[i];
        }
        return copiaInstrucciones;
    }

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

    public boolean ingredientesCompletos() {
        if(numIngredientes()>=maxIngredientes){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean instruccionesCompletas() {
        if(numInstrucciones()>=maxInstrucciones){
            return true;
        }
        else{
            return false;
        }
    }

    public int numIngredientes() {
        return numIngredientes;
    }

    public int numInstrucciones() {
        return numInstrucciones;
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("Receta: ").append(this.nombre).append("\n");
        sb.append("Ingredientes: ").append("\n");
        for(int i=0; i<numIngredientes;i++){
            sb.append("- ").append(ingredientes[i]).append("\n");
        }
        sb.append("Instrucciones: ").append("\n");
        for(int j=0; j<numInstrucciones;j++){
            sb.append(j+1+". ").append(instrucciones[j]).append("\n");
        }
        return sb.toString();
    }

    public String toRawString() {
        StringBuilder sb= new StringBuilder();
        sb.append(this.nombre).append("\n");
        for(int i=0; i<numIngredientes;i++){
            sb.append(ingredientes[i]).append("\n");
        }
        sb.append("INSTRUCCIONES: ").append("\n");
        for(int j=0; j<numInstrucciones;j++){
            sb.append(instrucciones[j]).append("\n");
        }
        sb.append("----").append("\n");
        return sb.toString();
    }

    public int getMaxIngredientes() {
        return ingredientes.length;
    }

    public int getMaxInstrucciones() {
        return instrucciones.length;
    }

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
        while(linea!= null && !linea.equals("----")){
            nuevaReceta.agregarInstruccion(linea);
            linea= reader.readLine();
        }
        return nuevaReceta;
    }
}

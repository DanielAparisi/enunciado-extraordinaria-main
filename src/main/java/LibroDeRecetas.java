import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibroDeRecetas {

    public static final int EXITO = 0;
    public static final int ERROR_RECETA_NULL = 1;
    public static final int ERROR_DEMASIADAS = 2;

    // @todo: atributos privados
    private int maxRecetas;
    private int numRecetas;
    private Receta[]recetas;

    public LibroDeRecetas(int maxRecetasEnLibro) {
       this.maxRecetas=maxRecetasEnLibro;
       this.recetas= new Receta[maxRecetasEnLibro];
       this.numRecetas= 0;
    }

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

    public void guardarRecetasEnArchivo(String nombreArchivo) throws IOException {
        Path path= Path.of(nombreArchivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < numRecetas; i++) {
                writer.write(recetas[i].toRawString());
            }
        }

    }

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

    public boolean recetasCompletas() {
        return this.numRecetas>=this.maxRecetas;
    }

    public int numRecetas() {
        return this.numRecetas;
    }

    public boolean eliminarReceta(Receta seleccionada) {
        if(seleccionada==null){
            return false;
        }
        for (int i=0; i<numRecetas;i++){
            if(recetas[i]== seleccionada){
                for(int j=i; j<numRecetas; j++){
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

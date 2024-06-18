package Plato;

import Interfaces.IABM;
import Plato.Excepciones.ExcepFileNF;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Variedad.Variedad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class PlatoRepositorio implements IABM<Plato> {

    private Gson gson = new Gson();
    private static final String FILE_COMIDAS = "src/main/java/Plato/File/platos.json";
    private Set<Plato> platoSet;

    //Constructor del Repositorio de Plato
    public PlatoRepositorio() {
        this.platoSet = new HashSet<>();
        loadFilePlatos();
    }

    //Deserializacion del archivo de Platos a la Coleccion HashSet
    private void loadFilePlatos()  {
        try(Reader reader = new FileReader(FILE_COMIDAS)){
            Type setType = new TypeToken<Set<Plato>>(){}.getType();
            platoSet = gson.fromJson(reader,setType);
            if (platoSet ==null) platoSet = new HashSet<>();
        }catch (FileNotFoundException fnf){
            try {
                throw new ExcepFileNF("El archivo " + FILE_COMIDAS + " no fue encontrado.");
            } catch (ExcepFileNF ex) {
                ex.printStackTrace();
                platoSet = new HashSet<>();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    //Serializacion del HashSet al archivo
    private void saveFilePlatos(){
        try(Writer writer = new FileWriter(FILE_COMIDAS)){
            gson.toJson(platoSet,writer);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //Agregar plato al archivo y HashSet
    @Override
    public void agregar(Plato comida) {
        this.platoSet.add(comida);
        saveFilePlatos();
    }

    //Eliminar plato del archivo y HashSet
    @Override
    public void eliminar(Plato comida) {
        this.platoSet.remove(comida);
        saveFilePlatos();
    }
    //Modificar y actualizar plato en el archivo y HashSet
    @Override
    public void modificar(Plato comida) {
        for(Plato plato : platoSet){
            if(comida.getNombre().equals(plato.getNombre())){
                plato.setPrecio(comida.getPrecio());
                plato.getVariedades().clear();
                plato.setVariedades(comida.getVariedades());
            }
        }
        saveFilePlatos();
    }

    public Plato buscarPlatoXnombre(String nombre) {
        for (Plato plato : platoSet) {
            if (plato.getNombre().equals(nombre)) {
                return plato;
            }
        }
        return null;
    }

    public List<Plato> enlistarXTipo(String tipo) {
        List<Plato> listaAuxiliar = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                if (plato.getVariedades().isEmpty()) {
                    listaAuxiliar.add(plato);
                } else {
                    for (Variedad variedad : plato.getVariedades()) {
                        String nombreCompuesto = String.format(plato.getNombre() + " " + variedad.getNombre());
                        Plato platoConVariedad = new Plato(tipo, nombreCompuesto, variedad.getPrecio());
                        listaAuxiliar.add(platoConVariedad);
                    }
                }
            }
        }
        return listaAuxiliar;
    }

    public void mostrarEnlistadosBonito(String tipo){
        List<String> nombre = new ArrayList<>();
        List<Double> precio = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                if (plato.getVariedades().isEmpty()) {
                    nombre.add(plato.getNombre());
                    precio.add(plato.getPrecio());
                } else {
                    for (Variedad variedad : plato.getVariedades()) {
                        String nombreCompuesto = String.format(plato.getNombre() + " " + variedad.getNombre());
                        nombre.add(nombreCompuesto);
                        precio.add(variedad.getPrecio());
                    }
                }
            }
        }

        int largoMaximoDelNombreDelPlato = 0;
        for (String plato : nombre) {
            if (plato.length() > largoMaximoDelNombreDelPlato) {
                largoMaximoDelNombreDelPlato = plato.length();
            }
        }
        int totalIndices = String.valueOf(nombre.size()).length();

        // Imprimir el menú formateado
        System.out.println("Menu de " + tipo);
        System.out.println("========================================");
        for (int i = 0; i < nombre.size(); i++) {
            System.out.printf("% " + totalIndices + "d. %-" + largoMaximoDelNombreDelPlato + "s  $%.2f%n", i+1, nombre.get(i), precio.get(i));
        }
    }






    //Si el plato contiene variedades imprime las variedades, sino imprime los platos base con sus valores
    public void mostrarPlato(Plato plato){
        if(plato.getVariedades().isEmpty()){
            System.out.print(plato.toString());
        }else {
            System.out.println("-"+plato.getNombre());
            for(Variedad variedad : plato.getVariedades()) {
                System.out.println(variedad.toString());
            }
        }
    }

    public void mostrarPlatosXtipo(String tipo){
        for (Plato plato : platoSet){
            if (plato.getTipo().equals(tipo)){
                mostrarPlato(plato);
            }
        }
    }



    public void aumentoPorcentualPrecio(double aumento){
        aumento = aumento * 0.01;
        for (Plato plato : this.platoSet) {
            if (plato.getVariedades().size() == 0) {
                // Si el plato no tiene variedades, aumentar el precio del plato mismo
                double valorAnterior = plato.getPrecio();
                double nuevoValor = valorAnterior + (valorAnterior * aumento);
                plato.setPrecio(nuevoValor);
            }else {
                // Aumentar el precio de las variedades, si existen
                for (Variedad variedad : plato.getVariedades()) {
                    double valorAnterior = variedad.getPrecio();
                    double nuevoValor = valorAnterior + (valorAnterior * aumento);
                    variedad.setPrecio(nuevoValor);
                }
            }
        }
        saveFilePlatos();
    }




}

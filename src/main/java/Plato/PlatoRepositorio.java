package Plato;

import Interfaces.IABM;
import Plato.Excepciones.ExcepFileNF;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Variedad.Variedad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlatoRepositorio implements IABM<Plato> {

    private Gson gson = new Gson();
    private static final String FILE_COMIDAS = "src/main/resources/Platos.json";
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

    //Recibe un string con el nombre del plato a buscar. Si lo encuentra devuelve el plato, caso contrario devuelve null
    public Plato buscarPlatoXnombre(String nombre) throws ExcepIngresoInvalido {
        for (Plato plato : platoSet) {
            if (plato.getNombre().equals(nombre)) {
                return plato;
            }
        }
        return null;
    }

    //recibe un String con el tipo de categoria y carga una lista recorriendo el set la cual devuelve
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

    //crea una lista con el tipo ingresado pero no considera las variedades de cada plato
    public List<Plato> enlistarXTipoSinVariedad(String tipo) {
        List<Plato> listaAuxiliar = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                listaAuxiliar.add(plato);
            }
        }
        return listaAuxiliar;
    }

    // Recibe un tipo por parametro y crea dos listas, una con nombre y otra con precio. Complementa el nombre del plato que contiene variedad
    // Luego busca el nombre mas largo y lo toma de referencia para acomodar el menu con un largo estandarizado.
    // Utilizado en la seleccion de plato para orden y eliminacion por seleccion
    public void mostrarEnlistadoBonitoXtipoOld(String tipo){
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
        // busca el plato con el nombre mas largo
        int largoMaximoDelNombreDelPlato = 0;
        for (String plato : nombre) {
            if (plato.length() > largoMaximoDelNombreDelPlato) {
                largoMaximoDelNombreDelPlato = plato.length();
            }
        }
        // crea una variable de tipo int que contiene la cantidad total de indices
        int totalIndices = String.valueOf(nombre.size()).length();

        // Imprimir el menú formateado
        System.out.println("Menu de " + tipo);
        System.out.println("========================================");
        for (int i = 0; i < nombre.size(); i++) {
            System.out.printf("% " + totalIndices + "d. %-" + largoMaximoDelNombreDelPlato + "s  $%.2f%n", i+1, nombre.get(i), precio.get(i));
        }
    }

    //metodo para mostrar la carta completa accediendo desde el set. metodologia similar a la utilizada en el mostrarEnlistadoBonitoXtipoOld
    // pero se modularizó las secciones del método mensionado. Se calcula por separado el nombre mas largo del total de nombres en el set
    // y el total de indices. en diferentes metodos. Por ultimo mostrarEnlistadoBonitoXtipoNew imprime el total del Set.

    public void mostrarCartaDePlatosCompleta() {
        int largoMaximoDelNombre = calcularLargoMaximoDelNombre();
        int largoMaximoDelIndice = calcularLargoMaximoDelIndice();
        Set<String> tiposYaMostrados = new HashSet<>();
        for (Plato plato : this.platoSet) {
            String tipoActual = plato.getTipo();
            if (!tiposYaMostrados.contains(tipoActual)) {
                tiposYaMostrados.add(tipoActual);
                mostrarEnlistadoBonitoXtipoNew(tipoActual, largoMaximoDelNombre, largoMaximoDelIndice);
            }
        }
    }

    public int calcularLargoMaximoDelNombre() {
        int largoMaximoDelNombre = 0;
        for (Plato plato : platoSet) {
            if (plato.getVariedades().isEmpty()) {
                if (plato.getNombre().length() > largoMaximoDelNombre) {
                    largoMaximoDelNombre = plato.getNombre().length();
                }
            } else {
                for (Variedad variedad : plato.getVariedades()) {
                    String nombreCompuesto = plato.getNombre() + " " + variedad.getNombre();
                    if (nombreCompuesto.length() > largoMaximoDelNombre) {
                        largoMaximoDelNombre = nombreCompuesto.length();
                    }
                }
            }
        }
        return largoMaximoDelNombre;
    }
    public int calcularLargoMaximoDelIndice() {
        int totalPlatos = 0;
        for (Plato plato : platoSet) {
            if (plato.getVariedades().isEmpty()) {
                totalPlatos++;
            } else {
                totalPlatos += plato.getVariedades().size();
            }
        }
        return String.valueOf(totalPlatos).length();
    }

    public void mostrarEnlistadoBonitoXtipoNew(String tipo, int largoMaximoDelNombre, int largoMaximoDelIndice) {
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

        // Imprimir el menú formateado
        System.out.println("Menu de " + tipo);
        System.out.println("====================================================");
        for (int i = 0; i < nombre.size(); i++) {
            System.out.printf("% " + largoMaximoDelIndice + "d. %-" + largoMaximoDelNombre + "s  $%.2f%n", i + 1, nombre.get(i), precio.get(i));
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

    // Se ingresa un valor para aumentar el precio total de los productos de manera porcentual
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

package Orden;

import Excepciones.ExcepcionOrdenNoEncontrada;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class OrdenRepositorio {
    private TreeMap<Integer, Orden> ordenes = new TreeMap<>();


    private static final String PATH_Ordenes = "src/main/resources/Ordenes.json";
    private Gson gson = new Gson();


    public void guardarOrden(Orden orden) {
        ordenes.put(orden.getId(), orden);
    }

    public Orden obtenerOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        if(ordenes.get(id)== null) {
            throw new ExcepcionOrdenNoEncontrada("Orden no encontrada");
        }
        return ordenes.get(id);
    }

    public void eliminarOrden(Integer id) {
        ordenes.remove(id);
    }

    public void actualizarOrden(Orden orden) {
        ordenes.put(orden.getId(), orden);
    }

    public void guardarOrdenesEnArchivo() {
        try (FileWriter writer = new FileWriter(PATH_Ordenes)) {
            gson.toJson(ordenes, writer);
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void cargarOrdenesDesdeArchivo() {
        try (FileReader reader = new FileReader(PATH_Ordenes)) {
            Type type = new TypeToken<Map<Integer, Orden>>() {}.getType();
            ordenes = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public TreeMap<Integer, Orden> obtenerMap(){
        return ordenes;
    }

}

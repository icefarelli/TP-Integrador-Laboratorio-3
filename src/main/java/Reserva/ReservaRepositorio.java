package Reserva;

import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Interfaces.IABM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;


public class ReservaRepositorio implements IABM<Reserva> {
    List<Reserva> reservas = new LinkedList<Reserva>();

    private static final String PATH_Reservas = "src/main/resources/Reservas.json";
    private Gson gson = new Gson();

    @Override
    public void agregar(Reserva obj) {
        reservas.add(obj);
    }

    @Override
    public void eliminar(Reserva obj) {
        reservas.remove(obj);
    }

    @Override
    public void modificar(Reserva obj) {
        reservas.add(obj);
    }

    public Reserva buscarReserva(Integer id) throws ExcepcionReservaNoEncontrada {
        Reserva reserva = reservas.get(id);
        if (reserva == null) {
            throw new ExcepcionReservaNoEncontrada("Reserva no encontrada.");
        }
        return reserva;
    }

    public void guardarReserva(){
        try (FileWriter writer = new FileWriter(PATH_Reservas)){
            gson.toJson(reservas, writer);
        }catch (IOException e){
            e.getMessage();
        }
    }

    public void cargarReserva(){
        try (FileReader reader = new FileReader(PATH_Reservas)){
            Type type = new TypeToken<List<Reserva>>() {}.getType();
            reservas = gson.fromJson(reader, type);
        }catch (IOException e){
            e.getMessage();
        }
    }

    public List<Reserva> todasLasReservas() {
        return reservas;
    }
}

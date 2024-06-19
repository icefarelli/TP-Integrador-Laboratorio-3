package Reserva;

import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Interfaces.IABM;
import MesasReservadas.MesasReservadas;
import MesasReservadas.MesasReservadasRepositorio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ReservaRepositorio implements IABM<Reserva> {
    List<Reserva> reservas = new LinkedList<Reserva>();
    MesasReservadasRepositorio mesasReservadasRepositorio = new MesasReservadasRepositorio();

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
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getFecha().equals(obj.getFecha())) {
                reservas.set(i, obj);
                break;
            }
        }
    }

    public Reserva buscarFecha(LocalDateTime fecha) throws ExcepcionReservaNoEncontrada {
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().equals(fecha)) {
                return reserva;
            }else {
              return null;
            }
        }
        throw new ExcepcionReservaNoEncontrada("Reserva no encontrada para la fecha: " + fecha);
    }

    public Reserva buscarReserva(LocalDateTime fecha) throws ExcepcionReservaNoEncontrada {
        Reserva reserva = null;
        for (Reserva reserva1 : reservas) {
            if (reserva.getFecha().equals(fecha)) {
                return reserva1;
            } else {
                return reserva;
            }
            throw new ExcepcionReservaNoEncontrada("Reserva no encontrada para la fecha: " + fecha);
        }
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
            List<Reserva> reservas = gson.fromJson(reader, type);
            for (Reserva reserva: this.reservas){
                for(MesasReservadas mesasReservadas: mesasReservadasRepositorio.getMesasReservadas()){
                    mesasReservadasRepositorio.agregar(mesasReservadas);
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
    }

    public List<Reserva> todasLasReservas() {
        return reservas;
    }

    public List<MesasReservadas> todasLasMesasReservadas(){return mesasReservadasRepositorio.todasLasMesasReservadas();}
}

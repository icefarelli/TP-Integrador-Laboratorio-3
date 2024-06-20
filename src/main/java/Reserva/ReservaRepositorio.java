package Reserva;


import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Interfaces.IABM;
import MesasReservadas.MesasReservadas;
import MesasReservadas.MesasReservadasRepositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
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


    public ReservaRepositorio() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gson = gsonBuilder.create();
    }


    public LocalDate buscarFecha(LocalDate fecha) throws ExcepcionReservaNoEncontrada {
        try {
            for (Reserva reserva : reservas) {
                if (reserva.getFecha().equals(fecha)) {
                    return reserva.getFecha();
                }else {
                    throw new ExcepcionReservaNoEncontrada("Reserva no encontrada para la fecha: " + fecha);
                }
            }
        }catch (ExcepcionReservaNoEncontrada e){
            System.out.println("Error al buscar la fecha");
        }
        return null;
    }


    public Reserva buscarReserva(LocalDate fecha) throws ExcepcionReservaNoEncontrada {
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().equals(fecha)) {
                return reserva;
            }
        }
        throw new ExcepcionReservaNoEncontrada("Reserva no encontrada para la fecha: " + fecha);
    }



    public void guardarReserva() {
        try (FileWriter writer = new FileWriter(PATH_Reservas)) {
            gson.toJson(reservas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarReserva() {
        try (FileReader reader = new FileReader(PATH_Reservas)) {
            Type type = new TypeToken<List<Reserva>>() {}.getType();
            List<Reserva> reservaList = gson.fromJson(reader, type);
            if (reservaList != null) {
                reservas = reservaList;
            } else {
                reservas = new LinkedList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public List<Reserva> todasLasReservas() {
        return reservas;
    }


    public List<MesasReservadas> todasLasMesasReservadas(){return mesasReservadasRepositorio.todasLasMesasReservadas();}
}

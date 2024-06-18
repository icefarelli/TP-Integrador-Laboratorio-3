package Reserva;

import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Excepciones.Reservas.ExcepcionReserva;

import java.util.List;

public class ReservaControlador {
    private ReservaRepositorio reservaRepositorio;
    private ReservaVista reservaVista;
    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;

    public ReservaControlador(ReservaRepositorio reservaRepositorio, ReservaVista reservaVista, ClienteVista clienteVista, ClienteRepositorio clienteRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
        this.reservaVista = reservaVista;
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
    }

    /*public void agregarReserva(){
        Integer id = clienteVista.buscarCliente();
        Cliente clienteExistente = clienteRepositorio.buscarId(id);
        Reserva reserva;
        if(clienteExistente != null){
            reserva = reservaVista.crearReserva();
            reservaRepositorio.agregar(reserva);
            reserva.setCliente(clienteExistente);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva creada con éxito!");
            reservaVista.mostrarReserva(reserva);
        }else {
        reservaVista.mensaje("El cliente no se encuentra registrado.");
        }
    }*/


    public void eliminarReserva() throws ExcepcionReserva {
        Integer id = reservaVista.buscarIdReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(id);
        if(reserva != null) {
            reservaRepositorio.eliminar(reserva);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva eliminada con éxito!");
        }else {
            reservaVista.mensaje("Error al eliminar la reserva");
        }

    }

    public void modificarReserva() throws ExcepcionReserva {
        Integer id = reservaVista.buscarIdReserva();
        Reserva reservaExistente = reservaRepositorio.buscarReserva(id);

        if (reservaExistente != null) {
            Reserva nuevaReserva = reservaVista.modificarReserva(reservaExistente);
            reservaRepositorio.modificar(nuevaReserva);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva modificada con éxito!");
            reservaVista.mostrarReserva(nuevaReserva);
        } else {
            reservaVista.mensaje("Error al modificar la reserva");
        }
    }


    public void todasLasReservas() {
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();
        for(Reserva reserva: reservas){
            reservaVista.mostrarReserva(reserva);
        }
    }
}
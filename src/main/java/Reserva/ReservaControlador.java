package Reserva;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.model.entitie.Cliente;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;

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

    public void agregarReserva() throws ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios, ExcepcionReservaValorNegativo, ExcepcionClienteNoEncontrado {
        Integer id = clienteVista.seleccId();
        Cliente clienteExistente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
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
    }

    public void eliminarReserva() throws ExcepcionReservaNoEncontrada, ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
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

    public void modificarReserva() throws ExcepcionReservaNoEncontrada, ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
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

    public void mostrarTodasLasReservas() {
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();
        for(Reserva reserva: reservas){
            reservaVista.mostrarReserva(reserva);
        }
    }
}
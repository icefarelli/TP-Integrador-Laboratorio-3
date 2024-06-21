package Reserva;


import Cliente.ClienteControlador;
import Cliente.Cliente;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Excepciones.*;
import MesasReservadas.MesasReservadas;
import MesasReservadas.MesasReservadasRepositorio;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaControlador {
    private ReservaRepositorio reservaRepositorio;
    private ReservaVista reservaVista;
    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;
    private MesasReservadasRepositorio mesasReservadasRepositorio;
    private ClienteControlador clienteControlador;

    public ReservaControlador(ReservaRepositorio reservaRepositorio, ReservaVista reservaVista, ClienteVista clienteVista, ClienteRepositorio clienteRepositorio, MesasReservadasRepositorio mesasReservadasRepositorio, ClienteControlador clienteControlador) {
        this.reservaRepositorio = reservaRepositorio;
        this.reservaVista = reservaVista;
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
        this.mesasReservadasRepositorio = mesasReservadasRepositorio;
        this.clienteControlador = clienteControlador;
    }


    public void agregarReserva() throws ExcepcionClienteNoEncontrado {
        clienteControlador.loadGestionCliente();
        reservaRepositorio.cargarReserva();
        Reserva reserva = reservaVista.pedirFecha();
        LocalDate fecha = reservaRepositorio.buscarFecha(reserva.getFecha());
        Integer cantPersonas = reservaVista.pedirCantidadPersonas();
        Integer id = clienteVista.seleccId();
        Cliente cliente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
        if (cliente == null) {
            clienteRepositorio.addCliente(cliente);
        }
        List<MesasReservadas> mesasReservadas = new ArrayList<>();
        mesasReservadas.add(new MesasReservadas(cliente, cantPersonas));
        Reserva reservaCompleta = new Reserva(reserva.getFecha(), mesasReservadas);
        reservaRepositorio.agregar(reservaCompleta);
        reservaRepositorio.guardarReserva();
    }

    public void eliminarReserva(){
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);
        Integer id = clienteVista.consultarCliente();
        Integer indice = mesasReservadasRepositorio.buscarIndice(id);
        MesasReservadas mesasReservadas = mesasReservadasRepositorio.buscarMesaReservadaPorIndice(indice);

        if (reserva == null) {
            reservaVista.mensaje("Esa reserva no existe.");
        } else {
            mesasReservadasRepositorio.eliminar(mesasReservadas);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva eliminada con éxito!");
        }
    }

    public void modificarReserva() {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);
        Integer id = clienteVista.consultarCliente();
        Integer indice = mesasReservadasRepositorio.buscarIndice(id);
        MesasReservadas mesasReservadas = mesasReservadasRepositorio.buscarMesaReservadaPorIndice(indice);
        if (reserva == null) {
            reservaVista.mensaje("Esa reserva no existe.");
        } else {
            String opcion = reservaVista.preguntarQueModificar();
            if (opcion.equalsIgnoreCase("fecha")) {
                LocalDate nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                while (nuevaFecha.isEqual(fecha)) {
                    reservaVista.mensaje("La fecha ingresada es la misma. Ingrese nuevamente.");
                    nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                }
                reservaRepositorio.modificar(reserva);
                reservaRepositorio.guardarReserva();
                reservaVista.mensaje("Reserva modificada con éxito!");
            } else if (opcion.equalsIgnoreCase("cantPersonas")) {
                Integer cantPersonas = reservaVista.modificarCantPersonas();
                mesasReservadas.setCantPersonas(cantPersonas);
                reservaRepositorio.guardarReserva();
                reservaVista.mensaje("Cantidad de personas modificada con éxito!");
            }
        }
    }

    public void mostrarReservasConArreglo() {
        reservaRepositorio.cargarReserva();
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();
        List<MesasReservadas> mesasReservadas = reservaRepositorio.todasLasMesasReservadas();
        reservaVista.mostrarReservaConArreglo(reservas, mesasReservadas);
    }
}


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
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class ReservaControlador {
    private ReservaRepositorio reservaRepositorio;
    private ReservaVista reservaVista;
    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;
    private MesasReservadasRepositorio mesasReservadasRepositorio;
    private ClienteControlador clienteControlador;

    public ReservaControlador(ReservaRepositorio reservaRepositorio, ReservaVista reservaVista,
                              ClienteVista clienteVista, ClienteRepositorio clienteRepositorio,
                              MesasReservadasRepositorio mesasReservadasRepositorio, ClienteControlador clienteControlador) {
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
        LocalDate fecha = reserva.getFecha();

        Reserva reservaExistente = reservaRepositorio.buscarReserva(fecha);
        Integer cantPersonas = reservaVista.pedirCantidadPersonas();

        Cliente cliente = null;
        do {
            clienteVista.verTodosClientesVersionCorta(clienteRepositorio.getClienteSet());

            Integer id = clienteVista.seleccId();
            try {
                cliente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
            } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                System.out.println(excepcionClienteNoEncontrado.getMessage());
                System.out.println("Quiere agregar el cliente? s/n");
                Scanner scan = new Scanner(System.in);
                String agregar = scan.nextLine();
                if (agregar.equalsIgnoreCase("s")) {
                    clienteControlador.agregarClientes();
                    clienteControlador.Update();
                }
            }
        }while(cliente==null);

        MesasReservadas nuevaMesaReservada = new MesasReservadas(cliente, cantPersonas);
        if (reservaExistente == null) {
            List<MesasReservadas> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(nuevaMesaReservada);
            Reserva nuevaReserva = new Reserva(fecha, mesasReservadas);
            reservaRepositorio.agregar(nuevaReserva);
            System.out.println("Reserva agregada con éxito!");
        } else if(mesasReservadasRepositorio.buscarIndice(reservaExistente,cliente.getIdCliente())) {
            System.out.println("El cliente ya tiene una mesa reservada");
            return;
        } else{
            reservaExistente.getMesasReservadas().add(nuevaMesaReservada);
            System.out.println("Reserva agregada con éxito!");
        }

        reservaRepositorio.guardarReserva();

    }

    public void eliminarMesaReserva() {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);

        if (reserva == null) {
            reservaVista.mensaje("No hay reservas para esa fecha.");
            return;
        }

        clienteControlador.viewClientes();
        Integer id = clienteVista.seleccId();
        MesasReservadas mesasReservadasAEliminar = mesasReservadasRepositorio.buscarMesasReservadas(reserva.getMesasReservadas(), id);

        if (mesasReservadasAEliminar == null) {
            reservaVista.mensaje("Esa mesa reserva no está reservada.");
        } else {
            reserva.getMesasReservadas().remove(mesasReservadasAEliminar);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva eliminada con éxito!");
        }
    }

    public void modificarReserva() {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);

        if (reserva == null) {
            reservaVista.mensaje("No hay reservas para esa fecha.");
            return;
        }

        clienteControlador.viewClientes();
        Integer id = clienteVista.seleccId();
        MesasReservadas mesasReservadasAModificar = mesasReservadasRepositorio.buscarMesasReservadas(reserva.getMesasReservadas(), id);
        if (mesasReservadasAModificar == null) {
            reservaVista.mensaje("Esa mesa reserva no existe.");
        } else {
            String opcion = reservaVista.preguntarQueModificar();

            if (opcion.equalsIgnoreCase("fecha")) {
                LocalDate nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                while (nuevaFecha.isEqual(fecha)) {
                    reservaVista.mensaje("La fecha ingresada es la misma. Ingrese nuevamente.");
                    nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                }

                reserva.getMesasReservadas().remove(mesasReservadasAModificar);

                if (reserva.getMesasReservadas().isEmpty()) {
                    reservaRepositorio.eliminar(reserva);
                }

                Reserva nuevaReserva = reservaRepositorio.buscarReserva(nuevaFecha);
                if (nuevaReserva == null) {
                    nuevaReserva = new Reserva(nuevaFecha, new ArrayList<>());
                    reservaRepositorio.agregar(nuevaReserva);
                }

                nuevaReserva.getMesasReservadas().add(mesasReservadasAModificar);
                reservaRepositorio.guardarReserva();
                reservaVista.mensaje("Fecha de la reserva modificada con éxito!");

            } else if (opcion.equalsIgnoreCase("cantPersonas")) {
                Integer cantPersonas = reservaVista.modificarCantPersonas();
                mesasReservadasAModificar.setCantPersonas(cantPersonas);
                reservaRepositorio.guardarReserva();
                reservaVista.mensaje("Cantidad de personas modificada con éxito!");
            }
        }
    }

    public void mostrarReservasConArreglo() {
        reservaRepositorio.cargarReserva();
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();

        // Filtrar reservas que tienen mesas reservadas
        List<Reserva> reservasConMesas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (!reserva.getMesasReservadas().isEmpty()) {
                reservasConMesas.add(reserva);
            }
        }

        Collections.sort(reservasConMesas, (reserva1, reserva2) -> reserva1.getFecha().compareTo(reserva2.getFecha()));

        reservaVista.mostrarReservaConArreglo(reservasConMesas);
    }

    public void mostrarReservaPorFecha() {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        List<Reserva> reservasPorFecha = new ArrayList<>();

        for (Reserva reserva : reservaRepositorio.todasLasReservas()) {
            if (reserva.getFecha().equals(fecha)) {
                reservasPorFecha.add(reserva);
            }
        }

        if (reservasPorFecha.isEmpty()) {
            reservaVista.mensaje("No hay reservas para la fecha especificada.");
        } else {
            reservaVista.mostrarReservaConArreglo(reservasPorFecha);
        }
    }

}

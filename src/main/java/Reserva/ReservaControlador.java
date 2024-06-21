package Reserva;


import Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.ClienteControlador;
import Cliente.Cliente;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Excepciones.ExcepcionReservaCamposVacios;
import Excepciones.ExcepcionReservaCaracterInvalido;
import Excepciones.ExcepcionReservaNoEncontrada;
import Excepciones.ExcepcionReservaValorNegativo;
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

    public void agregarReserva() throws ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios, ExcepcionReservaValorNegativo, ExcepcionClienteNoEncontrado, ExcepcionReservaNoEncontrada {
        clienteControlador.loadGestionCliente();
        reservaRepositorio.cargarReserva();
        Reserva reserva = reservaVista.pedirFecha();
        LocalDate fecha = reservaRepositorio.buscarFecha(reserva.getFecha());
        if (fecha == null) {
            reservaVista.mensaje("Esa fecha no existe.");
            reserva = reservaVista.pedirFecha();
            Integer cantPersonas = reservaVista.pedirCantidadPersonas();
            Integer id = clienteVista.consultarCliente();
            Cliente cliente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
            if (cliente == null) {
                clienteRepositorio.addCliente(cliente);
            }
            List<MesasReservadas> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(new MesasReservadas(cliente, cantPersonas));
            Reserva reservaCompleta = new Reserva(reserva.getFecha(), mesasReservadas);
            reservaRepositorio.agregar(reservaCompleta);
            reservaRepositorio.guardarReserva();
        } else {
            Integer cantPersonas = reservaVista.pedirCantidadPersonas();
            Integer id = clienteVista.seleccId();
            Cliente cliente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
            List<MesasReservadas> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(new MesasReservadas(cliente, cantPersonas));
            Reserva reservaCompleta = new Reserva(fecha, mesasReservadas);
            reservaRepositorio.agregar(reservaCompleta);
            reservaRepositorio.guardarReserva();
        }
    }

    public void eliminarReserva() throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        try {
            reservaRepositorio.cargarReserva();
            LocalDate fecha = reservaVista.buscarFechaReserva();
            System.out.println("Fecha ingresada: " + fecha); // Mensaje de depuración
            Reserva reserva = reservaRepositorio.buscarReserva(fecha);
            System.out.println("Reserva encontrada: " + reserva); // Mensaje de depuración
            reservaRepositorio.eliminar(reserva);
            reservaRepositorio.guardarReserva();
            reservaVista.mensaje("Reserva eliminada con éxito!");
        } catch (ExcepcionReservaNoEncontrada e) {
            reservaVista.mensaje("Error al eliminar la reserva: " + e.getMessage());
        }
    }


    public void modificarReserva() throws ExcepcionReservaNoEncontrada, ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo, ExcepcionClienteNoEncontrado {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);
        Integer id = clienteVista.consultarCliente();
        Integer indice = mesasReservadasRepositorio.buscarIndice(id);
        MesasReservadas mesasReservadas = mesasReservadasRepositorio.buscarMesaReservadaPorIndice(id);


        if(reserva == null){
            reservaVista.mensaje("Esa reserva no existe.");
        }else{
            String opcion = reservaVista.preguntarQueModificar();


            if (opcion.equalsIgnoreCase("fecha")) {
                LocalDate nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                while(nuevaFecha.isEqual(fecha)){
                    reservaVista.mensaje("La fecha ingresa es la misma. Ingrese nuevamente");
                    nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                }
                LocalDate fechaNueva = reservaVista.modificarFecha();
                Reserva nuevaReserva = reservaRepositorio.buscarReserva(fechaNueva);
                if(nuevaReserva == null){
                    List<MesasReservadas> mesas = new ArrayList<>();
                    mesas.add(mesasReservadas);
                    Reserva reserva1 = new Reserva(fecha,mesas);
                    reservaRepositorio.agregar(reserva1);
                    reservaRepositorio.guardarReserva();
                }else {
                    nuevaReserva.getMesasReservadas().add(mesasReservadas);
                    reservaRepositorio.guardarReserva();
                }
                reservaRepositorio.modificar(nuevaReserva);
                reservaRepositorio.guardarReserva();
                reservaVista.mensaje("Reserva modificada con éxito!");
            } else if(opcion.equalsIgnoreCase("cantPersonas")) {
                Integer cantPersonas = reservaVista.modificarCantPersonas();
                reserva.getMesasReservadas().get(id).setCantPersonas(cantPersonas);
                reservaRepositorio.guardarReserva();
            }
        }
    }


    public void mostraReservasConFechaYArreglo(){
        reservaRepositorio.cargarReserva();
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();
        List<MesasReservadas> mesasReservadas = reservaRepositorio.todasLasMesasReservadas();
        reservaVista.mostrarReservaConArreglo(reservas, mesasReservadas);
    }
}
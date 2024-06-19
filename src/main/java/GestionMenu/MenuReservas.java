package GestionMenu;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;
import Reserva.ReservaControlador;
import Reserva.ReservaRepositorio;
import Reserva.ReservaVista;

import java.util.Scanner;

public class MenuReservas {
    public static void main(String[] args) throws ExcepcionReservaCamposVacios, ExcepcionReservaNoEncontrada, ExcepcionClienteNoEncontrado, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        ReservaRepositorio reservaRepositorio = new ReservaRepositorio();
        ReservaVista reservaVista= new ReservaVista();
        ClienteVista clienteVista = new ClienteVista();
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        ReservaControlador reservaControlador = new ReservaControlador(reservaRepositorio, reservaVista, clienteVista, clienteRepositorio);
        menuReservas(reservaControlador);
    }

    public static Scanner scanner = new Scanner(System.in);

    public static void menuReservas(ReservaControlador reservaControlador) throws ExcepcionReservaCamposVacios, ExcepcionClienteNoEncontrado, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo, ExcepcionReservaNoEncontrada {
        int opcion = 0;
        do {
            System.out.println("=====================MENU RESERVAS=====================");
            System.out.println("1. Agregar Reserva.");
            System.out.println("2. Eliminar Reserva.");
            System.out.println("3. Modificar Reserva.");
            System.out.println("4. Mostrar Todas Reservas.");
            System.out.println("5. Salir.");

            System.out.println("Ingrese una opción");
            opcion = scanner.nextInt();

            switch (opcion){
                case 1:
                    reservaControlador.agregarReserva();
                    break;
                case 2:
                    reservaControlador.eliminarReserva();
                    break;
                case 3:
                    reservaControlador.modificarReserva();
                    break;
                case 4:
                    reservaControlador.mostraReservasConFechaYArreglo();
                    break;
                case 5:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }while (opcion != 5);
        scanner.close();
    }
}

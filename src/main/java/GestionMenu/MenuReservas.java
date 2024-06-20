package GestionMenu;


import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.controller.ClienteControlador;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;
import MesasReservadas.MesasReservadasRepositorio;
import Reserva.ReservaControlador;
import Reserva.ReservaRepositorio;
import Reserva.ReservaVista;


import java.io.IOException;
import java.util.Scanner;


public class MenuReservas {
    ReservaVista reservaVista = new ReservaVista();
    ReservaRepositorio reservaRepositorio = new ReservaRepositorio();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    ClienteVista clienteVista = new ClienteVista();
    MesasReservadasRepositorio  mesasReservadasRepositorio = new MesasReservadasRepositorio();
    ClienteControlador clienteControlador = new ClienteControlador(clienteVista,clienteRepositorio);
    ReservaControlador reservaControlador = new ReservaControlador(reservaRepositorio,reservaVista,clienteVista,clienteRepositorio,mesasReservadasRepositorio,clienteControlador);


    public static Scanner scanner = new Scanner(System.in);


    public void menuReservas() throws ExcepcionReservaCamposVacios, ExcepcionClienteNoEncontrado, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo, ExcepcionReservaNoEncontrada, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {
        int opcion = 0;
        do {
            System.out.println("=====================MENU RESERVAS=====================");
            System.out.println("1. Agregar Reserva.");
            System.out.println("2. Eliminar Reserva.");
            System.out.println("3. Modificar Reserva.");
            System.out.println("4. Mostrar Todas Reservas.");
            System.out.println("5. Volver al Menu Principal.");


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
                    new MenuPrincipal().menuPrincipal();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }while (opcion != 5);
        scanner.close();
    }
}
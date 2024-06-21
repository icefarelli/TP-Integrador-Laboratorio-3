package GestionMenu;


import Excepciones.*;
import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import MesasReservadas.MesasReservadasRepositorio;
import Plato.Colores.Colores;
import Reserva.ReservaControlador;
import Reserva.ReservaRepositorio;
import Reserva.ReservaVista;


import java.io.IOException;
import java.util.InputMismatchException;
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

    public void menuReservas() throws ExcepcionClienteNoEncontrado, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {
        int op = 0;
        do {
            try {
                Colores.printInColor("==========MENU RESERVAS==========", Colores.BLUE);
                System.out.println(". Elija una opción: ");
                System.out.println("1. Agregar reserva.");
                System.out.println("2. Eliminar reserva.");
                System.out.println("3. Modificar reserva.");
                System.out.println("4. Mostrar todas reservas.");
                System.out.println("5. Volver al menu principal");
                System.out.println("6. Salir del sistema");
                Colores.printInColor("-------------------------------", Colores.BLUE);

                op = scanner.nextInt();


                switch (op) {
                    case 1:
                        reservaControlador.agregarReserva();
                        break;
                    case 2:
                        reservaControlador.eliminarMesaReserva();
                        break;
                    case 3:
                        reservaControlador.modificarReserva();
                        break;
                    case 4:
                        reservaControlador.mostrarReservasConArreglo();
                        break;
                    case 5:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            }catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scanner.nextLine();
            }
        }while (op != 6 && op != 7);
    }
}
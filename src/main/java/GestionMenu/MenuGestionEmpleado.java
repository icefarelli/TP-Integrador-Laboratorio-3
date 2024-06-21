package GestionMenu;

import Excepciones.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuGestionEmpleado {
    public void menuGestionEmpleado() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionReservaCamposVacios, ExcepcionReservaNoEncontrada, ExcepcionClienteNoEncontrado, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo, ExcepcionFormatoIncorrecto {

        MenuReservas menuReservas = new MenuReservas();
        MenuOrdenes menuOrdenes = new MenuOrdenes();

        System.out.println("--------------------BIENVENIDOS A ´INTERFAZ DE SABORES´---------------------------");
        System.out.println("Que desea hacer?");

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try{
                System.out.println("1- RESERVAS");
                System.out.println("2- ORDENES");
                System.out.println("0- Salir");

                System.out.println("Ingrese una opción");
                op = scanner.nextInt(); // Leer la entrada como cadena
                switch (op) {
                    case 1:
                        menuReservas.menuReservas();
                        break;
                    case 2:
                        //menuOrdenes.menuOrdenes();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }

            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scanner.nextLine();
            }
        } while (op!=0);
        scanner.close();
    }

}

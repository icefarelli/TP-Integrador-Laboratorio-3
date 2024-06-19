package GestionMenu;

import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;

import java.io.IOException;
import java.util.Scanner;

public class MenuPrincipal {

    public void menuPrincipal() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException {

        MenuEmpleados menuEmpleados = new MenuEmpleados();

        System.out.println("--------------------BIENVENIDOS A ´INTERFAZ DE SABORES´---------------------------");
        System.out.println("Que desea hacer?");

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            System.out.println("1- RESERVAS");
            System.out.println("2- ORDENES");
            System.out.println("3- GESTION DE EMPLEADOS");
            System.out.println("4- GESTION DE CLIENTES");

            System.out.println("5- Salir");

            System.out.println("Ingrese una opción");
            op = scanner.nextInt(); // Leer la entrada como cadena
            switch (op) {
                case 1:
                    // agregar menu de reservas
                    break;
                case 2:
                    //agregar menu de ordenes
                    break;
                case 3:
                    menuEmpleados.menuEmpleados();
                    break;
                case 4:
                   //agregar menu de clientes
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        } while (op!=5);
        scanner.close();
    }

}


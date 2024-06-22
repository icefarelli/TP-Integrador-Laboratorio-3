package GestionMenu;

import Excepciones.*;
import Plato.Colores.Colores;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    public void menuPrincipal() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto, ExcepcionClienteNoEncontrado {

        MenuEmpleados menuEmpleados = new MenuEmpleados();
        MenuReservas menuReservas = new MenuReservas();
        MenuClientes menuClientes = new MenuClientes();
        MenuPlato menuPlato = new MenuPlato();
        MenuOrdenes menuOrdenes = new MenuOrdenes();
        MenuUsuarios menuUsuarios = new MenuUsuarios();

        Colores.printInColor("========BIENVENIDOS A ´INTERFAZ DE SABORES´========", Colores.YELLOW);
        System.out.println("Que desea hacer?");

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try {
                System.out.println("1. Reservas");
                System.out.println("2. Ordenes");
                System.out.println("3. Gestion de Empleados");
                System.out.println("4. Gestion de Clientes");
                System.out.println("5. Gestion de Platos");
                System.out.println("6. Gestion de Usuarios");
                System.out.println("7. Volver al menu Login");

                System.out.println("0. Salir");

                System.out.println("Ingrese una opción");
                op = scanner.nextInt(); // Leer la entrada como cadena
                scanner.nextLine();
                switch (op) {
                    case 1:
                        menuReservas.menuReservas();
                        break;
                    case 2:
                        menuOrdenes.menuOrdenes();
                        break;
                    case 3:
                        menuEmpleados.menuEmpleados();
                        break;
                    case 4:
                        menuClientes.menuCliente();
                        break;
                    case 5:
                        menuPlato.mainMenu();
                        break;
                    case 6:
                        menuUsuarios.menuUsuarios();
                    case 7:
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine();
            } catch (ExcepcionEntradaInvalida e) {
                System.out.println(e.getMessage());
            } catch (ExcepcionOrdenNoEncontrada excepcionOrdenNoEncontrada) {
                System.out.println(excepcionOrdenNoEncontrada.getMessage());;
            }
        } while (op!=0 && op!= 7);
    }
}
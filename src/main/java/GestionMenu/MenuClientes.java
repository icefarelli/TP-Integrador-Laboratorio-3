package GestionMenu;

import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.Excepciones.ExcepcionListaVacia;
import Cliente.controller.ClienteControlador;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuClientes {


     ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
     ClienteVista clienteVista = new ClienteVista();
    ClienteControlador clienteControlador = new ClienteControlador(clienteVista,clienteRepositorio);
    MenuPrincipal menuPrincipal = new MenuPrincipal();


    public MenuClientes(ClienteControlador clienteControlador) {

        this.clienteControlador = clienteControlador;
    }

    private Integer seguirOSalir() throws ExcepcionFormatoIncorrecto{
        Scanner scanner = new Scanner(System.in);
        Integer num = null;
        try {
            System.out.println("¿Desea realizar otra acción?");
            System.out.println("1. Sí");
            System.out.println("0. No");
            num = scanner.nextInt();
            String numStr = String.valueOf(num);
            if (!clienteVista.esNumero(numStr)) {
                throw new ExcepcionFormatoIncorrecto("Entrada inválida. Solo se aceptan números. Verifique y vuelva a intentar");
            }
        } catch (InputMismatchException e) {
            throw new ExcepcionFormatoIncorrecto("Entrada inválida. Debe ingresar un número entero. Verifique y vuelva a intentar");
        }
        return num;
    }
    public void menuCliente(ClienteControlador clienteControlador) throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {
        Integer ok = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("----------------------------------MENU GESTION CLIENTES------------------------------");
                System.out.println(". Elija una opción: ");
                System.out.println("1. Agregar cliente");
                System.out.println("2. Eliminar cliente");
                System.out.println("3. Actualizar teléfono de cliente");
                System.out.println("4. Consultar teléfono de cliente");
                System.out.println("5. Ver todos los clientes");
                System.out.println("6. Volver al menú principal");
                System.out.println("7. XXXXXXX EXIT XXXXXXX");
                ok = scanner.nextInt();

                switch (ok) {
                    case 1:
                        clienteControlador.agregarClientes();
                        break;
                    case 2:
                        clienteControlador.removeCliente();
                        break;
                    case 3:
                        clienteControlador.updateClientes();
                        break;
                    case 4:
                        clienteControlador.consultCliente();
                        break;
                    case 5:
                        clienteControlador.viewClientes();
                        break;
                    case 6:
                        menuPrincipal.menuPrincipal();
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                        continue;
                }

                Integer seguir = seguirOSalir();
                if (seguir == 0) {
                    System.out.println("Saliendo...");
                    System.exit(0);
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine();
            } catch (ExcepcionFormatoIncorrecto e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        } while (ok != 6 && ok != 7);

        scanner.close();
    }

}

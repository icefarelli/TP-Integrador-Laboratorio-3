package GestionMenu;

import Excepciones.*;
import Login.LoginController;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuLogin {

    LoginController loginController;

    MenuPrincipal menuPrincipal = new MenuPrincipal();

    public MenuLogin(LoginController loginController) {
        this.loginController = loginController;
    }

    public void menu() throws ExcepcionReservaCamposVacios, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, ExcepcionReservaNoEncontrada, ExcepcionClienteNoEncontrado, IOException, ExcepcionReservaCaracterInvalido, ExcepcionFormatoIncorrecto, ExcepcionReservaValorNegativo, ExcepcionCamposVacios, ExcepcionNombreNumerico {
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);
        Integer ok = -1;

        do {
            System.out.println("\n");
            System.out.println("---------------------------------- GESTION RESTAURANT ------------------------------");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            try {
                ok = scanner.nextInt();
                scanner.nextLine();
                switch (ok) {
                    case 1:
                        if (loginController.iniciarSesion()) {
                            loggedIn = true;
                            menuPrincipal.menuPrincipal();
                        }
                        menu();
                        break;
                    case 2:
                        loginController.addUser();
                        menu();
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\nNumero invalido. Vuelva a intentarlo.");


                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine();

            } catch (ExcepcionFormatoIncorrecto e) {
                System.out.println(e.getMessage());
                scanner.nextLine();

            }

        } while (ok!=0);
    }
}


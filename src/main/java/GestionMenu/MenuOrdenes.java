package GestionMenu;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaNoEncontrada;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;
import Orden.OrdenControlador;
import Orden.OrdenRepositorio;
import Orden.OrdenVista;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuOrdenes {

    OrdenRepositorio ordenRepositorio = new OrdenRepositorio();
    OrdenVista ordenVista = new OrdenVista();
    EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();
    EmpleadoVista empleadoVista = new EmpleadoVista();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    ClienteVista clienteVista = new ClienteVista();
    PlatoRepositorio platoRepositorio = new PlatoRepositorio();
    PlatoVista platoVista = new PlatoVista();
    PlatoControlador platoControlador = new PlatoControlador(platoRepositorio, platoVista);

    OrdenControlador ordenControlador = new OrdenControlador(ordenRepositorio, ordenVista, empleadoRepositorio, empleadoVista,clienteRepositorio, clienteVista, platoRepositorio, platoVista, platoControlador);
    public void menuOrdenes() throws ExcepcionDNIStringInvalido, ExcepcionClienteNoEncontrado, ExcepcionOrdenNoEncontrada, ExcepcionFormatoIncorrecto, ExcepcionEntradaInvalida, ExcepcionNombreInvalido, IOException {

        Scanner scanner = new Scanner(System.in);

        int op = -1;

            do {
                try {
                System.out.println("----------------------------------MENU GESTION ORDENES--------------------------------");

                System.out.println("1- Agregar orden");
                System.out.println("2- Eliminar orden");
                System.out.println("3- Modificar orden");
                System.out.println("4- Listar ordenes");
                System.out.println("5- Mostrar orden por ID");
                System.out.println("6- Volver al menu principal");
                System.out.println("7- Salir del sistema");

                System.out.println("Ingrese una opción");
                op = scanner.nextInt(); // Leer la entrada
                switch (op) {
                    case 1:
                        ordenControlador.crearOrden();
                        break;
                    case 2:
                        ordenControlador.eliminarOrden();
                        break;
                    case 3:
                        ordenControlador.modificarOrden();
                        break;
                    case 4:
                        ordenControlador.mostrarTodasLasOrdenes();
                        break;
                    case 5:
                        try {
                            ordenControlador.mostrarOrden(ordenVista.pedirIdOrden());
                        } catch (ExcepcionEntradaInvalida e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        new MenuPrincipal().menuPrincipal();
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
                }catch (InputMismatchException | ExcepcionReservaCamposVacios | ExcepcionReservaNoEncontrada |
                        ExcepcionReservaCaracterInvalido | ExcepcionReservaValorNegativo e) {
                    System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                    scanner.nextLine();
                } catch (ExcepcionReservaCamposVacios e) {
                    throw new RuntimeException(e);
                } catch (ExcepcionReservaNoEncontrada e) {
                    throw new RuntimeException(e);
                } catch (ExcepcionReservaCaracterInvalido e) {
                    throw new RuntimeException(e);
                } catch (ExcepcionReservaValorNegativo e) {
                    throw new RuntimeException(e);
                }
            } while (op != 6 && op != 7);
            scanner.close();
        }
}

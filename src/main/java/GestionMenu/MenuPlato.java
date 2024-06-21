package GestionMenu;

import Plato.Colores.Colores;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadRepositorio;
import Plato.Variedad.VariedadVista;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPlato {

    public void mainMenu() {
        PlatoRepositorio repositorio = new PlatoRepositorio();
        PlatoVista vista = new PlatoVista();
        PlatoControlador pControlador = new PlatoControlador(repositorio, vista);
        VariedadVista varVista = new VariedadVista();
        VariedadRepositorio varRepositorio = new VariedadRepositorio();
        VariedadController varController = new VariedadController(varVista, varRepositorio);
        MenuPlato mPlato = new MenuPlato();


        Scanner scanner = new Scanner(System.in);

        int opcion = -1;

        do {
            try {
                Colores.printInColor("==========MENU PLATOS==========", Colores.BLUE);
                System.out.println(" 1. Nuevo plato\n 2. Modificar plato\n 3. Aumentar precio a los platos\n 4. Eliminar platos\n 5. Mostrar todos los platos por Categoria\n 6. Mostrar carta completa\n 7. Volver al menu principal\n 8. Salir del sistema");
                Colores.printInColor("-------------------------------", Colores.BLUE);
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        pControlador.cargarPlatoEnSistema(repositorio, vista);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 2:
                        pControlador.actualizarPlatoExistente(repositorio, vista, varController, varVista);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 3:
                        pControlador.aumentoPreciosPorcentualmente(repositorio, vista);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 4:
                        mPlato.menuEliminar(repositorio, vista, pControlador, varController, varVista);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 5:
                        pControlador.mostrarPlatosXTipo(repositorio, vista);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 6:
                        pControlador.mostrarMenuCompleto(repositorio);
                        PlatoControlador.pausarPantalla(vista);
                        break;
                    case 7:
                        vista.mensajePersonalizado("Saliendo del Menu...");
                        break;
                    case 8:
                        System.out.println("Saliendo...");
                        System.exit(0);
                    default:
                        vista.mensajePersonalizado("Opcion Invalida");

                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Ingresar solo numero.");
                scanner.nextLine();
            } catch (RuntimeException e) {
                scanner.nextLine();
                e.printStackTrace();
            }
        } while (opcion != 0);
    }


    public void menuEliminar(PlatoRepositorio pRepositorio, PlatoVista pVista, PlatoControlador pControlador, VariedadController varController, VariedadVista varVista) throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            try {
                Colores.printInColor("===============================", Colores.RED);
                Colores.printInColor("==========MENU ELIMINAR==========", Colores.RED);
                System.out.println(" 1. Eliminar por NOMBRE\n 2. Eliminar por SELECCION\n 0. Volver al menu anterior");
                Colores.printInColor("-------------------------------", Colores.RED);
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        pControlador.eliminarPlatosDelSistemaXNombre(pRepositorio, pVista, varController, varVista);
                        break;
                    case 2:
                        pControlador.eliminarPlatosDelSistemaXSeleccion(pRepositorio, pVista);
                        break;
                    case 0:
                        pVista.mensajePersonalizado("Volviendo al menu anterior...");
                        break;
                    default:
                        pVista.mensajePersonalizado("Opcion Invalida");
                }
            }catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine();
            }
        } while (opcion != 0);
    }
}


package GestionMenu;

import Plato.Colores.Colores;
import Plato.Excepciones.ExcepCargaNoRealizada;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadRepositorio;
import Plato.Variedad.VariedadVista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPlato {

    public static void mainMenu() {
        PlatoRepositorio repositorio = new PlatoRepositorio();
        PlatoVista vista = new PlatoVista();
        PlatoControlador pControlador = new PlatoControlador(repositorio, vista);
        VariedadVista varVista = new VariedadVista();
        VariedadRepositorio varRepositorio = new VariedadRepositorio();
        VariedadController varController = new VariedadController(varVista,varRepositorio);
        MenuPlato mPlato = new MenuPlato();


        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        try {
            do {
                Colores.printInColor("==========MENU PLATOS==========", Colores.BLUE);
                System.out.println(" 1. NUEVO PLATO\n 2. MODIFICAR PLATO\n 3. AUMENTAR PRECIO A LOS PLATOS\n 4. ELIMINAR PLATOS\n 5. MOSTRAR PLATOS POR CATEGORIA\n 6. MOSTRAR CARTA COMPLETA\n 0. SALIR");
                Colores.printInColor("-------------------------------", Colores.YELLOW);
                opcion = scanner.nextInt();
                scanner.nextLine();

                    switch (opcion) {
                        case 1:
                            pControlador.cargarPlatoEnSistema(repositorio, vista);
                            PlatoControlador.pausarPantalla(vista);
                            break;
                        case 2:
                            pControlador.actualizarPlatoExistente(repositorio, vista, varController,varVista);
                            PlatoControlador.pausarPantalla(vista);
                            break;
                        case 3:
                            pControlador.aumentoPreciosPorcentualmente(repositorio, vista);
                            PlatoControlador.pausarPantalla(vista);
                            break;
                        case 4:
                            mPlato.menuEliminar(repositorio, vista, pControlador,varController,varVista);
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
                        case 0:
                            vista.mensajePersonalizado("Saliendo del Menu...");
                            break;
                        default:
                            vista.mensajePersonalizado("Opcion Invalida");

                    }

                } while (opcion != 0);
        }catch (InputMismatchException inputMismatchException){
        System.out.println("Ingresar solo numero.");
        mainMenu();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }


    public void menuEliminar(PlatoRepositorio pRepositorio, PlatoVista pVista, PlatoControlador pControlador, VariedadController varController,VariedadVista varVista) throws InputMismatchException{
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            Colores.printInColor("===============================", Colores.RED);
            Colores.printInColor("==========MENU ELIMINAR==========", Colores.RED);
            System.out.println(" 1. ELIMINAR INGRESANDO NOMBRE\n 2. ELIMINAR POR SELECCION\n 0. SALIR");
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
        } while (opcion != 0);

    }

}


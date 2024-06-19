package Plato;

import Plato.Colores.Colores;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.Scanner;

public class MenuPlato {

    public static void mainMenu() throws IOException, ExcepIngresoInvalido {
        PlatoRepositorio repositorio = new PlatoRepositorio();
        PlatoVista vista = new PlatoVista();
        PlatoControlador pControlador = new PlatoControlador(repositorio, vista);

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        Colores.printInColor("===============================",Colores.BLUE);
        Colores.printInColor("==========MENU PLATOS==========",Colores.BLUE);
        System.out.println(" 1. NUEVO PLATO\n 2. MODIFICAR PLATO\n 3. AUMENTAR PRECIO A LOS PLATOS\n 4. ELIMINAR PLATOS\n 5. MOSTRAR PLATOS POR CATEGORIA\n 0. SALIR");
        Colores.printInColor("-------------------------------",Colores.YELLOW);
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                    pControlador.cargarPlatoEnSistema(repositorio,vista);
                break;
            case 2:
                    try{
                        pControlador.actualizarPlatoExistente(repositorio, vista);
                    }catch (RuntimeException rte){
                        throw new RuntimeException("Error");
                    }
                break;
            case 3:
                pControlador.aumentoPreciosPorcentualmente(repositorio, vista);
                break;
            case 4:
                pControlador.eliminarPlatosDelSistema(repositorio, vista);
                break;
            case 5:
                pControlador.mostrarPlatosXTipo(repositorio, vista);
                break;
            default:
        }



    }

}


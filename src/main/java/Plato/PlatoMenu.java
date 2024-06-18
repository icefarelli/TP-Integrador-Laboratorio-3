package Plato;

import Plato.Excepciones.ExcepBusquedaSinResultados;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.Scanner;

public class PlatoMenu {

    public static void mainMenu() throws IOException, ExcepIngresoInvalido {
        PlatoControlador pControlador = new PlatoControlador(new PlatoRepositorio(),new PlatoVista());
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        System.out.println("----- MENU PLATOS -----");
        System.out.println("1. NUEVO PLATO\n2. MODIFICAR PLATO\n3. AUMENTAR PRECIO A LOS PLATOS\n4. ELIMINAR PLATOS\n5. MOSTRAR PLATOS POR CATEGORIA \n---------------------------- \n 0.SALIR");
        System.out.println("Ingrese una opcion: ");
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                try {
                    pControlador.cargarPlatoEnSistema(new PlatoRepositorio(),new PlatoVista());
                } catch (ExcepIngresoInvalido e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                    try{
                        pControlador.actualizarPlatoExistente(new PlatoRepositorio(),new PlatoVista());
                    }catch (RuntimeException rte){
                        throw new RuntimeException("Error");
                    }
                break;
            case 3:
                pControlador.aumentoPreciosPorcentualmente(new PlatoRepositorio(), new PlatoVista());
                break;
            case 4:
                pControlador.eliminarPlatosDelSistema(new PlatoRepositorio(),new PlatoVista());
                break;
            case 5:
                pControlador.mostrarPlatosXTipo(new PlatoRepositorio(), new PlatoVista());
                break;
            default:
        }



    }

}


package Plato;

import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.Scanner;

public class MainP {
    public static void main(String[] args) {

//        int opcion = 1;
//        Scanner scanner = new Scanner(System.in);
        //PlatoControlador platoControlador = new PlatoControlador(new PlatoRepositorio(),new PlatoVista());
//
//        while (opcion == 1){
//            try {
//                platoControlador.cargarPlatoEnSistema(new PlatoRepositorio(),new PlatoVista());
//            } catch (ExcepIngresoInvalido e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Otro?");
//            opcion = scanner.nextInt();
//            scanner.nextLine();
//        }


        try {
            PlatoMenu.mainMenu();
        } catch (IOException | ExcepIngresoInvalido e) {
            throw new RuntimeException(e);
        }

//        Plato plato1;
//        try {
//            plato1 = PlatoControlador.seleccionPlatoParaOrden(new PlatoRepositorio(), new PlatoVista());
//        } catch (ExcepIngresoInvalido e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(plato1);

    }
}

package Plato;

import GestionMenu.MenuPlato;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;

public class MainP {
    public static void main(String[] args) {
        int opcion = -1;


        try {
            MenuPlato.mainMenu();
        } catch (IOException | ExcepIngresoInvalido ignored) {

        }

//        PlatoRepositorio repositorio = new PlatoRepositorio();
//        PlatoVista vista = new PlatoVista();
//        PlatoControlador controlador = new PlatoControlador(repositorio,vista);
//        repositorio.mostrarCartaDePlatosCompleta();
//

//
//
//        Plato plato = controlador.seleccionPlatoParaOrden(repositorio,vista);
//
//        System.out.println(plato);




    }
}

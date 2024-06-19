package GestionMenu;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Orden.OrdenControlador;
import Orden.OrdenRepositorio;
import Orden.OrdenVista;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;
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
    public void menuOrdenes() throws ExcepcionDNIStringInvalido, ExcepcionClienteNoEncontrado, ExcepcionOrdenNoEncontrada, ExcepcionFormatoIncorrecto {

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            System.out.println("----------------------------------MENU GESTION ORDENES--------------------------------");

            System.out.println("1- Agregar orden");
            System.out.println("2- Eliminar orden");
            System.out.println("3- Modificar orden");
            System.out.println("4- Listar ordenes");
            System.out.println("5- Mostrar orden por ID");
            System.out.println("6- Volver");

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
                    ordenControlador.mostrarOrden(ordenVista.pedirIdOrden());
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        } while (op!=5);
        scanner.close();
    }
}

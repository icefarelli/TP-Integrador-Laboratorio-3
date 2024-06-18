package GestionMenu;

import Empleado.EmpleadoControlador;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;

import java.io.IOException;
import java.util.Scanner;

public class MenuEmpleados {

    EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();
    EmpleadoVista empleadoVista = new EmpleadoVista();

    EmpleadoControlador empleadoControlador = new EmpleadoControlador(empleadoVista,empleadoRepositorio);

    public void menuEmpleados() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException {

    Scanner scanner = new Scanner(System.in);

    int op = -1;
        do {
        System.out.println("----------------------------------MENU GESTION EMPLEADOS------------------------------");

        System.out.println("1- Agregar empleados");
        System.out.println("2- Eliminar empleados");
        System.out.println("3- Modificar empleados");
        System.out.println("4- Listar empleados");
        System.out.println("5- Volver");

        System.out.println("Ingrese una opción");
        op = scanner.nextInt(); // Leer la entrada como cadena
        switch (op) {
            case 1:
                    empleadoControlador.agregarEmpleado();
                break;
            case 2:
                    empleadoControlador.eliminarEmpleado();
                break;
            case 3:
                    empleadoControlador.modificarEmpleado();
                break;
            case 4:
                    empleadoControlador.mostrarListaEmpleados();
                break;
            case 5:
                break;
            default:
                System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
        }
    } while (op!=5);
        scanner.close();
}
}

package Empleado;

import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class EmpleadoVista {

public String elegirPuesto() {
    Scanner scan = new Scanner(System.in);
    int op = -1;
    String nombre = null;
    do {
        System.out.println("Ingrese una opcion para el puesto:");
        System.out.println("1. Gerente general");
        System.out.println("2. Anfitrión");
        System.out.println("3. Cajero");
        System.out.println("4. Mesero");

        op = scan.nextInt();

        switch (op) {
            case 1:
                nombre ="Gerente general";
                break;
            case 2:
                nombre ="Anfitrion";
                break;
            case 3:
                nombre ="Cajero";
                break;
            case 4:
                nombre ="Mesero";
                break;
            default:
                System.out.println("Opcion incorrecta, ingrese una opcion valida");

        }
    } while(op!=1 && op!=2 && op!=3 && op!=4);
    return nombre;
}


public Empleado pedirUnEmpleado() throws ExcepcionNombreInvalido, ExcepcionDNIStringInvalido {

    Scanner scan = new Scanner(System.in);
    System.out.println("Ingrese el nombre y apellido del empleado:");
    String nombre = scan.nextLine();

    if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")){
        throw new ExcepcionNombreInvalido("El nombre solo debe contener letras");
    }
    System.out.println("Ingrese el DNI del empleado:");
    String DNI = scan.nextLine();

    if(DNI==null || !DNI.matches("\\d{8}")) {
        throw new ExcepcionDNIStringInvalido("El DNI solo debe contener números y ser de 8 dígitos");
    }

    String puesto = elegirPuesto();

    Empleado empleado = new Empleado(nombre,DNI,puesto);

    return empleado;
}

public Integer pedirClave() throws ExcepcionDNIStringInvalido {
    Scanner scan = new Scanner(System.in);
    System.out.println("Ingrese el ID del empleado:");
    Integer id = scan.nextInt();
    //CARGAR UNA EXCEPCION PARA VERIFICAR

    return id;
}

public String pedirModificacion() {
    Scanner scan = new Scanner(System.in);
    int op = -1;
    String nombre = null;
    do {
        System.out.println("Ingrese una opcion para modificar:");
        System.out.println("1. Nombre y apellido");
        System.out.println("2. Puesto");


        op = scan.nextInt();

        switch (op) {
            case 1:
                nombre ="nombre";
                break;
            case 2:
                nombre ="puesto";
                break;
            default:
                System.out.println("Opcion incorrecta, ingrese una opcion valida");

        }
    } while(op!=1 && op!=2);
    return nombre;
}

public String pedirNombreParaModificar() throws ExcepcionNombreInvalido {
    Scanner scan = new Scanner(System.in);
    System.out.println("Ingrese el nombre y apellido del empleado:");
    String nombre = scan.nextLine();

    if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")){
        throw new ExcepcionNombreInvalido("El nombre solo debe contener letras");
    }

    return nombre;
}

public void mensajeErrorBusqueda() {
    System.out.println("Empleado no encontrado");
}

public void mostrarEmpleado(Empleado e){
    System.out.println("DNI: " + e.getId());
    System.out.println("Nombre y Apellido: " + e.getNombre());
    System.out.println("Id empleado: " + e.getIdEmpleado());
    System.out.println("Puesto: " + e.getPuesto());
}

public void mostrarListaEmpleados(Map<Integer, Empleado> mapaEmpleados){
    if (mapaEmpleados.isEmpty()){
        System.out.println("lista de empleados vacia");
    } else {
        for (Map.Entry<Integer,Empleado> entry : mapaEmpleados.entrySet()){
            Empleado empleado = entry.getValue();
            System.out.println("-------------------------------------------------------");
            mostrarEmpleado(empleado);
        }
    }
}



}

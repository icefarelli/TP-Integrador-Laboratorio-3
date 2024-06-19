package Orden;

import Plato.Plato;

import java.util.Scanner;

public class OrdenVista {
    public void mostrarUnaOrden(Orden orden) {
        System.out.println("ID: " + orden.getId());
        System.out.println("Cliente: " + orden.getCliente().getNombre());
        System.out.println("Empleado: " + orden.getEmpleado().getNombre());
        System.out.println("Platos:");
        mostrarPlatosDeOrden(orden);
    }

    public void mostrarPlatosDeOrden(Orden orden) {
        int i = 0;
        for (Plato p : orden.getPlatoList()) {
            System.out.println(i + " - " + p.getNombre() + ": $ " + p.getPrecio());
            i++;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public Integer pedirIdOrden(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden buscada: ");
        return scan.nextInt();
    }

    public Integer pedirIdOrdenModificar(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden a modificar: ");
        return scan.nextInt();
    }

    public Integer pedirIdOrdenEliminar(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden a eliminar: ");
        return scan.nextInt();
    }
}
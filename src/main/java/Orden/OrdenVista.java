package Orden;

import Plato.Plato;

import java.util.Scanner;

public class OrdenVista {
    public void mostrarUnaOrden(Orden orden) {
        System.out.println("ID: " + orden.getId());
        System.out.println("Cliente: " + orden.getCliente().getNombre());
        System.out.println("Empleado: " + orden.getEmpleado().getNombre());
        System.out.println("Platos:");
        for (Plato p : orden.getPlatoList()) {
            System.out.println("- " + p.getNombre() + ": $ " + p.getPrecio());
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
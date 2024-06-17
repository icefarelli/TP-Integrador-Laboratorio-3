package Reserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ReservaVista {
    public static Scanner scanner = new Scanner(System.in);

    public Reserva crearReserva() {
        Integer cantPersonas = 0;
        LocalDateTime fecha = null;

        while (cantPersonas <= 0) {
            try {
                System.out.println("Ingrese Cantidad de personas: ");
                cantPersonas = scanner.nextInt();

                if (cantPersonas <= 0) {
                    System.out.println("Error: la cantidad de personas debe ser un número mayor que 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: la entrada no es un número válido. Intente nuevamente.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        while (fecha == null) {
            try {
                System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
                fecha = LocalDateTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy.");
            }
        }
        return new Reserva(cantPersonas, fecha);
    }

    public Reserva modificarReserva(Reserva reserva) {
        // Implementar la lógica para modificar la reserva
        System.out.println("Modificar Reserva con ID: " + reserva.getId());
        System.out.println("¿Desea modificar la cantidad de personas? (si/no)");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            int cantPersonas = 0;
            while (cantPersonas <= 0) {
                try {
                    System.out.println("Ingrese nueva cantidad de personas: ");
                    cantPersonas = scanner.nextInt();

                    if (cantPersonas <= 0) {
                        System.out.println("Error: la cantidad de personas debe ser un número mayor que 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: la entrada no es un número válido. Intente nuevamente.");
                    scanner.nextLine(); // Consumir el valor no válido
                }
            }
            scanner.nextLine(); // Consumir el carácter de nueva línea
            reserva.setCantPersonas(cantPersonas);
        }

        System.out.println("¿Desea modificar la fecha de la reserva? (si/no)");
        opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            LocalDateTime nuevaFecha = null;
            while (nuevaFecha == null) {
                try {
                    System.out.println("Ingrese nueva fecha de la reserva (formato dd/MM/yyyy): ");
                    nuevaFecha = LocalDateTime.parse(scanner.nextLine());
                } catch (DateTimeParseException e) {
                    System.out.println("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy.");
                }
            }
            reserva.setFecha(nuevaFecha);
        }

        System.out.println("Reserva modificada con éxito!");

        return reserva;
    }

    public Integer buscarIdReserva(){
        Integer id = 0;
        try {
            System.out.println("Ingrese Id: ");
            id = scanner.nextInt();

            if (id <= 0) {
                System.out.println("Error: el ID debe ser un número mayor que 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: la entrada no es un número válido. Intente nuevamente.");
            scanner.nextLine();
        }
        scanner.nextLine();

        return id;
    }

    public void mostrarReserva(Reserva reserva){
        System.out.println("ID reserva: " +reserva.getId());
        System.out.println("Cliente" +reserva.getCliente().getNombre());
        System.out.println("Telefono" +reserva.getCliente().getTelefono());
        System.out.println("Cantidad de Personas" +reserva.getCantPersonas());
        System.out.println("Fecha de la Reserva" +reserva.getFecha());
    }

    public void mensaje(String mensaje){
        System.out.println(mensaje);
    }
}
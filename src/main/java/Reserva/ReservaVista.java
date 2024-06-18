package Reserva;

import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ReservaVista {
    public static Scanner scanner = new Scanner(System.in);

    public Reserva crearReserva() throws ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios {
                System.out.println("Ingrese Cantidad de personas: ");
                Integer cantPersonas = scanner.nextInt();

                if (cantPersonas == null) {
                    throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio.");
                }

                if(!esNumero(String.valueOf(cantPersonas))){
                    throw new ExcepcionReservaCaracterInvalido("Error: la entrada no es un número válido. Intente nuevamente.");
                }

        scanner.nextLine();

                System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
                LocalDateTime fecha = LocalDateTime.parse(scanner.nextLine());
                if(fecha == null){
                    throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio.");
                }
                if(!esNumero(String.valueOf(fecha))){
                    throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy.");
                }

        return new Reserva(cantPersonas, fecha);
    }

    public Reserva modificarReserva(Reserva reserva) throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido {
        System.out.println("Modificar Reserva con ID: " + reserva.getId());
        System.out.println("¿Desea modificar la cantidad de personas? (si/no)");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            Integer cantPersonas = 0;
            if (cantPersonas == null) {
                throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio.");
            }

            if(!esNumero(String.valueOf(cantPersonas))){
                throw new ExcepcionReservaCaracterInvalido("Error: la entrada no es un número válido. Intente nuevamente.");
            }
            scanner.nextLine();
            reserva.setCantPersonas(cantPersonas);
        }

        System.out.println("¿Desea modificar la fecha de la reserva? (si/no)");
        opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            LocalDateTime fecha = LocalDateTime.parse(scanner.nextLine());
            if(fecha == null){
                throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio.");
            }
            if(!esNumero(String.valueOf(fecha))){
                throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy.");
            }
        }

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

    private boolean esNumero(String str) {
        return str.matches("\\d+");
    }
}
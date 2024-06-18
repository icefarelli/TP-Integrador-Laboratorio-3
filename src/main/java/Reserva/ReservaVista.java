package Reserva;

import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ReservaVista {
    public static Scanner scanner = new Scanner(System.in);

    public Reserva crearReserva() throws ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios, ExcepcionReservaValorNegativo {
        System.out.println("Ingrese Cantidad de personas: ");
        Integer cantPersonas = scanner.nextInt();

        if (cantPersonas <= 0) {
            throw new ExcepcionReservaValorNegativo("Error: el valor no puede ser negativo. Intente nuevamente.");
        }

        if (cantPersonas == null) {
            throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }

        if(!esNumero(String.valueOf(cantPersonas))){
            throw new ExcepcionReservaCaracterInvalido("Error: la entrada no es un número válido. Intente nuevamente.");
        }

        scanner.nextLine();

        System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
        LocalDateTime fecha = LocalDateTime.parse(scanner.nextLine());
        if(fecha == null){
            throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }
        if(!esNumero(String.valueOf(fecha))){
            throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy. Intente nuevamente.");
        }

        return new Reserva(cantPersonas, fecha);
    }

    public Reserva modificarReserva(Reserva reserva) throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        System.out.println("Modificar Reserva con ID: " + reserva.getId());
        System.out.println("¿Desea modificar la cantidad de personas? (si/no)");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            System.out.println("Ingrese Cantidad de personas: ");
            Integer cantPersonas = scanner.nextInt();

            if (cantPersonas <= 0) {
                throw new ExcepcionReservaValorNegativo("Error: el valor no puede ser negativo. Intente nuevamente.");
            }
            if (cantPersonas == null) {
                throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
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
                throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
            }
            if(!esNumero(String.valueOf(fecha))){
                throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy. Intente nuevamente.");
            }
        }

        return reserva;
    }

    public Integer buscarIdReserva() throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        Integer id = 0;
        System.out.println("Ingrese Id: ");
        id = scanner.nextInt();

        if (id <= 0) {
            throw new ExcepcionReservaValorNegativo("Error: el valor no puede ser un valor negativo. Intente nuevamente.");
        }

        if(id == null){
            throw new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }
        if(!esNumero(String.valueOf(id))){
            throw new ExcepcionReservaCaracterInvalido("Error: la entrada no es un número válido. Intente nuevamente. Intente nuevamente.");
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
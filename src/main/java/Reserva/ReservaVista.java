package Reserva;

import Excepciones.Reservas.ExcepcionReservaCamposVacios;
import Excepciones.Reservas.ExcepcionReservaCaracterInvalido;
import Excepciones.Reservas.ExcepcionReservaValorNegativo;
import MesasReservadas.MesasReservadas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservaVista {
    public static Scanner scanner = new Scanner(System.in);

    public Reserva pedirFecha() throws ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios, ExcepcionReservaValorNegativo {
        System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
        LocalDateTime fecha = LocalDateTime.parse(scanner.nextLine());
        if(fecha == null){
            throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }
        if(!esNumero(String.valueOf(fecha))){
            throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy. Intente nuevamente.");
        }

        return new Reserva(fecha);
    }

    public Integer pedirCantidadPersonas() throws ExcepcionReservaValorNegativo, ExcepcionReservaCaracterInvalido, ExcepcionReservaCamposVacios {
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
        return cantPersonas;
    }

    public Integer modificarCantPersonas() throws ExcepcionReservaValorNegativo, ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido {
        Integer cantPersonas = 0;

        System.out.println("Ingrese Cantidad de personas: ");
        cantPersonas = scanner.nextInt();

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
        return cantPersonas;
    }

    public String preguntarQueModificar(){
        String nombre = null;
        Integer opcion;
        do {
            System.out.println("Que desea modificar: ");
            System.out.println("1. Fecha");
            System.out.println("2. Cantidad de Personas ");
            opcion = scanner.nextInt();
            switch (opcion){
                case 1:
                    nombre = "fecha";
                    break;
                case 2:
                    nombre = "cantPersonas";
                    break;
            }
        }while (opcion != 1 && opcion != 2);

        return nombre;
    }

    public LocalDateTime modificarFecha() throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        LocalDateTime fecha = null;
        System.out.println("Ingrese la nueva fecha. ");

        fecha = LocalDateTime.parse(scanner.nextLine());
        if(fecha == null){
            throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }
        if(!esNumero(String.valueOf(fecha))){
            throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy. Intente nuevamente.");
        }
        return fecha;
    }

    public LocalDateTime buscarFechaReserva() throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        LocalDateTime fecha = null;
        System.out.println("Ingrese fecha: ");
        fecha = LocalDateTime.parse(scanner.nextLine());

        if(fecha == null){
            throw  new ExcepcionReservaCamposVacios("Error: el campo no puede estar vacio. Intente nuevamente.");
        }
        if(!esNumero(String.valueOf(fecha))){
            throw new ExcepcionReservaCaracterInvalido("Error: el formato de fecha no es válido. Debe ser dd/MM/yyyy. Intente nuevamente.");
        }

        return fecha;
    }

    public void mostrarReservaConArreglo(List<Reserva> reservas, List<MesasReservadas> mesasReservadas){
        for(Reserva reserva:reservas){
            System.out.println("Fecha: " +reserva.getFecha());
            System.out.println("Todas las Reservas");
            for(MesasReservadas mesas: mesasReservadas){
                System.out.println("Cliente: " +mesas.getCliente().getNombre());
                System.out.println("Cantidad de Personas: " +mesas.getCantPersonas());
            }
        }
    }

    public void mostrarReservasDisponibles(List<Reserva> reservas) {
        LocalDateTime hoy = LocalDateTime.now();

        for (Reserva reserva : reservas) {
            if (reserva.getMesasReservadas().isEmpty()) {
                LocalDateTime fechaInicio = reserva.getFecha();
                LocalDateTime fechaFin = fechaInicio.plusDays(15);

                if (fechaInicio.isAfter(hoy) && fechaInicio.isBefore(fechaFin)) {
                    System.out.println("Reservas disponibles desde " + fechaInicio + " hasta " + fechaFin + ": " + reserva);
                }
            }
        }
    }

    public void mensaje(String mensaje){
        System.out.println(mensaje);
    }

    private boolean esNumero(String str) {
        return str.matches("\\d+");
    }
}
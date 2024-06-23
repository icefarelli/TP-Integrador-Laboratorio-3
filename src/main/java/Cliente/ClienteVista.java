package Cliente;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionFormatoIncorrecto;
import Excepciones.ExcepcionNombreNumerico;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class ClienteVista {


    public Cliente agregarVista() {
        Scanner scann = new Scanner(System.in);
        System.out.println("Agregando cliente al sistema...");
        Cliente cliente = null;

        while (true) {
            try {
                System.out.println("Nombre y apellido: ");
                String name = scann.nextLine();
                validarNombre(name);

                System.out.println("Dni: ");
                String dni = scann.nextLine();
                validarDni(dni);

                System.out.println("Teléfono: ");
                String phone = scann.nextLine();
                validarTelefono(phone);

                cliente = new Cliente(name, dni, phone);
                System.out.println("Cliente cargado exitosamente.");
                break;
            } catch (ExcepcionCamposVacios | ExcepcionNombreNumerico | ExcepcionFormatoIncorrecto | ExcepcionDNIStringInvalido e) {
                System.out.println(e.getMessage());
                System.out.println("Por favor, intente de nuevo.");
            }
        }

        return cliente;
    }

    private void validarNombre(String nombre) throws ExcepcionCamposVacios, ExcepcionNombreNumerico {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionCamposVacios("El nombre y apellido no pueden estar vacíos.");
        }
        if (contieneNumeros(nombre)) {
            throw new ExcepcionNombreNumerico("El nombre no puede contener números.");
        }
    }

    private void validarDni(String dni) throws ExcepcionCamposVacios, ExcepcionFormatoIncorrecto, ExcepcionDNIStringInvalido {
        if (dni == null || dni.isEmpty()) {
            throw new ExcepcionCamposVacios("El DNI no puede estar vacío.");
        }
        if (!esNumero(dni)) {
            throw new ExcepcionFormatoIncorrecto("El DNI debe contener solo números.");
        }

        if (dni.isEmpty() || !dni.matches("\\d{7,8}")) {
            throw new ExcepcionDNIStringInvalido("El DNI debe contener números y ser de 7 u 8 dígitos");
        }
    }

    private void validarTelefono(String telefono) throws ExcepcionCamposVacios, ExcepcionFormatoIncorrecto {
        if (telefono == null || telefono.isEmpty()) {
            throw new ExcepcionCamposVacios("El teléfono no puede estar vacío.");
        }
        if (!esNumero(telefono)) {
            throw new ExcepcionFormatoIncorrecto("El teléfono debe contener solo números.");
        }
    }
    public Integer seleccId() {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Ingrese el id del cliente: ");
            try {
                id = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Formato de id incorrecto. Por favor, ingrese un id válido.");
                scanner.next(); // Limpiar el buffer del scanner
            }
        }
        return id;
    }

    public String newPhone () throws ExcepcionFormatoIncorrecto
    {
        System.out.println("Ingrese el nuevo numero del cliente: ");
        Scanner scanner = new Scanner(System.in);
        String phone = scanner.nextLine();

        if (!esNumero(phone)) {
            throw new ExcepcionFormatoIncorrecto("Formato de teléfono incorrecto. Por favor, ingrese un teléfono válido.");
        }
        return  phone;

    }


    public Integer selecIdRemove() throws ExcepcionFormatoIncorrecto {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id del cliente a eliminar:");

        String input = scanner.nextLine();
        Integer id = parseInteger(input);

        if (id == null) {
            throw new ExcepcionFormatoIncorrecto("Entrada inválida. No es un número entero.");
        }
        return id;
    }

    private Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean contieneNumeros(String str) {
        return str.matches(".*\\d.*");
    }

    public boolean esNumero(String str) {
        return str.matches("\\d+");
    }

    public void verTodosClientes(Set<Cliente> clienteSet) {
        System.out.println("\n");
        System.out.println("----------- LISTA DE CLIENTES -----------");
        for (Cliente cliente : clienteSet) {
            System.out.println(cliente.toString());
        }
        System.out.println("\n");

    }

    public Integer consultarCliente ()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese id del cliente que quiere consultar");
        Integer id = scanner.nextInt();

        return id;
    }

    public void verIdAndName (Set<Cliente> clienteSet)
    {
        for (Cliente cliente: clienteSet)
        {
            System.out.println("ID: "+cliente.getIdCliente());
            System.out.println("NOMBRE Y APELLIDO: "+cliente.getNombre());
            System.out.println(" \n--------------------------------\n");
        }
    }

    public void verClienteVersionCorta(Cliente c){
        System.out.println("ID: " + c.getIdCliente() + " " + "Nombre: " + c.getNombre());
    }
    public void verTodosClientesVersionCorta(Set<Cliente> clienteSet) {
        System.out.println("\n");
        System.out.println("------------Clientes cargados------------");
        for (Cliente cliente : clienteSet) {
            verClienteVersionCorta(cliente);
        }
        System.out.println("\n");

    }



}
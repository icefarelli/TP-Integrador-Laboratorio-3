package Cliente.view;

import Cliente.Excepciones.ExcepcionCamposVacios;
import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.Excepciones.ExcepcionNombreNumerico;
import Cliente.model.entitie.Cliente;

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
                if (name.isEmpty()) {
                    throw new ExcepcionCamposVacios("El nombre y apellido no pueden estar vacíos.");
                }
                if (contieneNumeros(name)) {
                    throw new ExcepcionNombreNumerico("El nombre no puede contener números.");
                }

                System.out.println("Dni: ");
                String dni = scann.nextLine();
                if (dni.isEmpty()) {
                    throw new ExcepcionCamposVacios("El DNI no puede estar vacío.");
                }
                if (!esNumero(dni)) {
                    throw new ExcepcionFormatoIncorrecto("El DNI debe contener solo números.");
                }

                System.out.println("Teléfono: ");
                String phone = scann.nextLine();
                if (phone.isEmpty()) {
                    throw new ExcepcionCamposVacios("El teléfono no puede estar vacío.");
                }
                if (!esNumero(phone)) {
                    throw new ExcepcionFormatoIncorrecto("El teléfono debe contener solo números.");
                }

                cliente = new Cliente(name, dni, phone);
                System.out.println("Cliente agregado exitosamente.");
                break; // Salir del bucle si se ingresaron correctamente todos los datos
            } catch (ExcepcionCamposVacios | ExcepcionNombreNumerico | ExcepcionFormatoIncorrecto e) {
                System.out.println(e.getMessage());
                System.out.println("Por favor, intente de nuevo.");
            }
        }

        return cliente;
    }

    public Integer seleccId ()
    {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Ingrese el id del cliente a modificar");
        Integer id = scanner.nextInt();
        return id;
    }

    public String newPhone ()
    {
        System.out.println("Ingres el nuevo numero del cliente: ");
        Scanner scanner = new Scanner(System.in);
        String phone = scanner.nextLine();
        return  phone;
    }

    /*public Integer selecIdRemove () throws ExcepcionFormatoIncorrecto
    {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el id del cliente a eliminar");
            Integer id = scanner.nextInt();
            return id;

    }*/
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

    private boolean contieneNumeros(String str) {
        return str.matches(".*\\d.*");
    }

    private boolean esNumero(String str) {
        return str.matches("\\d+");
    }

    public void verTodosClientes(Set<Cliente> clienteSet) {
        System.out.println("----------- LISTA DE CLIENTES -----------");
        for (Cliente cliente : clienteSet) {
            System.out.println(cliente.toString());
        }
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


}
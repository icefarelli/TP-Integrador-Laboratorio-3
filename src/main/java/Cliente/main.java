package Cliente;

import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.controller.ClienteControlador;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import GestionMenu.MenuClientes;

import javax.swing.*;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {



        ClienteVista clienteVista = new ClienteVista();
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        ClienteControlador clienteControlador = new ClienteControlador(clienteVista,clienteRepositorio);

        MenuClientes menuClientes = new MenuClientes(clienteControlador);
        menuClientes.menuCliente(clienteControlador);


    }
}

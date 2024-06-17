package Cliente.controller;

import Cliente.model.entitie.Cliente;
import Cliente.view.ClienteVista;
import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.model.repository.ClienteRepositorio;

public class ClienteControlador {

    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;


    public ClienteControlador(ClienteVista clienteVista,ClienteRepositorio clienteRepositorio) {
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
    }

    public ClienteVista getClienteVista() {
        return clienteVista;
    }

    public void setClienteVista(ClienteVista clienteVista) {
        this.clienteVista = clienteVista;
    }

    public ClienteRepositorio getClienteRepositorio() {
        return clienteRepositorio;
    }

    public void setClienteRepositorio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public Cliente  agregarClientes () {

        Cliente cliente = this.clienteVista.agregarVista();
        this.clienteRepositorio.addCliente(cliente);
        this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
        return cliente;
    }

    public void updateClientes ()
    {
        Integer id = this.clienteVista.seleccId();
        try{
        Cliente cliente = this.clienteRepositorio.findCliente(id,this.clienteRepositorio.getClienteSet());
        String phone = this.clienteVista.newPhone();
        this.clienteRepositorio.updateCliente(cliente,phone);
            System.out.println(cliente.toString());
        } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado)
        {
            System.out.println(excepcionClienteNoEncontrado.getMessage());
        }
    }

    public void removeCliente ()
    {
        Integer id = this.clienteVista.selecIdRemove();
        try
        {
            Cliente cliente = this.clienteRepositorio.findCliente(id,this.clienteRepositorio.getClienteSet());
            this.clienteRepositorio.removeCliente(cliente);
            System.out.println("------ NUEVA LISTA DE CLIENTES ------");
            this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
        }catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado)
        {
            System.out.println(excepcionClienteNoEncontrado.getMessage());
        }
    }
    public void viewClientes ()
    {
        this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
    }
}

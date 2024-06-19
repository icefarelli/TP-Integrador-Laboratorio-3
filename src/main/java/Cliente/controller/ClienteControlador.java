package Cliente.controller;

import Cliente.Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.model.entitie.Cliente;
import Cliente.view.ClienteVista;
import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.model.repository.ClienteRepositorio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


public class ClienteControlador {

    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;
    private Set<Cliente> clienteSet;
    private Gson gson = new Gson();
    private  static  final String PATH= "src/main/resources/Clientes.json";
    private static int lastId=0;


    public ClienteControlador(ClienteVista clienteVista, ClienteRepositorio clienteRepositorio) {
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
        this.clienteSet = new HashSet<>();
        this.loadGestionCurso();
    }

    public void loadGestionCurso()
    {
        try (Reader reader= new FileReader(PATH);)
        {
            Type type= new TypeToken<Set<Cliente>>(){}.getType();
            clienteSet = gson.fromJson(reader,type);
            if (clienteSet==null)
            {
                clienteSet = new HashSet<>();
            }
            else
            {
                clienteSet.forEach( cliente -> {
                    clienteRepositorio.addCliente(cliente);
                    lastId = cliente.getIdCliente();
                });

            }
        } catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
    }

    public Set<Cliente> getClienteSet() {
        return clienteSet;
    }

    public void Update ()
    {
        try (Writer writer = new FileWriter(PATH);)
        {
            gson.toJson(clienteSet,writer);
        }catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
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

    public void  agregarClientes () {

        Cliente cliente = this.clienteVista.agregarVista();
        lastId ++;
        cliente.setIdCliente(lastId);
        this.clienteRepositorio.addCliente(cliente);
        this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
        this.Update();
    }

    public void updateClientes ()
    {           Cliente cliente= null;
            Integer ok=0;
        do {
           try {
               Integer id = this.clienteVista.seleccId();

               cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
               String phone = this.clienteVista.newPhone();
               this.clienteRepositorio.updateCliente(cliente, phone);
               System.out.println("--- ¡ DATOS MODIFICADOS CON EXITO ! ---");
               ok=1;
               System.out.println(cliente.toString());
           } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
               System.out.println(excepcionClienteNoEncontrado.getMessage());
           }catch (ExcepcionFormatoIncorrecto excepcionFormatoIncorrecto)
           {
               System.out.println(excepcionFormatoIncorrecto.getMessage());
           }
       } while (ok==0);
        this.Update();

    }

    public void removeCliente ()
    {
        Cliente cliente = null;
        Integer id= null;

        do {
            try {
                id = this.clienteVista.selecIdRemove();
                 cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
                 this.clienteRepositorio.removeCliente(cliente);
                 System.out.println("------ NUEVA LISTA DE CLIENTES ------");
                 this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
            } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                System.out.println(excepcionClienteNoEncontrado.getMessage());
                cliente=null;
            }catch (ExcepcionFormatoIncorrecto excepcionFormatoIncorrecto)
                {
                    System.out.println(excepcionFormatoIncorrecto.getMessage());
                    cliente=null;
                }
         }while (cliente==null);
        this.Update();
    }
    public void viewClientes ()
    {
        this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
    }

    public void consultCliente ()  {
        this.clienteVista.verIdAndName(this.clienteRepositorio.getClienteSet());
        try {
            Integer id = this.clienteVista.consultarCliente();
            Cliente cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
            System.out.println("------------------------");
            System.out.println("Telefono: "+cliente.getTelefono());
            System.out.println("------------------------");
        }catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado)
        {
            System.out.println(excepcionClienteNoEncontrado.getMessage());
        }

    }


}

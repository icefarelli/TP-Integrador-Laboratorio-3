package Cliente;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.controller.ClienteControlador;
import Cliente.model.entitie.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

    public class GestionClientes {

    private Set<Cliente> clienteSet;
    private ClienteControlador clienteControlador;
    private Gson gson = new Gson();
    private  static  final String PATH= "src/main/resources/Clientes.json";

    public GestionClientes( ClienteControlador clienteControlador) {
        this.clienteSet = new HashSet<>();
        this.clienteControlador = clienteControlador;
        this.loadGestionCurso();
    }

    public void loadGestionCurso()
    {
        try (Reader reader= new FileReader(PATH);)
        {
            Type type= new TypeToken <Set<Cliente>>(){}.getType();
            clienteSet = gson.fromJson(reader,type);
            if (clienteSet==null)
            {
                clienteSet = new HashSet<>();
            }
            else
            {
                clienteSet.forEach( cliente -> {
                    this.clienteControlador.getClienteRepositorio().addCliente (cliente);
                });
            }
            } catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
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

    public Set<Cliente> getClienteSet() {
        return clienteSet;
    }


    public void addGestionClientes ()
    {
        Cliente cliente = this.clienteControlador.agregarClientes();
        clienteSet.add(cliente);
        this.Update();
    }


    public void viewAll ()
    {
        clienteControlador.viewClientes();
    }

    public void clienteToRemove ()  {

            Cliente cliente= this.clienteControlador.removeCliente();
            clienteSet.remove(cliente);
            this.Update();

    }

    public void viewPhoneCliente ()
    {

        this.clienteControlador.consultCliente();

    }

    public void updateGestionCliente ()
    {
        viewAll();
        this.clienteControlador.updateClientes();
        this.Update();
    }

    }

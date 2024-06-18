package Cliente.model.repository;

import Cliente.model.entitie.Cliente;
import Cliente.Excepciones.ExcepcionClienteNoEncontrado;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClienteRepositorio {

    private Set<Cliente> clienteSet = new HashSet<>();

   public void addCliente(Cliente cliente)
    {
        clienteSet.add(cliente);
    }

    public void removeCliente (Cliente cliente)
    {
        clienteSet.remove(cliente);
    }

    public Set<Cliente> getClienteSet() {
        return clienteSet;
    }

    public void setClienteSet (Set<Cliente> clienteSet) {
        this.clienteSet = clienteSet;
    }

    public Cliente findCliente (Integer id, Set <Cliente> clienteSet) throws ExcepcionClienteNoEncontrado {
         for (Cliente cliente: clienteSet)
         {
             if (cliente.getIdCliente().equals(id))
             {
                 return cliente;
             }
         }
         throw new ExcepcionClienteNoEncontrado("No se encontro ningun cliente con el ID: "+id);
    }

    public void updateCliente (Cliente cliente, String phone)
    {
        cliente.setTelefono(phone);
    }


}

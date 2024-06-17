package Orden;

import Cliente.ClienteRepositorio;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;


public class OrdenControlador {
    private OrdenRepositorio ordenRepositorio;
    private OrdenVista ordenVista;
    private ClienteRepositorio clienteRepositorio;
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;

    public OrdenControlador(OrdenRepositorio ordenRepositorio, OrdenVista ordenVista, ClienteRepositorio clienteRepositorio, PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        this.ordenRepositorio = ordenRepositorio;
        this.ordenVista = ordenVista;
        this.clienteRepositorio = clienteRepositorio;
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
    }

    /*public void crearOrden(Integer clienteId, List<String> nombresPlatillos) {
        Cliente cliente = clienteRepositorio.obtenerCliente(clienteId);
        List<Plato> platos = new ArrayList<>();
        do {
            Plato p = platoVista.pedirPlato(); // renombrar al método que este hecho
            platos.add(p);
        }while (true);

        Orden orden = new Orden(cliente, platos);
        ordenRepositorio.guardarOrden(orden);
        ordenVista.mostrarMensaje("Orden creada exitosamente.");
    }*/

    public void mostrarOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        try {
            Orden orden = ordenRepositorio.obtenerOrden(id);
            ordenVista.mostrarUnaOrden(orden);
        }catch (ExcepcionOrdenNoEncontrada e){
            e.mensaje();
        }
    }

}

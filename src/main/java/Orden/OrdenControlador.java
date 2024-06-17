package Orden;

import Cliente.Cliente;
import Cliente.ClienteRepositorio;
import Empleado.Empleado;
import Empleado.EmpleadoRepositorio;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Plato.Plato;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OrdenControlador {
    private OrdenRepositorio ordenRepositorio;
    private OrdenVista ordenVista;
    private ClienteRepositorio clienteRepositorio;
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;
    private EmpleadoRepositorio empleadoRepositorio;

    public OrdenControlador(OrdenRepositorio ordenRepositorio, OrdenVista ordenVista,
                            ClienteRepositorio clienteRepositorio, PlatoRepositorio platoRepositorio,
                            PlatoVista platoVista, EmpleadoRepositorio empleadoRepositorio) {
        this.ordenRepositorio = ordenRepositorio;
        this.ordenVista = ordenVista;
        this.clienteRepositorio = clienteRepositorio;
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
        this.empleadoRepositorio = empleadoRepositorio;
    }

    /*public void crearOrden(Integer clienteId, Integer empleadoId) {
        Scanner scan = new Scanner(System.in);

        // try catch con excepcion de no existencia de cliente
        Cliente cliente = clienteRepositorio.obtenerCliente(clienteId);

        // try catch con la excepcion de no existencia de empleado
        Empleado empleado = empleadoRepositorio.obtenerEmpleado(empleadoId);


        List<Plato> platos = new ArrayList<>();
        String op;
        do {
            //try - catch con la excepcion de no existencia de plato
            Integer idPlatoaBuscar = platoVista.pedirIdPlato(); // renombrar al método que este hecho
            Plato p = platoRepositorio.buscarPlato(idPlatoaBuscar); // metodo que busque por ID y retorne
            platos.add(p);

            System.out.println("Quiere agregar otro plato? Pulse 'n' para salir");
            op = scan.nextLine();
        } while (op != "n");

        Orden orden = new Orden(cliente, empleado);
        orden.setPlatoList(platos);
        ordenRepositorio.guardarOrden(orden);
        ordenRepositorio.guardarOrdenesEnArchivo();
        ordenVista.mostrarMensaje("Orden creada exitosamente.");
    }*/


    public void mostrarOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        try {
            Orden orden = ordenRepositorio.obtenerOrden(id);
            ordenVista.mostrarUnaOrden(orden);
        }catch (ExcepcionOrdenNoEncontrada e){
            e.mensaje();
        }
    }

    public void modificarOrden(Integer id) throws ExcepcionOrdenNoEncontrada{
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Orden orden = ordenRepositorio.obtenerOrden(id);
        // CONTINUAR


    }

}

package Orden;

import Cliente.Excepciones.ExcepcionClienteNoEncontrado;
import Cliente.model.entitie.Cliente;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Empleado.Empleado;
import Empleado.EmpleadoVista;
import Empleado.EmpleadoRepositorio;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Plato.Plato;
import Plato.PlatoRepositorio;
import Plato.PlatoControlador;
import Plato.PlatoVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class OrdenControlador {
    private OrdenRepositorio ordenRepositorio;
    private OrdenVista ordenVista;
    private EmpleadoRepositorio empleadoRepositorio;
    private EmpleadoVista empleadoVista;
    private ClienteRepositorio clienteRepositorio;
    private ClienteVista clienteVista;
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;
    private PlatoControlador platoControlador;

    public OrdenControlador(OrdenRepositorio ordenRepositorio, OrdenVista ordenVista,
                            EmpleadoRepositorio empleadoRepositorio,EmpleadoVista empleadoVista,
                            ClienteRepositorio clienteRepositorio, ClienteVista clienteVista,
                            PlatoRepositorio platoRepositorio, PlatoVista platoVista, PlatoControlador platoControlador) {
        this.ordenRepositorio = ordenRepositorio;
        this.ordenVista = ordenVista;
        this.empleadoRepositorio = empleadoRepositorio;
        this.empleadoVista = empleadoVista;
        this.clienteRepositorio = clienteRepositorio;
        this.clienteVista = clienteVista;
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
        this.platoControlador = platoControlador;
    }

    public void crearOrden() throws ExcepcionClienteNoEncontrado, ExcepcionDNIStringInvalido {
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Integer idOrden = ordenRepositorio.obtenerMap().lastKey();

        Set<Cliente> setClientes = clienteRepositorio.getClienteSet();
        Integer clienteId;
        Cliente c;
        do {
            clienteId = clienteVista.seleccId();
            c = clienteRepositorio.findCliente(clienteId, setClientes);
            if (c == null) {
                System.out.println("Cliente no encontrado");
            }
        } while (c == null);


        Integer claveEmpleado;
        Empleado e;
        do {
            claveEmpleado = empleadoVista.pedirClave();
            e = empleadoRepositorio.buscarEmpleado(claveEmpleado);
            if(e == null){
                empleadoVista.mensajeErrorBusqueda();
            }
        } while (e == null);


        List<Plato> platos = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        String op = null;
        do {
            //Plato p = platoControlador. -> agregar del controlador de plato que retorna un plato.
            Plato p = null; // borrar al descomentar lo de arriba
            platos.add(p);
            System.out.println("Ingrese 's' si quiere agregar otro plato: ");
            op = scan.nextLine();
        } while (op.equals("s"));

        Orden orden = new Orden(c, e);
        orden.setId(idOrden + 1);
        orden.setPlatoList(platos);

        ordenRepositorio.guardarOrden(orden);
        ordenVista.mostrarMensaje("Orden creada exitosamente.");
    }

    public void mostrarOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        try {
            Orden orden = ordenRepositorio.obtenerOrden(id);
            ordenVista.mostrarUnaOrden(orden);
        }catch (ExcepcionOrdenNoEncontrada e){
            e.mensaje();
        }
    }

    public void modificarOrden(){
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            Scanner scan = new Scanner(System.in);

            Integer idOrden = ordenVista.pedirIdOrdenModificar();
            Orden orden = ordenRepositorio.obtenerOrden(idOrden);
            if (orden == null) {
                ordenVista.mostrarMensaje("Orden no encontrada.");
                return;
            }

            // Mostrar la orden existente
            ordenVista.mostrarUnaOrden(orden);

            // Modificar los platos de la orden
            List<Plato> platos = new ArrayList<>(orden.getPlatoList());
            String op = null;

            do {
                System.out.println("Seleccione una opción: ");
                System.out.println("1. Agregar un plato");
                System.out.println("2. Quitar un plato");
                System.out.println("3. Reemplazar un plato");
                System.out.println("4. Finalizar modificaciones");
                System.out.print("Opción: ");
                int opcion = scan.nextInt();
                scan.nextLine();  // capturar el salto de linea desp del nextInt

                switch (opcion) {
                    case 1:
                        // Agregar un plato
                        //Plato nuevoPlato = platoControlador.seleccionarPlato(); // Método que selecciona y retorna un plato
                        Plato nuevoPlato = null; //borrar al tener la funcion para agregar plato
                        platos.add(nuevoPlato);
                        break;
                    case 2:
                        // Quitar un plato
                        System.out.print("Ingrese el índice del plato a quitar (empezando desde 0): ");
                        int indiceQuitar = scan.nextInt();
                        scan.nextLine();  // capturar el salto de linea desp del nextInt
                        if (indiceQuitar >= 0 && indiceQuitar < platos.size()) {
                            platos.remove(indiceQuitar);
                        } else {
                            System.out.println("Índice inválido.");
                        }
                        break;
                    case 3:
                        // Reemplazar un plato
                        System.out.print("Ingrese el índice del plato a reemplazar (empezando desde 0): ");
                        int indiceReemplazar = scan.nextInt();
                        scan.nextLine();  // capturar el salto de linea desp del nextInt
                        if (indiceReemplazar >= 0 && indiceReemplazar < platos.size()) {
                            //Plato platoReemplazo = platoControlador.seleccionarPlato(); // Método que selecciona y retorna un plato
                            Plato platoReemplazo = null; //borrar al tener la funcion para agregar plato
                            platos.set(indiceReemplazar, platoReemplazo);
                        } else {
                            System.out.println("Índice inválido.");
                        }
                        break;
                    case 4:
                        // Finalizar modificaciones
                        op = "n";
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }

                if (!"n".equals(op)) {
                    System.out.print("¿Desea continuar modificando la orden? (s/n): ");
                    op = scan.nextLine();
                }

            } while ("s".equals(op));

            // Guardar los cambios
            orden.setPlatoList(platos);
            ordenRepositorio.guardarOrden(orden);
            ordenVista.mostrarMensaje("Orden modificada exitosamente.");

        } catch (ExcepcionOrdenNoEncontrada e) {
            e.mensaje();
        }
    }

    public void eliminarOrden() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            Scanner scan = new Scanner(System.in);
            Integer idOrdenEliminar = ordenVista.pedirIdOrdenEliminar();  // Limpiar el buffer

            Orden orden = ordenRepositorio.obtenerOrden(idOrdenEliminar);

            if (orden == null) {
                ordenVista.mostrarMensaje("Orden no encontrada.");
                return;
            }

            // Confirmar la eliminación
            System.out.println("¿Está seguro de que desea eliminar la siguiente orden?");
            ordenVista.mostrarUnaOrden(orden);
            System.out.print("Ingrese 's' para confirmar, cualquier otra tecla para cancelar: ");
            String op = scan.nextLine();

            if (op.equalsIgnoreCase("s")) {
                ordenRepositorio.eliminarOrden(idOrdenEliminar);
                ordenVista.mostrarMensaje("Orden eliminada exitosamente.");
                ordenRepositorio.guardarOrdenesEnArchivo();
            } else {
                ordenVista.mostrarMensaje("Eliminación cancelada.");
            }
        } catch (ExcepcionOrdenNoEncontrada e) {
            e.mensaje();
        }
    }
}

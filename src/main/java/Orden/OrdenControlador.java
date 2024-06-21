package Orden;

import Excepciones.ExcepcionClienteNoEncontrado;
import Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.Cliente;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Cliente.ClienteControlador;
import Empleado.Empleado;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Empleado.EmpleadoControlador;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Plato.Plato;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;

import java.io.FileNotFoundException;
import java.util.*;


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
                            EmpleadoRepositorio empleadoRepositorio, EmpleadoVista empleadoVista,
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


    public void crearOrden() throws ExcepcionClienteNoEncontrado, ExcepcionDNIStringInvalido, ExcepcionFormatoIncorrecto, FileNotFoundException {

        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Integer idOrden;
        if (ordenRepositorio.obtenerMap().isEmpty() || ordenRepositorio.obtenerMap() == null) {
            idOrden = 0;
        } else {
            idOrden = ordenRepositorio.obtenerMap().lastKey();
        }

        ClienteControlador clienteControlador = new ClienteControlador(clienteVista, clienteRepositorio);
        clienteControlador.loadGestionCliente();
        Set<Cliente> setClientes = clienteRepositorio.getClienteSet();
        Integer clienteId;
        Cliente c = null;
        do {
            clienteId = clienteVista.seleccId();
            try {
                c = clienteRepositorio.findCliente(clienteId, setClientes);
            } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                System.out.println(excepcionClienteNoEncontrado.getMessage());
            }
        } while (c == null);


        EmpleadoControlador empleadoControlador = new EmpleadoControlador(empleadoVista, empleadoRepositorio);
        empleadoControlador.pasarAMemoria();
        Integer claveEmpleado;
        Empleado e;
        do {
            claveEmpleado = empleadoVista.pedirClave();
            e = empleadoRepositorio.buscarEmpleado(claveEmpleado);
            if (e == null) {
                empleadoVista.mensajeErrorBusqueda();
            }
        } while (e == null);


        List<Plato> platos = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        String op;
        do {
            Plato p = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista);
            if (p != null) {
                platos.add(p);
            } else {
                System.out.println("Plato no agregado");
            }
            System.out.println("Ingrese 's' si quiere agregar otro plato: ");
            op = scan.nextLine();
        } while (op.equals("s"));

        if (!platos.isEmpty()) {
            Orden orden = new Orden(c, e);
            orden.setId(idOrden + 1);
            orden.setPlatoList(platos);

            ordenRepositorio.guardarOrden(orden);
            ordenRepositorio.guardarOrdenesEnArchivo();
            ordenVista.mostrarMensaje("Orden creada exitosamente.");
        }else {
            System.out.println("Orden cancelada, no se agrego ningun plato");
        }

    }

    public void modificarOrden() throws ExcepcionEntradaInvalida {

        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Scanner scan = new Scanner(System.in);

        try {
            Integer idOrden = ordenVista.pedirIdOrdenModificar();
            Orden orden = ordenRepositorio.obtenerOrden(idOrden);
            mostrarOrden(orden.getId());
            List<Plato> platos = new ArrayList<>(orden.getPlatoList());
            int opcion;

            do {
                opcion = 0;
                try {
                    System.out.println("Seleccione una opción: ");
                    System.out.println("1. Agregar un plato");
                    System.out.println("2. Quitar un plato");
                    System.out.println("3. Reemplazar un plato");
                    System.out.println("4. Finalizar modificaciones");
                    System.out.print("Opción: \n");
                    opcion = scan.nextInt();
                    scan.nextLine();  // capturar el salto de linea desp del nextInt
                    switch (opcion) {
                        case 1:
                            // Agregar un plato
                            Plato nuevoPlato = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista); // Método que selecciona y retorna un plato
                            if (nuevoPlato != null) {
                                platos.add(nuevoPlato);
                            } else {
                                System.out.println("Plato no agregado");
                            }
                            break;
                        case 2:
                            // Quitar un plato
                            System.out.print("Ingrese el índice del plato a quitar (empezando desde 0): \n");
                            ordenVista.mostrarPlatosDeOrden(orden);
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
                            System.out.print("Ingrese el índice del plato a reemplazar (empezando desde 0): \n");
                            ordenVista.mostrarPlatosDeOrden(orden);
                            int indiceReemplazar = scan.nextInt();
                            scan.nextLine();  // capturar el salto de linea desp del nextInt
                            if (indiceReemplazar >= 0 && indiceReemplazar < platos.size()) {
                                Plato platoReemplazo = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista);
                                if (platoReemplazo != null) {
                                    platos.set(indiceReemplazar, platoReemplazo);
                                } else {
                                    System.out.println("Plato no agregado");
                                }
                            } else {
                                System.out.println("Índice inválido.");
                            }
                            break;
                        case 4:
                            // Finalizar modificaciones
                            opcion = -1;
                            break;
                        default:
                            System.out.println("Opción inválida. Intente nuevamente.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                    scan.nextLine();
                }
            } while (opcion != -1);
            // Guardar los cambios
            orden.setPlatoList(platos);
            ordenRepositorio.guardarOrden(orden);
            ordenRepositorio.guardarOrdenesEnArchivo();
            ordenVista.mostrarMensaje("Orden modificada exitosamente.");
        } catch (ExcepcionOrdenNoEncontrada excepcionOrdenNoEncontrada) {
            System.out.println(excepcionOrdenNoEncontrada.getMessage());
        } catch (ExcepcionEntradaInvalida excepcionEntradaInvalida) {
            System.out.println(excepcionEntradaInvalida.getMessage());
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
            mostrarOrden(orden.getId());
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
            System.out.println(e.getMessage());
        } catch (ExcepcionEntradaInvalida excepcionEntradaInvalida) {
            System.out.println(excepcionEntradaInvalida.getMessage());
        }
    }

    public void mostrarTodasLasOrdenes() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            if (ordenRepositorio.obtenerMap().isEmpty()) {
                ordenVista.mostrarMensaje("No hay órdenes disponibles.");
                return;
            }

            for (Orden orden : ordenRepositorio.obtenerMap().values()) {
                mostrarOrden(orden.getId());
            }

        } catch (Exception e) {
            ordenVista.mostrarMensaje("Ocurrió un error al cargar las órdenes.");
            e.printStackTrace();
        }
    }

    public void mostrarOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        try {
            Orden orden = ordenRepositorio.obtenerOrden(id);
            ordenVista.mostrarUnaOrden(orden);
            System.out.println("------------------");
            System.out.println("Total  $ " + ordenRepositorio.calcularTotalOrden(orden.getId()));
            System.out.println("------------------");
            System.out.println("------------------------------------------------");
        } catch (ExcepcionOrdenNoEncontrada e) {
            System.out.println(e.getMessage());
        }
    }


}

package Empleado;

import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.FileSystemLoopException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmpleadoControlador {

    private EmpleadoVista empleadoVista = new EmpleadoVista();
    private EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();

    public EmpleadoControlador(EmpleadoVista empleadoVista, EmpleadoRepositorio empleadoRepositorio) {
        this.empleadoVista = empleadoVista;
        this.empleadoRepositorio = empleadoRepositorio;
    }

    Gson gson = new Gson();

    private static final String PATH ="src/main/resources/Empleados.json";

    public void pasarAarchivo() throws IOException {
        try (Writer writer = new FileWriter(PATH)){
            gson.toJson(empleadoRepositorio.listaEmpleados,writer);
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public void pasarAMemoria() throws FileNotFoundException {
        try(Reader reader = new FileReader(PATH)) {
          Type type = new TypeToken<TreeMap<Integer,Empleado>>(){}.getType();
          empleadoRepositorio.listaEmpleados = gson.fromJson(reader,type);
          if(empleadoRepositorio.listaEmpleados == null){
              empleadoRepositorio.listaEmpleados = new TreeMap<Integer, Empleado>();
          }
        } catch (IOException io) {
            System.out.println(io.getMessage());

        }
    }

    public void agregarEmpleado() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException {
        pasarAMemoria();
        Integer clave;
        Empleado empleado = empleadoVista.pedirUnEmpleado();
        Empleado empleadoEncontrado = empleadoRepositorio.buscarEmpleadoPorDNI(empleado.getId());
        if(empleadoEncontrado!=null){
            System.out.println("Empleado ya cargado en el sistema");

        } else if (!empleadoRepositorio.listaEmpleados.isEmpty() ) {
            clave = empleadoRepositorio.listaEmpleados.lastKey();
            empleado.setIdEmpleado(clave + 1);
            empleadoRepositorio.agregar(empleado);
            System.out.println("Empleado agregado con exito");
        } else {
            empleadoRepositorio.agregar(empleado);
            System.out.println("Empleado agregado con exito");
        }
        pasarAarchivo();
    }

    public void eliminarEmpleado() throws IOException, ExcepcionDNIStringInvalido {
        pasarAMemoria();
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if(empleado!=null){
            empleadoRepositorio.eliminar(empleado);
            System.out.println("Empleado eliminado con exito");
        } else {
            empleadoVista.mensajeErrorBusqueda();
        }
        pasarAarchivo();
    }

    public void modificarEmpleado() throws IOException, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido {
        pasarAMemoria();
        Empleado empleadoModificado;
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if(empleado!=null){
            String modificacion = empleadoVista.pedirModificacion();
            if(modificacion.equalsIgnoreCase("nombre")) {
                String nombreNuevo = empleadoVista.pedirNombreParaModificar();
                empleadoModificado = new Empleado(nombreNuevo,empleado.getId(),empleado.getIdEmpleado(),empleado.getPuesto());
                empleadoRepositorio.agregar(empleadoModificado);
                System.out.println("Empleado modificado correctamente");
            } else if(modificacion.equalsIgnoreCase("puesto")) {
                String puestoNuevo = empleadoVista.elegirPuesto();
                empleadoModificado = new Empleado(empleado.getNombre(),empleado.getId(),empleado.getIdEmpleado(),puestoNuevo);
                empleadoRepositorio.agregar(empleadoModificado);
                System.out.println("Empleado modificado correctamente");
            }
        } else {
            empleadoVista.mensajeErrorBusqueda();
        }
        pasarAarchivo();
    }

    public void mostrarListaEmpleados() throws IOException {
        pasarAMemoria();
        Map<Integer, Empleado> lista = empleadoRepositorio.getListaEmpleados();
        empleadoVista.mostrarListaEmpleados(lista);
        pasarAarchivo();
    }

    public void mostrarUnEmpleado() throws IOException {
        pasarAMemoria();
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if (empleado==null){
            System.out.println("Empleado no cargado en el sistema");
        } else {
            empleadoVista.mostrarEmpleado(empleado);
        }
        pasarAarchivo();

    }

}

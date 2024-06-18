package Plato;
import Plato.Excepciones.ExcepBusquedaSinResultados;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadRepositorio;
import Plato.Variedad.VariedadVista;
import java.util.List;
import java.util.Scanner;

public class PlatoControlador {
    private static Scanner scanner = new Scanner(System.in);

    PlatoRepositorio platoRepositorio;
    PlatoVista platoVista;
    VariedadController variedadController = new VariedadController(new VariedadVista(), new VariedadRepositorio());

    public PlatoControlador(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
    }

    //Agregar Plato
    public void cargarPlatoEnSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido {
        Plato nuevoPlato = platoVista.nuevoPlato();
        if (nuevoPlato != null){
            platoRepositorio.agregar(nuevoPlato);
        }else {
            throw new ExcepIngresoInvalido("No se realizo la Carga correctamente");
        }
    }

    //Eliminar Plato
    public void eliminarPlatosDelSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        Plato buscado = platoRepositorio.buscarPlatoXnombre(platoVista.ingreseNombreDePlato());
        platoRepositorio.eliminar(buscado);
    }

    //Aumentar precio de manera porcentual
    public void aumentoPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.aumentoPorcentualPrecio(platoVista.ingresePorcentaje());
    }

    //Actualizar Platos y sus variantes
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepBusquedaSinResultados, ExcepIngresoInvalido {
        String tipo = platoVista.menuTipoComida();
        platoRepositorio.mostrarPlatillosXtipo(tipo);
        String nombre = platoVista.ingreseNombreDePlato();
        Plato buscado = platoRepositorio.buscarPlatoXnombre(nombre);
        if(buscado!=null){
            buscado = platoVista.actualizarPlato(tipo,nombre);
            if(buscado!=null){
                platoRepositorio.modificar(buscado);
            }else {
                throw new ExcepIngresoInvalido("Carga de datos Invalida. \nIntente nuevamente.");
            }
            platoRepositorio.modificar(buscado);
        }else {
            throw new ExcepBusquedaSinResultados("El plato no existe. \nIntente nuevamente.");
        }
    }
    //Seleccion de Plato para Orden que devuelve el plato seleccionado
    public static Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido {

        List<Plato> listaP = platoRepositorio.enlistarXTipo(platoVista.menuTipoComida());

        for (int i = 0; i < listaP.size(); i++) {
            System.out.println(i + ": " + listaP.get(i).getNombre());
        }

        int indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);
        if (indiceSeleccionado == -1) {
            throw new ExcepIngresoInvalido("Seleccion invalida. \nIntente nuevamente.");

        }
        return listaP.get(indiceSeleccionado);
    }











}

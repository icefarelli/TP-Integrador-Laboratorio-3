package Plato;
import Plato.Excepciones.ExcepBusquedaSinResultados;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.List;


public class PlatoControlador {
    private PlatoRepositorio platoRepositorio = new PlatoRepositorio();
    private PlatoVista platoVista = new PlatoVista();


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
    public void eliminarPlatosDelSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido {
        Plato buscado = platoRepositorio.buscarPlatoXnombre(platoVista.ingreseNombreDePlato());
        platoRepositorio.eliminar(buscado);
    }

    //Aumentar precio de manera porcentual
    public void aumentoPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.aumentoPorcentualPrecio(platoVista.ingresePorcentaje());
    }

    //Actualizar Platos y sus variantes
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido, RuntimeException {
        String tipo = platoVista.menuTipoComida();
        platoRepositorio.mostrarPlatosXtipo(tipo);

        String nombre = platoVista.ingreseNombreDePlato();
        Plato buscado = platoRepositorio.buscarPlatoXnombre(nombre);

        if (buscado != null) {
            Plato actualizado = platoVista.actualizarPlato(tipo, nombre);
            if (actualizado != null) {
                platoRepositorio.modificar(actualizado);
            } else {
                throw new ExcepIngresoInvalido("Carga de datos Invalida. \nIntente nuevamente.");
            }
        } else {
            throw new ExcepIngresoInvalido("Error en la busqueda. \nIntente nuevamente.");
        }
    }


    //Selección de Plato para Orden que devuelve el plato seleccionado
    public static Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {

        String tipo = platoVista.menuTipoComida();
        List<Plato> listaP = platoRepositorio.enlistarXTipo(tipo);

        platoRepositorio.mostrarEnlistadosBonito(tipo);

        int indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);
        if (indiceSeleccionado == -1) {
        }
        return listaP.get(indiceSeleccionado);
    }

    public void mostrarPlatosXTipo (PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.mostrarEnlistadosBonito(platoVista.menuTipoComida());
    }


}

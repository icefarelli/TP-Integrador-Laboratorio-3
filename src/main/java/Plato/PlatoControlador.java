package Plato;
import Plato.Excepciones.ExcepBusquedaSinResultados;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.List;


public class PlatoControlador {
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;


    public PlatoControlador(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
    }

    //Agregar Plato
    public void cargarPlatoEnSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido {

        try {
            Plato nuevoPlato = platoVista.nuevoPlato();
            if (nuevoPlato != null) {
                boolean confirmacion = platoVista.metodoConfirmacion("¿Desea confirmar la carga?");

                if (confirmacion) {
                    platoRepositorio.agregar(nuevoPlato);
                    platoVista.mensajeCargaExitoFracaso(confirmacion);
                } else {
                    platoVista.mensajeCargaExitoFracaso(confirmacion);
                }
            } else {
                throw new ExcepIngresoInvalido("No se realizo la Carga correctamente");
            }
        }catch (ExcepIngresoInvalido e) {
            platoVista.mensajePersonalizado("Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            platoVista.mensajePersonalizado("Ocurrió un error inesperado al cargar el plato: " + e.getMessage());
//            e.printStackTrace();
            throw new ExcepIngresoInvalido("No se realizó la carga correctamente debido a un error inesperado.");
        }
    }

    //Eliminar Plato
    public void eliminarPlatosDelSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        Plato buscado = platoRepositorio.buscarPlatoXnombre(platoVista.ingresarNombre());
        boolean confirmEliminar = platoVista.metodoConfirmacion("¿Confirmar la eliminacion?");
        if (confirmEliminar){
            platoRepositorio.eliminar(buscado);
        }else {
            platoVista.mensajeEliminacionCancelada();
        }

    }

    //Aumentar precio de manera porcentual
    public void aumentoPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.aumentoPorcentualPrecio(platoVista.ingresePorcentaje());
    }

    //Actualizar Platos y sus variantes
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido, RuntimeException {
        String tipo = platoVista.menuTipoComida();
        platoRepositorio.mostrarPlatosXtipo(tipo);

        String nombre = platoVista.ingresarNombre();
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
    public Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {

        String tipo = platoVista.menuTipoComida();
        List<Plato> listaP = platoRepositorio.enlistarXTipo(tipo);
        int indiceSeleccionado = 0;
        do {
            platoRepositorio.mostrarEnlistadosBonito(tipo);
            indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);
            if (indiceSeleccionado == -1) {
                System.out.println("Selección cancelada.");
                return null;
            } else if (indiceSeleccionado > listaP.size()) {
                try {
                    throw new ExcepIngresoInvalido("Opcion Inexistente. Intente Nuevamente");
                } catch (ExcepIngresoInvalido e) {
                    throw new RuntimeException(e);
                }
            }
        }while (indiceSeleccionado > listaP.size());
        return listaP.get(indiceSeleccionado);
    }

    //Mostrar Platos por categoria
    public void mostrarPlatosXTipo (PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.mostrarEnlistadosBonito(platoVista.menuTipoComida());
    }


}

package Plato;
import Plato.Excepciones.ExcepCargaNoRealizada;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PlatoControlador {
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;

    public PlatoControlador(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
    }

    //Agregar Plato
    public void cargarPlatoEnSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
//
        Plato nuevoPlato = null;
        try {
            nuevoPlato = platoVista.nuevoPlato();

            if (nuevoPlato != null) {
                boolean confirm = platoVista.metodoConfirmacion("¿Desea confirmar la carga?");

                if (confirm) {
                    platoRepositorio.agregar(nuevoPlato);
                    platoVista.mensajeCargaExitoFracaso(true);
                } else {
                    platoVista.mensajeCargaExitoFracaso(false);
                }
            }
        } catch (ExcepCargaNoRealizada e) {
            throw new RuntimeException(e);
        }
    }

    //Eliminar Plato
    public void eliminarPlatosDelSistemaXNombre(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        Plato buscado;

        try {
            String nombre = platoVista.ingresarNombre();

            buscado = platoRepositorio.buscarPlatoXnombre(nombre);

            if( buscado!= null) {
                boolean confirmEliminar = platoVista.metodoConfirmacion("¿Confirmar la eliminacion?");
                if (confirmEliminar) {
                    platoRepositorio.eliminar(buscado);
                    platoVista.mensajeEliminacionExitoFracaso(confirmEliminar);
                } else {
                    platoVista.mensajeEliminacionExitoFracaso(confirmEliminar);
                }
            }else {
                platoVista.mensajeEliminacionPlatoInexistente();
                platoVista.mensajeEliminacionExitoFracaso(false);
            }
        }catch (ExcepIngresoInvalido eii){
            platoVista.mensajePersonalizado("Error: " + eii.getMessage());
        }
    }

    public void eliminarPlatosDelSistemaXSeleccion(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        try {
            Plato buscado;
            String tipo = platoVista.menuTipoComida();
            List<Plato> listaP = platoRepositorio.enlistarXTipoSinVariedad(tipo);
            int indiceSeleccionado = 0;
            do {
                platoRepositorio.mostrarEnlistadoBonitoXtipoOld(tipo);
                indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);
                if (indiceSeleccionado == -1) {
                    System.out.println("Selección cancelada.");
                    buscado = null;
                } else if (indiceSeleccionado > listaP.size()) {
                    try {
                        throw new ExcepIngresoInvalido("Opcion Inexistente. Intente Nuevamente");
                    } catch (ExcepIngresoInvalido e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    buscado = listaP.get(indiceSeleccionado);
                }
            }while (indiceSeleccionado > listaP.size());


            if (buscado != null) {
                boolean confirmEliminar = platoVista.metodoConfirmacion("¿Confirmar la eliminacion?");
                if (confirmEliminar) {
                    platoRepositorio.eliminar(buscado);
                    platoVista.mensajeEliminacionExitoFracaso(confirmEliminar);
                } else {
                    platoVista.mensajeEliminacionExitoFracaso(confirmEliminar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Actualizar Platos y sus variantes
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws ExcepIngresoInvalido, IOException {
        String tipo = platoVista.menuTipoComida();
        platoRepositorio.mostrarEnlistadoBonitoXtipoOld(tipo);

        String nombre;
        do {
            nombre = platoVista.ingresarNombre();
        }while (nombre==null);

        Plato buscado;
        try {
            buscado = platoRepositorio.buscarPlatoXnombre(nombre);
        } catch (ExcepIngresoInvalido e) {
            throw new RuntimeException(e);
        }

        if (buscado == null) {
            platoVista.mensajePersonalizado("Plato no encontrado");
        }else {
            System.out.println(buscado.toString());
            Plato actualizado = platoVista.actualizarPlato(tipo, nombre);
            if (actualizado == null) {
                throw new ExcepIngresoInvalido("Carga de datos inválida. \nIntente nuevamente.");
            }
            System.out.println(actualizado.toString());

            boolean confirmUpdate = platoVista.metodoConfirmacion("¿Confirmar la actualizacion?");
            if(confirmUpdate){
                platoRepositorio.modificar(actualizado);
                platoVista.mensajeCargaExitoFracaso(confirmUpdate);
            }else {
                platoVista.mensajeCargaExitoFracaso(confirmUpdate);
            }
        }
    }

    //Aumentar precio de manera porcentual
    public void aumentoPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        platoRepositorio.aumentoPorcentualPrecio(platoVista.ingresePorcentaje());
    }

    //Selección de Plato para Orden que devuelve el plato seleccionado
    public Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {

        String tipo = platoVista.menuTipoComida();
        List<Plato> listaP = new ArrayList<>();
        if(tipo !=null){
            listaP = platoRepositorio.enlistarXTipo(tipo);
        }else {
            return null;
        }

        int indiceSeleccionado = 0;
        do {
            platoRepositorio.mostrarEnlistadoBonitoXtipoOld(tipo);
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
        platoRepositorio.mostrarEnlistadoBonitoXtipoOld(platoVista.menuTipoComida());
    }

    //Mostrar Menu Completo
    public void mostrarMenuCompleto(PlatoRepositorio platoRepositorio){
        platoRepositorio.mostrarCartaDePlatosCompleta();
    }


    //metodo simil limpiar pantalla
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) { // Puedes ajustar el número de líneas según sea necesario
            System.out.println();
        }
    }
    //metodo pausar pantalla
    public static void pausarPantalla(PlatoVista platoVista) {
        platoVista.pausarPantalla();
    }

}

package Plato;

import Plato.Excepciones.ExcepCargaInvalida;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Variedad.Variedad;
import Plato.Variedad.VariedadController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlatoControlador {
    PlatoRepositorio platoRepositorio;
    PlatoVista platoVista;
    private static Scanner scanner = new Scanner(System.in);

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
            throw new RuntimeException("No se realizo la Carga correctamente");
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
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista){
        List<Variedad> listaVar = new ArrayList<>();
        VariedadController variedadController = new VariedadController();
        platoRepositorio.mostrarPlatillosXtipo(platoVista.menuTipoComida());
        Plato buscado = platoRepositorio.buscarPlatoXnombre(platoVista.ingreseNombreDePlato());
        if(buscado!=null){
            System.out.println("¿Desea Cargar Variedades? (s/n):");
            boolean tieneVariedades = scanner.nextLine().trim().equalsIgnoreCase("s");
            if (tieneVariedades) {
                boolean agregarMas;
                do {
                    Variedad variedad = variedadController.cargaDeVaridedad();
                    if(variedad!=null){
                        listaVar.add(variedad);
                    }else {
                        System.out.println("No se realizo la carga correctamente.");
                    }
                    System.out.println("¿Desea agregar otra variedad? (s/n):");
                    agregarMas = scanner.nextLine().trim().equalsIgnoreCase("s");
                } while (agregarMas);
            }else {
                buscado.setPrecio(platoVista.ingresePrecioDePlato());
            }

            buscado.setVariedades(listaVar);
        }
        platoRepositorio.modificar(buscado);
    }

    public static Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista){

        List<Plato> listaP = platoRepositorio.enlistarXTipo(platoVista.menuTipoComida());

        for (int i = 0; i < listaP.size(); i++) {
            System.out.println(i + ": " + listaP.get(i).getNombre());
        }

        System.out.println("Ingrese el índice del plato que desea seleccionar: ");
        int indiceSeleccionado = scanner.nextInt();
        scanner.nextLine();

        if (indiceSeleccionado < 0 || indiceSeleccionado >= listaP.size()) {
            System.out.println("Índice inválido. Selección cancelada.");
            return null;
        }

        return listaP.get(indiceSeleccionado);
    }











}

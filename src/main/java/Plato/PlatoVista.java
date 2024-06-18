package Plato;
import Plato.Variedad.Variedad;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadRepositorio;
import Plato.Variedad.VariedadVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlatoVista {

    private static final Scanner scanner = new Scanner(System.in);
    VariedadController variedadController = new VariedadController(new VariedadVista(), new VariedadRepositorio());

    //Crear un nuevo plato para el menu

    public Plato nuevoPlato() throws NumberFormatException {

        System.out.println("-Tipo de Comida:");
        String tipo = menuTipoComida();

        System.out.println("-Nombre del Plato");
        String nombre = scanner.nextLine();

        System.out.println("-¿Este plato tiene variedades? (s/n):");
        boolean tieneVariedades = scanner.nextLine().trim().equalsIgnoreCase("s");

        if (tieneVariedades) {
            List<Variedad> listaVariedad = variedadController.crearListaDeVariedades(new VariedadVista(),new VariedadRepositorio());
            return new Plato(tipo,nombre,listaVariedad);
        } else {
            double price = variedadController.cargarPrecio(new VariedadVista());
            if (price != 0.0) {
                return new Plato(tipo, nombre, price);
            } else {
                System.out.println("El Plato no pudo ser creado.");
                return null;
            }
        }
    }
    //Actualizar un plato para el menu

    public Plato actualizarPlato(String tipo, String nombre) throws NumberFormatException {

        System.out.println("-¿Este plato tiene variedades? (s/n):");
        boolean tieneVariedades = scanner.nextLine().trim().equalsIgnoreCase("s");

        if (tieneVariedades) {
            List<Variedad> listaVariedad = variedadController.crearListaDeVariedades(new VariedadVista(),new VariedadRepositorio());
            return new Plato(tipo,nombre,listaVariedad);
        } else {
            double price = variedadController.cargarPrecio(new VariedadVista());
            if (price != 0.0) {
                return new Plato(tipo, nombre, price);
            } else {
                System.out.println("El Plato no pudo ser creado.");
                return null;
            }
        }
    }

    // Seleccion de categoria por seleccion en menu. Devuelve el nombre en String para evitar errores de carga
    public String menuTipoComida(){
        int opcion;
        System.out.println(" Seleccione categoría:\n 1. Entradas\n 2. Carnes\n 3. Minutas");
        System.out.println(" 4. Pizzas\n 5. Pastas\n 6. Guarniciones y Ensaladas");
        System.out.println(" 7. Postres\n 8. Bebidas\n 9. Cafeteria y Pastelería\n 0.Salir");
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                return "Entradas";
            case 2:
                return "Carnes";
            case 3:
                return "Minutas";
            case 4:
                return "Pizzas";
            case 5:
                return "Pastas";
            case 6:
                return "Guarniciones y Ensaladas";
            case 7:
                return "Postres";
            case 8:
                return "Bebidas";
            case 9:
                return "Cafeteria y Pastelería";
            default:if (opcion!=0) {
                System.out.println("Tipo de Comida Inexistente");
            }
        }
        return null;
    }

    // Método de ingreso de porcentaje para aumentar el valor de todos los
    // platos del menu de manera porcentual con un tope máximo de 100
    public double ingresePorcentaje (){
        try {
            System.out.println("Ingrese el porcentaje que desea aumentar en los precios totales.\nRecuerde que el valor a ingresar debe tener un minimo de 1% y un tope de 100%");
            double aumento = scanner.nextDouble();
            scanner.nextLine();
            while (aumento < 0 || aumento >= 100) {
                System.out.println("El valor ingresado se halla fuera del margen permitido\nIntente nuevamente.");
                aumento = scanner.nextDouble();
                scanner.nextLine();
            }
            return aumento;
        }catch (NumberFormatException nfe){
            System.out.println("Formato Invalido");
        }
        return 0;
    }

    // Método de búsqueda de platos por categoría devuelve todos los contenidos en una categoría específica.-
//    public String busquedaXCategoria(){
//        System.out.println("----BUSQUEDA DE PLATOS POR CATEGORIA---");
//        return this.menuTipoComida();
//    }

    public String ingreseNombreDePlato(){
        System.out.println("Ingrese nombre del Plato a seleccionar");
        return scanner.nextLine();
    }

    public double ingresePrecioDePlato(){
        System.out.println(" Ingrese Valor en pesos (Formato X.XX): $");
        return variedadController.checkPrecio(scanner.nextLine());

    }
    public int obtenerIndiceSeleccionado(List<Plato> platos) {
        System.out.println("Ingrese el índice del plato que desea seleccionar: ");
        int indiceSeleccionado = scanner.nextInt();
        scanner.nextLine();

        if (indiceSeleccionado < 0 || indiceSeleccionado >= platos.size()) {
            System.out.println("Índice inválido.");
            return -1;
        }

        return indiceSeleccionado;
    }


}

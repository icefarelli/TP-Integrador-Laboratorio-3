package Plato;
import Plato.Colores.Colores;
import Plato.Variedad.Variedad;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadRepositorio;
import Plato.Variedad.VariedadVista;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PlatoVista {

    private static final Scanner scanner = new Scanner(System.in);
    VariedadVista varVista = new VariedadVista();
    VariedadRepositorio varRepositorio = new VariedadRepositorio();
    VariedadController variedadController = new VariedadController(varVista, varRepositorio);

    //Crear un nuevo plato para el menu
    public Plato nuevoPlato() throws NumberFormatException {
        Colores.printInColor("=====CARGAR UN NUEVO PLATO===== ",Colores.GREEN);
        try {
            String tipo = menuTipoComida();

            if (tipo==null){
                mensajePersonalizado("Saliendo...");
                return null;
            }

            //Nombre del Plato
            String nombre = ingresarNombre();

            boolean tieneVariedades = metodoConfirmacion("-¿Este plato tiene variedades?");

            if (tieneVariedades) {
                List<Variedad> listaVariedad = variedadController.crearListaDeVariedades(new VariedadVista(), new VariedadRepositorio());
                return new Plato(tipo, nombre, listaVariedad);
            } else {
                double price = variedadController.cargarPrecio(new VariedadVista());
                if (price != 0.0) {
                    return new Plato(tipo, nombre, price);
                } else {
                    System.out.println("El Plato no pudo ser creado.");
                    return null;
                }
            }
        }catch (Exception e){
            System.out.println("Ocurrió un error al crear el plato: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Ingreso de nombre modularizado con comprobacion
    public String ingresarNombre(){
        System.out.println("-Nombre del Plato");
        String nombre = scanner.nextLine();

        if (nombre == null || nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")) {
            System.out.println("El nombre del plato solo debe contener letras.");
            return ingresarNombre();
        }
        return nombre;
    }

    //Metodo de confirmacion para concretar o validar acciones
    public boolean metodoConfirmacion (String mensaje){
        System.out.println(mensaje + "(s/n)");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            return true;
        } else if (confirmacion.equals("n")) {
            return false;
        } else {
            System.out.println("Opción inválida. Por favor, ingrese 's' para SI o 'n' para NO.");
            return metodoConfirmacion(mensaje); // Llamada recursiva para volver a solicitar la entrada
        }
    }
    //utilizar para personalizar mensajes
    public void mensajePersonalizado(String mensajePersonalizado){
        System.out.println(mensajePersonalizado);
    }

    //Actualizar un plato para el menu
    public Plato actualizarPlato(String tipo, String nombre) throws NumberFormatException {

        boolean tieneVariedades = metodoConfirmacion("¿Este plato tiene variedades?");

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
        Colores.printInColor("===============================",Colores.YELLOW);
        Colores.printInColor(" Seleccione la Categoria:",Colores.GREEN);
        Colores.printInColor("-------------------------------",Colores.YELLOW);
        System.out.println(" 1. Entradas\n 2. Carnes\n 3. Minutas");
        System.out.println(" 4. Pizzas\n 5. Pastas\n 6. Guarniciones y Ensaladas");
        System.out.println(" 7. Postres\n 8. Bebidas\n 9. Cafeteria y Pastelería\n 0. Salir");
        Colores.printInColor("-------------------------------",Colores.YELLOW);
        String opcionS = scanner.nextLine().trim();
        int opcion;
        try {

            opcion = Integer.parseInt(opcionS);

            switch (opcion) {
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
                case 0:
                    return null;
                default:
                    System.out.println("Tipo de comida inexistente. Por favor, seleccione una opción válida.");
                    return menuTipoComida();
            }
        }catch (NumberFormatException nfe){
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return menuTipoComida();
        }
    }


    // Método de ingreso de porcentaje para aumentar el valor de todos los
    // platos del menu de manera porcentual con un tope máximo de 100
    public double ingresePorcentaje (){
        try {
            System.out.println("Ingrese el porcentaje que desea aumentar en los precios totales.\nRecuerde que el valor a ingresar debe tener un mínimo de 1% y un tope de 100%");
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

//    public String ingreseNombreDePlato(){
//        System.out.println("Ingrese nombre del Plato a seleccionar");
//        return scanner.nextLine();
//    }

//    public double ingresePrecioDePlato(){
//        System.out.println(" Ingrese Valor en pesos (Formato X.XX): $");
//        return variedadController.checkPrecio(scanner.nextLine());
//
//    }

    public int obtenerIndiceSeleccionado(List<Plato> platos) {
        int indiceSeleccionado = -1;
        boolean validarIngreso = false;
        System.out.println("---------------------------------------------");
        System.out.println(" 0. Cancelar");
        System.out.println("---------------------------------------------");
        try {
            System.out.println("Ingrese el índice del plato que desea seleccionar: ");
            indiceSeleccionado = scanner.nextInt();
            scanner.nextLine();

            indiceSeleccionado--;
            if (indiceSeleccionado == -1) {
                return indiceSeleccionado;
            }else if (indiceSeleccionado < -1 || indiceSeleccionado >= platos.size()) {
                System.out.println("Índice inválido. Por favor intente nuevamente.-");

                return obtenerIndiceSeleccionado(platos);
            } else {
                validarIngreso = true;
            }
        }catch (InputMismatchException ime){
            System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            scanner.nextLine();
            return obtenerIndiceSeleccionado(platos);
        }catch (IndexOutOfBoundsException io){
            io.printStackTrace();
            scanner.nextLine();
        }
        return indiceSeleccionado;
    }

    public void mensajeBusquedaXTipo(){
        System.out.println("---MOSTRAR MENU POR CATEGORIA---");
    }

    public void mensajeEliminacionCancelada(){
        System.out.println("Eliminacion Cancelada");
    }
    public void mensajeCargaExitoFracaso(boolean confirmacion){
        if(confirmacion) System.out.println("Se completo exitosamente.");
        else System.out.println("No se completo correctamente.");
    }

    public void mensajeEliminacionExitoFracaso(boolean confirmacion){
        if(confirmacion) System.out.println("Se elimino correctamente.");
        else System.out.println("No se completo la eliminacion.");
    }



}

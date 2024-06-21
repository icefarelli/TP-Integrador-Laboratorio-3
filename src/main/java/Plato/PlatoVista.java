package Plato;
import Plato.Colores.Colores;
import Plato.Excepciones.ExcepCargaNoRealizada;
import Plato.Excepciones.ExcepIngresoInvalido;
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
    public Plato nuevoPlato() throws ExcepCargaNoRealizada, NullPointerException {
        Colores.printInColor("=====CARGAR UN NUEVO PLATO===== ",Colores.GREEN);
        try {
            String tipo = menuTipoComida();
            String nombre;

            if (tipo == null) {
                throw new NumberFormatException("Saliendo...");
            } else {
                printearLineasSeparadoras();
                System.out.println("Ingrese el Nombre del Plato");
                nombre = variedadController.cargarNombre(varVista);
                printearLineasSeparadoras();
                boolean tieneVariedades = metodoConfirmacion("-¿Este plato tiene variedades?");

                if (tieneVariedades) {
                    printearLineasSeparadoras();
                    List<Variedad> listaVariedad = variedadController.crearListaDeVariedades(varVista, varRepositorio);
                    return new Plato(tipo, nombre, listaVariedad);
                } else {
                    printearLineasSeparadoras();
                    double price = variedadController.cargarPrecio(varVista);
                    if (price != 0.0) {
                        return new Plato(tipo, nombre, price);
                    } else {
                        printearLineasSeparadoras();
                        throw new ExcepCargaNoRealizada("No se realizo la Carga correctamente");
                    }
                }
            }
        }catch (ExcepIngresoInvalido eii){
            printearLineasSeparadoras();
            mensajePersonalizado("Ocurrió un error al cargar el plato: \n" + eii.getMessage());
            return null;
        }catch (NumberFormatException nfe){
            printearLineasSeparadoras();
            mensajePersonalizado(nfe.getMessage());
            return null;
        }catch (NullPointerException npe){
            printearLineasSeparadoras();
            mensajePersonalizado("Error Al Ingresar datos en el nombre.");
            return null;
        }

    }

    //Metodo de confirmacion para concretar o validar acciones
    public boolean metodoConfirmacion (String mensaje){
        System.out.println(mensaje + "(s/n)");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        if (respuesta.equals("s")) {
            return true;
        } else if (respuesta.equals("n")) {
            return false;
        } else {
            System.out.println("Opción inválida. Por favor, ingrese 's' para SI o 'n' para NO.");
            return metodoConfirmacion(mensaje); // Llamada recursiva para volver a solicitar la respuesta
        }
    }

    //utilizar para personalizar mensajes
    public void mensajePersonalizado(String mensajePersonalizado){
        System.out.println(mensajePersonalizado);
    }

    //Actualizar un plato para el menu
    public Plato actualizarPlato(String tipo, String nombre, List<Variedad> listaAnterior) {
        System.out.println("Se selecciono el plato " + nombre + " para modificar.");
        System.out.println("La Categoria -" + tipo + "- y su nombre -" + nombre + "- no seran afectados.");
        System.out.println("Al actualizar se pueden agregar variedades a las preexistentes o agregar si el plato no posee.");
        System.out.println("El precio nuevo reemplazara al anterior. Ingrese correctamente los datos.");
        boolean confirmacion2;
        if (!listaAnterior.isEmpty()) {
            boolean confirmacion1 = metodoConfirmacion("El plato ya posee variedades. ¿Agregar mas?");
            if (confirmacion1) {
                variedadController.limparListaVariedades();
                List<Variedad> listaAgregados = variedadController.crearListaDeVariedades(varVista, varRepositorio);
                listaAgregados.addAll(listaAnterior);
                return new Plato(tipo, nombre, listaAgregados);
            }
            confirmacion2 = metodoConfirmacion("¿Desea eliminar la variedad actual y crear una nueva?");
            if(confirmacion2) {
                variedadController.limparListaVariedades();
                List<Variedad> listaNueva = variedadController.crearListaDeVariedades(varVista, varRepositorio);
                return new Plato(tipo, nombre, listaNueva);
            }else {
                boolean confirmacion3 = metodoConfirmacion("¿Desea eliminar las variedades del producto?");
                if(confirmacion3) {
                    double price = ingresePrecioDePlato();
                    if (price != 0.0) {
                        return new Plato(tipo, nombre, price);
                    } else {
                        System.out.println("El Plato no pudo ser creado.");
                        return null;
                    }
                }else {
                    return null;
                }
            }
        } else {
            boolean respuesta = metodoConfirmacion("El plato no posee variedades. ¿Desea agregarlas?");
            if (respuesta) {
                variedadController.limparListaVariedades();
                List<Variedad> listaCreada = variedadController.crearListaDeVariedades(varVista, varRepositorio);
                return new Plato(tipo, nombre, listaCreada);
            } else {
                double price = ingresePrecioDePlato();
                if (price != 0.0) {
                    return new Plato(tipo, nombre, price);
                } else {
                    System.out.println("El Plato no pudo ser creado.");
                    return null;
                }
            }
        }
    }

    // Seleccion de categoria por seleccion en menu. Devuelve el nombre en String para evitar errores de carga
    public String menuTipoComida() throws NumberFormatException{
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

            return switch (opcion) {
                case 1 -> "Entradas";
                case 2 -> "Carnes";
                case 3 -> "Minutas";
                case 4 -> "Pizzas";
                case 5 -> "Pastas";
                case 6 -> "Guarniciones y Ensaladas";
                case 7 -> "Postres";
                case 8 -> "Bebidas";
                case 9 -> "Cafeteria y Pastelería";
                case 0 -> null;
                default -> {
                    System.out.println("Tipo de comida inexistente. Por favor, seleccione una opción válida.");
                    yield menuTipoComida();
                }
            };
        }catch (NumberFormatException nfe){
            throw new NumberFormatException("Entrada inválida. Solo se aceptan números. Carga cancelada.");
        }
    }


    // Método de ingreso de porcentaje para aumentar el valor de todos los
    // platos del menu de manera porcentual con un tope máximo de 100
    public double ingresePorcentaje () throws NumberFormatException, InputMismatchException {
        try {
            System.out.println("Ingrese el porcentaje que desea aumentar en los precios totales.\nRecuerde que el valor a ingresar debe tener un mínimo de 1% y un tope de 100%");
            double aumento = scanner.nextDouble();
            scanner.nextLine();

            if(aumento <= 0 || aumento >100) {
                System.out.println("El valor ingresado se halla fuera del margen permitido.");
                return 0;
            }else if(aumento == 0) {
                return 0;
            }else {
                boolean confirmacion = metodoConfirmacion("Se hara un aumento de un " +(int) aumento + "% sobre los precios. ¿Confirmar? " );
                if(confirmacion) {
                    return aumento;
                }else {
                    return 0.0;
                }
            }
        }catch (NumberFormatException nfe){
            System.out.println("Ingrese solo numeros");
        }catch (InputMismatchException ime){
            System.out.println("Formato Invalido");
            scanner.nextLine();
        }
        return 0;
    }


    public double ingresePrecioDePlato() {
        System.out.println(" Ingrese Valor en pesos (Formato X.XX): $");
        return variedadController.checkPrecio(scanner.nextLine());

    }

    public int obtenerIndiceSeleccionado(List<Plato> platos) {
        int indiceSeleccionado = 0;
        System.out.println("---------------------------------------------");
        System.out.println(" 0. Cancelar");
        System.out.println("---------------------------------------------");
        try {
            System.out.println("Ingrese el índice del plato que desea seleccionar: ");
            indiceSeleccionado = scanner.nextInt();
            scanner.nextLine();

            if (indiceSeleccionado == 0) {
                return -1; // Cancelar selección
            }

            indiceSeleccionado--; // Ajustar para índice de lista

            if (indiceSeleccionado >= 0 && indiceSeleccionado < platos.size()) {
                return indiceSeleccionado;
            } else {
                System.out.println("Índice fuera de rango. Por favor intente nuevamente.");
                return obtenerIndiceSeleccionado(platos);
            }
        }catch (InputMismatchException ime){
            System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            scanner.nextLine();
            return obtenerIndiceSeleccionado(platos);
        }catch (IndexOutOfBoundsException io){
            System.out.println(io.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return indiceSeleccionado;
    }

    public void mensajeEliminacionPlatoInexistente(){
        System.out.println("El plato ingresado no existe en el sistema.");
    }
    public void mensajeCargaExitoFracaso(boolean confirmacion){
        if(confirmacion) System.out.println("Se completo exitosamente.");
        else System.out.println("No se completo correctamente.");
    }

    public void mensajeEliminacionExitoFracaso(boolean confirmacion){
        if(confirmacion) System.out.println("Se elimino correctamente.");
        else System.out.println("No se completo la eliminacion.");
    }

    public void pausarPantalla() {
        System.out.println("Presione Enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    public void printearLineasSeparadoras(){
        Colores.printInColor("-------------------------------",Colores.YELLOW);
    }



}

package Plato.Variedad;
import java.util.Scanner;

public class VariedadVista {
    private static final Scanner scanner = new Scanner(System.in);

    //Crea una variedad dentro de un Plato

    public Variedad crearUnaVariedad() {
        System.out.println("Ingrese el nombre de la variedad:");
        String nombreVariedad = scanner.nextLine();
        double precioVariedad = ingresarPrecio();
        if (precioVariedad != 0.0) {
            return new Variedad(nombreVariedad, precioVariedad);
        } else {
            System.out.println("La variedad no pudo ser creada.");
            return null;
        }
    }

    //ingreso del precio modularizado
    public double ingresarPrecio() {
        boolean validarPrecio = false;
        double precioVariedad = 0.0;
        int intentos = 2;

        while (!validarPrecio && intentos > 0){
            try {
                System.out.println("Ingrese el precio de la variedad (Formato X.XX): $");
                precioVariedad = checkDouble(scanner.nextLine());
                validarPrecio = true;
            }catch (NumberFormatException nfe){
                System.out.println(nfe.getMessage());
                intentos--;
            }
        }
        if(!validarPrecio) System.out.println("Se agotaron los intentos. El Precio no fue cargado.");
        return precioVariedad;
    }

    //Metodo que corrobora que el ingreso del precio sea de tipo double
    public double checkDouble(String precioString) throws NumberFormatException {
            try {
                return Double.parseDouble(precioString);
            }catch (NumberFormatException nfe){
                throw new NumberFormatException("El formato ingresado es Invalido.");
            }
    }

    public void mensajeCargaIncorrecta(){
        System.out.println("No se realizo la carga correctamente.");
    }
    public boolean agregarMas(){
        System.out.println("¿Desea agregar otra variedad? (s/n):");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        if (respuesta.equals("s")) {
            return true;
        } else if (respuesta.equals("n")) {
            return false;
        } else {
            System.out.println("Opción inválida. Por favor, ingrese 's' para sí o 'n' para no.");
            return agregarMas(); // Llamada recursiva para volver a solicitar la entrada
        }
    }


}

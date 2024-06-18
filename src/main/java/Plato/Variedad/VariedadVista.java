package Plato.Variedad;
import java.util.Scanner;

public class VariedadVista {
    private static final Scanner scanner = new Scanner(System.in);

    //Carga una variedad dentro de un Plato
    public Variedad cargarUnaVariedad() {
        boolean validarPrecio = false;
        double precioVariedad = 0.0;
        int limite = 0;
        System.out.println("Ingrese el nombre de la variedad:");
        String nombreVariedad = scanner.nextLine();

        while (!validarPrecio){
            try {
                System.out.println("Ingrese el precio de la variedad (Formato X.XX): $");
                precioVariedad = checkDouble(scanner.nextLine());
                validarPrecio = true;
                limite = 3;
            }catch (NumberFormatException nfe){
                System.out.println("El formato ingresado es Invalido. Por Favor, Intente nuevamente");
                limite++;
            }
        }
        return new Variedad(nombreVariedad,precioVariedad);
    }

    //Metodo que corrobora que el ingreso del precio sea de tipo double
    public double checkDouble(String precioString) throws NumberFormatException{
            return Double.parseDouble(precioString);
    }


}

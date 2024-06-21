package Login;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionCaracteresEspeciales;
import Excepciones.ExcepcionNombreNumerico;

import java.util.Scanner;

public class LoginView {
    private Scanner scanner;

    public LoginView() {
        scanner = new Scanner(System.in);
    }


    public int solicitarOpcion() {
        return scanner.nextInt();
    }
    public Usuario solicitarDatosRegistro() throws ExcepcionCamposVacios, ExcepcionNombreNumerico {
        System.out.println("Ingrese nombre de usuario:");
        String username = scanner.nextLine();

        System.out.println("Ingrese contraseña:");
        String password = scanner.nextLine();
        if (username==null || username.isEmpty() || password.isEmpty() || password==null)
        {
            throw  new ExcepcionCamposVacios("No puede haber campos vacios ni en usuario ni en contraseña.");
        }
        if (contieneNum(username))
        {
            throw new ExcepcionNombreNumerico("El usario no puede contener numeros");
        }

        return new Usuario(username, password);
    }

    public boolean contieneNum(String str) {
        return str.matches(".*\\d.*");
    }
    public boolean noContieneEspeciales(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }
    public Boolean verificarUser (Usuario usuario)
    {
        Boolean ok = false;
        if (usuario==null)
        {

            System.out.println("XXX NOMBRE DE USUARIO Y CONTRASENA INCORRECTOS XXX");
        }
        else
        {
            ok=true;
        }
        return ok;
    }

    public void control ()
    {
        System.out.println("USUARIO REGISTRADO EXITOSAMENTE");
    }

}

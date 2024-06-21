package Login;

import Excepciones.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

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

        System.out.println("Ingrese la contraseña: ");
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

    public String pedirCargo() {
        Scanner scan = new Scanner(System.in);
        int op = -1;
        String nombre = null;
        do {
            try{
                System.out.println("Ingrese una opcion para el cargo:");
                System.out.println("1. Admin");
                System.out.println("2. Empleado");


                op = scan.nextInt();

                switch (op) {
                    case 1:
                        nombre ="Admin";
                        break;
                    case 2:
                        nombre ="Empleado";
                        break;
                    default:
                        System.out.println("Opcion incorrecta, ingrese una opcion valida");

                }
            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scan.nextLine();
            }
        } while(op!=1 && op!=2);
        return nombre;
    }

    public String pedirNombreUsuario(){
        boolean flag=false;
        Scanner scan = new Scanner(System.in);
        String nombre =null;
        while (!flag) {
            System.out.println("Ingrese el nombre de usuario:");
            try {
                nombre = scan.nextLine();
                if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")) {
                    throw new ExcepcionNombreInvalido("El nombre debe contener letras y no estar vacio");
                }
                flag=true;
            } catch (ExcepcionNombreInvalido e){
                System.out.println(e.getMessage());

            }
        }
        return nombre;
    }

    public void mostrarUnUsuario(Usuario usuario){
        System.out.println("--------------------------------------------------------");
        System.out.println("Usuario: " + usuario.getUsername());
        System.out.println("Contraseña: " + usuario.getPassword());
        System.out.println("Puesto: " + usuario.getCargo());
        System.out.println("--------------------------------------------------------");
    }

    public void mostrarUsuarios(Set<Usuario> set){
        for (Usuario user : set){
            mostrarUnUsuario(user);
        }
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

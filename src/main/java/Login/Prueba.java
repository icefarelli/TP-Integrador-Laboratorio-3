package Login;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionClienteNoEncontrado;
import Excepciones.ExcepcionFormatoIncorrecto;
import Excepciones.ExcepcionNombreNumerico;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.ExcepcionReservaCamposVacios;
import Excepciones.ExcepcionReservaCaracterInvalido;
import Excepciones.ExcepcionReservaNoEncontrada;
import Excepciones.ExcepcionReservaValorNegativo;

import java.io.IOException;

public class Prueba {

    public static void main(String[] args) throws ExcepcionReservaCamposVacios, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, ExcepcionReservaNoEncontrada, ExcepcionClienteNoEncontrado, IOException, ExcepcionReservaCaracterInvalido, ExcepcionFormatoIncorrecto, ExcepcionReservaValorNegativo, ExcepcionCamposVacios, ExcepcionNombreNumerico {
        LoginRepository loginRepository = new LoginRepository();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginRepository,loginView);

/*
        MenuLogin menuLogin = new MenuLogin(loginController);

        menuLogin.menu();*/
    }
}


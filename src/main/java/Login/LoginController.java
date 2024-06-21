package Login;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionCaracteresEspeciales;
import Excepciones.ExcepcionNombreNumerico;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class LoginController {


    private LoginRepository loginRepository;
    private LoginView loginView;
    private Gson gson = new Gson();
    private static final String PATH = "src/main/resources/Usuarios.json";


    public LoginController(LoginRepository loginRepository, LoginView loginView) {
        this.loginRepository = loginRepository;
        this.loginView = loginView;
        this.loadGestionUsuario();
    }

    public void loadGestionUsuario() {
        try (Reader reader = new FileReader(PATH);) {
            Type type = new TypeToken<Set<Usuario>>() {
            }.getType();
            loginRepository.setUsuarios = gson.fromJson(reader, type);
            if (this.loginRepository.setUsuarios == null) {
                this.loginRepository.setUsuarios = new HashSet<>();
            } else {
                this.loginRepository.setUsuarios.forEach(usuario -> {
                    loginRepository.registrarUsuario(usuario);
                });

            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void Update() {
        try (Writer writer = new FileWriter(PATH);) {
            gson.toJson(this.loginRepository.setUsuarios, writer);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void addUser() throws ExcepcionCamposVacios, ExcepcionNombreNumerico {
        try {
            Usuario usuario = this.loginView.solicitarDatosRegistro();
            this.loginRepository.registrarUsuario(usuario);
            this.loginView.control();
            this.Update();
        } catch (ExcepcionCamposVacios excepcionCamposVacios) {
            System.out.println(excepcionCamposVacios.getMessage());
        } catch (ExcepcionNombreNumerico excepcionNombreNumerico) {
            System.out.println(excepcionNombreNumerico.getMessage());
        }

    }

    public Boolean iniciarSesion() throws ExcepcionCamposVacios {
        {
            Boolean ok = false;
            try {
                Usuario usuario = this.loginView.solicitarDatosRegistro();
                Usuario usuario1 = this.loginRepository.buscar(usuario, this.loginRepository.getSetUsuarios());
                ok = this.loginView.verificarUser(usuario1);
            } catch (ExcepcionCamposVacios excepcionCamposVacios) {
                System.out.println(excepcionCamposVacios.getMessage());
            } catch (ExcepcionNombreNumerico e) {
                System.out.println(e.getMessage());
            }
            return ok;

        }
    }
}

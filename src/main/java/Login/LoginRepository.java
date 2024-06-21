package Login;

import java.util.HashSet;
import java.util.Set;

public class LoginRepository {

      Set<Usuario> setUsuarios;

    public LoginRepository() {
        this.setUsuarios = new HashSet<>();
    }

    public void registrarUsuario (Usuario usuario)
    {
        setUsuarios.add(usuario);
    }


    public Usuario buscar (Usuario usuario, Set <Usuario> set)
    {
        for (Usuario user : set )
        {
            if(user.equals(usuario))
            {
                return usuario;
            }
        }
        return null;
    }

    public Set<Usuario> getSetUsuarios() {
        return setUsuarios;
    }
    public boolean noContieneEspeciales(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }
}

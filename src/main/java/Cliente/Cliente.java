package Cliente;

import Persona.Persona;

public class Cliente extends Persona {

    private static int secCliente = 0;
    private Integer idCliente;
    private String telefono;


    public Cliente(String nombre, String id, String telefono) {
        super(nombre, id);
        this.idCliente = ++secCliente;
        this.telefono = telefono;
    }
}

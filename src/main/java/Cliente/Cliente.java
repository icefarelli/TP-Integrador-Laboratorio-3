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

    public static int getSecCliente() {
        return secCliente;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

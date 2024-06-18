package Cliente.model.entitie;

import Cliente.Excepciones.ExcepcionCamposVacios;
import Persona.Persona;

public class Cliente extends Persona {

    private static int secCliente = 0;
    private Integer idCliente;
    private String telefono;


    public Cliente(String nombre, String id, String telefono) throws ExcepcionCamposVacios {
        super(nombre, id);
        if (nombre == null | nombre.isEmpty() || id.isEmpty() || id == null || telefono == null || telefono.isEmpty()) {
            throw new ExcepcionCamposVacios("XXX ERROR: NO PUEDE HABER CAMPOS VACIOS XXX");
        }

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


    @Override
    public String toString() {
        return super.toString()+" - Id:" + idCliente +
                " - Telefono:" + telefono ;
    }
    }

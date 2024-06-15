package Empleado;

import Persona.Persona;

public class Empleado extends Persona {
    private static int secEmpleado = 0;
    private Integer idEmpleado;
    private String puesto;

    public Empleado(String nombre, String id, String puesto) {
        super(nombre, id);
        this.idEmpleado = ++secEmpleado;
        this.puesto = puesto;
    }
}

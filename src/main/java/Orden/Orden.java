package Orden;

import Cliente.model.entitie.Cliente;
import Empleado.Empleado;
import Plato.Plato;
import java.util.List;

public class Orden {

    private static int secOrden = 0;
    private Integer id;
    private Cliente cliente;
    private Empleado empleado;
    private List<Plato> platoList;

    public Orden(Cliente cliente, Empleado empleado) {
        this.id = ++secOrden;
        this.empleado = empleado;
        this.cliente = cliente;
    }

    public static int getSecOrden() {
        return secOrden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Plato> getPlatoList() {
        return platoList;
    }

    public void setPlatoList(List<Plato> platoList) {
        this.platoList = platoList;
    }
}

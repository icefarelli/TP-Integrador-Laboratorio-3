package Orden;

import Cliente.Cliente;
import Plato.Plato;
import java.util.List;

public class Orden {

    private static int secOrden = 0;
    private Integer id;
    private Cliente cliente;
    private List<Plato> platoList;

    public Orden(Cliente cliente) {
        this.id = ++secOrden;
        this.cliente = cliente;
    }

    public static int getSecOrden() {
        return secOrden;
    }

    public static void setSecOrden(int secOrden) {
        Orden.secOrden = secOrden;
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

    public List<Plato> getPlatoList() {
        return platoList;
    }

    public void setPlatoList(List<Plato> platoList) {
        this.platoList = platoList;
    }
}

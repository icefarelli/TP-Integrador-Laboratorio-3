package MesasReservadas;

import Cliente.model.entitie.Cliente;

public class MesasReservadas {
    private Cliente cliente;
    private Integer cantPersonas;

    public MesasReservadas(Cliente cliente, Integer cantPersonas) {
        this.cliente = cliente;
        this.cantPersonas = cantPersonas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(Integer cantPersonas) {
        this.cantPersonas = cantPersonas;
    }
}

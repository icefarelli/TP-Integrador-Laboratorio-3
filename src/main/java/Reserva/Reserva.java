package Reserva;

import Cliente.model.entitie.Cliente;

import java.time.LocalDateTime;

public class Reserva {
    private static int secReserva = 0;
    private Integer id;
    private Cliente cliente;
    private Integer cantPersonas;
    private LocalDateTime fecha;

    public Reserva(Integer cantPersonas, LocalDateTime fecha) {
        this.id = ++secReserva;
        this.cliente = cliente;
        this.cantPersonas = cantPersonas;
        this.fecha = fecha;
    }

    public static int getSecReserva() {
        return secReserva;
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

    public Integer getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(Integer cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

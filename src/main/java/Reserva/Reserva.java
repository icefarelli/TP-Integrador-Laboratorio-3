package Reserva;

import MesasReservadas.MesasReservadas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private LocalDateTime fecha;
    private List<MesasReservadas> mesasReservadas = new ArrayList<MesasReservadas>();

    public Reserva(LocalDateTime fecha, List<MesasReservadas> mesasReservadas) {
        this.fecha = fecha;
        this.mesasReservadas = mesasReservadas;
    }

    public Reserva(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<MesasReservadas> getMesasReservadas() {
        return mesasReservadas;
    }

    public void setMesasReservadas(List<MesasReservadas> mesasReservadas) {
        this.mesasReservadas = mesasReservadas;
    }
}

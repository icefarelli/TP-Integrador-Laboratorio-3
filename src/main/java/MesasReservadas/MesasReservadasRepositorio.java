package MesasReservadas;

import Interfaces.IABM;

import java.util.ArrayList;
import java.util.List;

public class MesasReservadasRepositorio implements IABM<MesasReservadas> {

    List<MesasReservadas> mesasReservadas = new ArrayList<MesasReservadas>();


    public List<MesasReservadas> getMesasReservadas() {
        return mesasReservadas;
    }

    public void setMesasReservadas(List<MesasReservadas> mesasReservadas) {
        this.mesasReservadas = mesasReservadas;
    }

    @Override
    public void agregar(MesasReservadas obj) {
        mesasReservadas.add(obj);
    }

    @Override
    public void eliminar(MesasReservadas obj) {
        mesasReservadas.remove(obj);

    }

    @Override
    public void modificar(MesasReservadas obj) {
        mesasReservadas.add(obj);
    }

    public Integer buscarIndice(Integer id){
        Integer indice = -1;
        for(MesasReservadas mesas : mesasReservadas){
            if(mesas.getCliente().getIdCliente() == id){
                indice = mesasReservadas.indexOf(mesas);
            }
        }
        return indice;
    }

    public MesasReservadas buscarMesaReservadaPorIndice(Integer id){
        MesasReservadas mesa = null;
        for(MesasReservadas mesas : mesasReservadas){
            if(mesas.getCliente().getIdCliente() == id){
                mesa = mesas;
            }
        }
        return mesa;
    }

    public List<MesasReservadas> todasLasMesasReservadas(){return mesasReservadas;}
}
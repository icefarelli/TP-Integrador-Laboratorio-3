package Empleado;

import Interfaces.IABM;


import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmpleadoRepositorio implements IABM<Empleado> {

    TreeMap<String,Empleado> listaEmpleados;

    public EmpleadoRepositorio() {
        this.listaEmpleados = new TreeMap<String, Empleado>();
    }

    public Map<String,Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    @Override
    public void agregar(Empleado empleado) {
        this.listaEmpleados.put(empleado.getId(),empleado);
    }

    @Override
    public void eliminar(Empleado empleado) {
        this.listaEmpleados.remove(empleado.getId());
    }

    @Override
    public void modificar(Empleado empleado) {


    }

    public Empleado buscarEmpleado(String clave){
        Empleado empleado = null;
        for (Map.Entry<String,Empleado> entry : listaEmpleados.entrySet()){
            if(entry.getValue().getId().equals(clave)){
                empleado = entry.getValue();
            }
        }
        return empleado;
    }

}

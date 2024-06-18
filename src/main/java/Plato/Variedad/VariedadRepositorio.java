package Plato.Variedad;

import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Plato;

import java.util.ArrayList;
import java.util.List;

public class VariedadRepositorio {
    private List<Variedad> listaVar;

    public Variedad cargaDeVariedad(Variedad variedad) {
        if (variedad != null) return variedad;
        else{
            try {
                throw new ExcepIngresoInvalido("No ha sido posible cargar la variedad del plato.\n Intente nuevamente.");
            } catch (ExcepIngresoInvalido e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public VariedadRepositorio() {
        this.listaVar = new ArrayList<>();
    }

    public void cargaListaVariedad(Variedad variedad){
        listaVar.add(variedad);
    }
    public List<Variedad> copiaListaVar(){
        return this.listaVar;
    }
    public void limpiarLista(){
        this.listaVar.clear();
    }



}

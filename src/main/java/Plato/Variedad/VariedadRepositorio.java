package Plato.Variedad;

import Plato.Plato;

import java.util.ArrayList;
import java.util.List;

public class VariedadRepositorio {
    private List<Variedad> listaVar;

    public VariedadRepositorio() {
        this.listaVar = new ArrayList<>();
    }

    public void cargaListaVariedad(Variedad variedad){
        listaVar.add(variedad);
    }



}

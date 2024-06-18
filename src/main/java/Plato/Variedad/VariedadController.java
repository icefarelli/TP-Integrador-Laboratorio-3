package Plato.Variedad;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.util.ArrayList;
import java.util.List;

public class VariedadController {

    private VariedadVista variedadVista = new VariedadVista();

    public Variedad cargaDeVaridedad() {

        Variedad variedad = variedadVista.cargarUnaVariedad();
        try {
            if (variedad != null) return variedad;
            else throw new ExcepIngresoInvalido();
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public double checkPrecio(String precio){
        return variedadVista.checkDouble(precio);
    }
     public List<Variedad> cargaListaVariedad(){
        List<Variedad> listaV = new ArrayList<>();




        return listaV;
     }




}

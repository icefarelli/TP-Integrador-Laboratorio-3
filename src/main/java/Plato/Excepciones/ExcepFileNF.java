package Plato.Excepciones;

import java.io.FileNotFoundException;

public class ExcepFileNF extends FileNotFoundException {
    public ExcepFileNF(String mensaje) {
        super(mensaje);
    }
}
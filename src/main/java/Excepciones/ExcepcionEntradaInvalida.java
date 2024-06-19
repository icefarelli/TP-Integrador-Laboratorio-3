package Excepciones;


public class ExcepcionEntradaInvalida extends Exception {
    public ExcepcionEntradaInvalida(String mensaje) {
        super(mensaje);
    }

    public ExcepcionEntradaInvalida() {
    }

    public void mensaje(){
        System.out.println("Entrada invalida..");
    }
}

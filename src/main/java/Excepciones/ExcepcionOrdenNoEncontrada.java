package Excepciones;

import Cliente.model.entitie.Cliente;
import Cliente.controller.ClienteControlador;
import Cliente.model.repository.ClienteRepositorio;
import Cliente.view.ClienteVista;
import Cliente.Excepciones.ExcepcionCamposVacios;

import java.awt.*;

public class ExcepcionOrdenNoEncontrada extends Exception {
    public ExcepcionOrdenNoEncontrada(String message) {
        super(message);
    }

}


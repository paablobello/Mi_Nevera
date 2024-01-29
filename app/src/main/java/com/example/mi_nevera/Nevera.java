package com.example.mi_nevera;

import java.io.Serializable;
import java.util.Map;

public class Nevera implements Serializable {

    //TODO ES NECESARIO USAR MAP?
    private String[] ingredientes;

    public Nevera(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Nevera() {
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

}

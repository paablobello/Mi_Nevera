package com.example.mi_nevera.model;

import java.util.ArrayList;

/**
 * Esta clase representa una Lista en la aplicación.
 * Contiene los atributos de una lista y métodos para obtener y establecer los datos de la lista.
 */
public class Lista {
    private ArrayList<String> producto;

    /**
     * Constructor para la clase Lista.
     * @param producto La lista de productos.
     */
    public Lista(ArrayList<String> producto) {
        this.producto = producto;
    }

    /**
     * Constructor vacío para la clase Lista.
     * Inicializa la lista de productos como una nueva lista vacía.
     */
    public Lista() {
        this.producto = new ArrayList<>();
    }

    /**
     * Getter para la lista de productos.
     * @return La lista de productos.
     */
    public ArrayList<String> getLista() {
        return producto;
    }

    /**
     * Setter para la lista de productos.
     * @param producto La nueva lista de productos.
     */
    public void setProducto(ArrayList<String> producto) {
        this.producto = producto;
    }
}
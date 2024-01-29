package com.example.mi_nevera.model;

/**
 * Esta clase representa un Ingrediente en la aplicación.
 * Contiene los atributos de un ingrediente y métodos para obtener y establecer los datos del ingrediente.
 */
public class Ingrediente {
    private String nombre;

    /**
     * Constructor para la clase Ingrediente.
     * @param nombre El nombre del ingrediente.
     */
    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter para el nombre del ingrediente.
     * @return El nombre del ingrediente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter para el nombre del ingrediente.
     * @param nombre El nuevo nombre del ingrediente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
package com.example.mi_nevera;

import java.io.Serializable;

/**
 * Esta clase representa una Receta con un nombre, descripción y una lista de ingredientes.
 * Implementa la interfaz Serializable para permitir que se pase entre actividades.
 */
public class Receta implements Serializable {
    private String nombre;
    private String descripcion;
    private String[] ingredientes;

    /**
     * Constructor para la clase Receta.
     * @param nombre El nombre de la receta.
     * @param descripcion La descripción de la receta.
     * @param ingredientes1 La lista de ingredientes para la receta.
     */
    public Receta(String nombre, String descripcion, String[] ingredientes1) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes1;
    }

    /**
     * Getter para los ingredientes.
     * @return La lista de ingredientes para la receta.
     */
    public String[] getIngredientes() {
        return ingredientes;
    }

    /**
     * Setter para los ingredientes.
     * @param ingredientes La nueva lista de ingredientes para la receta.
     */
    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Getter para la descripción.
     * @return La descripción de la receta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter para la descripción.
     * @param descripcion La nueva descripción de la receta.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter para el nombre.
     * @return El nombre de la receta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter para el nombre.
     * @param nombre El nuevo nombre de la receta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Sobrescribe el método toString para devolver el nombre de la receta.
     * @return El nombre de la receta.
     */
    @Override
    public String toString(){
        return this.nombre;
    }

    /**
     * Convierte el array de ingredientes a una cadena.
     * Si el array no está vacío, une los ingredientes con un carácter de nueva línea.
     * Si el array está vacío, devuelve un mensaje indicando que no hay ingredientes disponibles.
     * @return Una representación en cadena de los ingredientes.
     */
    public String arrayToString() {
        if (ingredientes != null && ingredientes.length > 0) {
            return String.join("\n", ingredientes);
        } else {
            return "No hay ingredientes disponibles.";
        }
    }
}
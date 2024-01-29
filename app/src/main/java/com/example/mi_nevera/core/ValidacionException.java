package com.example.mi_nevera.core;

/**
 * Clase que representa una excepción de validación.
 * Esta excepción se lanza cuando se produce un error de validación en el formulario de registro.
 */
public class ValidacionException extends Exception {

    private int numError;

    //CONSTRUCTOR DE LA EXCEPCION FORMADA POR SU MENSAJE Y EL NUMERO DEL ERROR

    /**
     * Constructor de la excepción.
     * @param msg      Mensaje del error
     * @param numError Numero del error (va a venir dado por un String de la forma R.string.(String del error))
     */

    public ValidacionException(String msg, int numError) {
        super(msg);
        this.numError = numError;
    }
    /**
     *Devuelve el número de error.
     */
    //DEVUELVE EL NUMERO DE ERROR
    public int getError() {
        return numError;
    }
}

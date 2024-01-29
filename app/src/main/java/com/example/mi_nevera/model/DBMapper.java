package com.example.mi_nevera.model;

import android.content.Context;

import com.example.mi_nevera.core.DBManager;

/**
 * Esta clase se utiliza para representar la clase base que todas las clases Mapper van a usar.
 * Contiene una instancia de la base de datos.
 */
public class DBMapper {
    // Instancia de la base de datos
    public DBManager instance;

    /**
     * Constructor que obtiene la instancia de conexión con la base de datos.
     * @param context Contexto de la aplicación.
     */
    public DBMapper(Context context) {
        this.instance = DBManager.getManager(context);
    }
}
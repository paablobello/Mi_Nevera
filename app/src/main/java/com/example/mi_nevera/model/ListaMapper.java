package com.example.mi_nevera.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import static com.example.mi_nevera.core.DBManager.TABLA_LISTA_COMPRA;
import static com.example.mi_nevera.core.DBManager.CAMPO_LISTA_COMPRA_ID;
import static com.example.mi_nevera.core.DBManager.CAMPO_LISTA_COMPRA_NAME;
import static com.example.mi_nevera.core.DBManager.CAMPO_LISTA_COMPRA_OWNER;


import com.example.mi_nevera.core.DBManager;

import java.util.ArrayList;

/**
 * Esta clase se encarga de mapear los datos de las listas con la base de datos y la aplicación, y viceversa.
 * Extiende DBMapper para tener todas las funcionalidades de un mapeador de base de datos.
 */
public class ListaMapper extends DBMapper {

    /**
     * Constructor que llama al constructor de la clase DBMapper para obtener la instancia de conexión
     * con la base de datos.
     *
     * @param context Contexto de la aplicación.
     */
    public ListaMapper(Context context) {
        super(context);
    }

    /**
     * Método para añadir un item a la lista de la base de datos.
     *
     * @param item Item que se va a añadir.
     * @param username Nombre de usuario al que pertenece la lista.
     */
    public void insertarListaItem(String item, String username) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(CAMPO_LISTA_COMPRA_NAME, item);
        values.put(CAMPO_LISTA_COMPRA_OWNER, username);

        try {
            Log.i("DB", "insertar item: " + item);
            db.beginTransaction();
            db.insertOrThrow(TABLA_LISTA_COMPRA, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para eliminar un item de la lista de la base de datos.
     *
     * @param item Item que se va a eliminar.
     * @param username Nombre de usuario al que pertenece la lista.
     * @return Verdadero si el item fue eliminado, falso en caso contrario.
     */
    public boolean deleteListaItem(String item, String username) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        String[] args = new String[]{item, username};

        try {
            Log.i("DB", "borrar item: " + item);
            db.beginTransaction();
            int rows = db.delete(TABLA_LISTA_COMPRA, CAMPO_LISTA_COMPRA_NAME + "=? AND " + CAMPO_LISTA_COMPRA_OWNER + "=?", args);
            db.setTransactionSuccessful();
            return rows > 0;
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
            return false;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para obtener la lista de un usuario específico.
     *
     * @param username El nombre de usuario.
     * @return Una lista de items.
     */
    public ArrayList<String> getLista(String username) {
        ArrayList<String> toRet = new ArrayList<>();
        final SQLiteDatabase db = instance.getReadableDatabase();

        String[] args = new String[]{username};

        try {
            Log.i("DB", "Busqueda de lista: " + username);

            try (Cursor cursor = db.query(TABLA_LISTA_COMPRA, null, CAMPO_LISTA_COMPRA_OWNER + "=? ", args, null, null, null)) {
                while (cursor.moveToNext()) {
                    toRet.add(cursor.getString(cursor.getColumnIndex(CAMPO_LISTA_COMPRA_NAME)));
                }
            }
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
            throw new RuntimeException("Error DB");
        }

        return toRet;
    }
}
package com.example.mi_nevera.model;

import static com.example.mi_nevera.core.DBManager.CAMPO_INGREDIENTES_NAME;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mi_nevera.model.Ingrediente;

import java.util.ArrayList;

/**
 * Esta clase se encarga de mapear los datos de los ingredientes con la base de datos y la aplicación, y viceversa.
 * Extiende DBMapper para tener todas las funcionalidades de un mapeador de base de datos.
 */
public class IngredienteMapper extends DBMapper {

    /**
     * Constructor que llama al constructor de la clase DBMapper para obtener la instancia de conexión
     * con la base de datos.
     *
     * @param context Contexto de la aplicación.
     */
    public IngredienteMapper(android.content.Context context) {
        super(context);
    }

    /**
     * Método para añadir un ingrediente a la base de datos.
     *
     * @param ingrediente Ingrediente que se va a añadir.
     */
    public void addIngrediente(Ingrediente ingrediente) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final android.content.ContentValues values = new android.content.ContentValues();

        values.put(CAMPO_INGREDIENTES_NAME, ingrediente.getNombre());

        try {
            android.util.Log.i("DB", "insertar ingrediente: " + ingrediente.getNombre());
            db.beginTransaction();
            db.insertOrThrow(com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES, null, values);
            db.setTransactionSuccessful();
        } catch (android.database.SQLException err) {
            android.util.Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para eliminar un ingrediente de la base de datos.
     *
     * @param ingrediente Ingrediente que se va a eliminar.
     */
    public void deleteIngrediente(Ingrediente ingrediente) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final android.content.ContentValues values = new android.content.ContentValues();

        values.put(CAMPO_INGREDIENTES_NAME, ingrediente.getNombre());

        try {

        } catch (android.database.SQLException err) {
            android.util.Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para verificar si un ingrediente ya existe en la base de datos.
     *
     * @param ingrediente Ingrediente que se va a verificar.
     * @return Verdadero si el ingrediente existe, falso en caso contrario.
     * @throws RuntimeException al producirse algún error en la base de datos.
     */
    public boolean existeIngrediente(Ingrediente ingrediente) {
        boolean toRet;
        final SQLiteDatabase db = instance.getReadableDatabase();
        final String[] args = {ingrediente.getNombre()};
        final Cursor cursor = db.rawQuery("SELECT * FROM " + com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES + " WHERE " + CAMPO_INGREDIENTES_NAME + "=?", args);

        try {
            if (cursor.moveToFirst()) {
                toRet = true;
            } else {
                toRet = false;
            }
        } catch (RuntimeException err) {
            android.util.Log.e("DB", err.getMessage());
            throw err;
        } finally {
            cursor.close();
        }
        return toRet;

    }

    /**
     * Método para obtener todos los ingredientes de la base de datos.
     *
     * @return Una lista de ingredientes.
     */
    public ArrayList<String> getIngredientesTodos() {
        final SQLiteDatabase db = instance.getReadableDatabase();
        final ArrayList<String> toRet = new java.util.ArrayList<>();
        final Cursor cursor = db.rawQuery("SELECT " + CAMPO_INGREDIENTES_NAME + " FROM " + com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES, null);

        try {
            while (cursor.moveToNext()) {
                final Ingrediente ingrediente = new Ingrediente(cursor.getString(0));
                toRet.add(ingrediente.getNombre());
            }
        } catch (RuntimeException err) {
            android.util.Log.e("DB", err.getMessage());
            throw err;
        } finally {
            cursor.close();
        }
        return toRet;
    }

    /**
     * Método para eliminar un ingrediente por su nombre en la base de datos.
     *
     * @param nombre Nombre del ingrediente a eliminar.
     * @return Verdadero si el ingrediente fue eliminado, falso en caso contrario.
     */
    public boolean deleteIngrediente(String nombre) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final String[] args = {nombre};
        final int affectedRows = db.delete(com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES, CAMPO_INGREDIENTES_NAME + "=?", args);
        return affectedRows > 0;
    }

    /**
     * Método para obtener los ingredientes de un usuario específico.
     *
     * @param username El nombre de usuario.
     * @return Una lista de ingredientes.
     */
    public ArrayList<String> getIngredientesUsuario(String username) {
        final SQLiteDatabase db = instance.getReadableDatabase();
        final ArrayList<String> toRet = new java.util.ArrayList<>();

        //FILTRADO
        final Cursor cursor = db.rawQuery("SELECT " + CAMPO_INGREDIENTES_NAME + " FROM " + com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES + " WHERE " + com.example.mi_nevera.core.DBManager.CAMPO_INGREDIENTES_OWNER + "=?", new String[]{username});

        try {
            while (cursor.moveToNext()) {
                final Ingrediente ingrediente = new Ingrediente(cursor.getString(0));
                toRet.add(ingrediente.getNombre());
            }
        } catch (RuntimeException err) {
            android.util.Log.e("DB", err.getMessage());
            throw err;
        } finally {
            cursor.close();
        }
        return toRet;
    }

    /**
     * Método para añadir un ingrediente con usuario a la base de datos.
     *
     * @param ingrediente Ingrediente que se va a añadir.
     * @param username Nombre de usuario al que pertenece el ingrediente.
     */
    public void addIngredienteUsuario(Ingrediente ingrediente, String username) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final android.content.ContentValues values = new android.content.ContentValues();

        values.put(CAMPO_INGREDIENTES_NAME, ingrediente.getNombre());
        values.put(com.example.mi_nevera.core.DBManager.CAMPO_INGREDIENTES_OWNER, username);

        try {
            android.util.Log.i("DB", "insertar ingrediente: " + ingrediente.getNombre());
            db.beginTransaction();
            db.insertOrThrow(com.example.mi_nevera.core.DBManager.TABLA_INGREDIENTES, null, values);
            db.setTransactionSuccessful();
        } catch (android.database.SQLException err) {
            android.util.Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
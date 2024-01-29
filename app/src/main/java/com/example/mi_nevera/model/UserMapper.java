package com.example.mi_nevera.model;

import static com.example.mi_nevera.core.DBManager.CAMPO_USUARIOS_EMAIL;
import static com.example.mi_nevera.core.DBManager.CAMPO_USUARIOS_PASSWORD;
import static com.example.mi_nevera.core.DBManager.CAMPO_USUARIOS_USERNAME;
import static com.example.mi_nevera.core.DBManager.TABLA_USUARIOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Esta clase se encarga de mapear los datos de los usuarios con la base de datos y la aplicación, y viceversa.
 * Extiende DBMapper para tener todas las funcionalidades de un mapeador de base de datos.
 */
public class UserMapper extends DBMapper {

    /**
     * Constructor que llama al constructor de la clase DBMapper para obtener la instancia de conexión
     * con la base de datos.
     *
     * @param context Contexto de la aplicación.
     */
    public UserMapper(Context context) {
        super(context);
    }

    /**
     * Método para añadir un usuario a la base de datos.
     *
     * @param user Usuario que se va a añadir.
     */
    public void addUser(User user) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(CAMPO_USUARIOS_USERNAME, user.getUsername());
        values.put(CAMPO_USUARIOS_PASSWORD, user.getPassword());
        values.put(CAMPO_USUARIOS_EMAIL, user.getEmail());

        try {
            Log.i("DB", "insertar usuario: " + user.getUsername());
            db.beginTransaction();
            db.insertOrThrow(TABLA_USUARIOS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para verificar si un usuario es válido.
     *
     * @param user Usuario que se va a verificar.
     * @return Verdadero si el usuario es válido, falso en caso contrario.
     * @throws RuntimeException al producirse algún error en la base de datos.
     */
    public boolean isValidUser(User user) {
        boolean toRet;
        final SQLiteDatabase db = instance.getReadableDatabase();
        final ContentValues values = new ContentValues();

        values.put(CAMPO_USUARIOS_USERNAME, user.getUsername());
        values.put(CAMPO_USUARIOS_PASSWORD, user.getPassword());

        String[] args = new String[]{user.getUsername(), user.getPassword()};

        try {
            Log.i("DB", "haciendo validacion usuario: " + user.getUsername());

            try (Cursor cursor = db.query(TABLA_USUARIOS, null, CAMPO_USUARIOS_USERNAME + "=? AND " + CAMPO_USUARIOS_PASSWORD + "=?", args, null, null, null)) {
                toRet = cursor.getCount() == 1;
            }
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
            throw new RuntimeException("Error DB");
        }
        Log.i("DB_isValidUser", "validacion usuario: " + user.getUsername() + " resultado: " + toRet);
        return toRet;
    }

    /**
     * Método para verificar si un nombre de usuario ya existe en la base de datos.
     *
     * @param username Nombre de usuario que se va a verificar.
     * @return Verdadero si el nombre de usuario existe, falso en caso contrario.
     */
    public boolean usernameExists(String username) {
        boolean toRet;
        final SQLiteDatabase db = instance.getWritableDatabase();

        String[] args = new String[]{username};

        try {
            Log.i("DB", "Busqueda del usuario: " + username);

            try (Cursor cursor = db.query(TABLA_USUARIOS, null, CAMPO_USUARIOS_USERNAME + "=? ", args, null, null, null)) {
                toRet = cursor.getCount() == 1;
            }
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
            throw new RuntimeException("Error DB");
        }

        return toRet;
    }

    /**
     * Método para verificar si un correo electrónico ya existe en la base de datos.
     *
     * @param email Correo electrónico que se va a verificar.
     * @return Verdadero si el correo electrónico existe, falso en caso contrario.
     */
    public boolean emailExists(String email) {
        boolean toRet;
        final SQLiteDatabase db = instance.getWritableDatabase();

        String[] args = new String[]{email};

        try {
            Log.i("DB", "Busqueda de email: " + email);

            try (Cursor cursor = db.query(TABLA_USUARIOS, null, CAMPO_USUARIOS_EMAIL + "=? ", args, null, null, null)) {
                toRet = cursor.getCount() == 1;
            }
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
            throw new RuntimeException("Error DB");
        }

        return toRet;
    }
}
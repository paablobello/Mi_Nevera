package com.example.mi_nevera.model;

import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_ALTURA;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_APELLIDO;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_EDAD;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_GENERO;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_NOMBRE;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_OWNER;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_PESO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mi_nevera.core.DBManager;

import java.util.ArrayList;

public class PerfilMapper extends DBMapper {

    public PerfilMapper(android.content.Context context) {
        super(context);
    }

    public void addPerfil(Perfil perfil, String username) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(CAMPO_PERFIL_NOMBRE, perfil.getNombre());
        values.put(CAMPO_PERFIL_APELLIDO, perfil.getApellidos());
        values.put(CAMPO_PERFIL_EDAD, perfil.getEdad());
        values.put(CAMPO_PERFIL_PESO, perfil.getPeso());
        values.put(CAMPO_PERFIL_ALTURA, perfil.getAltura());
        values.put(CAMPO_PERFIL_GENERO, perfil.getGenero());
        values.put(CAMPO_PERFIL_OWNER, username);

        try {
            db.beginTransaction();
            db.insertOrThrow(DBManager.TABLA_PERFIL, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException err) {
            Log.e("DB", err.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public boolean deletePerfil(String nombre, String username) {
        final SQLiteDatabase db = instance.getWritableDatabase();
        final String[] args = {nombre, username};
        final int affectedRows = db.delete(DBManager.TABLA_PERFIL, CAMPO_PERFIL_NOMBRE + "=? AND " + CAMPO_PERFIL_OWNER + "=?", args);
        return affectedRows > 0;
    }

    public boolean existePerfil(Perfil perfil, String username) {
        final SQLiteDatabase db = instance.getReadableDatabase();
        final String[] args = {perfil.getNombre(), username};
        final Cursor cursor = db.rawQuery("SELECT * FROM " + DBManager.TABLA_PERFIL + " WHERE " + CAMPO_PERFIL_NOMBRE + "=? AND " + CAMPO_PERFIL_OWNER + "=?", args);

        try {
            return cursor.moveToFirst();
        } catch (RuntimeException err) {
            Log.e("DB", err.getMessage());
            throw err;
        } finally {
            cursor.close();
        }
    }

    public ArrayList<String> getPerfilUsuario(String username) {
        final SQLiteDatabase db = instance.getReadableDatabase();
        final ArrayList<String> toRet = new java.util.ArrayList<>();

        // Consulta SQL para obtener todos los campos de la tabla TABLA_PERFIL para un usuario específico
        final Cursor cursor = db.rawQuery("SELECT * FROM " + DBManager.TABLA_PERFIL + " WHERE " + CAMPO_PERFIL_OWNER + "=?", new String[]{username});

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
}

package com.example.mi_nevera.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mi_nevera.model.Perfil;

import java.util.ArrayList;

import kotlin.Pair;
import kotlin.Triple;

/**
 * Esta clase se utiliza para representar la clase base que todas las clases Mapper van a usar.
 * Contiene una instancia de la base de datos.
 */

public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;

    private static final String DB_NOMBRE = "Recetas_DB";
    private static final int DB_VERSION = 1;

    //Creacion de la tabla usuarios
    public static final String TABLA_USUARIOS = "users";
    public static final String CAMPO_USUARIOS_USERNAME = "username";

    public static final String CAMPO_USUARIOS_PASSWORD = "password";
    public static final String CAMPO_USUARIOS_EMAIL = "email";

    //Creacion de la tabla ingredientes
    public static final String TABLA_INGREDIENTES = "ingredientes";
    public static final String CAMPO_INGREDIENTES_ID = "_id";
    public static final String CAMPO_INGREDIENTES_NAME = "name";
    public static final String CAMPO_INGREDIENTES_OWNER = "owner";

    //Lista de la compra
    public static final String TABLA_LISTA_COMPRA = "lista_compra";
    public static final String CAMPO_LISTA_COMPRA_ID = "_id";
    public static final String CAMPO_LISTA_COMPRA_NAME = "name";
    public static final String CAMPO_LISTA_COMPRA_OWNER = "owner";

    //Lista de la compra
    public static final String TABLA_PERFIL = "perfil";
    public static final String CAMPO_PERFIL_ID = "_id";
    public static final String CAMPO_PERFIL_NOMBRE = "name";
    public static final String CAMPO_PERFIL_APELLIDO = "apellido";
    public static final String CAMPO_PERFIL_EDAD = "age";
    public static final String CAMPO_PERFIL_PESO = "kg";
    public static final String CAMPO_PERFIL_ALTURA = "cm";
    public static final String CAMPO_PERFIL_GENERO = "gender";
    public static final String CAMPO_PERFIL_OWNER = "owner";
    private Session session;


    /**
     * Devuelve una instancia de la conexión con la BBDD, si no existe crea una nueva
     *
     * @param context Contexto de la app
     * @return instancia que da conexión a la BBDD
     */
    public static DBManager getManager(Context context) {
        if (instance == null) {
            instance = new DBManager(context.getApplicationContext());
        }
        return instance;
    }

    //Constructor de la conexión con la BBDD
    // context --> contexto de la app, proporciona info sobre la app y el entorno que está a ejecutar en ese momento
    // DB_NOMBRE --> nombre de la base de datos
    // null --> representa el objeto CursorFactory, se pasa null porque no es necesario para la mayoria de las apps Android
    // DB_VERSION --> numero de version de la BBDD. Para gestionar actualizaciones de la BBDD

    /**
     * Constructor de la conexión con la BBDD
     *
     * @param context
     */
    private DBManager(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    // Establece si las restricciones de clave externa están habilitadas para la base de datos.
    // Por defecto, las restricciones de clave externa no son aplicadas por la base de datos

    /**
     * Establece si las restricciones de clave externa están habilitadas para la base de datos.
     * Por defecto, las restricciones de clave externa no son aplicadas por la base de datos
     *
     * @param db
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Método que se ejecuta cuando se crea la base de datos.
     *
     * @param db Base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(DB_NOMBRE, "Llevando a cabo la creación de las tablas");
            db.beginTransaction();

            // SQL DE EJECUCIÓN PARA CREAR TABLA USUARIOS
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_USUARIOS + "(" +
                    CAMPO_USUARIOS_USERNAME + " TEXT PRIMARY KEY, " +
                    CAMPO_USUARIOS_PASSWORD + " TEXT NOT NULL, " +
                    CAMPO_USUARIOS_EMAIL + " TEXT NOT NULL UNIQUE " +
                    ")"
            );

            // SQL DE EJECUCION PARA CREAR LA TABLA INGREDIENTES
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_INGREDIENTES + "(" +
                    CAMPO_INGREDIENTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_INGREDIENTES_NAME + " TEXT NOT NULL, " +
                    CAMPO_INGREDIENTES_OWNER + " TEXT NOT NULL " +
                    ")"
            );
            // SQL DE EJECUCION PARA CREAR LA TABLA LISTA_COMPRA
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_LISTA_COMPRA + "(" +
                    CAMPO_LISTA_COMPRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_LISTA_COMPRA_NAME + " TEXT NOT NULL, " +
                    CAMPO_LISTA_COMPRA_OWNER + " TEXT NOT NULL " +
                    ")"
            );

            // SQL DE EJECUCION PARA CREAR LA TABLA PERFIL
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_PERFIL + "(" +
                    CAMPO_PERFIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_PERFIL_NOMBRE + " TEXT NOT NULL, " +
                    CAMPO_PERFIL_APELLIDO + " TEXT NOT NULL, " +
                    CAMPO_PERFIL_EDAD + " INTEGER NOT NULL, " +
                    CAMPO_PERFIL_PESO + " DOUBLE NOT NULL, " +
                    CAMPO_PERFIL_ALTURA + " DOUBLE NOT NULL, " +
                    CAMPO_PERFIL_GENERO + " TEXT NOT NULL, " +
                    CAMPO_PERFIL_OWNER + " TEXT NOT NULL " +
                    ")"
            );

            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e(DB_NOMBRE, ex.getMessage());
        } finally {
            db.endTransaction();
        }

        //CARGA EN LA BBDD INFORMACION INICIAL PARA QUE NO ESTÉ VACÍA SIEMPRE
        this.loadInitialData(db);
        this.loadInitialDataUser(db);
        this.loadInitialDataList(db);
        this.loadInitialDataProfile(db);
    }

    /**
     * Método para introducir ingredientes inicial
     *
     * @param db
     */
    private void loadInitialDataList(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            ArrayList<Pair<String, String>> listaCompra = new ArrayList<>();
            listaCompra.add(new Pair<>("Pera", "iago"));

            //listaIncredientes.add("Patatas","iago");

            //listaIncredientes.add("Cebolla");

            //listaIncredientes.add("Pimiento");

            //Almacenar un conjunto de valores que se pueden utilizar para insertar o actualizar registros en una base de datos
            ContentValues cntValues;
            for (Pair<String, String> t : listaCompra) {
                cntValues = new ContentValues();
                cntValues.put(CAMPO_LISTA_COMPRA_NAME, t.getFirst());
                cntValues.put(CAMPO_LISTA_COMPRA_OWNER, t.getSecond());
                db.insert(
                        TABLA_LISTA_COMPRA,
                        null,
                        cntValues
                );
            }
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(DB_NOMBRE, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para introducir ingredientes inicial
     *
     * @param db
     */
    private void loadInitialData(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            ArrayList<Pair<String, String>> listaIncredientes = new ArrayList<>();
            listaIncredientes.add(new Pair<>("Tomate", "iago"));

            //listaIncredientes.add("Patatas","iago");

            //listaIncredientes.add("Cebolla");

            //listaIncredientes.add("Pimiento");

            //Almacenar un conjunto de valores que se pueden utilizar para insertar o actualizar registros en una base de datos
            ContentValues cntValues;
            for (Pair<String, String> t : listaIncredientes) {
                cntValues = new ContentValues();
                cntValues.put(CAMPO_INGREDIENTES_NAME, t.getFirst());
                cntValues.put(CAMPO_INGREDIENTES_OWNER, t.getSecond());
                db.insert(
                        TABLA_INGREDIENTES,
                        null,
                        cntValues
                );
            }
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(DB_NOMBRE, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para introducir usuario 'iago' inicial
     */
    private void loadInitialDataUser(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            ArrayList<Triple<String, String, String>> listaUsuarios = new ArrayList<>();

            listaUsuarios.add(new Triple<>("iago", "iago", "iago@iago.com"));

            //Almacenar un conjunto de valores que se pueden utilizar para insertar o actualizar registros en una base de datos
            ContentValues cntValues;
            for (Triple<String, String, String> t : listaUsuarios) {
                cntValues = new ContentValues();
                cntValues.put(CAMPO_USUARIOS_USERNAME, t.getFirst());
                cntValues.put(CAMPO_USUARIOS_PASSWORD, t.getSecond());
                cntValues.put(CAMPO_USUARIOS_EMAIL, t.getThird());
                db.insert(
                        TABLA_USUARIOS,
                        null,
                        cntValues
                );
            }
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e(DB_NOMBRE, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método para introducir datos del perfil 'iago' inicial
     */
    private void loadInitialDataProfile(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            ArrayList<Pair<Perfil, String>> lista = new ArrayList<>();
            //ArrayList<Perfil> listaPerfiles = new ArrayList<>();

            lista.add(new Pair<>(new Perfil("iago", "doval mendez", 23, 70.5, 180, "hombre"), "iago"));

            //Almacenar un conjunto de valores que se pueden utilizar para insertar o actualizar registros en una base de datos
            ContentValues cntValues;
            for (Pair<Perfil, String> pair : lista) {
                Perfil perfil = pair.getFirst();
                String owner = pair.getSecond();

                cntValues = new ContentValues();
                cntValues.put(CAMPO_PERFIL_NOMBRE, perfil.getNombre());
                cntValues.put(CAMPO_PERFIL_APELLIDO, perfil.getApellidos());
                cntValues.put(CAMPO_PERFIL_EDAD, perfil.getEdad());
                cntValues.put(CAMPO_PERFIL_PESO, perfil.getPeso());
                cntValues.put(CAMPO_PERFIL_ALTURA, perfil.getAltura());
                cntValues.put(CAMPO_PERFIL_GENERO, perfil.getGenero());
                cntValues.put(CAMPO_PERFIL_OWNER, owner);
                db.insert(
                        TABLA_PERFIL,
                        null,
                        cntValues
                );
            }
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e(DB_NOMBRE, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método que resetea la BBDD cuando se quiere actualizar algún parámetro
     *
     * @param db          NOMBRE DE LA BBDD
     * @param version_OLD numero de la version antigua
     * @param version_NEW numero de la version nueva
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_OLD, int version_NEW) {
        try {
            Log.i(DB_NOMBRE, "ACTUALIZANDO BBDD");
            db.beginTransaction();

            db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_INGREDIENTES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_PERFIL);

            db.setTransactionSuccessful();
        } catch (SQLException err) {
            Log.e(DB_NOMBRE, err.getMessage());
        } finally {
            db.endTransaction();
        }

        this.onCreate(db);
    }
}

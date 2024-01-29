package com.example.mi_nevera.core;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mi_nevera.model.User;
import com.example.mi_nevera.model.UserMapper;

//LA CLASE SESSION HACE REFERENCIA A LA SESION DE UN USUARIO. A la app solamente puede acceder un usuario
// a la vez, entonces con Session usando el patrón Singleton (una unica instancia en cada momento) conseguimos
// garantizar que solamente exista una única sesión durante la ejecución del programa
public class Session {
    private static Session session;
    private SharedPreferences preferences;
    private User usuario;
    private UserMapper userMapper;

    private boolean sessionActiva;

    /**
     * Accedemos a la session actual. Si no existe session, se crea una nueva
     *
     * @param context Contexto de la app
     * @return Instancia de la session
     */
    public static Session getSession(Context context) {
        if (session == null) {
            session = new Session(context.getApplicationContext());
        }
        return session;
    }

    /**
     * Constructor de la sesion. Con una instancia de UserMapper para iniciar la sesion, desde las preferencias
     * obtiene los datos para obtener la sesion guardada
     *
     * @param context Contexto de la aplicacion
     */
    public Session(Context context) {
        this.userMapper = new UserMapper(context);
        this.preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        this.sessionActiva = getSession();
    }

    /**
     * Genera una nueva sesion con los datos que tenemos en las preferencias
     *
     * @return en el caso de que los datos almacenados sean correctos devuelve T, F en caso contrario
     */
    private boolean getSession() {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);

        if (username != null && password != null) {
            usuario = new User(username, password);
            try {
                usuario.validacionForLogin();
                if (userMapper.isValidUser(usuario)) {
                    Log.e("Session", "TRUE");
                    return true;
                } else {
                    Log.e("Session", "FALSE");
                    return false;
                }
            } catch (ValidacionException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Inicia la creacion de una nueva session para USER
     *
     * @param user Almacenamos los datos de usuario
     * @return T en caso de que los datos sean correctos, F en caso contrario
     */
    public boolean iniciarSession(User user) {
        try {
            user.validacionForLogin();
        } catch (ValidacionException ex) {
            return false;
        }

        if (userMapper.isValidUser(user)) {
            SharedPreferences.Editor editor = this.preferences.edit();
            editor.putString("username", user.getUsername());
            editor.putString("password", user.getPassword());
            editor.commit();
            this.usuario = user;
            this.sessionActiva = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cierra la session y la elimina de las preferencias
     */
    public void cerrarSession() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.commit();
        this.sessionActiva = false;
    }
    /**
     * Devuelve si la session esta activa o no
     *
     * @return T si la session esta activa, F en caso contrario
     */
    public boolean isSessionActiva() {
        return sessionActiva;
    }
    /**
     * Devuelve el usuario de la session
     *
     * @return Username de la session
     */
    public String getUsername() {
        return usuario.getUsername();
    }
}

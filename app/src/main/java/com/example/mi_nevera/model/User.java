package com.example.mi_nevera.model;

import android.app.admin.SystemUpdatePolicy;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.ValidacionException;

import java.util.regex.Pattern;

/**
 * Esta clase representa un usuario en la aplicación.
 * Contiene los atributos de un usuario y métodos para validar los datos del usuario.
 */
public class User {

    private String username;
    private String password;
    private String email;

    /**
     * Constructor vacío para la clase User.
     */
    public User() {
    }

    /**
     * Constructor para la clase User.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @param email El correo electrónico del usuario.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Constructor para la clase User.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter para el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter para el nombre de usuario.
     * @param username El nuevo nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter para la contraseña.
     * @return La contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter para la contraseña.
     * @param password La nueva contraseña.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter para el correo electrónico.
     * @return El correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter para el correo electrónico.
     * @param email El nuevo correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para validar el nombre de usuario.
     * @param username El nombre de usuario a validar.
     * @throws ValidacionException si el nombre de usuario no es válido.
     */
    public static void validacionUSERNAME(String username) throws ValidacionException {
        final String regex = "[A-Za-z0-9]{3,30}";
        if (!Pattern.matches(regex, username)) {
            throw new ValidacionException("VALIDATION ERROR - LOGIN", R.string.username_erroneo);
        }
    }

    /**
     * Método para validar la contraseña.
     * @param username La contraseña a validar.
     * @throws ValidacionException si la contraseña no es válida.
     */
    public static void validacionPASSWORD(String username) throws ValidacionException {
        final String regex = "[A-Za-z0-9]{3,30}";
        if (!Pattern.matches(regex, username)) {
            throw new ValidacionException("VALIDATION ERROR - LOGIN", R.string.password_erroneo);
        }
    }

    /**
     * Método para validar el correo electrónico.
     * @param username El correo electrónico a validar.
     * @throws ValidacionException si el correo electrónico no es válido.
     */
    public static void validacionEMAIL(String username) throws ValidacionException {
        final String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(regex, username)) {
            throw new ValidacionException("VALIDATION ERROR - LOGIN", R.string.email_erroneo);
        }
    }

    /**
     * Método para validar los datos del usuario para el registro.
     * @throws ValidacionException si los datos del usuario no son válidos.
     */
    public void validacionForRegister() throws ValidacionException {
        validacionUSERNAME(this.username);
        validacionPASSWORD(this.password);
        validacionEMAIL(this.email);
    }

    /**
     * Método para validar los datos del usuario para el inicio de sesión.
     * @throws ValidacionException si los datos del usuario no son válidos.
     */
    public void validacionForLogin() throws ValidacionException {
        validacionUSERNAME(this.username);
        validacionPASSWORD(this.password);
    }
}
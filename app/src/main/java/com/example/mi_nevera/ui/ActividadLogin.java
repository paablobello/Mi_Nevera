package com.example.mi_nevera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.core.ValidacionException;
import com.example.mi_nevera.model.User;

/**
 * Esta clase representa la actividad de inicio de sesión en la aplicación.
 * Extiende AppCompatActivity para tener todas las funcionalidades de una actividad de Android.
 */
public class ActividadLogin extends AppCompatActivity {
    private Session session;
    private User user;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método onCreate de la clase base (superclase)
        super.onCreate(savedInstanceState);

        // Establece la vista para esta actividad
        setContentView(R.layout.activity_login);

        // Inicialización de atributos de la actividad
        ActividadLogin.this.session = Session.getSession(ActividadLogin.this);
        ActividadLogin.this.user = new User();

        // Configurar la barra de herramientas (ActionBar)
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.loginTEXT);

        // Configurar el OnClickListener para el botón de inicio de sesión
        final Button btnLOGIN = ActividadLogin.this.findViewById(R.id.btnLogin);
        btnLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método login() cuando se hace clic en el botón
                ActividadLogin.this.login2();
            }
        });

        // Obtener referencias a los campos de entrada de texto
        final EditText editText_USERNAME = ActividadLogin.this.findViewById(R.id.edUsername);
        final EditText editText_PASSWORD = ActividadLogin.this.findViewById(R.id.edPasswd);

        // Configurar el evento de cambio de foco para el campo de nombre de usuario
        editText_USERNAME.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Realiza validación del nombre de usuario
                User.validacionUSERNAME(editText_USERNAME.getText().toString());
            } catch (ValidacionException err) {
                // Muestra un error si la validación falla
                editText_USERNAME.setError(getResources().getString(err.getError()));
            }
        });

        // Configurar el evento de cambio de foco para el campo de contraseña
        editText_PASSWORD.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Realiza validación de la contraseña
                User.validacionPASSWORD(editText_PASSWORD.getText().toString());
            } catch (ValidacionException err) {
                // Muestra un error si la validación falla
                editText_PASSWORD.setError(getResources().getString(err.getError()));
            }
        });

        // Verifica si hay una sesión activa; si es así, finaliza la actividad
        if (ActividadLogin.this.session.isSessionActiva()) {
            ActividadLogin.this.finish();
        }
    }

    /**
     * Método que se llama cuando se reanuda la actividad.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActividadLogin.this.session.isSessionActiva()) {
            ActividadLogin.this.finish();
        }
    }

    /**
     * Método para iniciar sesión.
     */
    private void login2() {
        // Obtener referencias a los EditText en la interfaz de usuario
        final EditText editText_USERNAME = findViewById(R.id.edUsername);
        final EditText editText_PASSWORD = findViewById(R.id.edPasswd);

        // Establecer el nombre de usuario y la contraseña en el objeto 'user'
        ActividadLogin.this.user.setUsername(editText_USERNAME.getText().toString());
        ActividadLogin.this.user.setPassword(editText_PASSWORD.getText().toString());

        try {
            // Realizar la validación del usuario
            ActividadLogin.this.user.validacionForLogin();

            try {
                // Intentar iniciar sesión utilizando el objeto 'session'
                if (ActividadLogin.this.session.iniciarSession(ActividadLogin.this.user)) {
                    // Mostrar un mensaje de inicio de sesión exitoso
                    Toast.makeText(ActividadLogin.this, R.string.login_OK, Toast.LENGTH_SHORT).show();

                    // Navegar a MainActivity
                    Intent mainActivityIntent = new Intent(ActividadLogin.this, MainActivity.class);
                    startActivity(mainActivityIntent);

                    // Finalizar la ActividadLogin para que el usuario no pueda volver a ella con el botón atrás
                    finish();
                }
            } catch (RuntimeException ex) {
                // Manejar una excepción si hay un error durante el inicio de sesión en la base de datos
                Log.e("LoginError", "Error durante el inicio de sesión", ex);
                Toast.makeText(this, R.string.bd_err, Toast.LENGTH_SHORT).show();
            }
        } catch (ValidacionException err) {
            // Manejar una excepción si la validación del usuario falla
            Toast.makeText(ActividadLogin.this, R.string.campos_KO, Toast.LENGTH_SHORT).show();
        }
    }
}
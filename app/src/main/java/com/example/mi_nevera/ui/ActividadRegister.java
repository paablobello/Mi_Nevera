package com.example.mi_nevera.ui;

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
import com.example.mi_nevera.model.UserMapper;

/**
 * Esta clase representa la actividad de registro en la aplicación.
 * Extiende AppCompatActivity para tener todas las funcionalidades de una actividad de Android.
 */
public class ActividadRegister extends AppCompatActivity {

    private Session session;
    private User user;
    private UserMapper userMapper;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método onCreate de la clase base (superclase)
        super.onCreate(savedInstanceState);

        // Establece la vista para esta actividad
        setContentView(R.layout.activity_registro);

        // Inicialización de atributos de la actividad
        ActividadRegister.this.session = Session.getSession(ActividadRegister.this);
        ActividadRegister.this.user = new User();
        ActividadRegister.this.userMapper = new UserMapper(ActividadRegister.this);

        // Configurar la barra de herramientas (ActionBar)
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.singin);

        // Configurar el OnClickListener para el botón de registro
        final Button btnREGISTER = ActividadRegister.this.findViewById(R.id.btnRegistro);
        btnREGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método register() cuando se hace clic en el botón
                ActividadRegister.this.register();
            }
        });

        // Obtener referencias a los campos de entrada de texto
        final EditText editText_USERNAME = ActividadRegister.this.findViewById(R.id.edUsername);
        final EditText editText_PASSWORD = ActividadRegister.this.findViewById(R.id.edPasswd);
        final EditText editText_EMAIL = ActividadRegister.this.findViewById(R.id.edEmail);

        // Configurar el evento de cambio de foco para el campo de nombre de usuario
        editText_USERNAME.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Realiza validación del nombre de usuario
                User.validacionUSERNAME(editText_USERNAME.getText().toString());
                try {
                    // Verifica si el nombre de usuario ya existe en la base de datos
                    if (ActividadRegister.this.userMapper.usernameExists(editText_USERNAME.getText().toString())) {
                        editText_USERNAME.setError(getResources().getString(R.string.username_duplicate));
                    }
                } catch (RuntimeException err) {
                    // Manejar un error de base de datos relacionado con el nombre de usuario
                    Log.e("BD", "Error BD Registro USERNAME");
                }
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

        // Configurar el evento de cambio de foco para el campo de correo electrónico
        editText_EMAIL.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                // Realiza validación del correo electrónico
                User.validacionEMAIL(editText_EMAIL.getText().toString());
                try {
                    // Verifica si el correo electrónico ya existe en la base de datos
                    if (ActividadRegister.this.userMapper.emailExists(editText_EMAIL.getText().toString())) {
                        editText_EMAIL.setError(getResources().getText(R.string.email_duplicate));
                    }
                } catch (RuntimeException err) {
                    // Manejar un error de base de datos relacionado con el correo electrónico
                    Log.e("BD", "ERROR BD Registro PASSWORD");
                }
            } catch (ValidacionException err) {
                // Muestra un error si la validación falla
                editText_EMAIL.setError(getResources().getText(err.getError()));
            }
        });

        // Verifica si hay una sesión activa; si es así, finaliza la actividad
        if (ActividadRegister.this.session.isSessionActiva()) {
            ActividadRegister.this.finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActividadRegister.this.session.isSessionActiva()) {
            ActividadRegister.this.finish();
        }
    }

    /**
     * Método para registrar un nuevo usuario.
     */
    private void register() {
        final EditText editText_USERNAME = ActividadRegister.this.findViewById(R.id.edUsername);
        final EditText editText_PASSWORD = ActividadRegister.this.findViewById(R.id.edPasswd);
        final EditText editText_EMAIL = ActividadRegister.this.findViewById(R.id.edEmail);

        ActividadRegister.this.user.setUsername(editText_USERNAME.getText().toString());
        ActividadRegister.this.user.setPassword(editText_PASSWORD.getText().toString());
        ActividadRegister.this.user.setEmail(editText_EMAIL.getText().toString());

        try {
            ActividadRegister.this.user.validacionForRegister();
            try {
                boolean usernameExists = ActividadRegister.this.userMapper.usernameExists(ActividadRegister.this.user.getUsername());
                boolean emailExists = ActividadRegister.this.userMapper.emailExists(ActividadRegister.this.user.getEmail());

                if (usernameExists || emailExists) {
                    if (usernameExists) {
                        Toast.makeText(ActividadRegister.this, R.string.username_duplicate, Toast.LENGTH_SHORT).show();
                    }
                    if (emailExists) {
                        Toast.makeText(ActividadRegister.this, R.string.email_duplicate, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ActividadRegister.this.userMapper.addUser(ActividadRegister.this.user);
                    Toast.makeText(ActividadRegister.this, R.string.registro_OK, Toast.LENGTH_SHORT).show();
                    ActividadRegister.this.finish();
                }
            } catch (RuntimeException err) {
                Toast.makeText(ActividadRegister.this, R.string.bd_err, Toast.LENGTH_SHORT).show();
            }
        } catch (ValidacionException err) {
            Toast.makeText(ActividadRegister.this, R.string.campos_KO, Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.mi_nevera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.Session;

/**
 * Esta clase representa la actividad inicial de la aplicación.
 * Extiende AppCompatActivity para tener todas las funcionalidades de una actividad de Android.
 */
public class ActividadInicial extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegistro;
    private Session session;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial); // Asegúrate de que este es el nombre correcto de tu layout

        // Inicializa los botones y la sesión
        btnLogin = findViewById(R.id.btnInicioLogin);
        btnRegistro = findViewById(R.id.btnInicialRegistro);
        ActividadInicial.this.session = Session.getSession(ActividadInicial.this);

        // Configura el botón de inicio de sesión para iniciar la actividad de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(ActividadInicial.this, ActividadLogin.class);
                startActivity(loginIntent);
            }
        });

        // Configura el botón de registro para iniciar la actividad de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ActividadInicial.this, ActividadRegister.class);
                startActivity(registerIntent);
            }
        });

        // Si hay una sesión activa, inicia la actividad del usuario
        if (ActividadInicial.this.session.isSessionActiva()) {
            ActividadInicial.this.startUserActivity();
        }
    }

    /**
     * Método para iniciar la actividad del usuario.
     */
    private void startUserActivity() {
        ActividadInicial.this.startActivity(new Intent(ActividadInicial.this, MainActivity.class));
        ActividadInicial.this.finish();
        Log.i("IR AL MAIN", "NOS FUIMOS");
    }
}
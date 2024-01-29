package com.example.mi_nevera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mi_nevera.R;

/**
 * Esta clase representa la actividad de carga en la aplicación.
 * Extiende AppCompatActivity para tener todas las funcionalidades de una actividad de Android.
 */
public class ActividadCarga extends AppCompatActivity {

    // Duración de la pantalla de carga en milisegundos
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        // Oculta la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Puedes iniciar tus animaciones aquí si lo deseas
        final Animation ANIMACION_NOMBRE = AnimationUtils.loadAnimation(this, R.anim.init);
        final Animation ANIMACION_LOGO = AnimationUtils.loadAnimation(this, R.anim.init);

        // Configura la animación para el logo
        final ImageView TV_LOGO = this.findViewById(R.id.ivLogoSplash);
        TV_LOGO.setAnimation(ANIMACION_LOGO);

        // Configura la animación para el texto de bienvenida
        final TextView TV_WELCOME = this.findViewById(R.id.tvWelcome);
        TV_WELCOME.setAnimation(ANIMACION_NOMBRE);

        // Usar un manejador para iniciar ActividadInicial después del tiempo de SPLASH_DISPLAY_LENGTH
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar ActividadInicial cuando el tiempo ha transcurrido
                Intent intent = new Intent(ActividadCarga.this, ActividadInicial.class);
                ActividadCarga.this.startActivity(intent);
                ActividadCarga.this.finish(); // Finaliza la actividad de carga para que el usuario no pueda volver a ella
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
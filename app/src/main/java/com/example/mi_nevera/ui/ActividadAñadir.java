package com.example.mi_nevera.ui;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.model.Ingrediente;
import com.example.mi_nevera.model.IngredienteMapper;

/**
 * Esta clase representa la actividad de añadir ingredientes en la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class ActividadAñadir extends MenuActivity {

    private Session session;

    private TextView textViewAñadir;
    private Button buttonAñadir;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_anadir);

        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Añadir productos");

        textViewAñadir = findViewById(R.id.editTextTexto);
        buttonAñadir = findViewById(R.id.botonConfirmar);

        session = Session.getSession(ActividadAñadir.this);

        //Toast.makeText(ActividadAñadir.this, session.getUsername(), Toast.LENGTH_SHORT).show();
        buttonAñadir.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String texto = textViewAñadir.getText().toString().trim();

                // Verificar si el texto está vacío
                if (texto.isEmpty()) {
                    // Mostrar un Toast si está vacío
                    Toast.makeText(ActividadAñadir.this, "Por favor, introduce un ingrediente válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Añadir a la base de datos y cerrar la actividad si el texto no está vacío
                    Ingrediente ingrediente = new Ingrediente(texto);
                    IngredienteMapper ingredienteMapper = new IngredienteMapper(ActividadAñadir.this);
                    ingredienteMapper.addIngredienteUsuario(ingrediente, session.getUsername());

                    // Finalizar la actividad y mostrar un Toast de confirmación
                    ActividadAñadir.this.finish();
                    Toast.makeText(ActividadAñadir.this, "Añadido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
package com.example.mi_nevera.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mi_nevera.ActividadKcal;
import com.example.mi_nevera.R;
import com.example.mi_nevera.Receta;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;


/**
 * Esta clase representa la actividad de una receta en la aplicación.
 * Extiende AppCompatActivity para tener todas las funcionalidades de una actividad de Android.
 */
public class ActividadReceta extends AppCompatActivity {

    public int itemAbout = R.id.itemAbout;
    public int itemBack = R.id.itemBack;
    public int itemLogout = R.id.itemLogout;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        TextView textView = findViewById(R.id.textRecetaCompleta);
        textView.setMovementMethod(new ScrollingMovementMethod());

        // Obtén la receta pasada a esta actividad
        Receta receta = (Receta) getIntent().getSerializableExtra("receta");

        // Encuentra las vistas que mostrarán los detalles
        TextView nombreRecetaView = findViewById(R.id.textRecetaTitulo);
        TextView recetaCompletaView = findViewById(R.id.textRecetaCompleta);

        // Asigna los valores de la receta a las vistas
        nombreRecetaView.setText(receta.getNombre());
        String textoReceta = "Ingredientes:\n" + receta.getDescripcion();
        recetaCompletaView.setText(textoReceta);
    }

    /**
     * Método para inflar el menú en la barra de acción.
     * @param menu El menú a inflar.
     * @return Verdadero para mostrar el menú.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_receta, menu);
        return true;
    }

    /**
     * Método para manejar cuando se selecciona un item del menú.
     * @param item El item seleccionado.
     * @return Verdadero si el item fue manejado.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ver_kcal) {
            abrirActividadKcal();
            return true;
        }else if (id == itemBack) {
            performBack();
            return true;
        } else if (id == itemLogout) {
            performLogout();
            return true;
        } else if (id == itemAbout) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Método para abrir la actividad de kcal.
     */
    private void abrirActividadKcal() {
        Intent intent = new Intent(this, ActividadKcal.class);
        startActivity(intent);
    }

    /**
     * Método que se ejecuta cuando se selecciona el elemento de menú "About".
     */
    private void showAbout() {
        Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ActividadAbout.class);
        startActivity(intent);
    }
    /**
     * Método que se ejecuta cuando se selecciona el elemento de menú "Atras".
     */
    public void performBack() {
        //Session session = Session.getSession(this);
        //session.cerrarSession();
        // Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        this.finish();
    }
    /**
     * Método que se ejecuta cuando se selecciona el elemento de menú "Logout".
     */
    public void performLogout() {
        Session session = Session.getSession(this);
        session.cerrarSession();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

        // Iniciar la actividad de inicio despues de cerrar sesión
        Intent intent = new Intent(this, ActividadInicial.class);
        startActivity(intent);

        this.finish();
    }
}
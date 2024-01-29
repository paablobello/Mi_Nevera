package com.example.mi_nevera.core;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mi_nevera.R;
import com.example.mi_nevera.ui.ActividadAbout;
import com.example.mi_nevera.ui.ActividadCarga;
import com.example.mi_nevera.ui.ActividadInicial;


/**
 * Clase de la que extienden todas las actividades que tienen menú de opciones, en algunos casos
 * se puede sobreeescribir el método onCreateOptionsMenu para editar elementos al menú.
 *
 */
public abstract class MenuActivity extends AppCompatActivity {

    public int itemAbout = R.id.itemAbout;
    public int itemBack = R.id.itemBack;
    public int itemLogout = R.id.itemLogout;

    /**
     * Método que se ejecuta cuando se crea el menú de opciones.
     * @param menu Menú de opciones
     * @return true si se ha creado correctamente, false en caso contrario
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Método que se ejecuta cuando se selecciona un elemento del menú de opciones.
     * @param item Elemento del menú seleccionado
     * @return true si se ha seleccionado correctamente, false en caso contrario
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == itemAbout) {
            // Acción para el primer elemento del menú
            showAbout();
            return true;
        } else if (idItem == itemLogout) {
            // Acción para el segundo elemento del menú
            performLogout();
            return true;
        } else if (idItem == itemBack) {
            // Acción para el segundo elemento del menú
            performBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

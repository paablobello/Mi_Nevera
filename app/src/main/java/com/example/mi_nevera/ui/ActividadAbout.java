package com.example.mi_nevera.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.MenuActivity;

public class ActividadAbout extends MenuActivity {

        /**
        * Método que se ejecuta cuando se crea la actividad.
        * @param savedInstanceState Bundle con el estado de la instancia
        */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
        }

    /**
     * Método que se ejecuta cuando se crea el menú de opciones.
     * @param menu Menú de opciones
     * @return true si se ha creado correctamente, false en caso contrario
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú; esto agrega elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Encuentra y elimina el ítem "About" del menú
        MenuItem itemAbout = menu.findItem(R.id.itemAbout);
        if (itemAbout != null) {
            menu.removeItem(itemAbout.getItemId());
        }

        return true;
    }
}




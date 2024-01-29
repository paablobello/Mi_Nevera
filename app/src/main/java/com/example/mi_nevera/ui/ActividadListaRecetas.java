package com.example.mi_nevera.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mi_nevera.R;
import com.example.mi_nevera.Receta;
import com.example.mi_nevera.core.MenuActivity;

import java.util.ArrayList;

/**
 * Esta clase representa la actividad de la lista de recetas en la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class ActividadListaRecetas extends MenuActivity {
    private ArrayList<Receta> recetas;
    private ArrayAdapter<Receta> adaptadorRecetas;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Mi nevera");

        // Trabajar con ListView
        ListView listaRecetas = (ListView) this.findViewById(R.id.listViewRecetas);
        recetas = new ArrayList<Receta>();
        adaptadorRecetas = new ArrayAdapter<Receta>(this,
                android.R.layout.simple_list_item_1,
                recetas);

        // Añadir recetas de ejemplo
        Receta r1 = new Receta("Tortilla de patatas", "Tortilla de patatas con cebolla", new String[]{"patatas", "huevos", "cebolla"});
        Receta r2 = new Receta("Pan con pan", "pan", new String[]{"pan"});

        recetas.add(r1);
        recetas.add(r2);

        adaptadorRecetas.notifyDataSetChanged();

        listaRecetas.setAdapter(adaptadorRecetas);

        // Abrir actividad de receta
        listaRecetas.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                Receta receta = recetas.get(position);
                android.content.Intent intent = new android.content.Intent(ActividadListaRecetas.this, ActividadReceta.class);
                intent.putExtra("receta", receta);
                ActividadListaRecetas.this.startActivity(intent);
            }
        });

        // Abrir actividad de añadir receta
        Button botonAñadir = (android.widget.Button) this.findViewById(R.id.botonAñadir);

        botonAñadir.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ActividadListaRecetas.this, ActividadAñadir.class);
                ActividadListaRecetas.this.startActivity(intent);
            }
        });

        // Abrir actividad de nevera
        Button botonNevera = (android.widget.Button) this.findViewById(R.id.botonNevera);

        botonNevera.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ActividadListaRecetas.this, ActividadNevera.class);
                ActividadListaRecetas.this.startActivity(intent);
            }
        });

        // Abrir actividad de perfil
        Button botonPerfil = (android.widget.Button) this.findViewById(R.id.botonPerfil);

        botonPerfil.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ActividadListaRecetas.this, ActividadPerfil.class);
                ActividadListaRecetas.this.startActivity(intent);
            }
        });

        // Abrir actividad de lista de compra
        Button botonCompra = (android.widget.Button) this.findViewById(R.id.botonListaCompra);

        botonCompra.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ActividadListaRecetas.this, ActividadCompra.class);
                ActividadListaRecetas.this.startActivity(intent);
            }
        });
    }
}
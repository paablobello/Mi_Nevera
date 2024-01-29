package com.example.mi_nevera.ui;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mi_nevera.Nevera;
import com.example.mi_nevera.R;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.model.IngredienteMapper;

import java.util.ArrayList;

/**
 * Esta clase representa la actividad de la nevera en la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class ActividadNevera extends MenuActivity {

    private Nevera nevera;
    private ListView listViewNevera;
    private IngredienteMapper ingredienteMapper;

    private ArrayList<String> listaIngredientes;

    private ArrayAdapter<String> adapter;

    private Session session;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_nevera);

        session = Session.getSession(ActividadNevera.this);
        //getIngredientesBBDD();
        getIngredientes(session.getUsername());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaIngredientes);
        listViewNevera = findViewById(R.id.listViewNevera);
        listViewNevera.setAdapter(adapter);
        listViewNevera.setOnItemLongClickListener((parent, view, position, id) -> {

            String ingrediente = listaIngredientes.get(position);
            if (ingredienteMapper.deleteIngrediente(ingrediente)) {
                listaIngredientes.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, ingrediente + " borrado", Toast.LENGTH_SHORT).show();
            } else {
                //TODO se puede controlar con una excepcion
                Toast.makeText(this, "No se ha podido borrar", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Mi nevera");
    }

    /**
     * Método para obtener los ingredientes de la base de datos.
     */
    private void getIngredientesBBDD() {
        ingredienteMapper = new IngredienteMapper(this);
        listaIngredientes = ingredienteMapper.getIngredientesTodos();
    }

    /**
     * Método para obtener los ingredientes de un usuario específico.
     * @param username El nombre de usuario.
     */
    private void getIngredientes(String username) {
        ingredienteMapper = new IngredienteMapper(this);
        listaIngredientes = ingredienteMapper.getIngredientesUsuario(username);
    }
}
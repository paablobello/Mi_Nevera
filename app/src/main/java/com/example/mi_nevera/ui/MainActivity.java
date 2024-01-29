package com.example.mi_nevera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mi_nevera.R;
import com.example.mi_nevera.Receta;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.io.RecetasGPT35;
import com.example.mi_nevera.model.IngredienteMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa la actividad principal de la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class MainActivity extends MenuActivity {
    //TODO hacer esto global
    private ArrayList<Receta> recetas;
    //private ArrayAdapter<String> adaptadorRecetas;
    private RecetasGPT35 recetasGPT35;
    private Button buttonGenerateRecipes;
    private ArrayAdapter<Receta> adaptadorRecetas;
    private Session session;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa la clase para generar recetas y obtén la sesión actual
        recetasGPT35 = new RecetasGPT35(this);
        buttonGenerateRecipes = findViewById(R.id.buttonSugerir);
        session = Session.getSession(MainActivity.this);

        // Configura el botón para generar recetas
        buttonGenerateRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRecipes();
            }
        });

        // Configura la lista de recetas
        ListView listaRecetas = findViewById(R.id.listViewRecetas); // Asegúrate de que este ID corresponda
        adaptadorRecetas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<Receta>());

        listaRecetas.setAdapter(adaptadorRecetas);

        // Configura el evento de clic en la lista de recetas para abrir la receta seleccionada
        listaRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receta recetaSeleccionada = adaptadorRecetas.getItem(position);
                Intent intent = new Intent(MainActivity.this, ActividadReceta.class);
                intent.putExtra("receta", recetaSeleccionada);
                startActivity(intent);
            }
        });

        // Configura los botones para abrir las diferentes actividades
        Button botonAñadir = findViewById(R.id.botonAñadir);
        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActividadAñadir.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button botonNevera = findViewById(R.id.botonNevera);
        botonNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActividadNevera.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button botonPerfil = findViewById(R.id.botonPerfil);
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActividadPerfil.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button botonCompra = findViewById(R.id.botonListaCompra);
        botonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActividadCompra.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Método para generar recetas basadas en los ingredientes del usuario.
     */
    private void generateRecipes() {
        String ingredients;
        ArrayList<String> ingredientes = getIngredientesUsuarioBBDD(this.session.getUsername());
        ingredients = String.join(",", ingredientes);
        // Toast.makeText(this, ingredients, Toast.LENGTH_SHORT).show();

        // Solicita la generación de recetas a la API
        recetasGPT35.generarReceta(ingredients, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray choices = response.getJSONArray("choices");
                    JSONObject firstChoice = choices.getJSONObject(0);
                    String content = firstChoice.getJSONObject("message").getString("content");

                    // Imprime la respuesta completa en el log
                    Log.i("APIResponse", content);

                    // Aquí debes parsear 'content' para crear objetos Receta
                    List<Receta> recetasExtraidas = parsearRecetasDeRespuesta(content);

                    // Actualiza tu adaptador con las recetas extraídas
                    adaptadorRecetas.clear();
                    adaptadorRecetas.addAll(recetasExtraidas);
                    adaptadorRecetas.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("MainActivity", "Error al procesar la respuesta", e);
                    Toast.makeText(MainActivity.this, "Error al procesar las recetas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "UTF-8");
                        Log.e("APIError", "Error: " + responseBody);
                        Toast.makeText(MainActivity.this, "Error: " + responseBody, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("APIError", "Error de codificación", e);
                    }
                } else {
                    String message = error.getMessage() == null ? "Error desconocido" : error.getMessage();
                    Log.e("APIError", message, error);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    /**
     * Método para parsear las recetas de la respuesta de la API.
     * @param content El contenido de la respuesta de la API.
     * @return Una lista de recetas.
     */
    private List<Receta> parsearRecetasDeRespuesta(String content) {
        List<Receta> recetas = new ArrayList<>();

        // Divide el contenido en recetas individuales
        String[] recetasTexto = content.split("---");
        for (String recetaTexto : recetasTexto) {
            recetaTexto = recetaTexto.trim();
            if (recetaTexto.isEmpty()) continue;

            // Divide cada receta en sus componentes
            String[] lineas = recetaTexto.split("\n");
            if (lineas.length < 3) {
                // Si no hay suficientes líneas, log el error y continúa con la siguiente receta
                Log.e("parsearRecetasDeRespuesta", "No hay suficientes líneas para parsear una receta completa.");
                continue;
            }

            String nombreReceta = lineas[0].startsWith("Nombre de la receta:") ? lineas[0].substring("Nombre de la receta:".length()).trim() : "";
            String ingredientesTexto = lineas[1].startsWith("Ingredientes:") ? lineas[1].substring("Ingredientes:".length()).trim() : "";

            // Asume que el resto del texto son las instrucciones
            StringBuilder instrucciones = new StringBuilder();
            for (int i = 2; i < lineas.length; i++) {
                instrucciones.append(lineas[i]).append("\n");
            }

            String[] ingredientes = ingredientesTexto.isEmpty() ? new String[0] : ingredientesTexto.split(",\\s*");
            Receta receta = new Receta(nombreReceta, instrucciones.toString().trim(), ingredientes);
            recetas.add(receta);
        }

        return recetas;
    }

    /**
     * Método para obtener los ingredientes del usuario de la base de datos.
     * @param username El nombre de usuario.
     * @return Una lista de ingredientes.
     */
    private ArrayList<String> getIngredientesUsuarioBBDD(String username) {
        IngredienteMapper ingredienteMapper = new IngredienteMapper(this);
        return ingredienteMapper.getIngredientesUsuario(username);
    }

    /**
     * Método que se llama cuando se pausa la actividad. BUSCAR QUE AÑADI
     */
    public void onPause() {
        super.onPause();
        //Toast.makeText(this, "onPause del main", Toast.LENGTH_SHORT).show();
    }


    /**
     * Método para inflar el menú en la barra de acción.
     * @param menu El menú a inflar.
     * @return Verdadero para mostrar el menú.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }
}

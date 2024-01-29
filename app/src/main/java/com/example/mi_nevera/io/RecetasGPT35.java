package com.example.mi_nevera.io;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase se encarga de generar recetas utilizando la API de OpenAI.
 * Utiliza la biblioteca Volley para realizar las solicitudes HTTP.
 */
public class RecetasGPT35 {

    // URL base de la API de OpenAI
    private final static String BASE_URL = "https://api.openai.com/v1/chat/completions";

    // Cola de solicitudes de Volley
    private RequestQueue requestQueue;

    /**
     * Constructor que inicializa la cola de solicitudes de Volley.
     * @param context Contexto de la aplicación.
     */
    public RecetasGPT35(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Método para generar recetas basadas en los ingredientes proporcionados.
     * @param text Texto que contiene los ingredientes.
     * @param listener Listener para manejar la respuesta de la API.
     * @param errorListener Listener para manejar los errores de la solicitud.
     */
    public void generarReceta(String text, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        try{
            // Crear el cuerpo de la solicitud
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo-1106");
            jsonBody.put("temperature", 1);

            // Crear el array de mensajes para la solicitud
            JSONArray jsonArrayMessages = new JSONArray();
            JSONObject jsonObjectMessages = new JSONObject();

            // Añadir el rol y el contenido al objeto de mensajes
            jsonObjectMessages.put("role", "user");
            jsonObjectMessages.put("content", createStringContent(text));

            // Añadir el objeto de mensajes al array de mensajes
            jsonArrayMessages.put(jsonObjectMessages);
            jsonBody.put("messages", jsonArrayMessages);

            // Crear la solicitud
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        // Procesar la respuesta de la API
                        listener.onResponse(response);
                    }catch (Exception e){
                        Log.e("TranslatorGPT35", "Error parseando respuesta ", e);
                        errorListener.onErrorResponse(new VolleyError("Error parseando request"));
                    }
                }
            },errorListener){
                @Override
                public Map<String, String> getHeaders(){
                    // Añadir la cabecera de autorización a la solicitud
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization" , "Bearer " + Config.CHAT_GPT_API_KEY);
                    return  headers;
                }
            };

            // Configurar la política de reintento de la solicitud
            request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Añadir la solicitud a la cola de solicitudes
            requestQueue.add(request);
        }catch (Exception e){
            Log.e("RecetasGPT35", "Error creando request body ", e);
            errorListener.onErrorResponse(new VolleyError("Error creando request"));
        }
    }

    /**
     * Método para crear el contenido de la solicitud a la API de OpenAI.
     * @param ingredients Ingredientes para la receta.
     * @return El contenido de la solicitud.
     */
    private String createStringContent(String ingredients) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Basado en el/los ingrediente/s, sugiere recetas. ");
        prompt.append("Para cada receta, incluye 'Nombre de la receta:', seguido de 'Ingredientes:' en líneas separadas, ");
        prompt.append("y después 'Instrucciones:' paso a paso en líneas separadas. ");
        prompt.append("Si el/los ingrediente/s son insuficientes para una receta convencional, sé creativo y si es necesario, siente libertad de asumir que hay ingredientes básicos de cocina disponibles como aceite, sal, especias, etc. ");
        prompt.append("Separa cada receta con '---'. ");
        prompt.append("Ingrediente/s proporcionados: ");
        prompt.append(ingredients);
        prompt.append("\n\n---\n");

        prompt.append("Ejemplo:\nNombre de la receta: Ejemplo de Receta\n");
        prompt.append("Ingredientes:\n- Ingrediente 1\n- Ingrediente 2\n- Ingrediente 3\n");
        prompt.append("\n\n\nInstrucciones:\n1. Primer paso.\n2. Segundo paso.\n3. Tercer paso.\n");
        prompt.append("---\n");

        prompt.append("Por favor, sigue este formato para las recetas sugeridas.");
        return prompt.toString();
    }
}
    // de este saque cosas para mejorar el de arriba
    /*private String createStringContent(String ingredients) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Dado el ingrediente '");
        prompt.append(ingredients);
        prompt.append("', por favor sugiere recetas. Puedes asumir que hay ingredientes básicos de cocina disponibles como aceite, sal, especias, etc. ");
        prompt.append("Incluye 'Nombre de la receta:', 'Ingredientes:' y 'Instrucciones:'. ");
        prompt.append("Si es necesario, siente libertad de añadir ingredientes comunes o básicos para completar la receta. ");
        prompt.append("Separa cada receta con '---'. \n\n");

        prompt.append("---\nEjemplo de respuesta:\n");
        prompt.append("Nombre de la receta: Delicia de ");
        prompt.append(ingredients);
        prompt.append("\nIngredientes:\n- ");
        prompt.append(ingredients);
        prompt.append("\n- Ingredientes comunes como aceite, sal, etc.\n\n");
        prompt.append("Instrucciones:\n1. Primer paso utilizando ");
        prompt.append(ingredients);
        prompt.append(".\n2. Segundo paso añadiendo ingredientes comunes.\n3. Finalizar y servir.\n");
        prompt.append("---\n");

        prompt.append("Por favor, sigue este formato para las recetas sugeridas.");

        return prompt.toString();
    }*/




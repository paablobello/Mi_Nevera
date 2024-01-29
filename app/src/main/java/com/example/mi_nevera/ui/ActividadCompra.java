package com.example.mi_nevera.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.model.ListaMapper;

import java.util.ArrayList;

/**
 * Esta clase representa la actividad de la lista de compra en la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class ActividadCompra extends MenuActivity {

    private ArrayList<String> productos;
    private ArrayAdapter<String> adapter;
    private SparseBooleanArray checked;

    private ListaMapper listaMapper;
    private Session session;

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Lista de la compra");

        session = Session.getSession(ActividadCompra.this);
        listaMapper = new ListaMapper(this);

        productos = listaMapper.getLista(session.getUsername());

        // Obtener la referencia de la ListView y permitir multiple selección
        ListView listViewCompra = findViewById(R.id.listViewCompra);
        listViewCompra.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Crear un ArrayAdapter para la lista de productos
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, productos);

        // Establecer el adaptador en la ListView
        listViewCompra.setAdapter(adapter);

        // Obtener la referencia del botón para agregar productos
        Button btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        // Configurar el OnClickListener para el botón
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoAgregarProducto();
            }
        });

        //logica de seleccionar para borrar puesto para que solo salte el Toast
        checked = listViewCompra.getCheckedItemPositions();
        listViewCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Actualizar la lista de elementos seleccionados
                checked = listViewCompra.getCheckedItemPositions();

                // Si el elemento en la posición 'position' está seleccionado
                if (checked.get(position)) {
                    // Toast.makeText(ActividadCompra.this, "Seleccionado: " + productos.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(ActividadCompra.this, "Deseleccionado: " + productos.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método para mostrar el diálogo de agregar producto.
     */
    private void mostrarDialogoAgregarProducto() {
        // Crear un cuadro de diálogo personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_agregar_producto, null);
        builder.setView(dialogView);

        // Obtener referencias a los elementos del cuadro de diálogo
        final EditText etNuevoProducto = dialogView.findViewById(R.id.etNuevoProductoDialog);
        final Button btnConfirmar = dialogView.findViewById(R.id.btnConfirmarDialog);
        final Button btnCancelar = dialogView.findViewById(R.id.btnCancelarDialog);

        // Configurar el cuadro de diálogo
        final AlertDialog dialog = builder.create();

        // Configurar el botón de confirmar
        // Configurar el botón de confirmar
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el texto del EditText
                String nuevoProducto = etNuevoProducto.getText().toString().trim();

                // Verificar si el texto está vacío
                if (nuevoProducto.isEmpty()) {
                    // Mostrar un Toast si está vacío
                    Toast.makeText(ActividadCompra.this, "Por favor, introduce un producto válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Añadir el nuevo producto a la lista y actualizar el adaptador
                    listaMapper.insertarListaItem(nuevoProducto, session.getUsername());
                    productos.add(nuevoProducto);
                    adapter.notifyDataSetChanged();

                    // Cerrar el cuadro de diálogo
                    dialog.dismiss();
                }
            }
        });


        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Cerrar el cuadro de diálogo sin hacer nada
            }
        });

        // Mostrar el cuadro de diálogo
        dialog.show();
    }

    /**
     * Método que se llama cuando se detiene la actividad.
     */
    @Override
    public void onStop() {
        // TODO BORRAR LOS SELECCIONADOS CON TICK
        super.onStop();

        //logica para borrar los productos seleccionados
        ArrayList<String> elementosAEliminar = new ArrayList<>();


        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);

            // Si el elemento en la posición 'position' está seleccionado
            if (checked.valueAt(i)) {
                elementosAEliminar.add(adapter.getItem(position));
            }
        }

        // Eliminar los elementos seleccionados de la lista original
        for (String item : elementosAEliminar) {
            productos.remove(item);
            listaMapper.deleteListaItem(item, session.getUsername());
        }
        adapter.notifyDataSetChanged();

    }
}
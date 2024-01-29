package com.example.mi_nevera.ui;

import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_ALTURA;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_APELLIDO;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_EDAD;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_GENERO;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_NOMBRE;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_OWNER;
import static com.example.mi_nevera.core.DBManager.CAMPO_PERFIL_PESO;
import static com.example.mi_nevera.core.DBManager.TABLA_PERFIL;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mi_nevera.R;
import com.example.mi_nevera.core.DBManager;
import com.example.mi_nevera.core.MenuActivity;
import com.example.mi_nevera.core.Session;
import com.example.mi_nevera.model.DBMapper;
import com.example.mi_nevera.model.IngredienteMapper;
import com.example.mi_nevera.model.Perfil;
import com.example.mi_nevera.model.PerfilMapper;
import com.example.mi_nevera.model.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Esta clase representa la actividad del perfil en la aplicación.
 * Extiende MenuActivity para tener todas las funcionalidades de una actividad de Android con un menú.
 */
public class ActividadPerfil extends MenuActivity {
    //private String[] sexos = new String[]{"Hombre", "Mujer", "Otro"};
    private ArrayList<String> sexosList = new ArrayList<>(Arrays.asList("Hombre", "Mujer", "Otro"));

    private TextView nombreTextView;
    private TextView apellidosTextView;
    private TextView edadTextView;
    private TextView pesoTextView;
    private TextView alturaTextView;
    private Spinner generoSpinner;

    private PerfilMapper perfilMapper;
    private DBMapper dbMapper;

    private Button btnActualizar;

    private Session session;

    private ArrayList<String> listaPerfil;

    private String owner;

    /**
     * Método que se llama cuando se crea la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad.
     */
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_perfil);

        dbMapper = new DBMapper(this);

        session = Session.getSession(ActividadPerfil.this);


        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Mi perfil");

        //Crear un ArrayAdapter para el spinner de sexo
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexosList);

        //Especificar el layout a usar cuando aparece la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Aplicar el adaptador al spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSex);
        spinner.setAdapter(adapter);

        // Inicializa los TextViews del layout
        nombreTextView = findViewById(R.id.editTextFirstName);
        apellidosTextView = findViewById(R.id.editTextLastName);
        edadTextView = findViewById(R.id.editTextAge);
        pesoTextView = findViewById(R.id.editTextWeight);
        alturaTextView = findViewById(R.id.editTextHeight);
        generoSpinner = findViewById(R.id.spinnerSex);

        getDatosUsuario(session.getUsername());

        //ACTUALIZACION DE CAMPOS PERFIL
        btnActualizar = findViewById(R.id.buttonSaveProfile);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los nuevos valores de los elementos de la interfaz de usuario
                String nuevoNombre = nombreTextView.getText().toString();
                String nuevosApellidos = apellidosTextView.getText().toString();
                int nuevaEdad = Integer.parseInt(edadTextView.getText().toString());
                double nuevoPeso = Double.parseDouble(pesoTextView.getText().toString());
                int nuevaAltura = Integer.parseInt(alturaTextView.getText().toString());
                String nuevoGenero = generoSpinner.getSelectedItem().toString();
                owner = session.getUsername();


                // Llama al método para actualizar la base de datos
                actualizarPerfil(nuevoNombre, nuevosApellidos, nuevaEdad, nuevoPeso, nuevaAltura, nuevoGenero, owner);

                Intent profileIntent = new Intent(ActividadPerfil.this, MainActivity.class);
                startActivity(profileIntent);
                Toast.makeText(ActividadPerfil.this, "Perfil Actualizado", Toast.LENGTH_SHORT).show();
                ActividadPerfil.this.finish();

            }
        });
    }

    public Perfil getDatosUsuario(String username) {
        Perfil perfil = null;
        SQLiteDatabase db = dbMapper.instance.getReadableDatabase();
        String[] columnas = {
                CAMPO_PERFIL_NOMBRE,
                CAMPO_PERFIL_APELLIDO,
                CAMPO_PERFIL_EDAD,
                CAMPO_PERFIL_PESO,
                CAMPO_PERFIL_ALTURA,
                CAMPO_PERFIL_GENERO
        };

        String[] args = {username};
        Cursor cursor = db.query(
                DBManager.TABLA_PERFIL,
                columnas,
                CAMPO_PERFIL_OWNER + "=?",
                args,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                // Obtener los índices de las columnas después de verificar si hay datos
                int indiceNombre = cursor.getColumnIndex(CAMPO_PERFIL_NOMBRE);
                int indiceApellidos = cursor.getColumnIndex(CAMPO_PERFIL_APELLIDO);
                int indiceEdad = cursor.getColumnIndex(CAMPO_PERFIL_EDAD);
                int indicePeso = cursor.getColumnIndex(CAMPO_PERFIL_PESO);
                int indiceAltura = cursor.getColumnIndex(CAMPO_PERFIL_ALTURA);
                int indiceGenero = cursor.getColumnIndex(CAMPO_PERFIL_GENERO);

                // Verificar que los índices sean válidos
                if (indiceNombre >= 0 && indiceApellidos >= 0 && indiceEdad >= 0 && indicePeso >= 0 && indiceAltura >= 0 && indiceGenero >= 0) {
                    String nombre = cursor.getString(indiceNombre);
                    String apellidos = cursor.getString(indiceApellidos);
                    int edad = cursor.getInt(indiceEdad);
                    double peso = cursor.getDouble(indicePeso);
                    int altura = cursor.getInt(indiceAltura);
                    String genero = cursor.getString(indiceGenero);

                    perfil = new Perfil(owner, apellidos, edad, peso, altura, genero);

                    nombreTextView.setText(nombre);
                    apellidosTextView.setText(apellidos);
                    edadTextView.setText(String.valueOf(edad));
                    //pesoTextView.setText(String.valueOf(String.format("%.2f" + peso)));
                    pesoTextView.setText(String.valueOf(peso));
                    alturaTextView.setText(String.valueOf(altura));

                    ArrayAdapter<String> generoAdapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, sexosList);
                    generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    generoSpinner.setAdapter(generoAdapter);

                    int indiceGener = generoAdapter.getPosition(genero);
                    generoSpinner.setSelection(indiceGener);


                } else {
                    Log.e("DB", "Índice de columna no válido. Nombre de columna: " + CAMPO_PERFIL_NOMBRE);
                }
            }
        } catch (Exception e) {
            Log.e("DB", "Error al obtener datos de usuario: " + e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }
        return perfil;
    }




    public void actualizarPerfil(String nuevoNombre, String nuevosApellidos, Integer nuevaEdad, Double nuevoPeso, Integer nuevaAltura, String nuevoGenero, String owner) {
        SQLiteDatabase db = dbMapper.instance.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (nuevoNombre != null) values.put(CAMPO_PERFIL_NOMBRE, nuevoNombre);
        if (nuevosApellidos != null) values.put(CAMPO_PERFIL_APELLIDO, nuevosApellidos);
        if (nuevaEdad != null) values.put(CAMPO_PERFIL_EDAD, nuevaEdad);
        if (nuevoPeso != null) values.put(CAMPO_PERFIL_PESO, nuevoPeso);
        if (nuevaAltura != null) values.put(CAMPO_PERFIL_ALTURA, nuevaAltura);
        if (nuevoGenero != null) values.put(CAMPO_PERFIL_GENERO, nuevoGenero);
        values.put(CAMPO_PERFIL_OWNER, owner);

        db.beginTransaction();
        try {
            // Verificar si existe un perfil para el usuario
            Cursor cursor = db.query(DBManager.TABLA_PERFIL, null, CAMPO_PERFIL_OWNER + "=?", new String[]{owner}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Perfil existente, actualizar datos
                db.update(DBManager.TABLA_PERFIL, values, CAMPO_PERFIL_OWNER + "=?", new String[]{owner});
            } else {
                // Nuevo perfil, crear
                values.put(CAMPO_PERFIL_OWNER, owner);
                db.insert(DBManager.TABLA_PERFIL, null, values);
            }
            if (cursor != null) cursor.close();
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("DB", "Error al actualizar/perfil: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
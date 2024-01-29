package com.example.mi_nevera;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

public class ActividadKcal extends AppCompatActivity {
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_kcal);

        // Cambia el título de la ActionBar
        getSupportActionBar().setTitle("Datos nutricionales");
    }
}

package com.example.mi_nevera.model;

public class Perfil {
    private String nombre;
    private String apellidos;
    private int edad;
    private double peso;
    private int altura;
    private String genero;
    private String owner;

    public Perfil(String nombre, String apellidos, int edad, double peso, int altura, String genero) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public double getPeso() {
        return peso;
    }

    public int getAltura() {
        return altura;
    }

    public String getGenero() {
        return genero;
    }
}

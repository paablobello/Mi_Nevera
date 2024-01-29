package com.example.mi_nevera;


import java.util.ArrayList;

/**
 * Para mantener la lista de compras (productos) en toda la sesión de la aplicación,
 * una solución efectiva sería implementar un patrón Singleton, como mencioné anteriormente.
 *  Esto implica crear una clase Singleton que almacene la lista de compras. De esta manera,
 * la lista se mantendrá constante a lo largo de todas las actividades de la aplicación, siempre y cuando la aplicación esté en ejecución.
 */
public class ListaDeCompraManager {
    private static ListaDeCompraManager instance;
    private ArrayList<String> productos;

//BORRABLE CREO YO JAJAJA

    private ListaDeCompraManager() {
        productos = new ArrayList<>();
        productos.add("Pan");
        productos.add("Leche");
        productos.add("Huevos");
    }

    public static ListaDeCompraManager getInstance() {
        if (instance == null) {
            instance = new ListaDeCompraManager();
        }
        return instance;
    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void agregarProducto(String producto) {
        if (!productos.contains(producto) && !producto.isEmpty()) {
            productos.add(producto);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.uis.logica;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(int productoid) {
        productos.removeIf(p -> p.getProductoid() == productoid);
    }

    public void vaciarCarrito() {
        productos.clear();
    }

    public int calcularTotal() {
        return productos.stream().mapToInt(Producto::getPrecio).sum();
    }
}


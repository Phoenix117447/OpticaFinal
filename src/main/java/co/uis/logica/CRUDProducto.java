/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.uis.logica;

import co.uis.logica.Producto;
import java.util.List;

/**
 *
 * @author Ann
 */
public interface CRUDProducto {
    public boolean guardarProducto();

    public boolean eliminarProducto();

    public boolean actualizarProducto();

    public List<Producto> listarProductos();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.uis.logica;

import co.uis.persistencia.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arley Mantilla
 */
public class Producto implements CRUDProducto
{
    private int productoid;
    private String nombre_producto;
    private String descripcion;
    private int precio;
    private String imagen;

    public Producto(String nombre_producto, String descripcion, int precio) {
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    public Producto() {
        
    }

    public int getProductoid() {
        return productoid;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setProductoid(int productoid) {
        this.productoid = productoid;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }    
    
    @Override
    public String toString() {
        return "Producto{" + "id=" + productoid + ", nombre=" + nombre_producto + ", precio=" + precio + ", descripcion=" + descripcion + '}';
    }

    @Override
    public boolean guardarProducto() {
        ConexionBD conexion = new ConexionBD();
        boolean exito = false;
        String sql = "INSERT INTO producto (nombre_producto,precio,descripcion) VALUES ('" 
                + this.nombre_producto + "','" + this.precio + "','" + this.descripcion + "');";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sql)) {
                conexion.commitBD();
                exito = true;
            } else {
                conexion.rollbackBD();
            }
        }
        conexion.cerrarConexion();
        return exito;
    }

    @Override
    public boolean eliminarProducto() {
        ConexionBD conexion = new ConexionBD();
        boolean exito = false;
        String sql = "DELETE FROM producto WHERE id_producto='" + this.productoid + "';";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.borrarBD(sql)) {
                conexion.commitBD();
                exito = true;
            } else {
                conexion.rollbackBD();
            }
        }
        conexion.cerrarConexion();
        return exito;
    }

    @Override
    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();
        boolean exito = false;
        String sql = "UPDATE producto set nombre_producto='" + this.nombre_producto + "', precio='" 
                + this.precio + "', descripcion='" + this.descripcion
                + "' WHERE productoid='" + this.productoid + "';";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                exito = true;
            } else {
                conexion.rollbackBD();
            }
        }
        conexion.cerrarConexion();
        return exito;
    }

    @Override
public List<Producto> listarProductos() {
    ConexionBD conexion = new ConexionBD();
    String query = "SELECT * FROM producto;"; // Asegúrate de que la tabla producto tiene el campo 'imagen'
    List<Producto> productos = new ArrayList<>();
    ResultSet rs = conexion.consultarBD(query);
    try {
        Producto a;
        while (rs.next()) {
            a = new Producto();
            a.setNombre_producto(rs.getString("nombre_producto"));
            a.setPrecio(rs.getInt("precio"));
            a.setProductoid(rs.getInt("productoid"));
            a.setDescripcion(rs.getString("descripcion"));
            a.setImagen(rs.getString("imagen")); // Asignar el valor de la columna 'imagen'
            productos.add(a);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        conexion.cerrarConexion();
        return productos;
    }
}

}

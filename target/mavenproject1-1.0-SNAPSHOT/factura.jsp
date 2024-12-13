<%@ page import="java.util.List" %>
<%@ page import="co.uis.logica.Producto" %>
<%@ page import="co.uis.logica.Carrito" %>
<%@ page import="co.uis.logica.Usuario" %>
<%@ page import="co.uis.persistencia.ConexionBD" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Factura</title>
        <link href="css/factura.css" rel="stylesheet">
    </head>
    <body>
        <!-- Barra de Navegación -->
        <div class="navbar">
            <button class="nav-button" onclick="location.href='Usuario.html'">Inicio</button>
            <button class="nav-button" onclick="location.href='productosUsuario.jsp'">Productos</button>
            <button class="nav-button" onclick="location.href='index.html'">Cerrar Sesión</button>
        </div>
        
        <div class="factura-container">
            <h2 class="factura-title">Factura de Compra</h2>

            <%
                // Obtener el carrito de la sesión
                Carrito carrito = (Carrito) session.getAttribute("carrito");
                Usuario usuario = (Usuario) session.getAttribute("usuario");

                if (usuario == null) {
                    out.println("<p>No has iniciado sesión. Por favor, inicia sesión para generar la factura.</p>");
                } else if (carrito == null || carrito.getProductos().isEmpty()) {
                    out.println("<p>No hay productos en el carrito.</p>");
                } else {
                    ConexionBD conexionBD = new ConexionBD();
                    Connection conn = conexionBD.getConnection();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    int facturaId = 0;
                    double totalFactura = 0;

                    try {
                        // Calcular el total de la factura
                        for (Producto producto : carrito.getProductos()) {
                            totalFactura += producto.getPrecio();
                        }

                        // Insertar la factura en la base de datos
                        String insertFacturaSQL = "INSERT INTO factura (cedula_usuario, fecha, valor) VALUES (?, CURDATE(), ?)";
                        stmt = conn.prepareStatement(insertFacturaSQL, Statement.RETURN_GENERATED_KEYS);

                        stmt.setInt(1, usuario.getCedula());
                        stmt.setDouble(2, totalFactura); 
                        int affectedRows = stmt.executeUpdate();

                        if (affectedRows > 0) {
                            rs = stmt.getGeneratedKeys();
                            if (rs.next()) {
                                facturaId = rs.getInt(1);
                            }
                        }

                        // Insertar los productos de la factura
                        String insertDetalleFacturaSQL = "INSERT INTO detalle_factura (fk_factura, fk_producto, cantidad, precio) VALUES (?, ?, ?, ?)";
                        stmt = conn.prepareStatement(insertDetalleFacturaSQL);

                        for (Producto producto : carrito.getProductos()) {
                            stmt.setInt(1, facturaId);
                            stmt.setInt(2, producto.getProductoid());
                            stmt.setInt(3, 1);
                            stmt.setDouble(4, producto.getPrecio());
                            stmt.executeUpdate();
                        }

                        // Mostrar factura generada
                        out.println("<div class='factura'>");
                        out.println("<h3>Factura Generada Exitosamente</h3>");
                        out.println("<p><strong>ID Factura:</strong> " + facturaId + "</p>");
                        out.println("<p><strong>Total:</strong> $" + totalFactura + "</p>");

                        // Detalles de la compra
                        out.println("<table class='factura-table'>");
                        out.println("<thead><tr><th>Producto</th><th>Precio</th></tr></thead>");
                        out.println("<tbody>");
                        for (Producto producto : carrito.getProductos()) {
                            out.println("<tr><td>" + producto.getNombre_producto() + "</td><td>$" + producto.getPrecio() + "</td></tr>");
                        }
                        out.println("</tbody></table>");

                        // Datos del usuario
                        out.println("<div class='usuario-info'>");
                        out.println("<h4>Datos del Usuario</h4>");
                        out.println("<p><strong>Cédula:</strong> " + usuario.getCedula() + "</p>");
                        out.println("<p><strong>Nombre:</strong> " + usuario.getNombre() + "</p>");
                        out.println("</div>");

                        carrito.vaciarCarrito(); // Limpiar el carrito después de generar la factura
                        out.println("</div>");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        out.println("<p>Error al generar la factura. Intente nuevamente.</p>");
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (stmt != null) stmt.close();
                            if (conn != null) conexionBD.closeConnection(conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            %>
        </div>

        <footer class="footer">
            <div class="contacto-info">
                <p><strong>Nombre de la Empresa:</strong> Mi Empresa S.A.S.</p>
                <p><strong>Correo Electrónico:</strong> contacto@miempresa.com</p>
                <p><strong>Número de Contacto:</strong> +57 123 456 7890</p>
                <p><strong>Ciudad:</strong> Bogotá, Colombia</p>
            </div>
        </footer>
    </body>
</html>

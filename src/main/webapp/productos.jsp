<%@ page import="java.util.List" %> 
<%@ page import="co.uis.logica.Producto" %> 
<%@ page import="co.uis.logica.Carrito" %> 

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Productos</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> <!-- Incluir Font Awesome -->
        <link href="css/producto.css" rel="stylesheet">

    </head>
    <body>
        <!-- Barra de Navegación -->
        <div class="navbar">
            <button class="nav-button" onclick="location.href = 'index.html'">Inicio</button>
            <button class="nav-button" onclick="location.href = 'productos.jsp'">Productos</button>
            <button class="nav-button" onclick="location.href = 'registrarse.jsp'">Registrarse</button>
            <button class="nav-button" onclick="location.href = 'iniciarsesion.jsp'">Iniciar Sesión</button>
        </div>

        <%
            // Obtener carrito de la sesión
            Carrito carrito = (Carrito) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new Carrito();
                session.setAttribute("carrito", carrito);
            }

            // Lógica para agregar un producto al carrito
            if (request.getParameter("agregarAlCarrito") != null) {
                int productoid = Integer.parseInt(request.getParameter("productoid"));
                Producto productoSeleccionado = null;

                // Buscar el producto en la base de datos
                for (Producto p : new Producto().listarProductos()) {
                    if (p.getProductoid() == productoid) {
                        productoSeleccionado = p;
                        break;
                    }
                }

                if (productoSeleccionado != null) {
                    carrito.agregarProducto(productoSeleccionado);
                    // Mostrar el carrito automáticamente después de agregar un producto
                    out.println("<script>mostrarCarrito(); actualizarContador();</script>");
                }
            }

            // Lógica para eliminar un producto del carrito
            if (request.getParameter("eliminarDelCarrito") != null) {
                int productoid = Integer.parseInt(request.getParameter("productoid"));
                carrito.eliminarProducto(productoid);
                out.println("<script>actualizarContador();</script>");
            }
        %>

        <div class="container">
            <%
                List<Producto> productos = new Producto().listarProductos();
                if (productos != null && !productos.isEmpty()) {
            %>
            <% for (Producto producto : productos) {%>
            <div class="product-card">
                <!-- Mostrar la imagen específica de cada producto -->
                <img src="<%= "media/" + producto.getImagen()%>" alt="<%= producto.getNombre_producto()%>" class="product-image">
                <div class="product-info">
                    <div class="product-name"><%= producto.getNombre_producto()%></div>
                    <div class="product-price">$<%= producto.getPrecio()%></div>
                    <div class="product-description"><%= producto.getDescripcion()%></div>
                </div>
                <!-- Formulario para agregar producto al carrito -->
                <form action="productos.jsp" method="post">
                    <input type="hidden" name="productoid" value="<%= producto.getProductoid()%>">
                    <button type="submit" name="agregarAlCarrito">Añadir al Carrito</button>
                </form>
            </div>
            <% } %>
            <% } else { %>
            <p class="no-data">No hay productos disponibles.</p>
            <% }%>
        </div>

        <!-- Botón para abrir el carrito -->
        <button class="carrito-button" onclick="mostrarCarrito()">
            <i class="fas fa-shopping-cart"></i> <!-- Ícono del carrito de compras -->
            <span class="contador" id="contadorCarrito"><%= carrito.getProductos().size()%></span> <!-- Contador de productos -->
        </button>

        <!-- Carrito flotante -->
        <div class="carrito-float" id="carritoFloat">
            <h3>Carrito</h3>
            <% if (carrito != null && !carrito.getProductos().isEmpty()) { %>
            <ul>
                <% int subtotal = 0; %>
                <% for (Producto producto : carrito.getProductos()) {%>
                <li>
                    <span><%= producto.getNombre_producto()%></span>
                    <span>$<%= producto.getPrecio()%></span>
                    <form action="productos.jsp" method="post" style="display:inline;">
                        <input type="hidden" name="productoid" value="<%= producto.getProductoid()%>">
                        <button type="submit" name="eliminarDelCarrito">Eliminar</button>
                    </form>
                </li>
                <% subtotal += producto.getPrecio(); %>
                <% }%>
            </ul>
            <div class="subtotal">Subtotal: $<%= subtotal%></div>

            <!-- Botón para generar factura -->
            <button onclick="location.href = 'iniciarsesion.jsp'"  style="margin-top: 10px; background-color: #007bff; color: white;">Generar Factura</button>
            <% } else { %>
            <p>No hay productos en el carrito.</p>
            <% }%>
        </div>

        <!-- Pie de página con Información de Contacto -->
        <footer class="footer">
            <div class="contacto-info">
                <p><strong>Nombre de la Empresa:</strong> Mi Empresa S.A.S.</p>
                <p><strong>Correo Electrónico:</strong> contacto@miempresa.com</p>
                <p><strong>Número de Contacto:</strong> +57 123 456 7890</p>
                <p><strong>Ciudad:</strong> Bogotá, Colombia</p>
            </div>
        </footer>

        <script>
            function mostrarCarrito() {
                var carrito = document.getElementById("carritoFloat");
                if (carrito.style.display === "none" || carrito.style.display === "") {
                    carrito.style.display = "block";
                } else {
                    carrito.style.display = "none";
                }
            }

            // Actualiza el contador de productos en el carrito
            function actualizarContador() {
                var contador = document.getElementById("contadorCarrito");
                var cantidad = <%= carrito.getProductos().size()%>;
                contador.textContent = cantidad;
            }
        </script>
    </body>
</html>



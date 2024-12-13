
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar Sesión</title>
        <!-- Enlace a Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <link href="css/ingreso.css" rel="stylesheet">
    </head>
    <body>
        <!-- Barra de Navegación -->
        <div class="navbar">
            <button class="nav-button" onclick="location.href = 'index.html'">Inicio</button>
            <button class="nav-button" onclick="location.href = 'productos.jsp'">Productos</button>
            <button class="nav-button" onclick="location.href = 'registrarse.jsp'">Registrarse</button>
            <button class="nav-button" onclick="location.href = 'iniciarsesion.jsp'">Iniciar Sesión</button>
        </div>

        <!-- Tarjeta de inicio de sesión -->
        <div class="card">
            <div class="card-header">
                <h3>Iniciar Sesión</h3>
            </div>
            <div class="card-body">
                <form action="IniciarSesion" method="POST">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre de Usuario</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese su nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="cedula" class="form-label">Contraseña (Cédula)</label>
                        <input type="password" class="form-control" id="cedula" name="cedula" placeholder="Ingrese su cédula" required>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Ingresar</button>
                    </div>
                </form>
            </div>
            <div class="card-footer">
                ¿No tienes una cuenta? <a href="registrarse.jsp">Regístrate aquí</a>.
            </div>
        </div>

        <!-- Pie de página -->
        <footer class="footer">
            <div class="contacto-info">
                <p><strong>Nombre de la Empresa:</strong> Mi Empresa S.A.S.</p>
                <p><strong>Correo Electrónico:</strong> contacto@miempresa.com</p>
                <p><strong>Número de Contacto:</strong> +57 123 456 7890</p>
                <p><strong>Ciudad:</strong> Bogotá, Colombia</p>
            </div>
        </footer>

        <!-- Scripts opcionales de Bootstrap -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-wh2J6o7tW34Wp3mHkYdU7u9lA+au2J/eQuglg9lYBvhZPqJeImniBJRpMghgf1X" 
        crossorigin="anonymous"></script>
    </body>
</html>
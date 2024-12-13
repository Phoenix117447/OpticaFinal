<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Usuario</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <link href="css/registro.css" rel="stylesheet">
    </head>
    <body>
        <!-- Barra de Navegación -->
        <div class="navbar">
            <button class="nav-button" onclick="location.href = 'index.html'">Inicio</button>
            <button class="nav-button" onclick="location.href = 'productos.jsp'">Productos</button>
            <button class="nav-button" onclick="location.href = 'registrarse.jsp'">Registrarse</button>
            <button class="nav-button" onclick="location.href = 'iniciarsesion.jsp'">Iniciar Sesión</button>
        </div>

        <div class="card">
            <div class="card-header">
                <h3>Registro de Usuario</h3>
            </div>
            <div class="card-body">
                <form action="RegistrarUsuario" method="post" onsubmit="return validarFormulario()">
                    <div class="form-icon">
                        <label for="nombre" class="form-label">Nombre y Primer Apellido</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="form-icon">
                        <label for="cedula" class="form-label">Cédula</label>
                        <input type="text" class="form-control" id="cedula" name="cedula" required>
                    </div>
                    <div class="form-icon">
                        <label for="correo" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" id="correo" name="correo" required>
                    </div>
                    <div class="form-icon">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="tel" class="form-control" id="telefono" name="telefono" required>
                    </div>
                    <div class="d-flex justify-content-between">
                        <button type="submit" class="btn btn-success">Registrar</button>
                        <button type="reset" class="btn btn-secondary">Limpiar</button>
                    </div>
                </form>
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-w76A6e+PQA81i6ZnnFStDGFvj0bg59qHrf41Pg3QA2DvtmIvRFaNk/8zBvrew60a" 
        crossorigin="anonymous"></script>
    </body>
</html>

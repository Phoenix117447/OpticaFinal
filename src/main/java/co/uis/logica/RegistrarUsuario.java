package co.uis.logica;

import co.uis.persistencia.ConexionBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RegistrarUsuario", urlPatterns = {"/RegistrarUsuario"})
public class RegistrarUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        // Validar los parámetros
        if (cedula == null || nombre == null || correo == null || telefono == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Convertir la cédula y el teléfono a long (en lugar de int)
            long cedulaLong = Long.parseLong(cedula);
            long telefonoLong = Long.parseLong(telefono);

            // Crear instancia de la clase ConexionBD para obtener la conexión
            ConexionBD conexionBD = new ConexionBD();
            Connection connection = conexionBD.getConnection();

            // Consulta SQL para insertar un nuevo usuario
            String sql = "INSERT INTO usuarios (cedula, nombre, correo, telefono) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, cedulaLong);  // Usar setLong para cédula
                statement.setString(2, nombre);
                statement.setString(3, correo);
                statement.setLong(4, telefonoLong);  // Usar setLong para teléfono

                // Ejecutar la consulta
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.sendRedirect("iniciarsesion.jsp");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar el usuario.");
                }
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La cédula y el teléfono deben ser números válidos.");
        } finally {
            // Cerrar la conexión
            ConexionBD conexionBD = new ConexionBD();
            conexionBD.cerrarConexion();
        }
    }
}

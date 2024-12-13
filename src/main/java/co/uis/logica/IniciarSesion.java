package co.uis.logica;

import co.uis.persistencia.ConexionBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "IniciarSesion", urlPatterns = {"/IniciarSesion"})
public class IniciarSesion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String cedula = request.getParameter("cedula");

        // Validar los parámetros
        if (nombre == null || cedula == null || nombre.isEmpty() || cedula.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Convertir la cédula a long
            long cedulaLong = Long.parseLong(cedula);

            // Crear instancia de la clase ConexionBD para obtener la conexión
            ConexionBD conexionBD = new ConexionBD();
            Connection connection = conexionBD.getConnection();

            // Consulta SQL para verificar las credenciales
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND cedula = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setLong(2, cedulaLong);
                
                // Ejecutar la consulta
                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        // Credenciales válidas - Crear sesión y almacenar datos del usuario
                        
                        // Crea un objeto Usuario para guardar en la sesión (puedes crear una clase Usuario si no la tienes)
                        Usuario usuario = new Usuario();
                        usuario.setNombre(resultSet.getString("nombre"));
                        usuario.setCedula((int) resultSet.getLong("cedula"));

                        // Crear o recuperar la sesión actual
                        HttpSession session = request.getSession();
                        
                        // Guardar el objeto Usuario en la sesión
                        session.setAttribute("usuario", usuario);

                        // Si el nombre es "Andres Ruiz" y la cédula es "23456780", se considera trabajador
                        if ("Andres Ruiz".equals(nombre) && "23456780".equals(cedula)) {
                            // Guardar la cédula del usuario en la sesión
                            session.setAttribute("cedulaUsuario", cedula);
                            // Redirigir al trabajador
                            response.sendRedirect("trabajador.html");
                        } else {
                            // Guardar la cédula del usuario en la sesión
                            session.setAttribute("cedulaUsuario", cedula);
                            // Redirigir al usuario común
                            response.sendRedirect("Usuario.html");
                        }

                    } else {
                        // Credenciales inválidas
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credenciales incorrectas.");
                    }
                }
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La cédula debe ser un número válido.");
        } finally {
            // Cerrar la conexión
            ConexionBD conexionBD = new ConexionBD();
            conexionBD.cerrarConexion();
        }
    }
}

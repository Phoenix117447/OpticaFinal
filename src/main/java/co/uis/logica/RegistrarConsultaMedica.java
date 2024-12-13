/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

@WebServlet(name = "RegistrarConsultaMedica", urlPatterns = {"/RegistrarConsultaMedica"})
public class RegistrarConsultaMedica extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String fechaHora = request.getParameter("fechaHora");

        // Obtener cédula del usuario desde la sesión
        String cedulaUsuarioString = (String) request.getSession().getAttribute("cedulaUsuario");

        // Validar los parámetros
        if (fechaHora == null || cedulaUsuarioString == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos.");
            return;
        }

        try {
            // Convertir cedulaUsuario a int
            int cedulaUsuario = Integer.parseInt(cedulaUsuarioString);

            // Crear instancia de la clase ConexionBD para obtener la conexión
            ConexionBD conexionBD = new ConexionBD();
            Connection connection = conexionBD.getConnection();

            // Consulta SQL para insertar la consulta médica
            String sql = "INSERT INTO consultamedica (cedula_usuario, fecha_hora) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, cedulaUsuario); // Usar setInt para cedula_usuario
                statement.setString(2, fechaHora);

                // Ejecutar la consulta
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Consulta médica registrada con éxito.");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar la consulta.");
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La cédula debe ser un número válido.");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos: " + e.getMessage());
        }
    }
}

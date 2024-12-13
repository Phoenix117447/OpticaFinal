/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.uis.logica;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/ListarUsuarios")
public class ListarUsuariosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JSONArray usuariosArray = new JSONArray();

        // Conexión a la base de datos
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/optica", "root", "admin");
             PreparedStatement stmt = conn.prepareStatement("SELECT cedula, nombre, correo, telefono FROM usuarios");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject usuario = new JSONObject();
                usuario.put("cedula", rs.getInt("cedula"));
                usuario.put("nombre", rs.getString("nombre"));
                usuario.put("correo", rs.getString("correo"));
                usuario.put("telefono", rs.getString("telefono"));
                usuariosArray.put(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Enviar la respuesta JSON
        PrintWriter out = response.getWriter();
        out.print(usuariosArray);
        out.flush();
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.uis.logica;

import co.uis.persistencia.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListarConsultas")
public class ListarConsultasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtener la lista de consultas
        List<Consulta> consultas = listarConsultas();

        // Crear un array JSON para almacenar las consultas
        JSONArray consultasArray = new JSONArray();

        // Convertir las consultas en formato JSON
        for (Consulta consulta : consultas) {
            JSONObject consultaJSON = new JSONObject();
            consultaJSON.put("cedula_usuario", consulta.getCedulaUsuario());
            consultaJSON.put("fecha_hora", consulta.getFechaHora());
            consultasArray.put(consultaJSON);
        }

        // Enviar la respuesta JSON
        PrintWriter out = response.getWriter();
        out.print(consultasArray);
        out.flush();
    }

    // Método para listar las consultas desde la base de datos
    public List<Consulta> listarConsultas() {
        ConexionBD conexion = new ConexionBD();
        String query = "SELECT cedula_usuario, fecha_hora FROM consultamedica";
        List<Consulta> consultas = new ArrayList<>();
        ResultSet rs = conexion.consultarBD(query);

        try {
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setCedulaUsuario(rs.getInt("cedula_usuario"));
                consulta.setFechaHora(rs.getString("fecha_hora"));
                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListarConsultasServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.cerrarConexion();
        }

        return consultas;
    }

    // Clase interna para representar una consulta médica
    public class Consulta {
        private int cedulaUsuario;
        private String fechaHora;

        public int getCedulaUsuario() {
            return cedulaUsuario;
        }

        public void setCedulaUsuario(int cedulaUsuario) {
            this.cedulaUsuario = cedulaUsuario;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(String fechaHora) {
            this.fechaHora = fechaHora;
        }
    }
}

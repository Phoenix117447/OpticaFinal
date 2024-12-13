/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.uis.logica;

import java.util.List;

/**
 *
 * @author Arley Mantilla
 */
public interface CRUDUsuario 
{
    public boolean guardarUsuario();

    public boolean eliminarUsuario();

    public boolean actualizarUsuario();

    public List<Usuario> listarUsuarios();
}

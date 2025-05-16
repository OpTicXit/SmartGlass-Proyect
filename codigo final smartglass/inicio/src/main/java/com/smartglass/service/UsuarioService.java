package com.smartglass.service;

import com.smartglass.model.Usuario;
import com.smartglass.database.BaseDatosSimulada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService {
    private static final UsuarioService instancia = new UsuarioService();
    private final Map<String, Usuario> usuariosRegistrados = new HashMap<>();

    private UsuarioService() {
        cargarUsuariosDesdeBaseDatos(); // Cargar usuarios desde la persistencia al iniciar
    }

    public static UsuarioService getInstance() {
        return instancia;
    }

    private void cargarUsuariosDesdeBaseDatos() {
        List<Usuario> usuariosGuardados = BaseDatosSimulada.getUsuarios();
        for (Usuario usuario : usuariosGuardados) {
            usuariosRegistrados.put(usuario.getCorreo(), usuario);
        }
    }

    public Map<String, Usuario> getUsuarios() {
        return usuariosRegistrados;
    }

    public void registrarUsuario(String nombre, String correo, String contraseña) {
        Usuario nuevoUsuario = BaseDatosSimulada.agregarUsuario(nombre, correo, contraseña);
        usuariosRegistrados.put(nuevoUsuario.getCorreo(), nuevoUsuario);
    }

    public void eliminarUsuario(String correo) {
        Usuario usuario = usuariosRegistrados.get(correo);
        if (usuario != null) {
            usuariosRegistrados.remove(correo);
            BaseDatosSimulada.eliminarUsuario(usuario.getId()); // Eliminar de la persistencia
        }
    }

    public void modificarUsuario(String correo, String nuevoNombre) {
        Usuario usuario = usuariosRegistrados.get(correo);
        if (usuario != null && nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            usuario.setNombre(nuevoNombre);
        }
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuariosRegistrados.get(correo);
    }
}

package com.smartglass.service;

import com.smartglass.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioService {
    private static final UsuarioService instancia = new UsuarioService();
    private final Map<String, Usuario> usuariosRegistrados = new HashMap<>();

    private UsuarioService() {}

    public static UsuarioService getInstance() {
        return instancia;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuariosRegistrados;
    }

    public void registrarUsuario(String nombre, String correo, String contraseña) {
        usuariosRegistrados.put(correo, new Usuario(usuariosRegistrados.size() + 1, nombre, correo, contraseña));
    }

    public void eliminarUsuario(String correo) {
        if (usuariosRegistrados.containsKey(correo)) {
            usuariosRegistrados.remove(correo);
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

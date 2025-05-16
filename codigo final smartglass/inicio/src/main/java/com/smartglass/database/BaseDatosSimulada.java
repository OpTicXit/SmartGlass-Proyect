package com.smartglass.database;

import com.smartglass.model.Usuario;

import java.io.*;
import java.util.*;

public class BaseDatosSimulada {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static int idCounter = 1;  // Este contador maneja el id de los usuarios

    private static final String LAST_ID_FILE = "mis_usuarios/last_id.txt";

    static {
        GestorArchivos.cargarDatos();
    }


    public static Usuario agregarUsuario(String nombre, String correo, String password) {
        Usuario nuevoUsuario = new Usuario(idCounter++, nombre, correo, password);
        usuarios.add(nuevoUsuario);
        guardarUltimoId();
        return nuevoUsuario;
    }


    public static void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        if (usuario.getId() >= idCounter) {
            idCounter = usuario.getId() + 1;
        }
        guardarUltimoId();
    }

    // Obtener todos los usuarios
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Eliminar un usuario por su ID
    public static boolean eliminarUsuario(int id) {
        return usuarios.removeIf(usuario -> usuario.getId() == id);
    }

    // Establecer el id más alto para asegurar que no haya duplicados
    public static void establecerIdCounter(int maxId) {
        idCounter = maxId + 1;
    }

    // Guardar el último ID usado en el archivo para mantener la persistencia
    private static void guardarUltimoId() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_ID_FILE))) {
            writer.write(String.valueOf(idCounter));
        } catch (IOException e) {
            System.err.println("Error al guardar el último ID: " + e.getMessage());
        }
    }

    // Cargar el último ID desde el archivo
    public static void cargarUltimoId() {
        File file = new File(LAST_ID_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(LAST_ID_FILE))) {
                String lastId = reader.readLine();
                if (lastId != null) {
                    idCounter = Integer.parseInt(lastId);  // Cargar el último ID usado
                }
            } catch (IOException e) {
                System.err.println("Error al cargar el último ID: " + e.getMessage());
            }

        }

    }
}

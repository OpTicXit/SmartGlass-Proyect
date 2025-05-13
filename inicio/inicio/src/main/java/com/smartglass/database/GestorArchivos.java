package com.smartglass.database;

import com.smartglass.model.Usuario;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class GestorArchivos {
    private static final String CARPETA = "mis_usuarios";
    private static final String USUARIOS_FILE = CARPETA + "/usuarios.csv";  // Ruta completa

    // Cargar al iniciar
    static {
        crearCarpetaSiNoExiste();
        BaseDatosSimulada.cargarUltimoId();  // Cargar el último ID antes de cargar los usuarios
        cargarDatos();
    }

    // Guardar los datos de los usuarios en un archivo CSV
    public static void guardarDatos() {
        List<Usuario> usuarios = BaseDatosSimulada.getUsuarios();

        if (BaseDatosSimulada.getUsuarios().isEmpty()) {
            System.out.println("Advertencia: lista de usuarios vacía, no se sobrescribirá el archivo.");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(USUARIOS_FILE))) {
            writer.write("id,nombre,correo,password");
            writer.newLine();

            for (Usuario u : usuarios) {
                writer.write(String.format("%d,%s,%s,%s%n",
                        u.getId(), u.getNombre(), u.getCorreo(), u.getPassword()));
            }

            System.out.println("Usuarios guardados correctamente en '" + USUARIOS_FILE + "'");
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // Cargar usuarios desde el CSV
    public static void cargarDatos() {
        Path path = Paths.get(USUARIOS_FILE);
        if (!Files.exists(path)) {
            System.out.println("No se encontró el archivo de usuarios, se empezará con una base vacía.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            reader.readLine(); // Saltar encabezado

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {  // Ahora debe tener 4 campos: id, nombre, correo, password
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String correo = datos[2];
                    String password = datos[3];

                    // Crear usuario con id
                    Usuario usuario = new Usuario(id, nombre, correo, password);
                    BaseDatosSimulada.agregarUsuario(usuario);
                }
            }

            System.out.println("Usuarios cargados correctamente desde el archivo.");
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    // Crear carpeta si no existe
    private static void crearCarpetaSiNoExiste() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("Carpeta creada: " + CARPETA);
            } else {
                System.err.println("No se pudo crear la carpeta: " + CARPETA);
            }
        }
    }
}

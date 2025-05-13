package com.smartglass;


import com.smartglass.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Hook para guardar los datos al salir
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Guardando usuarios antes de salir...");
            com.smartglass.database.GestorArchivos.guardarDatos();
        }));

        // Lanzar ventana de login
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}



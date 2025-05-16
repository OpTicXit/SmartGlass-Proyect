package com.smartglass;


import com.smartglass.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Guardando usuarios antes de salir...");
            com.smartglass.database.GestorArchivos.guardarDatos();
        }));

        
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}



package com.smartglass.model;

public class Administrador {
    private static final String CORREO = "admin";
    private static final String CONTRASEÑA = "ADMIN";

    public static String getCorreo() {
        return CORREO;
    }

    public static String getContraseña() {
        return CONTRASEÑA;
    }

    public static boolean validarCredenciales(String correoIngresado, String contraseñaIngresada) {
        return correoIngresado.equals(CORREO) && contraseñaIngresada.equals(CONTRASEÑA);
    }
}

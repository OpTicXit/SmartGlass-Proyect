package com.smartglass.service;

import com.smartglass.ui.LoginFrame;
import com.smartglass.model.Material;
import com.smartglass.model.Usuario;

import javax.swing.*;
import java.util.Map;

public class FacturaService {

    public void generarFactura(double costoMaterial, double co2Total, Map<Integer, Material> materiales) {
        Usuario usuario = LoginFrame.getUsuarioActual(); // Obtener usuario autenticado
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay usuario autenticado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder factura = new StringBuilder();
        factura.append("🆔 ID: ").append(usuario.getId()).append("\n");
        factura.append("👤 Nombre: ").append(usuario.getCorreo()).append("\n"); // Usamos el correo como nombre
        factura.append("📧 Correo: ").append(usuario.getCorreo()).append("\n");
        factura.append("💰 Total a pagar: ").append(costoMaterial).append("\n");
        factura.append("🌱 CO2 liberado: ").append(co2Total).append("\n\n");

        factura.append("📦 Materiales usados:\n");
        for (Map.Entry<Integer, Material> entry : materiales.entrySet()) {
            factura.append("Código: ").append(entry.getKey())
                    .append(", Nombre: ").append(entry.getValue().getNombre())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, factura.toString(), "Factura", JOptionPane.INFORMATION_MESSAGE);
    }
}

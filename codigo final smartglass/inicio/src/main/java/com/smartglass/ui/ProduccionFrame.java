package com.smartglass.ui;

import javax.swing.*;
import java.awt.*;
import com.smartglass.service.MaterialService;
import com.smartglass.model.Material;

public class ProduccionFrame extends JFrame {
    private MaterialService materialService;
    private JTextField campoCodigoMaterial;
    private JTextField campoCantidad;
    private JButton botonProducir;
    private JButton botonVolver;
    private JTextArea areaResumenProduccion;
    private Image imagenFondo;

    public ProduccionFrame() {
        materialService = MaterialService.getInstance();

        setTitle("SmartGlass - Producción de Vidrio");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagen produccion.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de fondo: " + e.getMessage());
            imagenFondo = null;
        }

        JPanel panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        JPanel ProducirpanelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 255, 255, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel etiquetaTitulo = new JLabel("PRODUCCIÓN DE VIDRIO");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(etiquetaTitulo, gbc);

        campoCodigoMaterial = new JTextField(20);
        panelPrincipal.add(new JLabel("Código del Material:"), gbc);
        panelPrincipal.add(campoCodigoMaterial, gbc);

        campoCantidad = new JTextField(20);
        panelPrincipal.add(new JLabel("Cantidad a producir:"), gbc);
        panelPrincipal.add(campoCantidad, gbc);

        botonProducir = new JButton("Producir Vidrio ✅");
        botonProducir.setBackground(new Color(0, 102, 204));
        botonProducir.setForeground(Color.WHITE);
        botonProducir.addActionListener(e -> producirVidrio());
        panelPrincipal.add(botonProducir, gbc);

        areaResumenProduccion = new JTextArea(10, 40);
        areaResumenProduccion.setEditable(false);
        panelPrincipal.add(new JScrollPane(areaResumenProduccion), gbc);

        botonVolver = new JButton("Volver al Menú 🔙");
        botonVolver.setBackground(new Color(51, 153, 255));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.addActionListener(e -> volverMenu());
        panelPrincipal.add(botonVolver, gbc);

        add(panelPrincipal);
    }

    private void producirVidrio() {
        try {
            int codigo = Integer.parseInt(campoCodigoMaterial.getText());
            int cantidad = Integer.parseInt(campoCantidad.getText());

            if (!materialService.existeMaterial(codigo)) {
                JOptionPane.showMessageDialog(this, "Material no encontrado ❌", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Material material = materialService.getMateriales().get(codigo);
            material.setCantidad(material.getCantidad() + cantidad);

            double costoTotal = material.calcularCostoTotal();
            double co2Total = material.calcularCO2Total();

            String resumen = "✅ Producción realizada:\n";
            resumen += "📦 Material: " + material.getNombre() + "\n";
            resumen += "🔢 Cantidad producida: " + material.getCantidad() + "\n";
            resumen += "💰 Costo Total: $" + costoTotal + "\n";
            resumen += "🌱 CO2 generado: " + co2Total + " kg\n";

            areaResumenProduccion.setText(resumen);
            JOptionPane.showMessageDialog(this, "Producción realizada con éxito ✅", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos ❌", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverMenu() {
        dispose();
        new MainMenuFrame().setVisible(true);
    }
}

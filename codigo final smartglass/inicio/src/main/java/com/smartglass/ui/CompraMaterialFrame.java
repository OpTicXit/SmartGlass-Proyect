package com.smartglass.ui;

import javax.swing.*;
import java.awt.*;

import com.smartglass.service.MaterialService;
import com.smartglass.model.Material;

public class CompraMaterialFrame extends JFrame {
    private MaterialService materialService;
    private JTextField campoCodigoMaterial;
    private JTextField campoCantidad;
    private JButton botonComprar;
    private JButton botonVolver;
    private JTextArea areaResumenCompra;
    private Image imagenFondo;

    public CompraMaterialFrame() {
        materialService = MaterialService.getInstance();

        setTitle("SmartGlass - Compra de Materiales");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            imagenFondo = new ImageIcon(getClass().getResource("/comprar.jpg")).getImage();
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

        JPanel ComprarpanelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 255, 255, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel etiquetaTitulo = new JLabel("COMPRA DE MATERIALES");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(etiquetaTitulo, gbc);

        campoCodigoMaterial = new JTextField(20);
        panelPrincipal.add(new JLabel("Código del Material:"), gbc);
        panelPrincipal.add(campoCodigoMaterial, gbc);

        campoCantidad = new JTextField(20);
        panelPrincipal.add(new JLabel("Cantidad:"), gbc);
        panelPrincipal.add(campoCantidad, gbc);

        botonComprar = new JButton("Comprar Material ✅");
        botonComprar.setBackground(new Color(0, 102, 204));
        botonComprar.setForeground(Color.WHITE);
        botonComprar.addActionListener(e -> comprarMaterial());
        panelPrincipal.add(botonComprar, gbc);

        areaResumenCompra = new JTextArea(10, 40);
        areaResumenCompra.setEditable(false);
        panelPrincipal.add(new JScrollPane(areaResumenCompra), gbc);

        botonVolver = new JButton("Volver al Menú 🔙");
        botonVolver.setBackground(new Color(51, 153, 255));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.addActionListener(e -> volverMenu());
        panelPrincipal.add(botonVolver, gbc);

        add(panelPrincipal);
    }

    private void comprarMaterial() {
        try {
            int codigo = Integer.parseInt(campoCodigoMaterial.getText());
            int cantidad = Integer.parseInt(campoCantidad.getText());

            Material material = materialService.getMateriales().get(codigo);
            if (material == null) {
                JOptionPane.showMessageDialog(this, "Material no encontrado ❌", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double costoTotal = material.getPrecio() * cantidad;
            double co2Total = material.getCo2PorUnidad() * cantidad;

            String resumen = "✅ Compra realizada:\n";
            resumen += "📦 Material: " + material.getNombre() + "\n";
            resumen += "🔢 Cantidad: " + cantidad + "\n";
            resumen += "💰 Costo Total: $" + costoTotal + "\n";
            resumen += "🌱 CO2 generado: " + co2Total + " kg\n";

            areaResumenCompra.setText(resumen);
            JOptionPane.showMessageDialog(this, "Compra realizada con éxito ✅", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos ❌", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverMenu() {
        dispose();
        new MainMenuFrame().setVisible(true);
    }
}

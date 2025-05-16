package com.smartglass.ui;

import com.smartglass.model.Material;
import com.smartglass.service.MaterialService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Map;

public class MaterialFrame extends JFrame {
    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JTextField campoUnidad;
    private JTextField campoPrecio;
    private JTextField campoCO2;
    private JButton botonRegistrar;
    private JButton botonRegresar;
    private JTextArea areaMateriales;
    private MaterialService materialService;
    private Image imagenFondo;

    public MaterialFrame() {
        materialService = MaterialService.getInstance(); // Usamos la instancia única

        setTitle("SmartGlass - Registro de Material");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagen material.jpg")).getImage();
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


        JPanel MaterialpanelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 255, 255, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel etiquetaTitulo = new JLabel("REGISTRO DE MATERIAL");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(etiquetaTitulo, gbc);

        panelPrincipal.add(crearCampo("Código del Material:", campoCodigo = new JTextField(20)), gbc);
        panelPrincipal.add(crearCampo("Nombre del Material:", campoNombre = new JTextField(20)), gbc);
        panelPrincipal.add(crearCampo("Unidad de Medida:", campoUnidad = new JTextField(20)), gbc);
        panelPrincipal.add(crearCampo("Precio del Material:", campoPrecio = new JTextField(20)), gbc);
        panelPrincipal.add(crearCampo("CO2 por Unidad:", campoCO2 = new JTextField(20)), gbc);

        botonRegistrar = new JButton("Registrar Material 📝");
        botonRegistrar.setBackground(new Color(0, 102, 204));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.addActionListener(e -> registrarMaterial());
        panelPrincipal.add(botonRegistrar, gbc);

        botonRegresar = new JButton("Regresar al Menú 🔙");
        botonRegresar.setBackground(new Color(51, 153, 255));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.addActionListener(e -> regresarMenu());
        panelPrincipal.add(botonRegresar, gbc);

        JLabel etiquetaMateriales = new JLabel("📦 Materiales Registrados:");
        etiquetaMateriales.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(etiquetaMateriales, gbc);

        areaMateriales = new JTextArea(10, 40);
        areaMateriales.setEditable(false);
        areaMateriales.setBorder(new LineBorder(Color.GRAY));
        panelPrincipal.add(new JScrollPane(areaMateriales), gbc);

        actualizarListaMateriales();

        add(panelPrincipal);
    }

    private JPanel crearCampo(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        panel.add(campo);
        return panel;
    }

    private void registrarMaterial() {
        try {
            int codigo = Integer.parseInt(campoCodigo.getText());
            String nombre = campoNombre.getText();
            String unidad = campoUnidad.getText();
            int precio = Integer.parseInt(campoPrecio.getText());
            double co2PorUnidad = Double.parseDouble(campoCO2.getText());

            materialService.registrarMaterial(codigo, nombre, unidad, precio, co2PorUnidad);

            JOptionPane.showMessageDialog(this, "Material registrado exitosamente ✅", "Registro", JOptionPane.INFORMATION_MESSAGE);
            actualizarListaMateriales();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos ❌", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarListaMateriales() {
        StringBuilder lista = new StringBuilder("📦 Materiales Registrados:\n");
        for (Map.Entry<Integer, Material> entry : materialService.getMateriales().entrySet()) {
            lista.append("Código: ").append(entry.getKey())
                    .append(", Nombre: ").append(entry.getValue().getNombre())
                    .append(", Unidad: ").append(entry.getValue().getUnidad())
                    .append(", Precio: 💰 ").append(entry.getValue().getPrecio())
                    .append(", CO2: 🌱 ").append(entry.getValue().getCo2PorUnidad())
                    .append("\n");
        }
        areaMateriales.setText(lista.toString());
    }

    private void regresarMenu() {
        dispose();
        new MainMenuFrame().setVisible(true);
    }
}

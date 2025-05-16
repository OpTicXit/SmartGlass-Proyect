package com.smartglass.ui;

import javax.swing.*;
import java.awt.*;



public class MainMenuFrame extends JFrame {
    private JButton botonRegistrarMaterial;
    private JButton botonComprarMaterial;
    private JButton botonProduccionVidrio;
    private JButton botonCerrarSesion;
    private Image imagenFondo;


    public MainMenuFrame() {
        setTitle("SmartGlass - Menú Principal");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagen menus.jpg")).getImage();
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

        JPanel MenupanelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 255, 255, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel etiquetaTitulo = new JLabel("MENÚ PRINCIPAL");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(etiquetaTitulo, gbc);

        botonRegistrarMaterial = new JButton("Registrar Material 📝");
        botonRegistrarMaterial.setBackground(new Color(0, 102, 204));
        botonRegistrarMaterial.setForeground(Color.WHITE);
        botonRegistrarMaterial.addActionListener(e -> abrirRegistroMaterial());
        panelPrincipal.add(botonRegistrarMaterial, gbc);

        botonComprarMaterial = new JButton("Comprar Material 🛒");
        botonComprarMaterial.setBackground(new Color(51, 153, 255));
        botonComprarMaterial.setForeground(Color.WHITE);
        botonComprarMaterial.addActionListener(e -> abrirCompraMaterial());
        panelPrincipal.add(botonComprarMaterial, gbc);

        botonProduccionVidrio = new JButton("Producción de Vidrio 🏭");
        botonProduccionVidrio.setBackground(new Color(0, 153, 76));
        botonProduccionVidrio.setForeground(Color.WHITE);
        botonProduccionVidrio.addActionListener(e -> abrirProduccionVidrio());
        panelPrincipal.add(botonProduccionVidrio, gbc);

        botonCerrarSesion = new JButton("Cerrar Sesión 🔒");
        botonCerrarSesion.setBackground(new Color(204, 0, 0));
        botonCerrarSesion.setForeground(Color.WHITE);
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
        panelPrincipal.add(botonCerrarSesion, gbc);

        add(panelPrincipal);
    }

    private void abrirRegistroMaterial() {
        new MaterialFrame().setVisible(true);
        dispose();
    }

    private void abrirCompraMaterial() {
        new CompraMaterialFrame().setVisible(true);
        dispose();
    }

    private void abrirProduccionVidrio() {
        new ProduccionFrame().setVisible(true);
        dispose();
    }

    private void cerrarSesion() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}


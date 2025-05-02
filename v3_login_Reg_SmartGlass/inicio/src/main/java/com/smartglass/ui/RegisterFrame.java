package com.smartglass.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.HashMap;

public class RegisterFrame extends JFrame {
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;
    private JPasswordField campoConfirmarContraseña;
    private JCheckBox mostrarContraseñaCheckbox;
    private JButton botonRegistrar;
    private JButton botonRegresar;

    private HashMap<String, String> usuariosRegistrados;

    public RegisterFrame(HashMap<String, String> usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados;
        setTitle("SmartGlass - Registro");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Image imagenFondo1 = null;
        try {
            imagenFondo1 = new ImageIcon(getClass().getResource("/imagevidrio.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }

        final Image imagenFondo = imagenFondo1;
        JPanel panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBackground(new Color(255, 255, 255, 80));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel etiquetaTitulo = new JLabel("REGISTRO");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelRegistro.add(etiquetaTitulo, gbc);

        JLabel etiquetaNombre = new JLabel("Nombre:");
        panelRegistro.add(etiquetaNombre, gbc);

        campoNombre = new JTextField(20);
        campoNombre.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panelRegistro.add(campoNombre, gbc);

        JLabel etiquetaApellido = new JLabel("Apellido:");
        panelRegistro.add(etiquetaApellido, gbc);

        campoApellido = new JTextField(20);
        campoApellido.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panelRegistro.add(campoApellido, gbc);

        JLabel etiquetaCorreo = new JLabel("Correo:");
        panelRegistro.add(etiquetaCorreo, gbc);

        campoCorreo = new JTextField(20);
        campoCorreo.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panelRegistro.add(campoCorreo, gbc);

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        panelRegistro.add(etiquetaContraseña, gbc);

        campoContraseña = new JPasswordField(20);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panelRegistro.add(campoContraseña, gbc);

        JLabel etiquetaConfirmarContraseña = new JLabel("Confirmar contraseña:");
        panelRegistro.add(etiquetaConfirmarContraseña, gbc);

        campoConfirmarContraseña = new JPasswordField(20);
        campoConfirmarContraseña.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panelRegistro.add(campoConfirmarContraseña, gbc);

        // Checkbox para mostrar/ocultar contraseñas
        mostrarContraseñaCheckbox = new JCheckBox("Mostrar contraseña");
        mostrarContraseñaCheckbox.setOpaque(false);
        mostrarContraseñaCheckbox.addActionListener(e -> {
            if (mostrarContraseñaCheckbox.isSelected()) {
                campoContraseña.setEchoChar((char) 0);
                campoConfirmarContraseña.setEchoChar((char) 0);
            } else {
                campoContraseña.setEchoChar('•');
                campoConfirmarContraseña.setEchoChar('•');
            }
        });
        panelRegistro.add(mostrarContraseñaCheckbox, gbc);

        botonRegistrar = new JButton("REGISTRAR");
        botonRegistrar.setBackground(new Color(0, 102, 204));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.addActionListener(e -> manejarRegistro());
        panelRegistro.add(botonRegistrar, gbc);

        botonRegresar = new JButton("REGRESAR");
        botonRegresar.setBackground(new Color(51, 153, 255));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFocusPainted(false);
        botonRegresar.addActionListener(e -> regresarInicioSesion());
        panelRegistro.add(botonRegresar, gbc);

        panelPrincipal.add(panelRegistro);
        add(panelPrincipal);
    }

    private void manejarRegistro() {
        String correo = campoCorreo.getText();
        String contraseña = new String(campoContraseña.getPassword());

        if (correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contraseña.equals(new String(campoConfirmarContraseña.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuariosRegistrados.put(correo, contraseña);

        JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puede iniciar sesión.", "Registro", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new LoginFrame(correo, contraseña).setVisible(true);
    }

    private void regresarInicioSesion() {
        dispose();
        new LoginFrame("", "").setVisible(true);
    }
}

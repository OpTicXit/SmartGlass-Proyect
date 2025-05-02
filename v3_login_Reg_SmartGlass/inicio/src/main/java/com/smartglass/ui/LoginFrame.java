package com.smartglass.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.HashMap;

public class LoginFrame extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContraseña;
    private JCheckBox mostrarContraseñaCheckbox;
    private JButton botonIniciarSesion;
    private JButton botonRegistrar;
    private JLabel etiquetaErrorUsuario;
    private JLabel etiquetaErrorContraseña;
    private JLabel etiquetaOlvidoContraseña;
    private Image imagenFondo;

    private static HashMap<String, String> usuariosRegistrados = new HashMap<>();

    public LoginFrame() {
        this("", "");
    }

    public LoginFrame(String correoPrellenado, String contraseñaPrellenada) {
        setTitle("SmartGlass - Inicio de Sesión");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagevidrio.jpg")).getImage();
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

        JPanel panelLogin = new JPanel(new GridBagLayout());
        panelLogin.setBackground(new Color(255, 255, 255, 80));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel etiquetaTitulo = new JLabel("INICIO DE SESIÓN");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogin.add(etiquetaTitulo, gbc);

        JLabel etiquetaUsuario = new JLabel("Correo:");
        panelLogin.add(etiquetaUsuario, gbc);

        campoUsuario = new JTextField(20);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        campoUsuario.setText(correoPrellenado);
        panelLogin.add(campoUsuario, gbc);

        etiquetaErrorUsuario = new JLabel("");
        etiquetaErrorUsuario.setForeground(Color.RED);
        etiquetaErrorUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        panelLogin.add(etiquetaErrorUsuario, gbc);

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        panelLogin.add(etiquetaContraseña, gbc);

        campoContraseña = new JPasswordField(20);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        campoContraseña.setText(contraseñaPrellenada);
        panelLogin.add(campoContraseña, gbc);

        mostrarContraseñaCheckbox = new JCheckBox("Mostrar contraseña");
        mostrarContraseñaCheckbox.setOpaque(false);
        mostrarContraseñaCheckbox.addActionListener(e -> {
            if (mostrarContraseñaCheckbox.isSelected()) {
                campoContraseña.setEchoChar((char) 0);
            } else {
                campoContraseña.setEchoChar('•');
            }
        });

        panelLogin.add(mostrarContraseñaCheckbox, gbc);

        etiquetaErrorContraseña = new JLabel("");
        etiquetaErrorContraseña.setForeground(Color.RED);
        etiquetaErrorContraseña.setFont(new Font("Arial", Font.PLAIN, 12));
        panelLogin.add(etiquetaErrorContraseña, gbc);

        // Opción de "Olvidó su contraseña"
        etiquetaOlvidoContraseña = new JLabel("¿Olvidó su contraseña?");
        etiquetaOlvidoContraseña.setForeground(Color.BLUE);
        etiquetaOlvidoContraseña.setCursor(new Cursor(Cursor.HAND_CURSOR));
        etiquetaOlvidoContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                manejarOlvidoContraseña();
            }
        });
        panelLogin.add(etiquetaOlvidoContraseña, gbc);

        botonIniciarSesion = new JButton("INICIAR SESIÓN");
        botonIniciarSesion.setBackground(new Color(0, 102, 204));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFocusPainted(false);
        botonIniciarSesion.addActionListener(e -> manejarInicioSesion());
        panelLogin.add(botonIniciarSesion, gbc);

        botonRegistrar = new JButton("REGISTRAR");
        botonRegistrar.setBackground(new Color(51, 153, 255));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.addActionListener(e -> {
            dispose();
            new RegisterFrame(usuariosRegistrados).setVisible(true);
        });
        panelLogin.add(botonRegistrar, gbc);

        panelPrincipal.add(panelLogin);
        add(panelPrincipal);
    }

    private void manejarInicioSesion() {
        String correo = campoUsuario.getText();
        String contraseña = new String(campoContraseña.getPassword());

        if (correo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Correo' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (contraseña.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Contraseña' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuariosRegistrados.containsKey(correo)) {
            if (usuariosRegistrados.get(correo).equals(contraseña)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Inicio de sesión con el correo: " + correo);
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no registrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarOlvidoContraseña() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de recuperación de contraseña en desarrollo.", "Recuperar Contraseña", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Abriendo ventana de recuperación de contraseña...");
    }
}

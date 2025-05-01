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
    private JLabel etiquetaOlvidarContraseña;
    private JLabel etiquetaErrorUsuario;
    private JLabel etiquetaErrorContraseña;
    private Image imagenFondo;

    // HashMap que almacena los usuarios registrados temporalmente
    private static HashMap<String, String> usuariosRegistrados = new HashMap<>();

    public LoginFrame() {
        setTitle("SmartGlass - Inicio de Sesión");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar imagen de fondo
        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagevidrio.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de fondo: " + e.getMessage());
            imagenFondo = null;
        }

        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Panel de inicio de sesión
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new GridBagLayout());
        panelLogin.setBackground(new Color(255, 255, 255, 80));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Título
        JLabel etiquetaTitulo = new JLabel("INICIO DE SESIÓN");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogin.add(etiquetaTitulo, gbc);

        // Etiqueta y campo de usuario
        JLabel etiquetaUsuario = new JLabel("Correo:");
        panelLogin.add(etiquetaUsuario, gbc);

        campoUsuario = new JTextField(20);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelLogin.add(campoUsuario, gbc);

        // Etiqueta de error para usuario
        etiquetaErrorUsuario = new JLabel("");
        etiquetaErrorUsuario.setForeground(Color.RED);
        etiquetaErrorUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        panelLogin.add(etiquetaErrorUsuario, gbc);

        // Etiqueta de contraseña
        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        panelLogin.add(etiquetaContraseña, gbc);

        // Campo de contraseña
        campoContraseña = new JPasswordField(20);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Checkbox para mostrar/ocultar contraseña
        mostrarContraseñaCheckbox = new JCheckBox("Mostrar contraseña");
        mostrarContraseñaCheckbox.setOpaque(false);
        mostrarContraseñaCheckbox.addActionListener(e -> {
            if (mostrarContraseñaCheckbox.isSelected()) {
                campoContraseña.setEchoChar((char) 0); // Muestra la contraseña
            } else {
                campoContraseña.setEchoChar('•'); // Oculta la contraseña
            }
        });

        // Panel contenedor para el campo de contraseña y el checkbox
        JPanel contenedorContraseña = new JPanel(new BorderLayout(5, 0));
        contenedorContraseña.setOpaque(false);
        contenedorContraseña.add(campoContraseña, BorderLayout.CENTER);
        contenedorContraseña.add(mostrarContraseñaCheckbox, BorderLayout.SOUTH);
        panelLogin.add(contenedorContraseña, gbc);

        // Etiqueta de error para contraseña
        etiquetaErrorContraseña = new JLabel("");
        etiquetaErrorContraseña.setForeground(Color.RED);
        etiquetaErrorContraseña.setFont(new Font("Arial", Font.PLAIN, 12));
        panelLogin.add(etiquetaErrorContraseña, gbc);

        // Etiqueta "¿Olvidó su contraseña?"
        etiquetaOlvidarContraseña = new JLabel("¿Olvidó su contraseña?");
        etiquetaOlvidarContraseña.setForeground(Color.BLUE);
        etiquetaOlvidarContraseña.setCursor(new Cursor(Cursor.HAND_CURSOR));
        etiquetaOlvidarContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                manejarOlvidoContraseña();
            }
        });
        panelLogin.add(etiquetaOlvidarContraseña, gbc);

        // Botón de inicio de sesión
        botonIniciarSesion = new JButton("INICIAR SESIÓN");
        botonIniciarSesion.setBackground(new Color(0, 102, 204));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFocusPainted(false);
        botonIniciarSesion.addActionListener(e -> manejarInicioSesion());
        panelLogin.add(botonIniciarSesion, gbc);

        // Botón de registro
        botonRegistrar = new JButton("REGISTRAR");
        botonRegistrar.setBackground(new Color(51, 153, 255));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.addActionListener(e -> {
            dispose(); // Cerrar la ventana de inicio de sesión
            new RegisterFrame(usuariosRegistrados).setVisible(true); // Abrir la ventana de registro
        });
        panelLogin.add(botonRegistrar, gbc);

        // Agregar panel de login al panel principal
        panelPrincipal.add(panelLogin);

        // Agregar panel principal al frame
        add(panelPrincipal);
    }

    private void manejarInicioSesion() {
        String correo = campoUsuario.getText();
        String contraseña = new String(campoContraseña.getPassword());

        // Verificar que los campos no estén vacíos
        if (correo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Correo' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (contraseña.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Contraseña' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si el usuario está registrado
        if (usuariosRegistrados.containsKey(correo)) {
            // Verificar si la contraseña es correcta
            if (usuariosRegistrados.get(correo).equals(contraseña)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Inicio de sesión con el correo: " + correo);
                // Aquí puedes redirigir al usuario a la próxima funcionalidad
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

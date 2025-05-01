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
    private JButton botonRegistrar;
    private JButton botonRegresar;

    // HashMap para almacenar los usuarios registrados
    private HashMap<String, String> usuariosRegistrados;

    // Constructor que recibe el HashMap como parámetro
    public RegisterFrame(HashMap<String, String> usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados; // Asignar el mapa al registro
        setTitle("SmartGlass - Registro");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Declarar la imagen como final para que pueda ser usada en la clase interna
        final Image imagenFondo;
        Image imagenFondo1;
        try {
            imagenFondo1 = new ImageIcon(getClass().getResource("/imagevidrio.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de fondo: " + e.getMessage());
            imagenFondo1 = null;
        }

        // Panel principal con fondo
        imagenFondo = imagenFondo1;
        JPanel panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Panel de registro
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new GridBagLayout());
        panelRegistro.setBackground(new Color(255, 255, 255, 80));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Título
        JLabel etiquetaTitulo = new JLabel("REGISTRO");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelRegistro.add(etiquetaTitulo, gbc);

        // Etiqueta y campo de nombre
        JLabel etiquetaNombre = new JLabel("Nombre:");
        panelRegistro.add(etiquetaNombre, gbc);

        campoNombre = new JTextField(20);
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelRegistro.add(campoNombre, gbc);

        // Etiqueta y campo de apellido
        JLabel etiquetaApellido = new JLabel("Apellido:");
        panelRegistro.add(etiquetaApellido, gbc);

        campoApellido = new JTextField(20);
        campoApellido.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelRegistro.add(campoApellido, gbc);

        // Etiqueta y campo de correo
        JLabel etiquetaCorreo = new JLabel("Correo:");
        panelRegistro.add(etiquetaCorreo, gbc);

        campoCorreo = new JTextField(20);
        campoCorreo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelRegistro.add(campoCorreo, gbc);

        // Etiqueta y campo de contraseña
        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        panelRegistro.add(etiquetaContraseña, gbc);

        campoContraseña = new JPasswordField(20);
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelRegistro.add(campoContraseña, gbc);

        // Etiqueta y campo de confirmar contraseña
        JLabel etiquetaConfirmarContraseña = new JLabel("Confirmar contraseña:");
        panelRegistro.add(etiquetaConfirmarContraseña, gbc);

        campoConfirmarContraseña = new JPasswordField(20);
        campoConfirmarContraseña.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panelRegistro.add(campoConfirmarContraseña, gbc);

        // Botón para registrar
        botonRegistrar = new JButton("REGISTRAR");
        botonRegistrar.setBackground(new Color(0, 102, 204));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.addActionListener(e -> manejarRegistro());
        panelRegistro.add(botonRegistrar, gbc);

        // Botón para regresar al inicio de sesión
        botonRegresar = new JButton("REGRESAR");
        botonRegresar.setBackground(new Color(51, 153, 255));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFocusPainted(false);
        botonRegresar.addActionListener(e -> regresarInicioSesion());
        panelRegistro.add(botonRegresar, gbc);

        // Agregar panel de registro al panel principal
        panelPrincipal.add(panelRegistro);

        // Agregar panel principal al frame
        add(panelPrincipal);
    }

    private void manejarRegistro() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        String correo = campoCorreo.getText();
        String contraseña = new String(campoContraseña.getPassword());
        String confirmarContraseña = new String(campoConfirmarContraseña.getPassword());

        // Verificar que los campos no estén vacíos
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty()
                || contraseña.trim().isEmpty() || confirmarContraseña.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Agregar el usuario al mapa de usuarios registrados
        usuariosRegistrados.put(correo, contraseña);

        // Simulación de registro exitoso
        JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puede iniciar sesión.", "Registro", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Usuario registrado: " + nombre + " " + apellido + ", correo: " + correo);
        dispose(); // Cerrar la ventana de registro
        new LoginFrame().setVisible(true); // Abrir la ventana de inicio de sesión
    }

    private void regresarInicioSesion() {
        dispose(); // Cerrar la ventana de registro
        new LoginFrame().setVisible(true); // Abrir la ventana de inicio de sesión
    }
}

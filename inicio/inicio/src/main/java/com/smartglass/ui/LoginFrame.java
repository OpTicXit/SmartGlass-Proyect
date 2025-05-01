package com.smartglass.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel forgotPasswordLabel;
    private Image backgroundImage;

    public LoginFrame() {
        setTitle("SmartGlass - Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            backgroundImage = new ImageIcon(getClass().getResource("/imagevidrio.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Main panel with background image
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw the background image scaled to fit the panel
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255, 80));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Title
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel, gbc);

        // Username field
        userField = new JTextField(20);
        userField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JLabel userLabel = new JLabel("Usuario:");
        loginPanel.add(userLabel, gbc);
        loginPanel.add(userField, gbc);

        // Password field with show/hide functionality
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setOpaque(false);
        passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        showPasswordCheckbox = new JCheckBox("Mostrar contraseña");
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        JLabel passwordLabel = new JLabel("Contraseña:");
        loginPanel.add(passwordLabel, gbc);
        loginPanel.add(passwordField, gbc);
        loginPanel.add(showPasswordCheckbox, gbc);

        // Forgot password link
        forgotPasswordLabel = new JLabel("¿Olvidó su contraseña?");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgotPassword();
            }
        });
        loginPanel.add(forgotPasswordLabel, gbc);

        // Login button
        loginButton = new JButton("INICIAR SESIÓN");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton, gbc);

        // Register button
        registerButton = new JButton("REGISTRAR");
        registerButton.setBackground(new Color(51, 153, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> handleRegister());
        loginPanel.add(registerButton, gbc);

        // Add login panel to main panel
        mainPanel.add(loginPanel);

        // Add main panel to frame
        add(mainPanel);
    }

    private void handleLogin() {
        String username = userField.getText();
        String password = new String(passwordField.getPassword());

        // Add your login logic here
        System.out.println("Login attempt with username: " + username);
    }

    private void handleRegister() {
        // Add your registration logic here
        System.out.println("Opening registration window...");
    }

    private void handleForgotPassword() {
        // Add your forgot password logic here
        System.out.println("Opening forgot password window...");

    }
}
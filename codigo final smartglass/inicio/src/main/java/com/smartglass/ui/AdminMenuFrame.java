package com.smartglass.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.smartglass.service.UsuarioService;
import com.smartglass.service.MaterialService;

public class AdminMenuFrame extends JFrame {
    private UsuarioService usuarioService;
    private MaterialService materialService;
    private JTable tablaUsuarios;
    private JTable tablaMateriales;
    private JTextField campoBuscarUsuario;
    private JTextField campoBuscarMaterial;
    private JButton botonEliminarUsuario;
    private JButton botonModificarUsuario;
    private JButton botonEliminarMaterial;
    private JButton botonModificarMaterial;
    private JButton botonActualizarUsuarios;
    private JButton botonActualizarMateriales;
    private JButton botonVolverInicioSesion;
    private Image imagenFondo;

    public AdminMenuFrame() {
        usuarioService = UsuarioService.getInstance();
        materialService = MaterialService.getInstance();

        setTitle("SmartGlass - Administrador");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            imagenFondo = new ImageIcon(getClass().getResource("/imagen admin.jpg")).getImage();
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

        JPanel AdminpanelPrincipal = new JPanel(new GridLayout(1, 2, 20, 20));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Panel de Usuarios
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        panelUsuarios.setBackground(new Color(220, 220, 220));
        panelUsuarios.setBorder(BorderFactory.createTitledBorder("Usuarios Registrados"));

        tablaUsuarios = new JTable(new DefaultTableModel(new Object[]{"Nombre", "Correo"}, 0));
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);

        JPanel panelAccionesUsuarios = new JPanel(new GridLayout(5, 1, 10, 10));
        campoBuscarUsuario = new JTextField(20);
        panelAccionesUsuarios.add(new JLabel("Buscar usuario por correo:"));
        panelAccionesUsuarios.add(campoBuscarUsuario);

        botonEliminarUsuario = new JButton("Eliminar Usuario ❌");
        botonEliminarUsuario.setBackground(new Color(255, 51, 51));
        botonEliminarUsuario.setForeground(Color.WHITE);
        botonEliminarUsuario.addActionListener(e -> eliminarUsuario());
        panelAccionesUsuarios.add(botonEliminarUsuario);

        botonModificarUsuario = new JButton("Modificar Usuario ✏️");
        botonModificarUsuario.setBackground(new Color(51, 153, 255));
        botonModificarUsuario.setForeground(Color.WHITE);
        botonModificarUsuario.addActionListener(e -> modificarUsuario());
        panelAccionesUsuarios.add(botonModificarUsuario);

        botonActualizarUsuarios = new JButton("Actualizar Usuarios 🔄");
        botonActualizarUsuarios.setBackground(new Color(0, 102, 204));
        botonActualizarUsuarios.setForeground(Color.WHITE);
        botonActualizarUsuarios.addActionListener(e -> actualizarUsuarios());
        panelAccionesUsuarios.add(botonActualizarUsuarios);

        panelUsuarios.add(panelAccionesUsuarios, BorderLayout.SOUTH);
        panelPrincipal.add(panelUsuarios);

        // Panel de Materiales
        JPanel panelMateriales = new JPanel(new BorderLayout());
        panelMateriales.setBackground(new Color(220, 220, 220));
        panelMateriales.setBorder(BorderFactory.createTitledBorder("Materiales Registrados"));

        tablaMateriales = new JTable(new DefaultTableModel(new Object[]{"Código", "Nombre"}, 0));
        JScrollPane scrollMateriales = new JScrollPane(tablaMateriales);
        panelMateriales.add(scrollMateriales, BorderLayout.CENTER);

        JPanel panelAccionesMateriales = new JPanel(new GridLayout(5, 1, 10, 10));
        campoBuscarMaterial = new JTextField(20);
        panelAccionesMateriales.add(new JLabel("Buscar material por código:"));
        panelAccionesMateriales.add(campoBuscarMaterial);

        botonEliminarMaterial = new JButton("Eliminar Material ❌");
        botonEliminarMaterial.setBackground(new Color(255, 51, 51));
        botonEliminarMaterial.setForeground(Color.WHITE);
        botonEliminarMaterial.addActionListener(e -> eliminarMaterial());
        panelAccionesMateriales.add(botonEliminarMaterial);

        botonModificarMaterial = new JButton("Modificar Material ✏️");
        botonModificarMaterial.setBackground(new Color(51, 153, 255));
        botonModificarMaterial.setForeground(Color.WHITE);
        botonModificarMaterial.addActionListener(e -> modificarMaterial());
        panelAccionesMateriales.add(botonModificarMaterial);

        botonActualizarMateriales = new JButton("Actualizar Materiales 🔄");
        botonActualizarMateriales.setBackground(new Color(0, 102, 204));
        botonActualizarMateriales.setForeground(Color.WHITE);
        botonActualizarMateriales.addActionListener(e -> actualizarMateriales());
        panelAccionesMateriales.add(botonActualizarMateriales);

        panelMateriales.add(panelAccionesMateriales, BorderLayout.SOUTH);
        panelPrincipal.add(panelMateriales);

        // Botón para volver al inicio de sesión
        botonVolverInicioSesion = new JButton("Volver al Inicio de Sesión 🔄");
        botonVolverInicioSesion.setBackground(new Color(51, 51, 51));
        botonVolverInicioSesion.setForeground(Color.WHITE);
        botonVolverInicioSesion.addActionListener(e -> volverInicioSesion());

        JPanel panelInferior = new JPanel();
        panelInferior.add(botonVolverInicioSesion);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void actualizarUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tablaUsuarios.getModel();
        model.setRowCount(0);

        usuarioService.getUsuarios().forEach((correo, usuario) -> {
            model.addRow(new Object[]{usuario.getNombre(), usuario.getCorreo()});
        });
    }

    private void actualizarMateriales() {
        DefaultTableModel model = (DefaultTableModel) tablaMateriales.getModel();
        model.setRowCount(0);
        materialService.getMateriales().forEach((codigo, material) -> model.addRow(new Object[]{codigo, material.getNombre()}));
    }

    private void eliminarUsuario() {
        String correo = campoBuscarUsuario.getText();
        usuarioService.eliminarUsuario(correo);
        actualizarUsuarios();
        JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente ✅", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarUsuario() {
        String correo = campoBuscarUsuario.getText();
        String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre:");
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            usuarioService.modificarUsuario(correo, nuevoNombre);
            actualizarUsuarios();
            JOptionPane.showMessageDialog(this, "Usuario modificado correctamente ✅", "Modificación", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarMaterial() {
        int codigo = Integer.parseInt(campoBuscarMaterial.getText());
        materialService.eliminarMaterial(codigo);
        actualizarMateriales();
        JOptionPane.showMessageDialog(this, "Material eliminado correctamente ✅", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarMaterial() {
        int codigo = Integer.parseInt(campoBuscarMaterial.getText());
        String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre del material:");
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            materialService.modificarMaterial(codigo, nuevoNombre);
            actualizarMateriales();
            JOptionPane.showMessageDialog(this, "Material modificado correctamente ✅", "Modificación", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void volverInicioSesion() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

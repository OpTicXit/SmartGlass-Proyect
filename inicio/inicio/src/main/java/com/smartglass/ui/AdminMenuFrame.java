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



        JPanel AdminpanelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 255, 255, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel etiquetaTitulo = new JLabel("ADMINISTRACIÓN DEL SISTEMA");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(etiquetaTitulo, gbc);

        // Tabla de usuarios
        JLabel etiquetaUsuarios = new JLabel("Usuarios Registrados:");
        etiquetaUsuarios.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(etiquetaUsuarios, gbc);

        tablaUsuarios = new JTable(new DefaultTableModel(new Object[]{"Nombre", "Correo"}, 0));
        panelPrincipal.add(new JScrollPane(tablaUsuarios), gbc);

        campoBuscarUsuario = new JTextField(20);
        campoBuscarUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validarBotonesUsuario();
            }
        });
        panelPrincipal.add(new JLabel("Buscar usuario por correo:"), gbc);
        panelPrincipal.add(campoBuscarUsuario, gbc);

        botonEliminarUsuario = new JButton("Eliminar Usuario ❌");
        botonEliminarUsuario.setEnabled(false);
        botonEliminarUsuario.addActionListener(e -> eliminarUsuario());
        panelPrincipal.add(botonEliminarUsuario, gbc);

        botonModificarUsuario = new JButton("Modificar Usuario ✏️");
        botonModificarUsuario.setEnabled(false);
        botonModificarUsuario.addActionListener(e -> modificarUsuario());
        panelPrincipal.add(botonModificarUsuario, gbc);

        botonActualizarUsuarios = new JButton("Actualizar Usuarios 🔄");
        botonActualizarUsuarios.addActionListener(e -> actualizarUsuarios());
        panelPrincipal.add(botonActualizarUsuarios, gbc);

        // Tabla de materiales
        JLabel etiquetaMateriales = new JLabel("Materiales Registrados:");
        etiquetaMateriales.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(etiquetaMateriales, gbc);

        tablaMateriales = new JTable(new DefaultTableModel(new Object[]{"Código", "Nombre"}, 0));
        panelPrincipal.add(new JScrollPane(tablaMateriales), gbc);

        campoBuscarMaterial = new JTextField(20);
        campoBuscarMaterial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validarBotonesMaterial();
            }
        });
        panelPrincipal.add(new JLabel("Buscar material por código:"), gbc);
        panelPrincipal.add(campoBuscarMaterial, gbc);

        botonEliminarMaterial = new JButton("Eliminar Material ❌");
        botonEliminarMaterial.setEnabled(false);
        botonEliminarMaterial.addActionListener(e -> eliminarMaterial());
        panelPrincipal.add(botonEliminarMaterial, gbc);

        botonModificarMaterial = new JButton("Modificar Material ✏️");
        botonModificarMaterial.setEnabled(false);
        botonModificarMaterial.addActionListener(e -> modificarMaterial());
        panelPrincipal.add(botonModificarMaterial, gbc);

        botonActualizarMateriales = new JButton("Actualizar Materiales 🔄");
        botonActualizarMateriales.addActionListener(e -> actualizarMateriales());
        panelPrincipal.add(botonActualizarMateriales, gbc);

        // Botón para volver al inicio de sesión
        botonVolverInicioSesion = new JButton("Volver al Inicio de Sesión 🔄");
        botonVolverInicioSesion.setBackground(new Color(0, 102, 204));
        botonVolverInicioSesion.setForeground(Color.WHITE);
        botonVolverInicioSesion.addActionListener(e -> volverInicioSesion());
        panelPrincipal.add(botonVolverInicioSesion, gbc);

        add(panelPrincipal);
        actualizarUsuarios();
        actualizarMateriales();
    }

    private void validarBotonesUsuario() {
        boolean habilitar = !campoBuscarUsuario.getText().trim().isEmpty();
        botonEliminarUsuario.setEnabled(habilitar);
        botonModificarUsuario.setEnabled(habilitar);
    }

    private void validarBotonesMaterial() {
        boolean habilitar = !campoBuscarMaterial.getText().trim().isEmpty();
        botonEliminarMaterial.setEnabled(habilitar);
        botonModificarMaterial.setEnabled(habilitar);
    }

    private void actualizarUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tablaUsuarios.getModel();
        model.setRowCount(0);
        usuarioService.getUsuarios().forEach((correo, usuario) -> model.addRow(new Object[]{usuario.getNombre(), correo}));
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

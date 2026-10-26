package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import java.io.File;
import java.sql.SQLException;
import Dominio.dueno;
import servidor.DAO.DuenoDAO;
import Dominio.veterinario;
import servidor.DAO.VeterinarioDAO;
// [USER_IMPORTS_END]

public class FormularioRegistro extends JFrame {


    private JTextField txtNombre;
    private JTextField txtCI;
    private JTextField txtTelefono;
    private JPasswordField txtContraseña;
    private JPasswordField txtRepetirContraseña;
    private JTextField txtEmail;
    private JComboBox ComboTipo;
    private JMenuBar menuBar9;
    private JButton BotonIntroducir;
    private JTextField txtEspecialidad;
    private JLabel LabelNombre;
    private JLabel LabelContraseña;
    private JLabel LabelCI;
    private JLabel LabelRepetirContraseña;
    private JLabel LabelTelefono;
    private JLabel LabelEmail;
    private JLabel LabelTipo;
    private JLabel LabelEspecialidad;
    private JLabel labelBienvenido;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioRegistro() {
        setTitle("FormularioRegistro");
        setSize(500, 410);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
    }

    private void initComponents() {

        // ---------- FUENTE DEL TITULO ----------
        Font fuenteTitulo;
        try {
            fuenteTitulo = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/Fuentes/RacingSansOne-Regular.ttf")).deriveFont(32f);
        } catch (Exception ex) {
            fuenteTitulo = new Font("Georgia", Font.BOLD, 30);  
        }

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtNombre.setBounds(180, 70, 120, 22);

        txtCI = new JTextField();
        txtCI.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtCI.setBounds(180, 160, 120, 22);

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtTelefono.setBounds(180, 190, 120, 22);

        txtContraseña = new JPasswordField();
        txtContraseña.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtContraseña.setBounds(180, 100, 120, 22);

        txtRepetirContraseña = new JPasswordField();
        txtRepetirContraseña.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtRepetirContraseña.setBounds(180, 130, 120, 22);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtEmail.setBounds(180, 220, 120, 22);

        ComboTipo = new JComboBox<>(new String[]{"Veterinario", "Dueño"});
        ComboTipo.setBounds(180, 250, 120, 22);

        menuBar9 = new JMenuBar();
        JMenu menu_1 = new JMenu("Iniciar sesion");
        JMenuItem itemIniciarSesion = new JMenuItem("Iniciar sesion");

        // [USER_ACTION_START:itemIniciarSesion]
itemIniciarSesion.addActionListener(e -> {
            new formularioiniciarsesion().setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:itemIniciarSesion]

        menu_1.add(itemIniciarSesion);
        menuBar9.add(menu_1);


        BotonIntroducir = new JButton("Introducir");
        BotonIntroducir.setFont(new Font("Tahoma", Font.PLAIN, 11));

        // [USER_ACTION_START:BotonIntroducir]
BotonIntroducir.addActionListener(ev -> {
            String nombre = txtNombre.getText().trim();
            String pass = new String(txtContraseña.getPassword());
            String repetir = new String(txtRepetirContraseña.getPassword());
            boolean esDueno = ComboTipo.getSelectedItem().equals("Dueño");

            // 1) Campos comunes completos
            if (nombre.isEmpty() || pass.isEmpty() || repetir.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completá nombre y contraseña",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2) Las contraseñas tienen que coincidir
            if (!pass.equals(repetir)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // 3) El nombre no puede existir en ninguna de las dos tablas
                if (new DuenoDAO().existeNombre(nombre) || new VeterinarioDAO().existeNombre(nombre)) {
                    JOptionPane.showMessageDialog(this, "Ese nombre ya está en uso",
                            "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id;
                if (esDueno) {
                    String ci = txtCI.getText().trim();
                    String telefono = txtTelefono.getText().trim();
                    String email = txtEmail.getText().trim();

                    if (ci.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Completá CI, teléfono y email",
                                "Atención", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (!email.contains("@")) {
                        JOptionPane.showMessageDialog(this, "El email no es válido",
                                "Atención", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int ciNum = Integer.parseInt(ci);
                    int telNum = Integer.parseInt(telefono);

                    id = new DuenoDAO().guardar(new dueno(0, nombre, ciNum, telNum, email, pass));
                } else {
                    String especialidad = txtEspecialidad.getText().trim();
                    if (especialidad.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Completá la especialidad",
                                "Atención", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    id = new VeterinarioDAO().guardar(new veterinario(0, nombre, pass, especialidad));
                }

                if (id == -1) {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (esDueno) {
    JOptionPane.showMessageDialog(this, "¡Registro exitoso! Ya podés iniciar sesión.");
} else {
    JOptionPane.showMessageDialog(this,
            "Registro enviado. Vas a poder ingresar cuando el administrador apruebe tu cuenta.",
            "Registro enviado", JOptionPane.INFORMATION_MESSAGE);
}
new formularioiniciarsesion().setVisible(true);
dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "CI y teléfono deben ser números",
                        "Atención", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
            }
        });
        // [USER_ACTION_END:BotonIntroducir]

         
        BotonIntroducir.setBounds(195, 310, 90, 25);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setFont(new Font("Georgia", Font.PLAIN, 11));
        txtEspecialidad.setBounds(180, 280, 120, 22);

        LabelNombre = new JLabel("Nombre");
        LabelNombre.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelNombre.setBounds(80, 70, 80, 20);

        LabelContraseña = new JLabel("Contraseña");
        LabelContraseña.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelContraseña.setBounds(80, 100, 80, 20);

        LabelCI = new JLabel("CI");
        LabelCI.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelCI.setBounds(80, 160, 80, 20);

        LabelRepetirContraseña = new JLabel("Repetir Contraseña");
        LabelRepetirContraseña.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelRepetirContraseña.setBounds(80, 130, 100, 20);

        LabelTelefono = new JLabel("Telefono");
        LabelTelefono.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelTelefono.setBounds(80, 190, 80, 20);

        LabelEmail = new JLabel("Email");
        LabelEmail.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelEmail.setBounds(80, 220, 80, 20);

        LabelTipo = new JLabel("Tipo");
        LabelTipo.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelTipo.setBounds(80, 250, 80, 20);

        LabelEspecialidad = new JLabel("Especialidad");
        LabelEspecialidad.setFont(new Font("Georgia", Font.PLAIN, 11));
        LabelEspecialidad.setBounds(80, 280, 80, 20);

        labelBienvenido = new JLabel("Bienvenido");
        labelBienvenido.setFont(fuenteTitulo);
        labelBienvenido.setBounds(147, 30, 202, 23);

        // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar9.setBackground(Beige);

        txtEspecialidad.setForeground(Beige);
        txtNombre.setForeground(Beige);
        txtCI.setForeground(Beige);
        txtTelefono.setForeground(Beige);
        txtContraseña.setForeground(Beige);
        txtRepetirContraseña.setForeground(Beige);
        txtEspecialidad.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtNombre.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtCI.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtTelefono.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtContraseña.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtRepetirContraseña.setBorder(BorderFactory.createLineBorder(Beige, 2));
        txtEmail.setForeground(Beige);
        txtEmail.setBorder(BorderFactory.createLineBorder(Beige, 2));

       
JLabel[] labels = {
    LabelNombre, LabelContraseña, LabelCI, LabelRepetirContraseña,
    LabelTelefono, LabelEmail, LabelTipo, LabelEspecialidad, labelBienvenido
};
for (JLabel l : labels) {
    l.setForeground(Beige);
}



// ComboBox
ComboTipo.setForeground(verdeOscuro);
ComboTipo.setBackground(Beige);

        BotonIntroducir.setBackground(Beige);
        BotonIntroducir.setForeground(verdeOscuro);
        BotonIntroducir.setOpaque(true);
        BotonIntroducir.setBorderPainted(false);
        BotonIntroducir.setFocusPainted(false);


        getContentPane().add(txtNombre);
        getContentPane().add(txtCI);
        getContentPane().add(txtTelefono);
        getContentPane().add(txtContraseña);
        getContentPane().add(txtRepetirContraseña);
        getContentPane().add(txtEmail);
        getContentPane().add(ComboTipo);
        setJMenuBar(menuBar9);
        getContentPane().add(BotonIntroducir);
        getContentPane().add(txtEspecialidad);
        getContentPane().add(LabelNombre);
        getContentPane().add(LabelContraseña);
        getContentPane().add(LabelCI);
        getContentPane().add(LabelRepetirContraseña);
        getContentPane().add(LabelTelefono);
        getContentPane().add(LabelEmail);
        getContentPane().add(LabelTipo);
        getContentPane().add(LabelEspecialidad);
        getContentPane().add(labelBienvenido);

        //imagen--------------------------------------------
        ImageIcon icono = new ImageIcon("src/imagenes/FondoRegistro.png");
        Image img = icono.getImage().getScaledInstance(270, 360, Image.SCALE_SMOOTH);
        JLabel BorderCollieLabel = new JLabel(new ImageIcon(img));
        BorderCollieLabel.setBounds(250, 0, 270, 360);
        getContentPane().add(BorderCollieLabel);

        ComboTipo.addActionListener(e -> actualizarCamposSegunTipo());
        actualizarCamposSegunTipo();
    }

// [USER_METHODS_START]
    private void actualizarCamposSegunTipo() {
        boolean esDueno = ComboTipo.getSelectedItem().equals("Dueño");

        // Datos del dueño
        LabelCI.setVisible(esDueno);
        txtCI.setVisible(esDueno);
        LabelTelefono.setVisible(esDueno);
        txtTelefono.setVisible(esDueno);
        LabelEmail.setVisible(esDueno);
        txtEmail.setVisible(esDueno);

        // Dato del veterinario
        LabelEspecialidad.setVisible(!esDueno);
        txtEspecialidad.setVisible(!esDueno);
    }
// [USER_METHODS_END]
}
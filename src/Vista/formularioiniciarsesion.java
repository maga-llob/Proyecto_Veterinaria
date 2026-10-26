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

public class formularioiniciarsesion extends JFrame {

    private JPasswordField PasswordField;
    private JMenuBar menuBar;
    private JTextField UsuarioTextField;
    private JLabel BienvenidoText;
    private JLabel passwordText;
    private JLabel UsuarioText;
    private JButton BotonIngresar;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public formularioiniciarsesion() {
        setTitle("iniciar sesion");
        setSize(780, 410);
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

        // ---------- CREAR COMPONENTES ----------
        PasswordField = new JPasswordField();
        PasswordField.setFont(new Font("Georgia", Font.PLAIN, 14));

        menuBar = new JMenuBar();
        JMenu menu_1 = new JMenu("Registrarse");
        JMenuItem itemRegistrarse = new JMenuItem("Registrarse");

        // [USER_ACTION_START:itemRegistrarse]
itemRegistrarse.addActionListener(e -> {
            new FormularioRegistro().setVisible(true);
    dispose();
        });
        // [USER_ACTION_END:itemRegistrarse]

        menu_1.add(itemRegistrarse);
        menuBar.add(menu_1);

        UsuarioTextField = new JTextField();
        UsuarioTextField.setFont(new Font("Georgia", Font.PLAIN, 14));

        BienvenidoText = new JLabel("Bienvenido de nuevo");
        BienvenidoText.setFont(fuenteTitulo);

        passwordText = new JLabel("Password");
        passwordText.setFont(new Font("Georgia", Font.BOLD, 13));

        UsuarioText = new JLabel("Usuario");
        UsuarioText.setFont(new Font("Georgia", Font.BOLD, 13));

        BotonIngresar = new JButton("Ingresar");
        BotonIngresar.setFont(new Font("Georgia", Font.BOLD, 16));

        // ---------- POSICIONES ----------
        BienvenidoText.setBounds(100, 30, 360, 45);

        UsuarioText.setBounds(100, 120, 120, 20);
        UsuarioTextField.setBounds(100, 145, 300, 28);

        passwordText.setBounds(100, 190, 120, 20);
        PasswordField.setBounds(100, 215, 300, 28);

        BotonIngresar.setBounds(175, 280, 150, 40);

        // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar.setBackground(Beige);

        BienvenidoText.setForeground(Beige);
        UsuarioText.setForeground(Beige);
        passwordText.setForeground(Beige);

        UsuarioTextField.setBorder(BorderFactory.createLineBorder(Beige, 2));
        PasswordField.setBorder(BorderFactory.createLineBorder(Beige, 2));

        BotonIngresar.setBackground(Beige);
        BotonIngresar.setForeground(verdeOscuro);
        BotonIngresar.setOpaque(true);
        BotonIngresar.setBorderPainted(false);
        BotonIngresar.setFocusPainted(false);

        // [USER_ACTION_START:BotonIngresar]
BotonIngresar.addActionListener(e -> {
            String usuario = UsuarioTextField.getText().trim();
            String password = new String(PasswordField.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completá usuario y password",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

           try {
    dueno d = new DuenoDAO().iniciarSesion(usuario, password);
    if (d != null) {
        new FormularioDueno(d).setVisible(true);
        dispose();
        return;
    }

    veterinario v = new VeterinarioDAO().iniciarSesion(usuario, password);
if (v != null) {
    if (!"aprobado".equalsIgnoreCase(v.getEstado())) {
        String mensaje = "rechazado".equalsIgnoreCase(v.getEstado())
                ? "Tu solicitud de registro fue rechazada"
                : "Tu cuenta todavía está pendiente de aprobación";
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    new FormularioVeterinario(v).setVisible(true);
    dispose();
    return;
}

    JOptionPane.showMessageDialog(this, "Usuario o password incorrectos",
            "Error", JOptionPane.ERROR_MESSAGE);

} catch (SQLException ex) {
    JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
}
});
        // [USER_ACTION_END:BotonIngresar]

        // ---------- AGREGAR A LA VENTANA ----------
        getContentPane().add(PasswordField);
        setJMenuBar(menuBar);
        getContentPane().add(UsuarioTextField);
        getContentPane().add(BienvenidoText);
        getContentPane().add(passwordText);
        getContentPane().add(UsuarioText);
        getContentPane().add(BotonIngresar);

        // ---------- IMAGEN A LA DERECHA ----------
        ImageIcon icono = new ImageIcon("src/imagenes/fondo.png");
        Image img = icono.getImage().getScaledInstance(270, 360, Image.SCALE_SMOOTH);
        JLabel erizoLabel = new JLabel(new ImageIcon(img));
        erizoLabel.setBounds(480, 0, 270, 360);
        getContentPane().add(erizoLabel);
    }

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    public void actualizarTabla() {
        // TODO: recargar datos en la(s) tabla(s)
    }

    public void limpiarFormulario() {
        // TODO: limpiar campos del formulario
    }
// [USER_METHODS_END]

}
package Vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import java.io.File;
import java.sql.SQLException;
import Dominio.dueno;
import servidor.DAO.DuenoDAO;
import Dominio.mascota;
import java.util.List;
import servidor.DAO.MascotaDAO;
// [USER_IMPORTS_END]

public class FormularioPerfilDueno extends JFrame {

    private dueno usuario;

    private JMenuBar menuBar1;
    private JLabel LabelVariableNombre;
    private JLabel TelefonoLabel;
    private JLabel EmailLabel;
    private JLabel MascotaLabel;


// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioPerfilDueno(dueno usuario) {
        this.usuario = usuario;
        setTitle("FormularioPerfilDueno");
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


        menuBar1 = new JMenuBar();
        JMenu menu_1 = new JMenu("⌂ Inicio");
        JMenuItem itemInicio = new JMenuItem("⌂ Inicio");

        // [USER_ACTION_START:item_1_1]
itemInicio.addActionListener(e -> {
            new FormularioDueno(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_1_1]

        menu_1.add(itemInicio);
        menuBar1.add(menu_1);

       LabelVariableNombre = new JLabel("VariableNombre", SwingConstants.CENTER);
        LabelVariableNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
      

        TelefonoLabel = new JLabel("Telefono:");
        TelefonoLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        

        EmailLabel = new JLabel("Email:");
        EmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        

        MascotaLabel = new JLabel("Mascotas:");
        MascotaLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        

        LabelVariableNombre.setBounds(0, 165, 484, 25);
    MascotaLabel.setBounds(40, 225, 430, 20);
    TelefonoLabel.setBounds(40, 250, 430, 20);
    EmailLabel.setBounds(40, 275, 430, 20);

    // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setBackground(Beige);

        LabelVariableNombre.setForeground(Beige);
        TelefonoLabel.setForeground(Beige);
        EmailLabel.setForeground(Beige);
        MascotaLabel.setForeground(Beige);

        setJMenuBar(menuBar1);
        getContentPane().add(LabelVariableNombre);
        getContentPane().add(TelefonoLabel);
        getContentPane().add(EmailLabel);
        getContentPane().add(MascotaLabel);

        mostrarDatos();
    
    }

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    private void mostrarDatos() {
    LabelVariableNombre.setText(usuario.getNombre());
    TelefonoLabel.setText("Teléfono: " + usuario.getTelefono());
    EmailLabel.setText("Email: " + usuario.getEmail());

    try {
        List<mascota> lista = new MascotaDAO().listarPorDueno(usuario.getId_dueño());
        if (lista.isEmpty()) {
            MascotaLabel.setText("Mascotas: sin mascotas registradas");
        } else {
            StringBuilder nombres = new StringBuilder();
            for (mascota m : lista) {
                if (nombres.length() > 0) nombres.append(", ");
                nombres.append(m.getNombre());
            }
            MascotaLabel.setText("Mascotas: " + nombres);
        }
    } catch (SQLException ex) {
        MascotaLabel.setText("Mascotas: no se pudieron cargar");
    }
}
// [USER_METHODS_END]
}
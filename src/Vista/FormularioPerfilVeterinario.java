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
import Dominio.veterinario;

import java.util.List;
import servidor.DAO.MascotaDAO;
// [USER_IMPORTS_END]

public class FormularioPerfilVeterinario extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JLabel LabelVariableNombre;
    private JLabel EspecialidadLabel;


// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioPerfilVeterinario(veterinario usuario) {
        this.usuario = usuario;
        setTitle("FormularioPerfilVeterinaria");
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
            new FormularioVeterinario(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_1_1]

        menu_1.add(itemInicio);
        menuBar1.add(menu_1);

       LabelVariableNombre = new JLabel("VariableNombre", SwingConstants.CENTER);
        LabelVariableNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
      

       

        EspecialidadLabel = new JLabel("Especialidad:");
        EspecialidadLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        

        LabelVariableNombre.setBounds(0, 165, 484, 25);
    EspecialidadLabel.setBounds(40, 225, 430, 20);

    // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setBackground(Beige);

        LabelVariableNombre.setForeground(Beige);
        EspecialidadLabel.setForeground(Beige);

        setJMenuBar(menuBar1);
        getContentPane().add(LabelVariableNombre);
        getContentPane().add(EspecialidadLabel);

        mostrarDatos();
    
    }

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    private void mostrarDatos() {
    LabelVariableNombre.setText(usuario.getNombre());
    EspecialidadLabel.setText("Especialidad: " + usuario.getEspecialidad());
    }
        
    
}
// [USER_METHODS_END]

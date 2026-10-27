package Vista;

import javax.swing.*;

import Dominio.veterinario;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
// [USER_IMPORTS_START]
// import java.io.File;
import java.sql.SQLException;
import servidor.DAO.VeterinarioDAO;
// [USER_IMPORTS_END]

public class FormularioVeterinario extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JButton BotonRegistrarConsulta;
    private JButton BotonAgendarConsulta;
    private JLabel TextoVeterinariaMagic;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioVeterinario(veterinario usuario) {
        this.usuario = usuario;
        setTitle("Formulario Veterinario");
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
        JMenu menu_1 = new JMenu("Inicio");
        JMenuItem itemInicio = new JMenuItem("Inicio");

        // [USER_ACTION_START:itemInicio]
itemInicio.addActionListener(e -> {
            new FormularioVeterinario(usuario).setVisible(true);   
    dispose();   
        });
        // [USER_ACTION_END:itemInicio]

        menu_1.add(itemInicio);
        menuBar1.add(menu_1);

        JMenu menu_2 = new JMenu("Mascotas");
        JMenuItem itemMascotas = new JMenuItem("Mascotas");

        // [USER_ACTION_START:itemMascotas]
itemMascotas.addActionListener(e -> {
            new FormularioMascotasVeterinario(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:itemMascotas]

        menu_2.add(itemMascotas);
        menuBar1.add(menu_2);

        JMenu menu_3 = new JMenu("🕭");
        JMenuItem itemNotificaciones = new JMenuItem("🕭");

        // [USER_ACTION_START:itemNotificaciones]
itemNotificaciones.addActionListener(e -> {
            // TODO: accion para 🕭
        });
        // [USER_ACTION_END:itemNotificaciones]

        menu_3.add(itemNotificaciones);
        menuBar1.add(menu_3);

        JMenu menu_4 = new JMenu("perfil");
        JMenuItem itemPerfil = new JMenuItem("perfil");

        // [USER_ACTION_START:itemPerfil]
itemPerfil.addActionListener(e -> {
            new FormularioPerfilVeterinario(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:itemPerfil]

        menu_4.add(itemPerfil);
        menuBar1.add(menu_4);


        BotonRegistrarConsulta = new JButton("Registrar Consulta");
        BotonRegistrarConsulta.setFont(new Font("Georgia", Font.PLAIN, 11));

        // [USER_ACTION_START:BotonRegistrarConsulta]
BotonRegistrarConsulta.addActionListener(e -> {
            // TODO: accion de BotonRegistrarConsulta
        });
        // [USER_ACTION_END:BotonRegistrarConsulta]

        BotonRegistrarConsulta.setBounds(170, 144, 150, 27);

        BotonAgendarConsulta = new JButton("Agendar consulta");
        BotonAgendarConsulta.setFont(new Font("Georgia", Font.PLAIN, 11));

        // [USER_ACTION_START:BotonAgendarConsulta]
BotonAgendarConsulta.addActionListener(e -> {
            // TODO: accion de BotonAgendarConsulta
        });
        // [USER_ACTION_END:BotonAgendarConsulta]

        

        BotonAgendarConsulta.setBounds(170, 188, 150, 25);
        TextoVeterinariaMagic = new JLabel("Veterinaria Magic"); 

            TextoVeterinariaMagic.setFont(fuenteTitulo);
        TextoVeterinariaMagic.setBounds(120, 64, 500, 40);
        
        
    
        setJMenuBar(menuBar1);
        getContentPane().add(BotonRegistrarConsulta);
        getContentPane().add(BotonAgendarConsulta);
        getContentPane().add(TextoVeterinariaMagic); 

         //------------------------------------COLORES---------------------------------------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        TextoVeterinariaMagic.setForeground(Beige);



        BotonAgendarConsulta.setBackground(Beige);
         BotonAgendarConsulta.setForeground(verdeOscuro);
         BotonAgendarConsulta.setOpaque(true);
        BotonAgendarConsulta.setBorderPainted(false);
        BotonAgendarConsulta.setFocusPainted(false);

        BotonRegistrarConsulta.setBackground(Beige);
         BotonRegistrarConsulta.setForeground(verdeOscuro);
         BotonRegistrarConsulta.setOpaque(true);
        BotonRegistrarConsulta.setBorderPainted(false);
        BotonRegistrarConsulta.setFocusPainted(false);

        //Imagen de fondo-----------------------------
        ImageIcon icono = new ImageIcon("src/imagenes/ImagenFVeterinario.png");
        Image img = icono.getImage().getScaledInstance(270, 360, Image.SCALE_SMOOTH);
        JLabel pollitoLabel = new JLabel(new ImageIcon(img));
        pollitoLabel.setBounds(100, 0, 400, 360);
        getContentPane().add(pollitoLabel);
    }

    


// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    
// [USER_METHODS_END]
}

package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import java.io.File;
import java.sql.SQLException;
import Dominio.dueno;
import servidor.DAO.DuenoDAO;
// [USER_IMPORTS_END]

public class FormularioDueno extends JFrame {

  private dueno usuario;

    private JMenuBar menuBarInicio;
    private JButton BotonPerfil;
    private JButton BotonMisMascotas;
    private JLabel TextoVeterinariaMagic;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioDueno(dueno usuario) {
        this.usuario = usuario;
        

        setTitle("Formulario Dueño");
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


        menuBarInicio = new JMenuBar();
        JMenu menu_1 = new JMenu("⌂ inicio");
        JMenuItem itemInicio = new JMenuItem("⌂ inicio");

        // [USER_ACTION_START:itemInicio]
itemInicio.addActionListener(e -> {
    new FormularioDueno(usuario).setVisible(true);   
    dispose(); 
        });
        // [USER_ACTION_END:itemInicio]

        menu_1.add(itemInicio);
        menuBarInicio.add(menu_1);


        BotonPerfil = new JButton("Perfil");
        BotonPerfil.setFont(new Font("Georgia", Font.PLAIN, 11));

        // [USER_ACTION_START:BotonPerfil]
BotonPerfil.addActionListener(e -> {
            new FormularioPerfilDueno(usuario).setVisible(true);
           dispose();
        });
        // [USER_ACTION_END:BotonPerfil]

        BotonPerfil.setBounds(175, 154, 150, 25);

        BotonMisMascotas = new JButton("mis mascotas");
        BotonMisMascotas.setFont(new Font("Georgia", Font.PLAIN, 11));

        

        // [USER_ACTION_START:BotonMisMascotas]
BotonMisMascotas.addActionListener(e -> {
    new FormularioMisMascotasDueno(usuario).setVisible(true);
    dispose();
        });
        // [USER_ACTION_END:BotonMisMascotas]

        BotonMisMascotas.setBounds(175, 219, 150, 25);

        TextoVeterinariaMagic = new JLabel("Veterinaria Magic") {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       FontMetrics fm = g2.getFontMetrics(getFont());
int x = 3;
int y = fm.getAscent() + 3;

        // El texto convertido en una forma dibujable
        Shape forma = new java.awt.font.TextLayout(getText(), getFont(), g2.getFontRenderContext())
                .getOutline(java.awt.geom.AffineTransform.getTranslateInstance(x, y));

        // 1) Borde
        g2.setColor(new Color(0x687560));
        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(forma);

       
        g2.setColor(getForeground());
        g2.fill(forma);

        g2.dispose();
    }
};
        TextoVeterinariaMagic.setFont(fuenteTitulo);
        TextoVeterinariaMagic.setBounds(120, 64, 500, 40);

        setJMenuBar(menuBarInicio);
        getContentPane().add(BotonPerfil);
        getContentPane().add(BotonMisMascotas);
        getContentPane().add(TextoVeterinariaMagic);

        //------------------------------------COLORES---------------------------------------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
         menuBarInicio.setBackground(Beige);
         TextoVeterinariaMagic.setForeground(Beige);

         BotonPerfil.setBackground(Beige);
         BotonPerfil.setForeground(verdeOscuro);
         BotonPerfil.setOpaque(true);
        BotonPerfil.setBorderPainted(false);
        BotonPerfil.setFocusPainted(false);

         BotonMisMascotas.setBackground(Beige);
         BotonMisMascotas.setForeground(verdeOscuro);
         BotonMisMascotas.setOpaque(true);
        BotonMisMascotas.setBorderPainted(false);
        BotonMisMascotas.setFocusPainted(false);

        //Imagen de fondo-----------------------------
        ImageIcon icono = new ImageIcon("src/imagenes/FondoFDueno.png");
        Image img = icono.getImage().getScaledInstance(270, 360, Image.SCALE_SMOOTH);
        JLabel perroLabel = new JLabel(new ImageIcon(img));
        perroLabel.setBounds(100, 0, 400, 360);
        getContentPane().add(perroLabel);

    }

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
// [USER_METHODS_END]
}
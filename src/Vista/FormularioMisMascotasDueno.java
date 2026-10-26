package Vista;

import javax.swing.*;

import Dominio.dueno;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
// [USER_IMPORTS_START]
import java.sql.SQLException;
import java.util.List;
import Dominio.mascota;
import servidor.DAO.MascotaDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Dominio.consulta;
import Dominio.vacuna;
import servidor.DAO.ConsultaDAO;
import servidor.DAO.VacunaDAO;
// [USER_IMPORTS_END]

public class FormularioMisMascotasDueno extends JFrame {

    private dueno usuario;

    private JMenuBar menuBar1;
    private JComboBox ElegirMascotas;
    private JTextArea TextAreaMascota;
    private JLabel MisMascotasText;

// [USER_DECLS_START]

private List<mascota> mascotas;

// [USER_DECLS_END]

    public FormularioMisMascotasDueno(dueno usuario) {
        this.usuario = usuario;
        setTitle("FormularioMisMascotasDueno");
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
        JMenu menu_1 = new JMenu("⌂");
        JMenuItem item_1_1 = new JMenuItem("⌂");

        // [USER_ACTION_START:item_1_1]
item_1_1.addActionListener(e -> {
    new FormularioDueno(usuario).setVisible(true);
    dispose();
        });
        // [USER_ACTION_END:item_1_1]

        menu_1.add(item_1_1);
        menuBar1.add(menu_1);

        JMenu menu_2 = new JMenu("Perfil");
        JMenuItem item_2_1 = new JMenuItem("Perfil");

        // [USER_ACTION_START:item_2_1]
item_2_1.addActionListener(e -> {
            // TODO: accion para Perfil
        });
        // [USER_ACTION_END:item_2_1]

        menu_2.add(item_2_1);
        menuBar1.add(menu_2);


        ElegirMascotas = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        ElegirMascotas.setBounds(4, 76, 120, 22);

        TextAreaMascota = new JTextArea();
        TextAreaMascota.setFont(new Font("Tahoma", Font.PLAIN, 11));
        TextAreaMascota.setLineWrap(true);
        TextAreaMascota.setWrapStyleWord(true);
        JScrollPane scroll_TextAreaMascota = new JScrollPane(TextAreaMascota);
        scroll_TextAreaMascota.setBounds(6, 107, 481, 264);

        MisMascotasText = new JLabel("Mis Mascotas");
        MisMascotasText.setFont(fuenteTitulo);
        MisMascotasText.setBounds(140, 50, 800, 20);

        // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setBackground(Beige);

        MisMascotasText.setForeground(Beige);
        TextAreaMascota.setBackground(Beige);
        ElegirMascotas.setBackground(Beige);
        ElegirMascotas.setForeground(verdeOscuro);
        scroll_TextAreaMascota.setBackground(Beige);
        scroll_TextAreaMascota.setForeground(verdeOscuro);

        

        setJMenuBar(menuBar1);
        getContentPane().add(ElegirMascotas);
        getContentPane().add(scroll_TextAreaMascota);
        getContentPane().add(MisMascotasText);
        getContentPane().add(MisMascotasText);

        cargarMascotas();
        ElegirMascotas.addActionListener(ev -> mostrarDatos());   
        mostrarDatos();                                           
        TextAreaMascota.setEditable(false);                       
    }
    
    

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
   private void cargarMascotas() {
    ElegirMascotas.removeAllItems();   // saca los "Item 1, 2, 3" de ejemplo
    try {
        mascotas = new MascotaDAO().listarPorDueno(usuario.getId_dueño());

        for (mascota m : mascotas) {
            ElegirMascotas.addItem(m.getNombre());
        }

        if (mascotas.isEmpty()) {
            TextAreaMascota.setText("Todavía no tenés mascotas registradas.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar las mascotas: " + ex.getMessage());}
    }

    private void mostrarDatos() {
    int i = ElegirMascotas.getSelectedIndex();
    if (i < 0 || mascotas == null || i >= mascotas.size()) {
        return;   // no hay ninguna mascota elegida
    }
    mascota m = mascotas.get(i);

    try {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime ultima = null;
        LocalDateTime proxima = null;

        for (consulta c : new ConsultaDAO().listarPorMascota(m.getId_mascota())) {
            LocalDateTime f = c.getFecha_hora();
            if (f == null) continue;

            if (!f.isAfter(ahora)) {
                if (ultima == null || f.isAfter(ultima)) ultima = f;
            } else {
                if (proxima == null || f.isBefore(proxima)) proxima = f;
            }
        }

        // Junta los tipos de vacuna separados por coma
        StringBuilder vacunas = new StringBuilder();
        for (vacuna v : new VacunaDAO().listarPorMascota(m.getId_mascota())) {
            if (vacunas.length() > 0) vacunas.append(", ");
            vacunas.append(v.getTipo());
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String edad = m.getEdad() + (m.getEdad() == 1 ? " año" : " años");

                String peso = String.format("%.1f kg", m.getPeso());

        TextAreaMascota.setText(
                "Nombre: " + m.getNombre() + "\n"
              + "Edad: " + edad + "\n"
              + "Género: " + m.getSexo() + "\n"
              + "Peso: " + peso + "\n"
              + "Raza: " + m.getRaza() + "\n"
              + "Especie: " + m.getEspecie() + "\n"
              + "Última consulta: " + (ultima != null ? ultima.format(formato) : "Sin consultas") + "\n"
              + "Próxima consulta: " + (proxima != null ? proxima.format(formato) : "Sin agendar") + "\n"
              + "Vacunas: " + (vacunas.length() > 0 ? vacunas.toString() : "Sin vacunas"));
        TextAreaMascota.setCaretPosition(0);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar los datos: " + ex.getMessage());
    }

    }
// [USER_METHODS_END]
}

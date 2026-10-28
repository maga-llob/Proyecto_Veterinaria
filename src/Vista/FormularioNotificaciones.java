package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import Dominio.veterinario;
import Dominio.mascota;
import Dominio.consulta;
import Dominio.dueno;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import servidor.DAO.MascotaDAO;
import servidor.DAO.ConsultaDAO;
import servidor.DAO.DuenoDAO;
// [USER_IMPORTS_END]

public class FormularioNotificaciones extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JTextArea Notificacion1Label;
    private JTextArea Notificacion2Label;
    private JTextArea Notificacion3Label;
    private JTextArea Notificacion4Label;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioNotificaciones(veterinario usuario) {
        this.usuario = usuario;
        setTitle("FormularioNotificaciones");
        setSize(500, 410);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
    }

    private void initComponents() {
        menuBar1 = new JMenuBar();
        JMenu menu_1 = new JMenu("⌂ Inicio");
        JMenuItem item_1_1 = new JMenuItem("⌂ Inicio");

        // [USER_ACTION_START:item_1_1]
item_1_1.addActionListener(e -> {
            new FormularioVeterinario(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_1_1]

        menu_1.add(item_1_1);
        menuBar1.add(menu_1);

        JMenu menu_2 = new JMenu("Agendar Consulta");
        JMenuItem item_2_1 = new JMenuItem("Agendar Consulta");

        // [USER_ACTION_START:item_2_1]
item_2_1.addActionListener(e -> {
            new FormularioAgendarConsulta(usuario) .setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_2_1]

        menu_2.add(item_2_1);
        menuBar1.add(menu_2);

        JMenu menu_3 = new JMenu("Registrar Consulta");
        JMenuItem item_3_1 = new JMenuItem("Registrar Consulta");

        // [USER_ACTION_START:item_3_1]
item_3_1.addActionListener(e -> {
            new FormularioRegistrarConsulta(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_3_1]

        menu_3.add(item_3_1);
        menuBar1.add(menu_3);

        JMenu menu_4 = new JMenu("Perfil");
        JMenuItem item_4_1 = new JMenuItem("Perfil");

        // [USER_ACTION_START:item_4_1]
item_4_1.addActionListener(e -> {
            new FormularioPerfilVeterinario(usuario).setVisible(true);
            dispose(); 
        });
        // [USER_ACTION_END:item_4_1]

        menu_4.add(item_4_1);
        menuBar1.add(menu_4);


        Notificacion1Label = new JTextArea();
        Notificacion1Label.setFont(new Font("Tahoma", Font.PLAIN, 11));
        Notificacion1Label.setLineWrap(true);
        Notificacion1Label.setWrapStyleWord(true);
        JScrollPane scroll_Notificacion1Label = new JScrollPane(Notificacion1Label);
        scroll_Notificacion1Label.setBounds(10, 40, 480, 75);

        Notificacion2Label = new JTextArea();
        Notificacion2Label.setFont(new Font("Tahoma", Font.PLAIN, 11));
        Notificacion2Label.setLineWrap(true);
        Notificacion2Label.setWrapStyleWord(true);
        JScrollPane scroll_Notificacion2Label = new JScrollPane(Notificacion2Label);
        scroll_Notificacion2Label.setBounds(10, 120, 480, 75);

        Notificacion3Label = new JTextArea();
        Notificacion3Label.setFont(new Font("Tahoma", Font.PLAIN, 11));
        Notificacion3Label.setLineWrap(true);
        Notificacion3Label.setWrapStyleWord(true);
        JScrollPane scroll_Notificacion3Label = new JScrollPane(Notificacion3Label);
        scroll_Notificacion3Label.setBounds(10, 200, 480, 75);

        Notificacion4Label = new JTextArea();
        Notificacion4Label.setFont(new Font("Tahoma", Font.PLAIN, 11));
        Notificacion4Label.setLineWrap(true);
        Notificacion4Label.setWrapStyleWord(true);
        JScrollPane scroll_Notificacion4Label = new JScrollPane(Notificacion4Label);
        scroll_Notificacion4Label.setBounds(10, 280, 480, 75);


        //------------------------------------COLORES---------------------------------------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setForeground(Beige);
        scroll_Notificacion1Label.getViewport().setBackground(verdeFondo);
        scroll_Notificacion2Label.getViewport().setBackground(verdeFondo);
        scroll_Notificacion3Label.getViewport().setBackground(verdeFondo);
        scroll_Notificacion4Label.getViewport().setBackground(verdeFondo);
        scroll_Notificacion1Label.setBorder(BorderFactory.createLineBorder(Beige, 2));
        scroll_Notificacion2Label.setBorder(BorderFactory.createLineBorder(Beige, 2));
        scroll_Notificacion3Label.setBorder(BorderFactory.createLineBorder(Beige, 2));
        scroll_Notificacion4Label.setBorder(BorderFactory.createLineBorder(Beige, 2));
        scroll_Notificacion1Label.setForeground(Beige);
        scroll_Notificacion2Label.setForeground(Beige);
        scroll_Notificacion3Label.setForeground(Beige);
        scroll_Notificacion4Label.setForeground(Beige);
        scroll_Notificacion1Label.getVerticalScrollBar().setBackground(verdeFondo);
        scroll_Notificacion2Label.getVerticalScrollBar().setBackground(verdeFondo);
        scroll_Notificacion3Label.getVerticalScrollBar().setBackground(verdeFondo);
        scroll_Notificacion4Label.getVerticalScrollBar().setBackground(verdeFondo);
        scroll_Notificacion1Label.getVerticalScrollBar().setForeground(Beige);
        scroll_Notificacion2Label.getVerticalScrollBar().setForeground(Beige);
        scroll_Notificacion3Label.getVerticalScrollBar().setForeground(Beige);
        scroll_Notificacion4Label.getVerticalScrollBar().setForeground(Beige);


        setJMenuBar(menuBar1);
        getContentPane().add(scroll_Notificacion1Label);
        getContentPane().add(scroll_Notificacion2Label);
        getContentPane().add(scroll_Notificacion3Label);
        getContentPane().add(scroll_Notificacion4Label);
                getContentPane().add(scroll_Notificacion4Label);

        cargarNotificaciones();
    
    }

// [USER_METHODS_START]

private void cargarNotificaciones() {
    JTextArea[] areas = { Notificacion1Label, Notificacion2Label, Notificacion3Label, Notificacion4Label };

    // Todos vacíos y de solo lectura
    for (JTextArea area : areas) {
        area.setText("");
        area.setEditable(false);
    }

    try {
        // Diccionarios para pasar de id a datos
        Map<Integer, mascota> mascotas = new HashMap<>();
        for (mascota m : new MascotaDAO().listar()) {
            mascotas.put(m.getId_mascota(), m);
        }

        Map<Integer, String> duenos = new HashMap<>();
        for (dueno d : new DuenoDAO().listar()) {
            duenos.put(d.getId_dueño(), d.getNombre());
        }

        // Consultas de ESTE veterinario que todavía no pasaron
        LocalDateTime ahora = LocalDateTime.now();
        List<consulta> proximas = new ArrayList<>();
        for (consulta c : new ConsultaDAO().listarPorVeterinario(usuario.getId_veterinario())) {
            if (c.getFecha_hora() != null && c.getFecha_hora().isAfter(ahora)) {
                proximas.add(c);
            }
        }

        // La más cercana primero
        proximas.sort((x, y) -> x.getFecha_hora().compareTo(y.getFecha_hora()));

        if (proximas.isEmpty()) {
            Notificacion1Label.setText("No tenés consultas agendadas próximamente.");
            return;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (int i = 0; i < areas.length && i < proximas.size(); i++) {
            consulta c = proximas.get(i);
            mascota m = mascotas.get(c.getId_mascota());

            String nombreMascota = (m != null) ? m.getNombre() + " (" + m.getEspecie() + ")" : "Mascota desconocida";
            String nombreDueno = (m != null) ? duenos.getOrDefault(m.getId_dueno(), "Sin dueño") : "-";

            areas[i].setText(
                    "Fecha: " + c.getFecha_hora().format(formato) + "\n"
                  + "Mascota: " + nombreMascota + "\n"
                  + "Dueño: " + nombreDueno + "\n"
                  + "Motivo: " + c.getProcedimiento());
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar las notificaciones: " + ex.getMessage());
    }
}

   
// [USER_METHODS_END]
}
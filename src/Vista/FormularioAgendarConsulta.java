package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.ZoneId;
import Dominio.veterinario;
import Dominio.mascota;
import Dominio.consulta;
import Dominio.dueno;
import servidor.DAO.MascotaDAO;
import servidor.DAO.ConsultaDAO;
import java.util.HashMap;
import java.util.Map;
import Dominio.dueno;
import servidor.DAO.DuenoDAO;
// [USER_IMPORTS_END]
import servidor.DAO.DuenoDAO;

public class FormularioAgendarConsulta extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JLabel LabelFecha;
    private JComboBox<String> cmb12;
    private JLabel LabelMascota;
    private JSpinner spFechaHora;
    private JButton BotonAgendar;

// [USER_DECLS_START]
    private List<mascota> mascotas;
// [USER_DECLS_END]

    public FormularioAgendarConsulta(veterinario usuario) {
        this.usuario = usuario;
        setTitle("FormularioAgendarConsulta");
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

        JMenu menu_2 = new JMenu("Registrar Consulta");
        JMenuItem item_2_1 = new JMenuItem("Registrar Consulta");

        // [USER_ACTION_START:item_2_1]
item_2_1.addActionListener(e -> {
            new FormularioRegistrarConsulta(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_2_1]

        menu_2.add(item_2_1);
        menuBar1.add(menu_2);

        LabelFecha = new JLabel("Para cuando?");
        LabelFecha.setFont(new Font("Tahoma", Font.PLAIN, 11));
        LabelFecha.setBounds(21, 50, 80, 20);

        spFechaHora = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        spFechaHora.setEditor(new JSpinner.DateEditor(spFechaHora, "dd/MM/yyyy HH:mm"));
        spFechaHora.setBounds(21, 75, 150, 25);

        LabelMascota = new JLabel("Para quien?");
        LabelMascota.setFont(new Font("Tahoma", Font.PLAIN, 11));
        LabelMascota.setBounds(20, 233, 80, 20);

        cmb12 = new JComboBox<>();
        cmb12.setBounds(17, 255, 452, 24);

        BotonAgendar = new JButton("Agendar");
        BotonAgendar.setFont(new Font("Georgia", Font.PLAIN, 11));
        BotonAgendar.setBounds(195, 310, 100, 25);

        // [USER_ACTION_START:BotonAgendar]
BotonAgendar.addActionListener(e -> agendar());
        // [USER_ACTION_END:BotonAgendar]

        //------------------------------------COLORES---------------------------------------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setForeground(Beige);
        LabelFecha.setForeground(Beige);
        LabelMascota.setForeground(Beige);
        cmb12.setBackground(verdeFondo);
        cmb12.setForeground(Beige);
        BotonAgendar.setBackground(Beige);
         BotonAgendar.setForeground(verdeOscuro);
         BotonAgendar.setOpaque(true);
        BotonAgendar.setBorderPainted(false);
        BotonAgendar.setFocusPainted(false);

        setJMenuBar(menuBar1);
        getContentPane().add(LabelFecha);
        getContentPane().add(spFechaHora);
        getContentPane().add(LabelMascota);
        getContentPane().add(cmb12);
        getContentPane().add(BotonAgendar);

        cargarMascotas();
    }

// [USER_METHODS_START]
    private void cargarMascotas() {
    cmb12.removeAllItems();
    try {
        mascotas = new MascotaDAO().listar();

        // id del dueño -> nombre del dueño
        Map<Integer, String> nombresDuenos = new HashMap<>();
        for (dueno d : new DuenoDAO().listar()) {
            nombresDuenos.put(d.getId_dueño(), d.getNombre());
        }

        for (mascota m : mascotas) {
            String nombreDueno = nombresDuenos.getOrDefault(m.getId_dueno(), "sin dueño");
            cmb12.addItem(m.getNombre() + " (#" + m.getId_mascota() + ")"
                    + " - " + m.getEspecie() + ", " + m.getRaza()
                    + " - " + m.getEdad() + (m.getEdad() == 1 ? " año" : " años")
                    + " - Dueño: " + nombreDueno);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar las mascotas: " + ex.getMessage());
    }
}
    private void agendar() {
        int i = cmb12.getSelectedIndex();
        if (i < 0 || mascotas == null || i >= mascotas.size()) {
            JOptionPane.showMessageDialog(this, "Elegí una mascota",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMascota = mascotas.get(i).getId_mascota();

        Date d = (Date) spFechaHora.getValue();
        LocalDateTime fechaHora = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (fechaHora.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(this, "La fecha tiene que ser futura",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            consulta c = new consulta(0, "Agendada", fechaHora, idMascota, usuario.getId_veterinario());
            int id = new ConsultaDAO().guardar(c);
            if (id == -1) {
                JOptionPane.showMessageDialog(this, "No se pudo agendar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Consulta agendada");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
        }
    }
// [USER_METHODS_END]
}
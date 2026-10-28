package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

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

public class FormularioRegistrarConsulta extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JTextArea txtArea15;
    private JLabel LabelTitulo;
    private JLabel LabelProcedimiento;
    private JSpinner spinner18;
    private JLabel labelDateTime;
    private JComboBox<String> cmb21;
    private JLabel labelMascota_dueno;
    private JButton BotonRegistrar;

// [USER_DECLS_START]
    
    private static final String AGENDADA = "Agendada";
    private List<consulta> pendientes = new ArrayList<>();
// [USER_DECLS_END]

    public FormularioRegistrarConsulta(veterinario usuario) {
        this.usuario = usuario;
        setTitle("FormularioRegistrarConsulta");
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
            new FormularioAgendarConsulta(usuario).setVisible(true);
            dispose();
        });
        // [USER_ACTION_END:item_2_1]

        menu_2.add(item_2_1);
        menuBar1.add(menu_2);

        LabelTitulo = new JLabel("Registrar Consulta");
        LabelTitulo.setFont(fuenteTitulo);
        LabelTitulo.setBounds(120, 40, 500, 50);

        labelMascota_dueno = new JLabel("A quien?");
        labelMascota_dueno.setFont(new Font("Tahoma", Font.PLAIN, 11));
        labelMascota_dueno.setBounds(15, 72, 80, 20);

        cmb21 = new JComboBox<>();
        cmb21.setBounds(15, 94, 455, 22);

        labelDateTime = new JLabel("Fecha/Hora en que se hizo:");
        labelDateTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
        labelDateTime.setBounds(15, 130, 180, 20);

        spinner18 = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        spinner18.setEditor(new JSpinner.DateEditor(spinner18, "dd/MM/yyyy HH:mm"));
        spinner18.setBounds(15, 152, 150, 25);

        LabelProcedimiento = new JLabel("Indicar Procedimiento:");
        LabelProcedimiento.setFont(new Font("Tahoma", Font.PLAIN, 11));
        LabelProcedimiento.setBounds(15, 190, 150, 21);

        txtArea15 = new JTextArea();
        txtArea15.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtArea15.setLineWrap(true);
        txtArea15.setWrapStyleWord(true);
        JScrollPane scroll_txtArea15 = new JScrollPane(txtArea15);
        scroll_txtArea15.setBounds(15, 212, 455, 80);

        BotonRegistrar = new JButton("Registrar");
        BotonRegistrar.setFont(new Font("Georgia", Font.PLAIN, 11));
        BotonRegistrar.setBounds(195, 305, 100, 25);

        // [USER_ACTION_START:BotonRegistrar]
BotonRegistrar.addActionListener(e -> registrar());
        // [USER_ACTION_END:BotonRegistrar]

        //------------------------------------COLORES---------------------------------------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setForeground(Beige);
        LabelTitulo.setForeground(Beige);
        labelMascota_dueno.setForeground(Beige);
        labelDateTime.setForeground(Beige);
        LabelProcedimiento.setForeground(Beige);
        cmb21.setBackground(verdeFondo);
        cmb21.setForeground(Beige);
        spinner18.setBackground(verdeFondo);
        spinner18.setForeground(Beige);
        txtArea15.setBackground(verdeFondo);
        txtArea15.setForeground(Beige);
        BotonRegistrar.setBackground(Beige);
         BotonRegistrar.setForeground(verdeOscuro);
         BotonRegistrar.setOpaque(true);
        BotonRegistrar.setBorderPainted(false);
        BotonRegistrar.setFocusPainted(false);
        


        setJMenuBar(menuBar1);
        getContentPane().add(LabelTitulo);
        getContentPane().add(labelMascota_dueno);
        getContentPane().add(cmb21);
        getContentPane().add(labelDateTime);
        getContentPane().add(spinner18);
        getContentPane().add(LabelProcedimiento);
        getContentPane().add(scroll_txtArea15);
        getContentPane().add(BotonRegistrar);

        cargarPendientes();
        cmb21.addActionListener(e -> sugerirFecha());
        sugerirFecha();
    }

// [USER_METHODS_START]
    // Llena el combo con las consultas agendadas de este veterinario (la más próxima primero)
    private void cargarPendientes() {
        cmb21.removeAllItems();
        pendientes.clear();
        try {
            Map<Integer, mascota> mascotas = new HashMap<>();
            for (mascota m : new MascotaDAO().listar()) {
                mascotas.put(m.getId_mascota(), m);
            }
            Map<Integer, String> duenos = new HashMap<>();
            for (dueno d : new DuenoDAO().listar()) {
                duenos.put(d.getId_dueño(), d.getNombre());
            }

            for (consulta c : new ConsultaDAO().listarPorVeterinario(usuario.getId_veterinario())) {
                if (AGENDADA.equals(c.getProcedimiento()) && c.getFecha_hora() != null) {
                    pendientes.add(c);
                }
            }
            pendientes.sort((a, b) -> a.getFecha_hora().compareTo(b.getFecha_hora()));

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (consulta c : pendientes) {
                mascota m = mascotas.get(c.getId_mascota());
                String nombreMascota = m != null ? m.getNombre() : "mascota #" + c.getId_mascota();
                String nombreDueno = m != null ? duenos.getOrDefault(m.getId_dueno(), "sin dueño") : "-";
                cmb21.addItem(c.getFecha_hora().format(formato) + " - " + nombreMascota
                        + " (Dueño: " + nombreDueno + ")");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar las consultas: " + ex.getMessage());
        }
    }

    
    private void sugerirFecha() {
        int i = cmb21.getSelectedIndex();
        if (i < 0 || i >= pendientes.size()) {
            return;
        }
        LocalDateTime programada = pendientes.get(i).getFecha_hora();
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime sugerida = programada.isAfter(ahora) ? ahora : programada;
        spinner18.setValue(Date.from(sugerida.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void registrar() {
        int i = cmb21.getSelectedIndex();
        if (i < 0 || i >= pendientes.size()) {
            JOptionPane.showMessageDialog(this, "No hay ninguna consulta agendada para registrar",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String procedimiento = txtArea15.getText().trim();
        if (procedimiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indicá el procedimiento realizado",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date d = (Date) spinner18.getValue();
        LocalDateTime fechaHora = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (fechaHora.isAfter(LocalDateTime.now().plusMinutes(1))) {
            JOptionPane.showMessageDialog(this, "La consulta ya tiene que haber ocurrido (fecha no futura)",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        consulta original = pendientes.get(i);
        consulta completa = new consulta(original.getId_consulta(), procedimiento, fechaHora,
                original.getId_mascota(), original.getId_veterinario());

        try {
            if (new ConsultaDAO().actualizar(completa)) {
                JOptionPane.showMessageDialog(this, "Consulta registrada");
                txtArea15.setText("");
                cargarPendientes();
                sugerirFecha();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
        }
    }
// [USER_METHODS_END]
}
package Vista;

import javax.swing.*;

import Dominio.veterinario;

import java.awt.*;
import java.awt.event.*;

// [USER_IMPORTS_START]
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import Dominio.dueno;
import Dominio.mascota;
import servidor.DAO.DuenoDAO;
import servidor.DAO.MascotaDAO;
// [USER_IMPORTS_END]

public class FormularioMascotasVeterinario extends JFrame {

    private veterinario usuario;

    private JMenuBar menuBar1;
    private JList<String> ListaMascotas;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public FormularioMascotasVeterinario(veterinario usuario) {
        this.usuario = usuario;
        setTitle("FormularioMascotasVeterinario");
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


        ListaMascotas = new JList<>(new String[]{"Item 1", "Item 2", "Item 3"});
        JScrollPane scroll_ListaMascotas = new JScrollPane(ListaMascotas);
        scroll_ListaMascotas.setBounds(6, 35, 483, 334);

        // ---------- COLORES ----------
        Color Beige       = new Color(0xE5D5BF);
        Color verdeOscuro = new Color(0x354C2B);
        Color verdeFondo  = new Color(0x687560);

        getContentPane().setBackground(verdeFondo);
        menuBar1.setBackground(Beige);
        scroll_ListaMascotas.getViewport().setBackground(verdeFondo);
        scroll_ListaMascotas.setBorder(BorderFactory.createLineBorder(Beige, 2));
        scroll_ListaMascotas.setForeground(Beige);
        scroll_ListaMascotas.getVerticalScrollBar().setBackground(verdeFondo);
        scroll_ListaMascotas.getVerticalScrollBar().setForeground(Beige);

        ListaMascotas.setBackground(verdeFondo);
    ListaMascotas.setForeground(Beige);
    ListaMascotas.setFont(new Font("Georgia", Font.PLAIN, 14));

    // Cuando el veterinario selecciona una fila
    ListaMascotas.setSelectionBackground(Beige);
    ListaMascotas.setSelectionForeground(verdeOscuro);
        

        setJMenuBar(menuBar1);
        getContentPane().add(scroll_ListaMascotas);
         

        cargarMascotas();
    
    }

// [USER_METHODS_START]
private void cargarMascotas() {
    try {
        // Diccionario: id del dueño -> nombre del dueño
        Map<Integer, String> nombreDueno = new HashMap<>();
        for (dueno d : new DuenoDAO().listar()) {
            nombreDueno.put(d.getId_dueño(), d.getNombre());
        }

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (mascota m : new MascotaDAO().listar()) {
            String edad = m.getEdad() + (m.getEdad() == 1 ? " año" : " años");
            modelo.addElement(m.getNombre() + " - " + m.getEspecie() + ", " + m.getRaza()
                    + ", " + edad
                    + "   |   Dueño: " + nombreDueno.getOrDefault(m.getId_dueno(), "Sin dueño"));
        }
        ListaMascotas.setModel(modelo);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar las mascotas: " + ex.getMessage());
    }
}
// [USER_METHODS_END]
}

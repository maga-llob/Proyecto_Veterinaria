package servidor;

import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Prueba de conexion: abre la base de datos, ejecuta un SELECT
 * sobre la tabla dueño y muestra los registros encontrados.
 *
 * Requisitos: MySQL (XAMPP) iniciado, la base importada y el jar del
 * conector dentro de la carpeta lib/ del proyecto.
 */
public class PruebaConexion {

    public static void main(String[] args) {
        String sql = "SELECT * FROM `dueño`";

        // try-with-resources: cierra solo la conexion, el Statement y el ResultSet
        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Conexion OK\n");
            System.out.println("Registros en la tabla dueño:\n");

            int contador = 0;
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_dueño")
                        + " | Nombre: " + rs.getString("nombre")
                        + " | CI: " + rs.getString("CI")
                        + " | Telefono: " + rs.getString("Telefono")
                        + " | Email: " + rs.getString("Email"));
                contador++;
            }

            if (contador == 0) {
                System.out.println("(La tabla dueño esta vacia)");
            } else {
                System.out.println("\nTotal: " + contador + " dueño(s)");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Revisa que MySQL (XAMPP) este iniciado y que el nombre de la base en Conexion.java sea el correcto");
            e.printStackTrace();
        }
    }
}
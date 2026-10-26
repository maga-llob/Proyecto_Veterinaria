package servidor.DAO;

import Dominio.dueno;
import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad dueno.
 */
public class DuenoDAO {

    // Devuelve el id generado, o -1 si no se pudo insertar.
    public int guardar(dueno dueno) throws SQLException {
        // El id es AUTO_INCREMENT: no se incluye en el INSERT.
        String sql = "INSERT INTO `dueño` (nombre, CI, Telefono, Email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dueno.getNombre());
            ps.setInt(2, dueno.getCI());
            ps.setInt(3, dueno.getTelefono());
            ps.setString(4, dueno.getEmail());
            ps.setString(5, dueno.getPassword());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return -1;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }

    public List<dueno> listar() throws SQLException {
    String sql = "SELECT `id_dueño`, nombre, CI, Telefono, Email FROM `dueño` ORDER BY nombre";
    List<dueno> lista = new ArrayList<>();

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            lista.add(new dueno(
                    rs.getInt("id_dueño"),
                    rs.getString("nombre"),
                    rs.getInt("CI"),
                    rs.getInt("Telefono"),
                    rs.getString("Email"),
                    null));
        }
    }
    return lista;
}
    

    public boolean actualizar(dueno dueno) throws SQLException {
    String sql = "UPDATE `dueño` SET nombre = ?, CI = ?, Telefono = ?, Email = ? WHERE `id_dueño` = ?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, dueno.getNombre());
        ps.setInt(2, dueno.getCI());
        ps.setInt(3, dueno.getTelefono());
        ps.setString(4, dueno.getEmail());
        ps.setInt(5, dueno.getId_dueño());

        return ps.executeUpdate() > 0;
    }
}
        
    

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM `dueño` WHERE `id_dueño` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

public dueno iniciarSesion(String nombre, String password) throws SQLException {
    String sql = "SELECT `id_dueño`, nombre, CI, Telefono, Email FROM `dueño` WHERE nombre = ? AND password = ?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre);
        ps.setString(2, password);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new dueno(
                        rs.getInt("id_dueño"),
                        rs.getString("nombre"),
                        rs.getInt("CI"),
                        rs.getInt("Telefono"),
                        rs.getString("Email"),
                        null);
            }
        }
    }
    return null;
}
public boolean existeNombre(String nombre) throws SQLException {
    String sql = "SELECT COUNT(*) FROM `dueño` WHERE nombre = ?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
}
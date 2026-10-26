package servidor.DAO;

import Dominio.veterinario;
import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Veterinario.
 */
public class VeterinarioDAO {

    public int guardar(veterinario veterinario) throws SQLException {
        String sql = "INSERT INTO `veterinario` (nombre, password, especialidad, estado) VALUES (?, ?, ?, 'Pendiente')";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, veterinario.getNombre());
            ps.setString(2, veterinario.getPassword());
            ps.setString(3, veterinario.getEspecialidad());

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

    public List<veterinario> listar() throws SQLException {
        String sql = "SELECT `id_veterinario`, nombre, especialidad FROM `veterinario` ORDER BY nombre";
        List<veterinario> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new veterinario(
                        rs.getInt("id_veterinario"),
                        rs.getString("nombre"),
                        null,
                        rs.getString("especialidad")));
            }
        }
        return lista;
    }

    public boolean actualizar(veterinario veterinario) throws SQLException {
        String sql = "UPDATE `veterinario` SET nombre = ?, especialidad = ? WHERE `id_veterinario` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, veterinario.getNombre());
            ps.setString(2, veterinario.getEspecialidad());
            ps.setInt(3, veterinario.getId_veterinario());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM `veterinario` WHERE `id_veterinario` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public veterinario iniciarSesion(String nombre, String password) throws SQLException {
    String sql = "SELECT `id_veterinario`, nombre, especialidad, estado FROM `veterinario` WHERE nombre = ? AND password = ?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre);
        ps.setString(2, password);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                veterinario v = new veterinario(
                        rs.getInt("id_veterinario"),
                        rs.getString("nombre"),
                        null,
                        rs.getString("especialidad"));
                v.setEstado(rs.getString("estado"));
                return v;
            }
        }
    }
    return null;
}
    
    public boolean existeNombre(String nombre) throws SQLException {
    String sql = "SELECT COUNT(*) FROM `veterinario` WHERE nombre = ?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
}
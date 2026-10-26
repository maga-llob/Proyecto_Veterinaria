package servidor.DAO;

import Dominio.consulta;
import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad consulta.
 */
public class ConsultaDAO {

    private static final String COLUMNAS =
            "`id_consulta`, `procedimiento`, `fecha_hora`, `id_mascota`, `id_veterinario`";

    private consulta armar(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("fecha_hora");
        return new consulta(
                rs.getInt("id_consulta"),
                rs.getString("procedimiento"),
                ts != null ? ts.toLocalDateTime() : null,
                rs.getInt("id_mascota"),
                rs.getInt("id_veterinario"));
    }

    // Devuelve el id generado, o -1 si no se pudo insertar.
    public int guardar(consulta c) throws SQLException {
        String sql = "INSERT INTO `consulta` (`procedimiento`, `fecha_hora`, `id_mascota`, `id_veterinario`) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getProcedimiento());
            ps.setTimestamp(2, c.getFecha_hora() != null ? Timestamp.valueOf(c.getFecha_hora()) : null);
            ps.setInt(3, c.getId_mascota());
            ps.setInt(4, c.getId_veterinario());

            if (ps.executeUpdate() == 0) {
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

    // Historial de una mascota (la más reciente primero)
    public List<consulta> listarPorMascota(int idMascota) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `consulta` WHERE `id_mascota` = ? ORDER BY `fecha_hora` DESC";
        List<consulta> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(armar(rs));
                }
            }
        }
        return lista;
    }

    // Consultas que atendió un veterinario
    public List<consulta> listarPorVeterinario(int idVeterinario) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `consulta` WHERE `id_veterinario` = ? ORDER BY `fecha_hora` DESC";
        List<consulta> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idVeterinario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(armar(rs));
                }
            }
        }
        return lista;
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM `consulta` WHERE `id_consulta` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
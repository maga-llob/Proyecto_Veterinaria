package servidor.DAO;

import Dominio.vacuna;
import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad vacuna.
 */
public class VacunaDAO {

    private static final String COLUMNAS =
            "`id_vacuna`, `tipo`, `fecha_hora`, `vencimiento`, `id_mascota`, `id_consulta`";

    private vacuna armar(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("fecha_hora");
        return new vacuna(
                rs.getInt("id_vacuna"),
                rs.getString("tipo"),
                ts != null ? ts.toLocalDateTime() : null,
                rs.getDate("vencimiento"),
                rs.getInt("id_mascota"),
                rs.getInt("id_consulta"));
    }

    // Devuelve el id generado, o -1 si no se pudo insertar.
    public int guardar(vacuna v) throws SQLException {
        String sql = "INSERT INTO `vacuna` (`tipo`, `fecha_hora`, `vencimiento`, `id_mascota`, `id_consulta`) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, v.getFecha_vacuna() != null ? Timestamp.valueOf(v.getFecha_vacuna()) : null);
            ps.setDate(3, v.getVencimiento() != null ? new java.sql.Date(v.getVencimiento().getTime()) : null);
            ps.setInt(4, v.getId_mascota());

            // Si la vacuna no está asociada a una consulta, se guarda como NULL
            if (v.getId_consulta() > 0) {
                ps.setInt(5, v.getId_consulta());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

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

    // Todas las vacunas de una mascota (la más reciente primero)
    public List<vacuna> listarPorMascota(int idMascota) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `vacuna` WHERE `id_mascota` = ? ORDER BY `fecha_hora` DESC";
        List<vacuna> lista = new ArrayList<>();

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

    // Vacunas aplicadas en una consulta
    public List<vacuna> listarPorConsulta(int idConsulta) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `vacuna` WHERE `id_consulta` = ? ORDER BY `fecha_hora` DESC";
        List<vacuna> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(armar(rs));
                }
            }
        }
        return lista;
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM `vacuna` WHERE `id_vacuna` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
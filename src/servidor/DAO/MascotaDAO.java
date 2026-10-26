package servidor.DAO;

import Dominio.mascota;
import servidor.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad mascota.
 */
public class MascotaDAO {

    private static final String COLUMNAS =
            "`id_mascota`, `nombre`, `edad`, `especie`, `raza`, `sexo`, `peso`, `id_dueño`";

    // Arma un objeto mascota con la fila actual del ResultSet
    private mascota armar(ResultSet rs) throws SQLException {
        return new mascota(
                rs.getString("nombre"),
                rs.getInt("edad"),
                rs.getString("especie"),
                rs.getString("raza"),
                rs.getString("sexo"),
                rs.getDouble("peso"),
                rs.getInt("id_mascota"),
                rs.getInt("id_dueño"));
    }

    // Devuelve el id generado, o -1 si no se pudo insertar.
    public int guardar(mascota m) throws SQLException {
        String sql = "INSERT INTO `mascota` (`nombre`, `edad`, `especie`, `raza`, `sexo`, `peso`, `id_dueño`) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getEdad());
            ps.setString(3, m.getEspecie());
            ps.setString(4, m.getRaza());
            ps.setString(5, m.getSexo());
            ps.setDouble(6, m.getPeso());
            ps.setInt(7, m.getId_dueno());

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

    // Todas las mascotas (para el veterinario)
    public List<mascota> listar() throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `mascota` ORDER BY `nombre`";
        List<mascota> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(armar(rs));
            }
        }
        return lista;
    }

    // Solo las mascotas de un dueño (para "mis mascotas")
    public List<mascota> listarPorDueno(int idDueno) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM `mascota` WHERE `id_dueño` = ? ORDER BY `nombre`";
        List<mascota> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idDueno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(armar(rs));
                }
            }
        }
        return lista;
    }

    public boolean actualizar(mascota m) throws SQLException {
        String sql = "UPDATE `mascota` SET `nombre` = ?, `edad` = ?, `especie` = ?, `raza` = ?, "
                   + "`sexo` = ?, `peso` = ? WHERE `id_mascota` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getEdad());
            ps.setString(3, m.getEspecie());
            ps.setString(4, m.getRaza());
            ps.setString(5, m.getSexo());
            ps.setDouble(6, m.getPeso());
            ps.setInt(7, m.getId_mascota());

            return ps.executeUpdate() > 0;
        }
    }

    // Cambia una mascota de dueño
    public boolean asignarDueno(int idMascota, int idDueno) throws SQLException {
        String sql = "UPDATE `mascota` SET `id_dueño` = ? WHERE `id_mascota` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idDueno);
            ps.setInt(2, idMascota);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM `mascota` WHERE `id_mascota` = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
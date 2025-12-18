package MODELS.DAO;
import MODELS.CLASS.CategoriaTarefa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaTarefaDAO {
    // ---- SELECT (one) ----
    public CategoriaTarefa getCategoriaTarefaById(long id) {
        String sql = "SELECT * FROM CategoriaTarefa WHERE IdTarefa = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CategoriaTarefa ct = new CategoriaTarefa();
                ct.setIdCategoriaTarefa(rs.getInt("IdTarefa"));
                ct.setNome(rs.getString("Nome"));
                return ct;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    
    // ---- INSERT ----
    public long insertCategoriaTarefa(CategoriaTarefa ct) {
        String SQL = "INSERT INTO CategoriaTarefa (Nome) VALUES (?)";
        int generatedId = -1;
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ct.getNome());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        ct.setIdCategoriaTarefa(generatedId);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return generatedId;
    }
    
    // ---- UPDATE ----
    public boolean updateCategoriaTarefa(CategoriaTarefa ct) {
        String SQL = "UPDATE CategoriaTarefa SET Nome = ? WHERE IdTarefa = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, ct.getNome());
            stmt.setLong(2, ct.getIdCategoriaTarefa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
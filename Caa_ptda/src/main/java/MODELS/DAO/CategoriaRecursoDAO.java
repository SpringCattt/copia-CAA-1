package MODELS.DAO;
import MODELS.DAO.BaseDados;
import MODELS.CLASS.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRecursoDAO {
    // ---- SELECT (one) ----
    public Recurso getCategoriaRecursoById(long id) {
        String sql = "SELECT * FROM CategoriaRecurso WHERE IdCategoria = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Recurso ct = new Recurso();
                ct.setCategoria(rs.getInt("IdCategoria"));
                ct.setNome(rs.getString("Nome"));
                return ct;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    
    // ---- INSERT ----
    public long insertCategoriaRecurso(Recurso ct) {
        String SQL = "INSERT INTO CategoriaRecurso (Nome) VALUES (?)";
        int generatedId = -1;
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ct.getNome());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        ct.setCategoria(generatedId);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return generatedId;
    }
    
    // ---- UPDATE ----
    public boolean updateCategoriaRecurso(Recurso ct) {
        String SQL = "UPDATE CategoriaRecurso SET Nome = ? WHERE IdCategoria = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, ct.getNome());
            stmt.setLong(2, ct.getCategoria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
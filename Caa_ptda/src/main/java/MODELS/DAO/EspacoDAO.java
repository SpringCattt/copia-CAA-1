package MODELS.DAO;
import MODELS.CLASS.Espaco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacoDAO {

    // ---- SELECT (one bilhete) ----
    public Espaco getEspacoById(int id) {
        String sql = "SELECT * FROM Espaco WHERE idEspaco = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Espaco e = new Espaco();
                e.setIdEspaco(rs.getInt("IdEspaco"));
                e.setNome(rs.getString("Nome"));
                return e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- SELECT (all bilhetes) ----
    public List<Espaco> getAllEspaco() {
        List<Espaco> espacos = new ArrayList<>();
        String sql = "SELECT * FROM Espaco";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Espaco e = new Espaco();
                e.setIdEspaco(rs.getInt("IdEspaco"));
                e.setNome(rs.getString("Nome"));
                espacos.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return espacos;
    }

    // ---- INSERT ----
    public long insertEspaco(Espaco espaco) {
        String SQL = "INSERT INTO Espaco (Nome) VALUES (?)";
        int generatedId = -1;

        try (Connection conn = BaseDados.getConnection();
             // Chave para obter o ID gerado (RETURN_GENERATED_KEYS)
            PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, espaco.getNome());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        espaco.setIdEspaco(generatedId); // Define o ID no objeto Java
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // ---- UPDATE ---- 
    public boolean updateEspaco(Espaco espaco) {
        String SQL = "UPDATE Espaco SET Nome = ? WHERE IdEspaco = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, espaco.getNome());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
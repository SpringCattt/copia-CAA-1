package MODELS.DAO;
import MODELS.CLASS.Trabalhador; // Assumindo esta classe existe
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabalhadorDAO {

    private Trabalhador mapResultSetToTrabalhador(ResultSet rs) throws SQLException {
        Trabalhador t = new Trabalhador();
        t.setIdTrabalhador(rs.getInt("IdTrabalhador"));
        t.setNome(rs.getString("Nome"));
        t.setCategoria(rs.getInt("Categoria")); // Chave estrangeira
        t.setEmailPessoal(rs.getString("Email"));
        t.setAtividade(rs.getBoolean("Atividade"));
        return t;
    }

    // ---- SELECT (um trabalhador) ----
    public Trabalhador getTrabalhadorById(int id) {
        String sql = "SELECT * FROM Trabalhador WHERE IdTrabalhador = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrabalhador(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- SELECT (todos os trabalhadores) ----
    public List<Trabalhador> getAllTrabalhadores() {
        List<Trabalhador> trabalhadores = new ArrayList<>();
        String sql = "SELECT * FROM Trabalhador";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                trabalhadores.add(mapResultSetToTrabalhador(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trabalhadores;
    }

    // ---- INSERT ----
    public long insertTrabalhador(Trabalhador trabalhador) {
        String SQL = "INSERT INTO Trabalhador (Nome, Categoria, Email, Atividade) VALUES (?, ?, ?, ?)";
        long generatedId = -1;

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, trabalhador.getNome());
            stmt.setInt(2, trabalhador.getCategoria());
            stmt.setString(3, trabalhador.getEmailPessoal());
            stmt.setBoolean(4, trabalhador.isAtividade());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getLong(1);
                        trabalhador.setIdTrabalhador((int) generatedId); // Define o ID no objeto Java
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // ---- UPDATE ----
    public boolean updateTrabalhador(Trabalhador trabalhador) {
        String SQL = "UPDATE Trabalhador SET Nome = ?, Categoria = ?, Email = ?, Atividade = ? WHERE IdTrabalhador = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, trabalhador.getNome());
            stmt.setInt(2, trabalhador.getCategoria());
            stmt.setString(3, trabalhador.getEmailPessoal());
            stmt.setBoolean(4, trabalhador.isAtividade());
            stmt.setInt(5, trabalhador.getIdTrabalhador());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
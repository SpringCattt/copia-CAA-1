package MODELS.DAO;
import MODELS.CLASS.Tarefa; // Assumindo esta classe existe
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private Tarefa mapResultSetToTarefa(ResultSet rs) throws SQLException {
        Tarefa t = new Tarefa();
        t.setIdTarefa(rs.getInt("IdTarefa"));
        t.setTitulo(rs.getString("Titulo"));
        t.setDescricao(rs.getString("Descricao"));
        t.setIdTrabalhador(rs.getInt("TrabalhadorId")); // Chave estrangeira
        t.setEvento(rs.getInt("Evento")); // Chave estrangeira
        t.setEstado(rs.getBoolean("Estado")); // Mapeado para boolean
        t.setCategoria(rs.getInt("CategoriaId")); // Chave estrangeira
        return t;
    }

    // ---- SELECT (uma tarefa) ----
    public Tarefa getTarefaById(int id) {
        String sql = "SELECT * FROM Tarefa WHERE IdTarefa = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTarefa(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- SELECT (todas as tarefas) ----
    public List<Tarefa> getAllTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM Tarefa";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tarefas.add(mapResultSetToTarefa(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    // ---- INSERT ----
    public long insertTarefa(Tarefa tarefa) {
        String SQL = "INSERT INTO Tarefa (Titulo, Descricao, TrabalhadorId, Evento, Estado, CategoriaId) VALUES (?, ?, ?, ?, ?, ?)";
        long generatedId = -1;

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getIdTrabalhador());
            stmt.setInt(4, tarefa.getEvento());
            stmt.setBoolean(5, tarefa.isEstado()); // isEstado() para boolean
            stmt.setInt(6, tarefa.getCategoria());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getLong(1);
                        tarefa.setIdTarefa((int) generatedId); // Define o ID no objeto Java
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // ---- UPDATE ----
    public boolean updateTarefa(Tarefa tarefa) {
        String SQL = "UPDATE Tarefa SET Titulo = ?, Descricao = ?, TrabalhadorId = ?, Evento = ?, Estado = ?, CategoriaId = ? WHERE IdTarefa = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getIdTrabalhador());
            stmt.setInt(4, tarefa.getEvento());
            stmt.setBoolean(5, tarefa.isEstado());
            stmt.setInt(6, tarefa.getCategoria());
            stmt.setInt(7, tarefa.getIdTarefa());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
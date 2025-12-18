package MODELS.DAO;
import MODELS.CLASS.Bilhete;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilheteDAO {

    // ---- SELECT (one bilhete) ----
    public Bilhete getBilheteById(int id) {
        String sql = "SELECT * FROM Bilhete WHERE idBilhete = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bilhete b = new Bilhete();
                b.setIdBilhete(rs.getInt("IdBilhete"));
                b.setIdEvento(rs.getInt("IdEvento"));
                b.setLugar(rs.getInt("Lugar"));
                b.setIdCliente(rs.getInt("IdCliente"));
                b.setPreco(rs.getInt("Preco"));
                return b;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- SELECT (all bilhetes) ----
    public List<Bilhete> getAllBilhetes() {
        List<Bilhete> bilhetes = new ArrayList<>();
        String sql = "SELECT * FROM Bilhete";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bilhete b = new Bilhete();
                b.setIdBilhete(rs.getInt("IdBilhete"));
                b.setIdEvento(rs.getInt("IdEvento"));
                b.setLugar(rs.getInt("IdLugar"));
                b.setIdCliente(rs.getInt("IdCliente"));
                b.setPreco(rs.getInt("Preco"));
                bilhetes.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilhetes;
    }

    // ---- INSERT ----
    public long insertBilhete(Bilhete bilhete) {
        String SQL = "INSERT INTO Bilhete (IdLugar, IdCliente, IdEvento, Preco) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = BaseDados.getConnection();
             // Chave para obter o ID gerado (RETURN_GENERATED_KEYS)
            PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, bilhete.getLugar());
            stmt.setLong(2, bilhete.getIdCliente());
            stmt.setLong(3, bilhete.getIdEvento());
            stmt.setDouble(4, bilhete.getPreco());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        bilhete.setIdBilhete(generatedId); // Define o ID no objeto Java
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // ---- UPDATE ---- 
    public boolean updateBilhete(Bilhete bilhete) {
        String SQL = "UPDATE Bilhete SET IdLugar = ?, IdCliente = ?, IdEvento = ?, Preco = ? WHERE IdBilhete = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setLong(1, bilhete.getLugar());
            stmt.setLong(2, bilhete.getIdCliente());
            stmt.setLong(3, bilhete.getIdEvento());
            stmt.setDouble(4, bilhete.getPreco());
            stmt.setLong(5, bilhete.getIdBilhete());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

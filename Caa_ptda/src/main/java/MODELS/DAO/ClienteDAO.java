package MODELS.DAO;
import MODELS.CLASS.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ---- SELECT (one bilhete) ----
    public Cliente getClienteById(int id) {
        String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("IdCliente"));
                c.setNome(rs.getString("Nome"));
                c.setEmail(rs.getString("Email"));
                c.setNumeroTelefone(rs.getInt("NumeroTelefone"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- SELECT (all bilhetes) ----
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("IdCliente"));
                c.setNome(rs.getString("Nome"));
                c.setEmail(rs.getString("Email"));
                c.setNumeroTelefone(rs.getInt("NumeroTelefone"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    // ---- INSERT ----
    public long insertCliente(Cliente cliente) {
        String SQL = "INSERT INTO Cliente (Nome, Email, NumeroTelefone) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = BaseDados.getConnection();
             // Chave para obter o ID gerado (RETURN_GENERATED_KEYS)
            PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setLong(3, cliente.getNumeroTelefone());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        cliente.setIdCliente(generatedId); // Define o ID no objeto Java
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // ---- UPDATE ---- 
    public boolean updateCliente(Cliente cliente) {
        String SQL = "UPDATE Cliente SET Nome = ?, Email = ?, NumeroTelefone = ? WHERE IdCliente = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setLong(3, cliente.getNumeroTelefone());
            stmt.setLong(4, cliente.getIdCliente());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

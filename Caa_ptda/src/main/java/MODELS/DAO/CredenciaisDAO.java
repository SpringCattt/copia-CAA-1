package MODELS.DAO;
import MODELS.CLASS.Credenciais;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredenciaisDAO {

    public Credenciais getCredenciaisById(int idTrabalhador) {
        String sql = "SELECT * FROM Credenciais WHERE IdTrabalhador = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrabalhador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Credenciais c = new Credenciais();
                c.setIdTrabalhador(idTrabalhador);
                c.setEmail(rs.getString("Email"));
                c.setPassword(rs.getString("Password"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean insertCredenciais(Credenciais credenciais) {
        String SQL = "INSERT INTO Credenciais (IdTrabalhador, Email, Password) VALUES (?, ?, ?)";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, credenciais.getIdTrabalhador()); // FK obrigatória
            stmt.setString(2, credenciais.getEmail());
            stmt.setString(3, credenciais.getPassword());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean updateCredenciais(Credenciais credenciais) {
        String SQL = "UPDATE Credenciais SET Email = ?, Password = ? WHERE IdTrabalhador = ?";

        try (Connection conn = BaseDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, credenciais.getEmail());
            stmt.setString(2, credenciais.getPassword());
            stmt.setInt(3, credenciais.getIdTrabalhador());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
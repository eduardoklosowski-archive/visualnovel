package br.eduardoklosowski.visualnovel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionDAO {
    private Connection connection;

    public OptionDAO() {
        connection = Database.getConnection();
    }

    public Option get(long id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT text, next FROM option WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Opção " + id + " não encontrada");
            }
            String text = rs.getString(1);
            long nextId = rs.getLong(2);

            return new Option(id, text, nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

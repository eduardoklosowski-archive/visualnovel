package br.eduardoklosowski.visualnovel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private Connection connection;

    public HistoryDAO() {
        connection = Database.getConnection();
    }

    public History get(long id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT text FROM history WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("História " + id + " não encontrada");
            }
            String text = rs.getString(1);

            List<Long> options = new ArrayList<>();
            stmt = connection.prepareStatement("SELECT option FROM history_option WHERE history = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                options.add(rs.getLong(1));
            }

            return new History(id, text, options);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package br.eduardoklosowski.visualnovel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveDAO {
    private Connection connection;

    public SaveDAO() {
        connection = Database.getConnection();
    }

    public List<Save> list() {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT id, history, date FROM save ORDER BY date DESC");
            ResultSet rs = stmt.executeQuery();
            HistoryDAO historyDAO = new HistoryDAO();
            List<Save> list = new ArrayList<>();
            while (rs.next()) {
                Save save = new Save();
                save.setId(rs.getLong(1));
                save.setHistory(historyDAO.get(rs.getLong(2)));
                save.setDate(new Date(rs.getLong(3)));
                list.add(save);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Save save) {
        try {
            PreparedStatement stmt;
            Date date = new Date();
            save.setDate(date);
            if (save.getId() != 0) {
                stmt = connection.prepareStatement("UPDATE save SET history = ?, date = ? WHERE id = ?");
                stmt.setLong(1, save.getHistory().getId());
                stmt.setLong(2, date.getTime());
                stmt.setLong(3, save.getId());
            } else {
                stmt = connection.prepareStatement("INSERT INTO save (history, date) VALUES (?, ?)");
                stmt.setLong(1, save.getHistory().getId());
                stmt.setLong(2, date.getTime());
            }
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Save save) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM save WHERE id = ?");
            stmt.setLong(1, save.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

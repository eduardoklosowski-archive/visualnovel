package br.eduardoklosowski.visualnovel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection = null;

    private Database() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void startDatabase() {
        createStructured();
        importHistory();
    }

    private static void createStructured() {
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY, text TEXT NOT NULL)");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS option (id INTEGER PRIMARY KEY, text TEXT NOT NULL, next INTEGER NOT NULL REFERENCES history (id))");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS history_option (history INTEGER REFERENCES history (id), option INTEGER REFERENCES option (id), PRIMARY KEY (history, option))");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS save (id INTEGER PRIMARY KEY AUTOINCREMENT, history INTEGER NOT NULL REFERENCES history (id), date INTEGER NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void importHistory() {
        addHistory(1, "Você acorda no sábado de manhã. O que você faz?");
        addHistory(2, "Você leva o final de semana inteiro, mas termina o trabalho.");
        addHistory(3,
                "Você faz várias coisas no final de semana e ele está chegando no final. O que deseja fazer nos últimos momentos?");
        addHistory(4, "Você não faz o trabalho de POO.");
        addHistory(5, "Você não termina o trabalho de POO.");
        addHistory(6, "Você está na aula, hora de entregar o trabalho.");
        addHistory(7, "Você passou em POO");
        addHistory(8, "Você ficou de recuperação");
        addHistory(9, "Você reprovou direto");
        addOption(1, "O trabalho de POO", 2);
        addOption(2, "Outra coisa", 3);
        addOption(3, "Continuar...", 6);
        addOption(4, "Dormir", 4);
        addOption(5, "O trabalho de POO", 5);
        addOption(6, "Você fez o trabalho", 7);
        addOption(7, "Você não terminou o trabalho", 8);
        addOption(8, "Você não fez o trabalho", 9);
        addHistoryOption(1, 1);
        addHistoryOption(1, 2);
        addHistoryOption(2, 3);
        addHistoryOption(3, 4);
        addHistoryOption(3, 5);
        addHistoryOption(4, 3);
        addHistoryOption(5, 3);
        addHistoryOption(6, 6);
        addHistoryOption(6, 7);
        addHistoryOption(6, 8);
    }

    private static void addHistory(long id, String text) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement("INSERT OR IGNORE INTO history (id, text) VALUES (?, ?)");
            stmt.setLong(1, id);
            stmt.setString(2, text);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addOption(long id, String text, long next) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement("INSERT OR IGNORE INTO option (id, text, next) VALUES (?, ?, ?)");
            stmt.setLong(1, id);
            stmt.setString(2, text);
            stmt.setLong(3, next);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addHistoryOption(long history, long option) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement("INSERT OR IGNORE INTO history_option (history, option) VALUES (?, ?)");
            stmt.setLong(1, history);
            stmt.setLong(2, option);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class ResponseDAO {

    private Connection conn;

    public ResponseDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    public void insertResponse(String question, String answer, String sessionId) {

        String sql = "INSERT INTO responses(question, answer, session_id) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, question);
            stmt.setString(2, answer);
            stmt.setString(3, sessionId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert Response Error: " + e.getMessage());
        }
    }
}
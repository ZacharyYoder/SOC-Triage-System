package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;
import util.IncidentFactory;
import model.Incident;

public class IncidentDAO {

    private Connection conn;

    public IncidentDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    // INSERT INCIDENT
    public int insertIncident(Incident incident) {

        String sql = "INSERT INTO incidents(type, severity) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, incident.getType());
            stmt.setString(2, incident.getSeverity());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Insert Incident Error: " + e.getMessage());
        }

        return -1;
    }

    // SAVE INCIDENT
    public void saveIncident(Incident incident) {
        insertIncident(incident);
    }

    // SELECT ALL INCIDENTS
    public List<Incident> getAllIncidents() {

        List<Incident> incidents = new ArrayList<>();

        String sql = "SELECT * FROM incidents ORDER BY id DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String type = rs.getString("type");
                String severity = rs.getString("severity");

                // Factory conversion
                Incident i = IncidentFactory.create(type, severity);

                if (i != null) {
                    incidents.add(i);
                }
            }

        } catch (SQLException e) {
            System.out.println("Select Error: " + e.getMessage());
        }

        return incidents;
    }
}
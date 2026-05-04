package model;

import java.util.List;

public abstract class Incident {

    protected String type;
    protected String severity;
    protected List<String> recommendations;

    public Incident(String type, String severity, List<String> recommendations) {
        this.type = type;
        this.severity = severity;
        this.recommendations = recommendations;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    @Override
    public String toString() {
        return "=== INCIDENT REPORT ===\n" +
               "Type: " + type + "\n" +
               "Severity: " + severity + "\n" +
               "Recommendations: " + recommendations;
    }
}
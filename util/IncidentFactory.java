package util;

import model.*;
import java.util.*;

public class IncidentFactory {

    // PRIMARY METHOD
    public static Incident create(String type, String severity) {

        List<String> recommendations = generateRecommendations(type, severity);

        switch (type.toUpperCase()) {

            case "PHISHING":
                return new PhishingIncident(severity, recommendations);

            case "MALWARE":
                return new MalwareIncident(severity, recommendations);

            default:
                return new UnknownIncident(severity, recommendations);
        }
    }

    private static List<String> generateRecommendations(String type, String severity) {

        switch (type.toUpperCase()) {

            case "PHISHING":
                if (severity.equalsIgnoreCase("HIGH")) {
                    return Arrays.asList(
                        "Reset user credentials immediately",
                        "Scan system for malware",
                        "Notify SOC team",
                        "Block malicious sender/domain"
                    );
                } else {
                    return Arrays.asList(
                        "Warn user about suspicious email",
                        "Mark email as spam",
                        "Monitor account activity"
                    );
                }

            case "MALWARE":
                if (severity.equalsIgnoreCase("HIGH")) {
                    return Arrays.asList(
                        "Isolate affected system from network",
                        "Run full antivirus/EDR scan",
                        "Remove malicious files",
                        "Check for lateral movement"
                    );
                } else {
                    return Arrays.asList(
                        "Run antivirus scan",
                        "Monitor system behavior",
                        "Review installed applications"
                    );
                }

            default:
                return getDefaultRecommendations();
        }
    }
    // DEFAULT FALLBACK
    private static List<String> getDefaultRecommendations() {
        return Arrays.asList(
            "Log incident for review",
            "No immediate action required"
        );
    }
}
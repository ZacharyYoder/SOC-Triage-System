package model;

import java.util.List;

public class PhishingIncident extends Incident {

    public PhishingIncident(String severity, List<String> recommendations) {
        super("PHISHING", severity, recommendations);
    }
}
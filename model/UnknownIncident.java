package model;

import java.util.List;

public class UnknownIncident extends Incident {

    public UnknownIncident(String severity, List<String> recommendations) {
        super("UNKNOWN", severity, recommendations);
    }
}
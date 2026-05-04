package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.TriageController;
import dao.IncidentDAO;
import model.Incident;

public class TriageGUI extends JFrame {

    private TriageController controller;

    private JLabel questionLabel;
    private JLabel resultLabel;

    private JButton yesButton;
    private JButton noButton;
    private JButton resetButton;
    private JButton viewDBButton;

    private JTextArea sessionArea; 

    public TriageGUI(TriageController controller) {

        this.controller = controller;

        setTitle("Cybersecurity Incident Triage System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // TITLE
        JLabel title = new JLabel("SOC Triage System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        resultLabel = new JLabel("Waiting...", SwingConstants.CENTER);

        // SESSION VIEW 
        sessionArea = new JTextArea();
        sessionArea.setEditable(false);
        sessionArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane sessionScroll = new JScrollPane(sessionArea);

        centerPanel.add(questionLabel);
        centerPanel.add(resultLabel);
        centerPanel.add(sessionScroll);

        add(centerPanel, BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();

        yesButton = new JButton("YES");
        noButton = new JButton("NO");
        resetButton = new JButton("RESET");
        viewDBButton = new JButton("VIEW DB");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(viewDBButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // EVENTS
        yesButton.addActionListener(e -> handleAnswer(true));
        noButton.addActionListener(e -> handleAnswer(false));
        resetButton.addActionListener(e -> reset());
        viewDBButton.addActionListener(e -> showDatabase());

        updateQuestion();
        updateSessionView();

        setVisible(true);
    }

    // HANDLE ANSWERS
    private void handleAnswer(boolean answer) {

        controller.answer(answer);

        updateSessionView(); 

        if (!controller.isComplete()) {
            updateQuestion();
        } 
        else {
            Incident incident = controller.generateIncident();

            IncidentDAO dao = new IncidentDAO();
            dao.saveIncident(incident);

            String formatted = "<html>" // html to format the incident reoprt
                    + "<b>=== INCIDENT REPORT ===</b><br><br>"
                    + "Type: " + incident.getType() + "<br>"
                    + "Severity: " + incident.getSeverity() + "<br><br>"
                    + "Recommendations:<br>- "
                    + String.join("<br>- ", incident.getRecommendations())
                    + "</html>";

            resultLabel.setText(formatted);
            questionLabel.setText("Triage Complete");

            updateSessionView(); // final refresh
        }
    }

    // UPDATE QUESTION
    private void updateQuestion() {
        String q = controller.getCurrentQuestion();
        questionLabel.setText(q != null ? q : "No question available");
    }

    // HASHMAP SESSION DISPLAY
    private void updateSessionView() {

        StringBuilder sb = new StringBuilder();
        sb.append("=== SESSION REVIEW ===\n\n");

        controller.getResponses().forEach((question, answer) -> {
            sb.append(question)
              .append(" → ")
              .append(answer)
              .append("\n");
        });

        sessionArea.setText(sb.toString());
    }

    // RESET
    private void reset() {
        controller.reset();
        updateQuestion();
        updateSessionView(); 
        resultLabel.setText("Waiting...");
    }

    // DATABASE VIEW
    private void showDatabase() {

        IncidentDAO dao = new IncidentDAO();
        List<Incident> incidents = dao.getAllIncidents();

        StringBuilder sb = new StringBuilder();
        sb.append("=== INCIDENT DATABASE ===\n\n");

        for (Incident i : incidents) {
            sb.append("Type: ").append(i.getType())
              .append(" | Severity: ").append(i.getSeverity())
              .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Database Records",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
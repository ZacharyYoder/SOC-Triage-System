package controller;

import java.util.HashMap;

import dao.ResponseDAO;
import model.DecisionNode;
import model.Incident;
import util.IncidentFactory;

public class TriageController {

    private DecisionNode root;
    private DecisionNode currentNode;

    private HashMap<String, String> responses;

    private String finalResult;
    private String finalSeverity;
    
    private ResponseDAO responseDAO = new ResponseDAO();
    private String sessionID = java.util.UUID.randomUUID().toString();

    // CONSTRUCTOR
    public TriageController(DecisionNode root) {
        this.root = root;
        this.currentNode = root;
        this.responses = new HashMap<>();
        this.finalResult = null;
        this.finalSeverity = null;
    }

    // GET CURRENT QUESTION
    public String getCurrentQuestion() {
        if (currentNode == null || currentNode.isLeaf()) {
            return null;
        }
        return currentNode.getQuestion();
    }

    // CORE TRAVERSAL LOGIC
    public void answer(boolean userInput) {

        // Store response
        if (currentNode != null && !currentNode.isLeaf()) {

            String answer = userInput ? "YES" : "NO";

            responseDAO.insertResponse(
                currentNode.getQuestion(),
                answer,
                sessionID
            );

            responses.put(currentNode.getQuestion(), answer);
        }

        // Move through tree
        if (currentNode != null) {
            currentNode = userInput
                ? currentNode.getYesBranch()
                : currentNode.getNoBranch();
        }

        // Check leaf node
        if (currentNode != null && currentNode.isLeaf()) {
            finalResult = currentNode.getIncidentType();

            finalSeverity = determineSeverity();
        }
    }

    // CHECK COMPLETION
    public boolean isComplete() {
        return currentNode == null || currentNode.isLeaf();
    }

    // GET FINAL RESULT TYPE
    public String getFinalResult() {
        return finalResult;
    }

    // FACTORY INTEGRATION FIXED
    public Incident generateIncident() {

        if (finalResult == null || finalSeverity == null) {
            return null;
        }

        System.out.println("TYPE RAW = [" + finalResult + "]");
        System.out.println("SEVERITY RAW = [" + finalSeverity + "]");

        return IncidentFactory.create(finalResult, finalSeverity);
    }

    // RESET SESSION
    public void reset() {
        this.currentNode = root;
        this.responses.clear();
        this.finalResult = null;
        this.finalSeverity = null;
    }

    // HASHMAP ACCESS FOR GUI
    public HashMap<String, String> getResponses() {
        return responses;
    }

    private String determineSeverity() {

        int riskScore = 0;

        for (String answer : responses.values()) {
            if ("YES".equalsIgnoreCase(answer)) {
                riskScore++;
            }
        }

        return (riskScore >= 2) ? "HIGH" : "LOW";
    }

}
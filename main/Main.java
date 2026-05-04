package main;

import model.DecisionNode;
import util.DBConnection;

import java.sql.Connection;

import controller.TriageController;
import view.TriageGUI;

public class Main {

    public static void main(String[] args) {

        Connection conn = DBConnection.getInstance().getConnection();

        if (conn != null) {
            System.out.println("DB CONNECTED SUCCESSFULLY");
        } else {
            System.out.println("DB CONNECTION FAILED");
        }

        DecisionNode root = DecisionNode.question("Is this email-related?");

        // EMAIL BRANCH 
        DecisionNode emailNode = DecisionNode.question("Does it contain a suspicious link?");
        DecisionNode phishingHigh = DecisionNode.leaf("PHISHING");
        DecisionNode phishingLow = DecisionNode.leaf("PHISHING");

        emailNode.setYesBranch(phishingHigh);
        emailNode.setNoBranch(phishingLow);

        //  NON-EMAIL BRANCH 
        DecisionNode nonEmailNode = DecisionNode.question("Is system behavior abnormal?");
        DecisionNode malwareMedium = DecisionNode.leaf("MALWARE");
        DecisionNode bruteForceHigh = DecisionNode.leaf("UNKNOWN");

        nonEmailNode.setYesBranch(malwareMedium);
        nonEmailNode.setNoBranch(bruteForceHigh);

        // CONNECT ROOT 
        root.setYesBranch(emailNode);
        root.setNoBranch(nonEmailNode);

        // STEP 2: CONTROLLER
        TriageController controller = new TriageController(root);

        // STEP 3: LAUNCH GUI
        new TriageGUI(controller);
        
    }
}
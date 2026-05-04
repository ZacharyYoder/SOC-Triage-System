# SOC-Triage-System


=====================================================

Project overview

Cybersecurity Incident Intake & Triage System

=====================================================

Use at least two non-trivial data structures

Data structures used:
Binary tree : interview logic
Hashmap : stores responses or quick lookup

======================================================
This app will simulate how a SOC team collects and processes incident reports. The user is “interviewed” through a series of questions, and based on their answers, the system determines:
Type of incident (phishing, malware, brute force, etc.)
Severity level (low, medium, high)
Recommended action
======================================================

GUI will present questions for user input 

Display the relevant data (user responses via buttons)
Include buttons to navigate between questions. Additionally one to terminate the report.

======================================================

2.3 Design Patterns

Separate the data (model), UI (View), and application logic (Controller) into distinct classes.
Use a singleton class to manage database connection
Use factory method 

======================================================

2.4  DB integration

Uses MySQL:
User information
User responses

Project structure

/model
DecisionNode.java 
Incident.java 
PhishingIncident.java 
MalwareIncident.java 
UnknownIncident.java 
/controller
TriageController.java 
/view
TriageGUI.java 
/util
DBConnection.java 
IncidentFactory.java 
/dao
IncidentDAO.java 
ResponseDAO.java 

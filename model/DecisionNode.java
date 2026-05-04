package model;

public class DecisionNode
{
    // Question to ask at this node
    private String question;

    // branches
    private DecisionNode yesBranch;
    private DecisionNode noBranch;
    
    // Result Field
    private String incidentType;

    private DecisionNode() {}

    // Constructor for QUESTION nodes
    public static DecisionNode question(String q) {
        DecisionNode node = new DecisionNode();
        node.question = q;
        node.incidentType = null;
        return node;
    }

    // constructor for RESULT (leaf) node
    public static DecisionNode leaf(String incident) {
        DecisionNode node = new DecisionNode();
        node.incidentType = incident;
        node.question = null;
        return node;
    }

    // Check if node is leaf (end of decision tree)
    public boolean isLeaf()
    {
        return incidentType != null;
    }

    // Getters

    public String getQuestion()         {  return question;     }
    public DecisionNode getYesBranch()  {  return yesBranch;    }
    public DecisionNode getNoBranch()   {  return noBranch;     }
    public String getIncidentType()     {  return incidentType; }

    // Setters
    public void setYesBranch(DecisionNode yesBranch){ 
        this.yesBranch = yesBranch; }
    public void setNoBranch(DecisionNode noBranch){ 
        this.noBranch = noBranch;}
}

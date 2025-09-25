package model;

import java.util.ArrayList;
import java.util.List;
import model.enums.AgentType;

public class Agent {
    private int idAgent;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AgentType agentType;
    private Department department;
    private List<Payment> payments;

    // --- Constructor ---
    public Agent(int idAgent, String firstName, String lastName, String email, String password, AgentType agentType,Department department) {
        this.idAgent = idAgent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.agentType = agentType;
        this.department=department;
        this.payments = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public int getIdAgent() { return idAgent; }
    public void setIdAgent(int idAgent) { this.idAgent = idAgent; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public AgentType getAgentType() { return agentType; }
    public void setAgentType(AgentType agentType) { this.agentType = agentType; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<Payment> getPayments() { return payments; }

    // --- Methods to manage payments ---
    public void addPayment(Payment payment) {
        payments.add(payment);
    }
    public void removePayment(Payment payment) {
        payments.remove(payment);
    }

    // --- Display info ---
    public void displayInfo() {
        System.out.println("Agent: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Type: " + agentType);
        if(department != null) {
            System.out.println("Department: " + department.getName());
        }
    }
}

package model;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private int id;
    private String name;
    private Agent manager;
    private List<Agent> agents;

    // Constructor
    public Department(int id, String name, Agent manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.agents = new ArrayList<>();
    }
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.manager = null;
        this.agents = new ArrayList<>();
    }
    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Agent getManager() { return manager; }
    public void setManager(Agent manager) { this.manager = manager; }

    public List<Agent> getAgents() { return agents; }

    // Methods to manage agents
    public void addAgent(Agent agent) {
        agent.setDepartment(this);
        agents.add(agent);
    }

    public void removeAgent(Agent agent) {
        agent.setDepartment(null);
        agents.remove(agent);
    }
}

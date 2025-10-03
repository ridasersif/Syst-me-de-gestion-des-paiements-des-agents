package controller;

import model.Agent;
import service.AgentService;
import java.util.List;
import java.util.Optional;

public class AgentController {

    private AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    public List<Agent> displayAllAgents() {
        List<Agent> agents = agentService.getAllAgents();
        return agents;
    }

    public boolean deleteAgent(int id) {
        return agentService.deleteAgent(id);
    }

    public boolean updateAgent(Agent agent) {
        return agentService.updateAgent(agent);
    }

    public Optional<Agent> getAgentById(int id) {
        return agentService.getAgentById(id);
    }

}

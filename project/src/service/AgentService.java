package service;

import model.Agent;
import repository.IAgentRepository;
import java.util.List;
import java.util.Optional;

public class AgentService {

    private IAgentRepository agentRepo;

    public AgentService(IAgentRepository agentRepo) {
        this.agentRepo = agentRepo;
    }

    public List<Agent> getAllAgents() {
        return agentRepo.getAllAgents();
    }

    public Optional<Agent> getAgentById(int id) {
        return agentRepo.getAgentById(id);
    }

    public boolean updateAgent(Agent agent) {
        Optional<Agent> existing = agentRepo.getAgentById(agent.getIdAgent());
        if(existing.isPresent()) {
            agentRepo.updateAgent(agent);
            return true;
        }
        return false;
    }

    public boolean deleteAgent(int id) {
        Optional<Agent> existing = agentRepo.getAgentById(id);
        if(existing.isPresent()) {
            agentRepo.deleteAgent(id);
            return true;
        }
        return false;
    }

}

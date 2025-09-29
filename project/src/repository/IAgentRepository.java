package repository;

import model.Agent;
import java.util.List;
import java.util.Optional;

public interface IAgentRepository {

    void addAgent(Agent agent);
    void updateAgent(Agent agent);
    void deleteAgent(int agentId);
    Optional<Agent> getAgentById(int agentId);
    List<Agent> getAllAgents();
    List<Agent> getAgentsByDepartment(int departmentId);
    Optional<Agent> findByEmail(String email);
}

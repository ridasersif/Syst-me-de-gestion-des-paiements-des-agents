package service;

import model.Agent;
import model.enums.AgentType;
import repository.IAgentRepository;

import java.util.Optional;

public class AuthService {
    private IAgentRepository agentRepo;

    public AuthService(IAgentRepository agentRepo) {
        this.agentRepo = agentRepo;
    }


    public Optional<Agent> login(String email, String password) {
        Optional<Agent> agentOpt = agentRepo.findByEmail(email);
        if(agentOpt.isPresent() && agentOpt.get().getPassword().equals(password)) {
            return agentOpt;
        }
        return Optional.empty();
    }


    public boolean register(Agent currentUser, Agent newAgent) {
        Optional<Agent> existingAgent=agentRepo.findByEmail(newAgent.getEmail());
        if(existingAgent.isPresent()){
            System.out.println("Cet email est déjà utilisé !");
            return false;
        }
        switch (currentUser.getAgentType()){
            case DIRECTOR:
                break;
            case MANAGER:
                switch (newAgent.getAgentType()){
                    case DIRECTOR:
                        System.out.println("Un manager ne peut pas créer un directeur !");
                        return false;
                    case MANAGER:
                        System.out.println("Un manager ne peut pas créer un autre manager !");
                        return false;
                }
                newAgent.setDepartment(currentUser.getDepartment());
                break;
            default:
                System.out.println("Vous n'avez pas les droits pour créer un agent !");
                return false;
        }
        agentRepo.addAgent(newAgent);
        return true;
    }
}

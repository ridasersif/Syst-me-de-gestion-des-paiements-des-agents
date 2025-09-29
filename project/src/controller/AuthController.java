package controller;


import model.Agent;
import repository.AgentRepositoryImpl;
import repository.IAgentRepository;
import service.AgentService;
import service.AuthService;
import view.auth.LoginMenu;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.Optional;

public class AuthController {
    private IAgentRepository agentRepository;
    private AuthService authService;


        //constructor
    public AuthController() {
        try {
          IAgentRepository agentRepo = new AgentRepositoryImpl();
          this.authService = new AuthService(agentRepo);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }


    public Agent login(String email,String password) throws AuthenticationException{
        Optional<Agent> agentOpt = authService.login(email,password);
        if(agentOpt.isEmpty()){
            throw new AuthenticationException("Invalid email or password");
        }
        return agentOpt.get();
    }
    public boolean register(Agent director, Agent newAgent) {
        return authService.register(director, newAgent);
    }
}
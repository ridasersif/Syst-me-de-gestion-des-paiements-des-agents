package view.auth;

import controller.AuthController;
import model.Agent;
import javax.naming.AuthenticationException;
import java.util.Scanner;
import util.InputUtil;
import view.agent.AgentMenu;
import view.agent.DirecteurMenu;
import view.agent.ManagerMenu;

public class LoginMenu {
    private AuthController authController = new AuthController();

    public LoginMenu(){
    }

    public void show() {

        while (true) {
            if (handleLogin()) {
                break;
            }
            if (!askRetry()) {
                return;
            }
        }
    }

    private boolean handleLogin() {
        try {

            System.out.println();

            String email = InputUtil.readString("Email    ");
            String password = InputUtil.readString("Password ");

            System.out.println("\n🔄 Vérification en cours...");
            Agent agent = authController.login(email, password);
            displayLoginSuccess(agent);
            redirectToMenu(agent);
            return true;

        } catch (AuthenticationException e) {
            displayLoginError(e.getMessage());
            return false;
        }
    }

    private void displayLoginSuccess(Agent agent) {
        String firstName = (agent.getFirstName() != null) ? agent.getFirstName() : "Utilisateur";

        System.out.println("\n ══════════════════════════════════════════════");
        System.out.println("    CONNEXION RÉUSSIE !");
        System.out.println("    Bienvenue " + firstName + " !");
        System.out.println("    " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.println("═════════════════════════════════════════════");


        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    }

    private void displayLoginError(String errorMessage) {
        System.out.println("\n ══════════════════════════════════════════════");
        System.out.println("    ÉCHEC DE CONNEXION");
        System.out.println("    " + errorMessage);
        System.out.println("═════════════════════════════════════════════");
    }

    private void redirectToMenu(Agent agent) {
        System.out.println("\n🔄 Redirection vers votre espace...\n");

        switch (agent.getAgentType()) {
            case DIRECTOR:
                new DirecteurMenu(agent).show();
                break;

            case MANAGER:
                new ManagerMenu().show();
                break;
            case WORKER:
            case INTERN:
                System.out.println(" Accès Espace Agent");
                new AgentMenu(agent).show();
                break;

        }
    }

    private boolean askRetry() {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("               QUE FAIRE ?                  ");
        System.out.println("══════════════════════════════════════════════");
        System.out.println("                                              ");
        System.out.println("  1️⃣  Réessayer                              ");
        System.out.println("  0️⃣  Retour menu principal                  ");
        System.out.println("                                              ");
        System.out.println("══════════════════════════════════════════════");

        int choice = InputUtil.readInt(" Votre choix ");

        switch (choice) {
            case 1:
                return true;
            case 0:
            default:
                System.out.println("\n🔙 Retour au menu principal...\n");
                return false;
        }
    }
}
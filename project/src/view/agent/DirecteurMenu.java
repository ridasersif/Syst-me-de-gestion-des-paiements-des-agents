package view.agent;

import model.Agent;
import util.InputUtil;
import view.DepartmentMenu;

public class DirecteurMenu {
    private Agent agent; // agent connecté
    private AgentMenu agentMenu;
    private AgentManagementMenu agentManagementMenu;

    public DirecteurMenu(Agent agent) {
        this.agent = agent;
        this.agentMenu = new AgentMenu(agent);
        this.agentManagementMenu = new AgentManagementMenu(agent);
    }

    public void show() {

        while (true) {
            displayMenu();
            int choice = InputUtil.readInt(" Choisissez une option ");

            if (handleChoice(choice)) {
                break;
            }

            pauseScreen();
        }
    }

    private void displayMenu() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║               ESPACE DIRECTEUR             ║");
        System.out.println("║              Panneau de Contrôle           ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║                                            ║");
        System.out.println("║  1️⃣ Mon Profil                             ║");
        System.out.println("║  2️⃣ Gestion Départements                   ║");
        System.out.println("║  3️⃣ Gestion des Agents                     ║");
        System.out.println("║  4️⃣ Gestion Paiements                      ║");
        System.out.println("║  5️⃣ Statistiques & Rapports                ║");
        System.out.println("║  0️⃣ Déconnexion                            ║");
        System.out.println("║                                            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                showProfile();
                return false;

            case 2:
                showDepartmentManagement();
                return false;

            case 3:
                showAgentManagement();
                return false;

            case 4:

                return false;

            case 5:

                return false;

            case 6:

                return false;

            case 0:
                showLogout();
                return true;

            default:
                System.out.println("❌ Option invalide ! Veuillez réessayer.\n");
                return false;
        }
    }

    private void showProfile() {
        agentMenu.show();
    }
    private void showAgentManagement() {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("     GESTION DES AGENTS");
        System.out.println("   Accès au module agents...");
        System.out.println("═══════════════════════════════════════════════");

        agentManagementMenu.show();
    }
    private void showDepartmentManagement() {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("     GESTION DES DÉPARTEMENTS");
        System.out.println("   Accès au module départements...");
        System.out.println("═══════════════════════════════════════════════");

        new DepartmentMenu(agent).show();
    }

    private void showLogout() {

        String directorName = (agent.getFirstName() != null) ? agent.getFirstName() : "Directeur";
        System.out.println("\n🔚═══════════════════════════════════════════════🔚");
        System.out.println("            DÉCONNEXION EN COURS...");
        System.out.println("     Au revoir " + directorName + " !");
        System.out.println("      Session fermée avec succès.");
        System.out.println("🔚═══════════════════════════════════════════════🔚\n");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

    }

    private void pauseScreen() {
        System.out.println("Appuyez sur Entrée pour continuer...");
        try {
            System.in.read();
        } catch (Exception e) {}
    }
}
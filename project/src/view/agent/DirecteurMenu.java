package view.agent;

import model.Agent;
import util.InputUtil;
import view.DepartmentMenu;
import view.PaymentMenu;

import static model.enums.AgentType.DIRECTOR;
public class DirecteurMenu {
    private Agent agent; // agent connecte
    private AgentMenu agentMenu;
    private AgentManagementMenu agentManagementMenu;
    private PaymentMenu paymentMenu;
    private DepartmentMenu departmentMenu;

    public DirecteurMenu(Agent agent) {
        this.agent = agent;
        this.agentMenu = new AgentMenu(agent);
        this.agentManagementMenu = new AgentManagementMenu(agent);
        this.departmentMenu = new DepartmentMenu(agent);
        this.paymentMenu = new PaymentMenu(agent);
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
        String title = (agent.getAgentType() == DIRECTOR)
                         ? "╔═════════════ ESPACE DIRECTEUR ═════════════╗"
                         : "╔══════════════ ESPACE MANAGER ══════════════╗";

        System.out.println(title);
        System.out.println("║              Panneau de Contrôle           ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║                                            ║");
        System.out.println("║  1️⃣ Mon Profil                             ║");
        if (agent.getAgentType() == DIRECTOR) {
            System.out.println("║  2️⃣ Gestion Départements                   ║");
        }
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
                agentMenu.show();
                return false;

            case 2:
                departmentMenu.show();
                return false;

            case 3:
                agentManagementMenu.show();
                return false;

            case 4:
                paymentMenu.show();
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
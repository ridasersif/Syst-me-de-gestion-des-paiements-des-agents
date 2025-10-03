package view.agent;

import controller.AgentController;
import controller.AuthController;
import controller.DepartmentController;
import model.Agent;
import model.Department;
import model.enums.AgentType;
import repository.AgentRepositoryImpl;
import service.AgentService;
import util.InputUtil;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AgentManagementMenu {

    private Agent currentUser;
    private Agent newAgent;
    private AuthController authController;
    private DepartmentController departmentController;
    private AgentService agentService;
    private AgentController agentController;
    private AgentRepositoryImpl agentRepository;

    public AgentManagementMenu(Agent currentUser) {
        this.currentUser = currentUser;
        this.authController= new AuthController();
        try {
            this.agentRepository = new AgentRepositoryImpl();
            this.departmentController = new DepartmentController();
            this.agentService = new AgentService(agentRepository);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Impossible de charger les departements !");
        }
    }

    public void show() {
        while (true) {
            displayMenu();
            int choice = InputUtil.readInt(" Choisissez une option ");

            switch (choice) {
                case 1:
                    addAgent();
                    break;
                case 2:
                    updateAgent();
                    break;
                case 3:
                    displayAgent();
                    break;
                case 4:
                    listAgents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide !");
            }
        }
    }

    private void displayMenu() {
      System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║               GESTION DES AGENTS             ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  1️⃣ Ajouter un agent                         ║");
        System.out.println("║  2️⃣ Mettre à jour un agent                   ║");
        System.out.println("║  3️⃣ Afficher un agent                        ║");
        System.out.println("║  4️⃣ Lister tous les agents                   ║");
        System.out.println("║  0️⃣ Retour                                   ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }
    private void addAgent() {
            System.out.println("\n➡️ AJOUT D'UN AGENT");

            String firstName = InputUtil.readString("Prénom : ");
            String lastName = InputUtil.readString("Nom : ");
            String email = InputUtil.readString("Email : ");
            String password = InputUtil.readString("Mot de passe : ");
            System.out.println("Type d'agent : ");
            if (currentUser.getAgentType() == AgentType.DIRECTOR) {
                System.out.println("1️⃣ MANAGER");
            }
            System.out.println("2️⃣ WORKER");
            System.out.println("3️⃣ INTERN");
            int typeChoice = InputUtil.readInt("Votre choix : ");
            AgentType agentType = null;
            switch (typeChoice) {
                case 1:
                    agentType = AgentType.MANAGER;
                    break;
                case 2:
                    agentType = AgentType.WORKER;
                    break;
                case 3:
                    agentType = AgentType.INTERN;
                    break;
                default:
                    System.out.println(" Option invalide ! Retour au menu.");
                    return;
            }
            newAgent = new Agent(0, firstName, lastName, email, password, agentType, null);
            if (currentUser.getAgentType() == AgentType.MANAGER) {
                newAgent.setDepartment(currentUser.getDepartment());
            } else {

                List<Department> allDepartments = departmentController.getAllDepartments();

                System.out.println("Liste des départements disponibles : ");
                for (Department dept : allDepartments) {
                    System.out.println(dept.getId() + dept.getName());
                }
                int deptId = InputUtil.readInt("Choisissez l'ID du département ");

                Optional<Department> selectedDept = allDepartments.stream()
                        .filter(d -> d.getId() == deptId)
                        .findFirst();

                if (selectedDept.isPresent()) {
                    newAgent.setDepartment(selectedDept.get());
                } else {
                    System.out.println(" Département invalide ! Agent non ajouté.");
                    return;
                }
            }

        boolean success = authController.register(currentUser, newAgent);
            if (success) {
                System.out.println(" Agent ajouté avec succès !");
            } else {
                System.out.println(" Échec de l'ajout de l'agent !");
            }

            System.out.println("\nAppuyez sur Entrée pour continuer...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
    }

    private void updateAgent() {
        System.out.println("Mise à jour d'un agent...");
        int id = InputUtil.readInt("Id de l'agent à modifier : ");
        agentController = new AgentController(agentService);
        Optional<Agent> agentOpt = agentController.getAgentById(id);

        if (agentOpt.isEmpty()) {
            System.out.println(" Agent non trouvé !");
            return;
        }

        Agent agentToUpdate = agentOpt.get();

        String newFirstName = InputUtil.readString("Nouveau prénom (" + agentToUpdate.getFirstName() + ")  ");
        if (!newFirstName.isEmpty()) agentToUpdate.setFirstName(newFirstName);

        String newLastName = InputUtil.readString("Nouveau nom (" + agentToUpdate.getLastName() + ")  ");
        if (!newLastName.isEmpty()) agentToUpdate.setLastName(newLastName);

        String newEmail = InputUtil.readString("Nouvel email (" + agentToUpdate.getEmail() + ")  ");
        if (!newEmail.isEmpty()) agentToUpdate.setEmail(newEmail);

        if (currentUser.getAgentType() == AgentType.DIRECTOR) {
            System.out.println("Type d'agent : 1️⃣ MANAGER 2️⃣ WORKER 3️⃣ INTERN");
            int typeChoice = InputUtil.readInt("Votre choix : ");
            switch (typeChoice) {
                case 1 -> agentToUpdate.setAgentType(AgentType.MANAGER);
                case 2 -> agentToUpdate.setAgentType(AgentType.WORKER);
                case 3 -> agentToUpdate.setAgentType(AgentType.INTERN);
            }
            List<Department> allDepartments = departmentController.getAllDepartments();
            System.out.println("Liste des départements disponibles : ");
            for (Department dept : allDepartments) {
                System.out.println(dept.getId() + "️⃣ " + dept.getName());
            }
            int deptId = InputUtil.readInt("Choisissez l'ID du département : ");
            Optional<Department> selectedDept = allDepartments.stream()
                    .filter(d -> d.getId() == deptId)
                    .findFirst();
            selectedDept.ifPresent(agentToUpdate::setDepartment);
        }

        boolean success = agentController.updateAgent(agentToUpdate);

        if (success) {
            System.out.println("Agent mis à jour avec succès !");
        } else {
            System.out.println("Échec de la mise à jour !");
        }

        try { System.in.read(); } catch (Exception e) {}
    }

    private void displayAgent() {
        System.out.println("\nAffichage d'un agent...");
        int id = InputUtil.readInt("Id de l'agent à afficher : ");
        agentController = new AgentController(agentService);
        Optional<Agent> agentOpt = agentController.getAgentById(id);

        if (agentOpt.isEmpty()) {
            System.out.println("Agent non trouvé !");
            return;
        }

        Agent a = agentOpt.get();
     System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.printf("║ %-15s : %-35d ║%n", "ID", a.getIdAgent());
        System.out.printf("║ %-15s : %-35s ║%n", "Nom", a.getLastName());
        System.out.printf("║ %-15s : %-35s ║%n", "Prénom", a.getFirstName());
        System.out.printf("║ %-15s : %-35s ║%n", "Email", a.getEmail());
        System.out.printf("║ %-15s : %-35s ║%n", "Type", a.getAgentType());
        if (a.getDepartment() != null) {
            System.out.printf("║ %-15s : %-35s ║%n", "Département", a.getDepartment().getName());
        }
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    private void listAgents() {
        System.out.println("\nListe des agents...");
        agentController = new AgentController(agentService);
        List<Agent> agents = agentController.displayAllAgents();

        if (agents.isEmpty()) {
            System.out.println("Aucun agent trouvé !");
            return;
        }

        System.out.println("\n╔════╦════════════════════╦════════════════════╦════════════════════════════════╦══════════════╦══════════════════╗");
        System.out.printf(   "║ %-2s ║ %-18s ║ %-18s ║ %-30s ║ %-12s ║ %-16s ║%n",
                "ID", "Nom", "Prénom", "Email", "Type", "Département");
        System.out.println(  "╠════╬════════════════════╬════════════════════╬════════════════════════════════╬══════════════╬══════════════════╣");

        for (Agent a : agents) {
            System.out.printf("║ %-2d ║ %-18s ║ %-18s ║ %-30s ║ %-12s ║ %-16s ║%n",
                    a.getIdAgent(),
                    a.getLastName(),
                    a.getFirstName(),
                    a.getEmail(),
                    a.getAgentType(),
                    (a.getDepartment() != null ? a.getDepartment().getName() : "N/A")
            );
        }
        System.out.println("╚════╩════════════════════╩════════════════════╩════════════════════════════════╩══════════════╩══════════════════╝");

    }



}

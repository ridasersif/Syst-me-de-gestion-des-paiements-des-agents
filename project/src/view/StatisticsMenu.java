package view;

import controller.StatisticsController;
import model.Agent;
import util.InputUtil;

public class StatisticsMenu {
    private Agent currentUser;
    private StatisticsController statisticsController;
    public StatisticsMenu(Agent currentUser){
        this.currentUser = currentUser;
    }

    public void show() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = InputUtil.readInt("Choisissez une option ");
            switch (choice) {
                case 1:
                    int agentId =  InputUtil.readInt("➡️ Entrez l’ID de l’agent: ");
                    statisticsController.showAgentStatistics(agentId);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
            pause();
        }
    }

    private void displayMenu() {
        System.out.println("╔════ Statistiques & Rapports ════╗");
        System.out.println("║ 1 - Statistiques par Agent       ║");
        System.out.println("║ 2 - Statistiques par Département ║");
        System.out.println("║ 3 - Rapport Global               ║");
        System.out.println("║ 4 - Exporter en CSV              ║");
        System.out.println("║ 0 - Retour                       ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    /*

    private void showAgentStats() {
        int agentId = InputUtil.readInt("ID de l'agent (ou 0 pour vous): ");
        if (agentId == 0) agentId = currentAgent.getIdAgent();
        try {
            Map<String, Object> stats = statsService.computeAgentStatistics(agentId);
            System.out.println("---- Statistiques Agent ID=" + agentId + " ----");
            System.out.println("Total annuel: " + stats.get("totalAnnual"));
            System.out.println("Nombre par type: " + stats.get("countByType"));
            System.out.println("Max paiement: " + stats.get("max"));
            System.out.println("Min paiement: " + stats.get("min"));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void showDepartmentStats() {
        int deptId = InputUtil.readInt("ID Département: ");
        try {
            Map<String, Object> stats = statsService.computeDepartmentStatistics(deptId);
            System.out.println("---- Statistiques Département ID=" + deptId + " ----");
            System.out.println("Total département: " + stats.get("totalDepartment"));
            System.out.println("Salaire moyen: " + stats.get("avgSalary"));
            System.out.println("Classement agents (top 5): " + stats.get("ranking"));
            System.out.println("Anomalies: " + stats.get("anomalies"));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void showGlobalReport() {
        try {
            Map<String, Object> report = statsService.computeGlobalReport();
            System.out.println("---- Rapport Global ----");
            System.out.println("Nombre agents: " + report.get("totalAgents"));
            System.out.println("Nombre départements: " + report.get("totalDepartments"));
            System.out.println("Répartition par type paiement: " + report.get("distributionByType"));
            System.out.println("Top agent: " + report.get("topAgent"));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void exportCsvMenu() {
        String path = InputUtil.readString("Chemin du fichier CSV (ex: reports/stats_agent.csv): ");
        int agentId = InputUtil.readInt("ID de l'agent (0 = tous): ");
        try {
            boolean ok = statsService.exportPaymentsCsv(agentId, path);
            System.out.println(ok ? "Export réussi." : "Échec de l'export.");
        } catch (Exception e) {
            System.out.println("Erreur export: " + e.getMessage());
        }
    }


    */

    private void pause() {
        System.out.println("Appuyez Entrée pour continuer...");
        try { System.in.read(); } catch (Exception e) {}
    }
}

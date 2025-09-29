package view.agent;

import util.InputUtil;

public class ManagerMenu {

    public void show() {
        while (true) {
            System.out.println("\n===== RESPONSABLE MENU =====");
            System.out.println("1. Gestion des départements");
            System.out.println("2. Gestion des agents");
            System.out.println("3. Gestion des paiements");
            System.out.println("4. Statistiques");
            System.out.println("0. Logout");

            int choice = InputUtil.readInt("Choose an option");
            switch (choice) {
                case 1: System.out.println("👉 [Départements CRUD]"); break;
                case 2: System.out.println("👉 [Agents CRUD]"); break;
                case 3: System.out.println("👉 [Paiements CRUD]"); break;
                case 4: System.out.println("👉 [Statistiques Département]"); break;
                case 0: return;
                default: System.out.println("❌ Invalid choice!");
            }
        }
    }
}

package view.agent;

import model.Agent;
import util.InputUtil;

public class AgentMenu {
    private Agent agent;

    public AgentMenu(Agent agent) {
        this.agent = agent;
    }

    public void show() {

        while (true) {

            System.out.println("═══════════════════════════════════════");
            System.out.println("1️⃣  Consulter mes informations");
            System.out.println("2️⃣  Voir historique de mes paiements");
            System.out.println("3️⃣  Filtrer / Trier mes paiements");
            System.out.println("4️⃣  Calculer le total de mes paiements");
            System.out.println("0️⃣  🔙 Retour");
            System.out.println("═══════════════════════════════════════");


            int choice = InputUtil.readInt("Choose an option");
            switch (choice) {
                case 1:
                    agent.displayInfo();
                    break;
                case 2:

                    System.out.println("👉 [Mon Département]");
                    break;
                case 3: System.out.println("👉 [Historique Paiements]"); break;
                case 4: System.out.println("👉 [Filtrer/Trier Paiements]"); break;
                case 5: System.out.println("👉 [Total Paiements]"); break;
                case 0: return;
                default: System.out.println("❌ Invalid choice!");
            }
        }
    }
}

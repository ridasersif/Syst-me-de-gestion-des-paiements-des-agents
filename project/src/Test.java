
import controller.AuthController;
import model.Department;
import model.Agent;
import model.enums.AgentType;



public class Test {
    public static void main(String[] args) {
        AuthController authController = new AuthController();

        try {
            // 1. directeur connecté
            Agent director = authController.login("ridasersif1@mail.com", "0000");

            // 2. directeur crée un manager
            Agent manager = new Agent(
                    0, "Ahmed", "Manager", "manager@mail.com", "pass123",
                    AgentType.MANAGER,
                    new Department(1, "Informatique") // département affecté
            );
            authController.register(director, manager);

            // 3. manager crée un agent normal
            Agent loggedManager = authController.login("manager@mail.com", "pass123");
            Agent staff = new Agent(
                    0, "Rida", "Sersif", "rida@mail.com", "pass123",
                    AgentType.WORKER, null // department sera fixé automatiquement
            );
            authController.register(loggedManager, staff);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package view;

import controller.AuthController;
import util.InputUtil;
import view.auth.LoginMenu;

public class MainMenu {

    public void show() {
        displayWelcome();
        while (true) {
            displayMenu();
            int choice = InputUtil.readInt("Choisissez une option ");

            if (handleChoice(choice)) {
                break;
            }
        }
    }
    private void displayWelcome() {
        System.out.println("\n🎉================================================🎉");
        System.out.println("           Bienvenue dans l'Application");
        System.out.println("              Système de Gestion");
        System.out.println("🎉================================================🎉\n");
    }

    private void displayMenu() {
        System.out.println("                                              ");
        System.out.println("     1. Connexion (Login)                     ");
        System.out.println("     0. Quitter                               ");
        System.out.println("                                              ");

    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                showLogin();
                return false;
            case 0:
                showGoodbye();
                return true;
            default:
                System.out.println("Option invalide ! Veuillez réessayer.\n");
                return false;
        }
    }
    private void showLogin() {
        System.out.println("\n----------------------------------------------------------");
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.show();
    }
    private void showGoodbye() {
        System.out.println("\n🌟================================================🌟");
        System.out.println("              Merci d'avoir utilisé");
        System.out.println("               notre application !");
        System.out.println("                 À bientôt ! 👋");
        System.out.println("🌟================================================🌟");

        System.exit(0);
    }
}
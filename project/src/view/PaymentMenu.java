package view;

import controller.PaymentController;
import model.Agent;
import model.Payment;
import model.enums.PaymentType;
import repository.PaymentRepositoryImpl;
import service.PaymentService;
import util.InputUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentMenu {
    private Agent currentUser;
    private PaymentController paymentController;

    public PaymentMenu(Agent currentUser) {
        this.currentUser = currentUser;
        try {
            PaymentRepositoryImpl paymentRepository = new PaymentRepositoryImpl();
            PaymentService paymentService = new PaymentService(paymentRepository, currentUser);
            this.paymentController = new PaymentController(paymentService);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Impossible de connecter au repository !");
        }
    }

    public void show() {
        while (true) {
            displayMenu();
            int choice = InputUtil.readInt(" Choisissez une option : ");

            if (handleChoice(choice)) {
                break;
            }
            pauseScreen();
        }
    }

    private void displayMenu() {
        System.out.println("╔════════════ ESPACE DES PAYMENTS ═══════════╗");
        System.out.println("║  1️⃣ Ajouter un paiement                    ║");
        System.out.println("║  2️⃣ Modifier un paiement                   ║");
        System.out.println("║  3️⃣ Consulter un paiement par ID           ║");
        System.out.println("║  4️⃣ Supprimer un paiement                  ║");
        System.out.println("║  5️⃣ Lister tous les paiements              ║");
        System.out.println("║  0️⃣ Déconnexion                            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1 -> addPayment();
            case 2 -> updatePayment();
            case 3 -> getPaymentById();
            case 4 -> deletePayment();
            case 5 -> listAllPayments();
            case 0 -> {
                showLogout();
                return true;
            }
            default -> System.out.println("❌ Option invalide ! Veuillez réessayer.\n");
        }
        return false;
    }

    private void addPayment() {
        System.out.println("\n➡️ AJOUT D'UN PAYMENT");

        int agentId = InputUtil.readInt("ID de l'agent ");
        double amount = InputUtil.readDouble("Montant ");
        String reason = InputUtil.readString("Motif ");

        PaymentType type = choosePaymentType();
        if (type == null) return;

        boolean conditionValidated = true;
        if (type == PaymentType.BONUS || type == PaymentType.ALLOWANCE) {
            String cond = InputUtil.readString("Condition validée ? (oui/non)  ");
            conditionValidated = cond.equalsIgnoreCase("oui");
        }

        Optional<Agent> agentOpt = paymentController.getAgentById(agentId);
        if (agentOpt.isEmpty()) {
            System.out.println("⚠️ Agent non trouvé !");
            return;
        }

        boolean success = paymentController.addPayment(
                agentOpt.get(), type, amount, reason, LocalDate.now(), conditionValidated
        );

        if (success) System.out.println("✅ Paiement ajouté avec succès !");
        else System.out.println("❌ Échec de l'ajout du paiement !");
    }

    private void updatePayment() {
        System.out.println("\n➡️ MODIFICATION D'UN PAYMENT");

        int paymentId = InputUtil.readInt("ID du paiement à modifier ");


        Optional<Payment> paymentOpt = paymentController.getPaymentById(paymentId);
        if (paymentOpt.isEmpty()) {
            System.out.println("⚠️ Paiement non trouvé !");
            return;
        }
        Payment oldPayment = paymentOpt.get();
        String amountStr = InputUtil.readString("Nouveau montant (laisser vide pour garder " + oldPayment.getAmount() + ") : ");
        double amount = amountStr.isEmpty() ? oldPayment.getAmount() : Double.parseDouble(amountStr);

        String reason = InputUtil.readString("Nouveau motif (laisser vide pour garder '" + oldPayment.getReason() + "') : ");
        if (reason.isEmpty()) reason = oldPayment.getReason();

        System.out.println("Type de paiement : 1️⃣ SALARY 2️⃣ BONUS 3️⃣ ALLOWANCE 4️⃣ PREMIUM");
        String typeStr = InputUtil.readString("Nouveau type (laisser vide pour garder " + oldPayment.getPaymentType() + ") : ");
        PaymentType type = oldPayment.getPaymentType();
        if (!typeStr.isEmpty()) {
            int typeChoice = Integer.parseInt(typeStr);
            type = switch (typeChoice) {
                case 1 -> PaymentType.SALARY;
                case 2 -> PaymentType.BONUS;
                case 3 -> PaymentType.ALLOWANCE;
                case 4 -> PaymentType.PREMIUM;
                default -> oldPayment.getPaymentType();
            };
        }

        boolean conditionValidated = oldPayment.getConditionValidated();
        if (type == PaymentType.BONUS || type == PaymentType.ALLOWANCE) {
            String cond = InputUtil.readString("Condition validée ? (oui/non) [actuel: " + (oldPayment.getConditionValidated() ? "oui" : "non") + "] : ");
            if (!cond.isEmpty()) conditionValidated = cond.equalsIgnoreCase("oui");
        }

        boolean success = paymentController.updatePayment(
                paymentId, oldPayment.getAgent(), type, amount, reason, LocalDate.now(), conditionValidated
        );
    }

    private void getPaymentById() {
        System.out.println("\n➡️ CONSULTATION D'UN PAYMENT");

        int paymentId = InputUtil.readInt("ID du paiement ");
        Optional<Payment> paymentOpt = paymentController.getPaymentById(paymentId);

        if (paymentOpt.isPresent()) {
            Payment p = paymentOpt.get();
            System.out.println("✅ Paiement trouvé :" + p);
        } else {
            System.out.println("❌ Aucun paiement trouvé !");
        }
    }
    private void deletePayment() {
        System.out.println("\n➡️ SUPPRESSION D'UN PAYMENT");

        int paymentId = InputUtil.readInt("ID du paiement à supprimer");
        paymentController.deletePayment(paymentId);
    }

    private void listAllPayments() {
        System.out.println("\nListe des paiements...");
        List<Payment> payments = paymentController.getAllPayments();

        if (payments.isEmpty()) {
            System.out.println("⚠️ Aucun paiement enregistré !");
            return;
        }

        // Header
        System.out.println("\n╔════╦════════════╦══════════╦════════════╦════════════════════════╦════════════════════╦══════════════════╦══════════╗");
        System.out.printf("║ %-2s ║ %-10s ║ %-8s ║ %-10s ║ %-22s ║ %-18s ║ %-16s ║ %-8s ║%n",
                           "ID", "Type", "Montant", "Date", "Raison", "Agent", "Département", "Validé");
        System.out.println("╠════╬════════════╬══════════╬════════════╬════════════════════════╬════════════════════╬══════════════════╬══════════╣");

        // Data
        for (Payment p : payments) {
            Agent a = p.getAgent();
            String agentFullName = a.getFirstName() + " " + a.getLastName();
            String departmentName = (a.getDepartment() != null ? a.getDepartment().getName() : "N/A");

            System.out.printf("║ %-2d ║ %-10s ║ %-8.2f ║ %-10s ║ %-22s ║ %-18s ║ %-16s ║ %-8s ║%n",
                    p.getIdPayment(),
                    p.getPaymentType().name(),
                    p.getAmount(),
                    p.getDate(),
                    p.getReason(),
                    agentFullName,
                    departmentName,
                    p.getConditionValidated() ? "Oui" : "Non"
            );
            System.out.println("╠════╬════════════╬══════════╬════════════╬════════════════════════╬════════════════════╬══════════════════╬══════════╣");

        }

        System.out.println("╚════╩════════════╩══════════╩════════════╩════════════════════════╩════════════════════╩══════════════════╩══════════╝");
    }




    // ------------------- UTILS -------------------

    private PaymentType choosePaymentType() {
        System.out.println("Type de paiement : 1️⃣ SALARY 2️⃣ BONUS 3️⃣ ALLOWANCE 4️⃣ PREMIUM");
        int typeChoice = InputUtil.readInt("Votre choix : ");
        return switch (typeChoice) {
            case 1 -> PaymentType.SALARY;
            case 2 -> PaymentType.BONUS;
            case 3 -> PaymentType.ALLOWANCE;
            case 4 -> PaymentType.PREMIUM;
            default -> {
                System.out.println("❌ Option invalide !");
                yield null;
            }
        };
    }

    private void showLogout() {
        String name = (currentUser.getFirstName() != null) ? currentUser.getFirstName() : "Utilisateur";
        System.out.println("\n🔚 Déconnexion en cours... Au revoir " + name + " !");
    }

    private void pauseScreen() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        try { System.in.read(); } catch (Exception ignored) {}
    }
}

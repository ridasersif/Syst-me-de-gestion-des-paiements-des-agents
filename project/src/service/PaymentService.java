package service;

import model.Agent;
import model.Payment;
import model.enums.PaymentType;
import repository.AgentRepositoryImpl;
import repository.IPaymentRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentService {

    private IPaymentRepository paymentRepo;
    private Agent currentUser;
    private AgentRepositoryImpl agentRepo;

    public PaymentService(IPaymentRepository paymentRepo,Agent currentUser) {
        this.paymentRepo = paymentRepo;
        this.currentUser=currentUser;
        try {
            this.agentRepo=new AgentRepositoryImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addPayment(Agent agent, PaymentType type, double amount, String reason, LocalDate date, Boolean conditionValidated) {
        switch (currentUser.getAgentType()) {
            case DIRECTOR:
                break;
            case MANAGER:
                if (agent.getDepartment() == null || !agent.getDepartment().equals(currentUser.getDepartment())) {
                    System.out.println("❌ Vous ne pouvez effectuer un paiement que pour votre département !");
                    return false;
                }
                break;
            default:
                System.out.println("❌ Vous n'avez pas les droits pour effectuer un paiement !");
                return false;
        }
        if ((type == PaymentType.BONUS || type == PaymentType.ALLOWANCE) && !conditionValidated) {
            System.out.println("❌ L'agent n'est pas éligible pour ce type de paiement !");
            return false;
        }
        Payment payment = new Payment(0,type, amount,date, reason, agent,conditionValidated);
        return paymentRepo.addPayment(payment);
    }


    public boolean updatePayment(int paymentId, Agent agent, PaymentType type, double amount, String reason, LocalDate date, Boolean conditionValidated) {

        Optional<Payment> paymentOpt = paymentRepo.getPaymentById(paymentId);
        if (paymentOpt.isEmpty()) {
            System.out.println("❌ Paiement non trouvé !");
            return false;
        }
        Payment payment = paymentOpt.get();

        switch (currentUser.getAgentType()) {
            case DIRECTOR:

                break;
            case MANAGER:
                if (agent.getDepartment() == null || !agent.getDepartment().equals(currentUser.getDepartment())) {
                    System.out.println("❌ Vous ne pouvez modifier un paiement que pour votre département !");
                    return false;
                }
                break;
            default:
                System.out.println("❌ Vous n'avez pas les droits pour modifier ce paiement !");
                return false;
        }


        if ((type == PaymentType.BONUS || type == PaymentType.ALLOWANCE) && !conditionValidated) {
            System.out.println("❌ L'agent n'est pas éligible pour ce type de paiement !");
            return false;
        }


        payment.setAgent(agent);
        payment.setPaymentType(type);
        payment.setAmount(amount);
        payment.setReason(reason);
        payment.setDate(date);
        payment.setConditionValidated(conditionValidated);


        boolean success = paymentRepo.updatePayment(payment);

        if (success) {
            System.out.println("✅ Paiement mis à jour avec succès !");
        } else {
            System.out.println("❌ Échec de la mise à jour du paiement !");
        }

        return success;
    }

    public Optional<Payment> getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            System.out.println("❌ ID de paiement invalide !");
            return Optional.empty();
        }

        Optional<Payment> paymentOpt = paymentRepo.getPaymentById(paymentId);

        if (paymentOpt.isEmpty()) {
            System.out.println("⚠ Aucun paiement trouvé avec l'ID : " + paymentId);
            return Optional.empty();
        }
        Payment payment = paymentOpt.get();
        switch (currentUser.getAgentType()) {
            case DIRECTOR:
                break;
            case MANAGER:
                if (payment.getAgent().getDepartment() == null
                        || !payment.getAgent().getDepartment().equals(currentUser.getDepartment())) {
                    System.out.println("❌ Vous ne pouvez consulter que les paiements de votre département !");
                    return Optional.empty();
                }
                break;
            default:
                System.out.println("❌ Vous n'avez pas les droits pour consulter ce paiement !");
                return Optional.empty();
        }

        return Optional.of(payment);
    }
    public boolean deletePayment(int paymentId) {
        Optional<Payment> paymentOpt = paymentRepo.getPaymentById(paymentId);
        if (paymentOpt.isEmpty()) {
            System.out.println("❌ Paiement non trouvé !");
            return false;
        }
        Payment payment = paymentOpt.get();
        switch (currentUser.getAgentType()) {
            case DIRECTOR:
                break;
            case MANAGER:
                if (payment.getAgent().getDepartment() == null
                        || !payment.getAgent().getDepartment().equals(currentUser.getDepartment())) {
                    System.out.println("❌ Vous ne pouvez supprimer que les paiements de votre département !");
                    return false;
                }
                break;
            default:
                System.out.println("❌ Vous n'avez pas les droits pour supprimer ce paiement !");
                return false;
        }

        boolean success = paymentRepo.deletePayment(paymentId);

        if (success) {
            System.out.println("✅ Paiement supprimé avec succès !");
        } else {
            System.out.println("❌ Échec de la suppression du paiement !");
        }

        return success;
    }

    public List<Payment> getAllPaymentBy() {
        List<Payment> allPayments = paymentRepo.getAllPaiement();
        switch (currentUser.getAgentType()) {
            case DIRECTOR:
                return allPayments;
            case MANAGER:
                return allPayments.stream()
                        .filter(p -> p.getAgent().getDepartment() != null
                                && p.getAgent().getDepartment().equals(currentUser.getDepartment()))
                        .toList();
            default:
                System.out.println("❌ Vous n'avez pas les droits pour consulter les paiements !");
                return List.of();
        }
    }
    public Optional<Agent> getAgentById(int id) {
        return agentRepo.getAgentById(id);
    }

}

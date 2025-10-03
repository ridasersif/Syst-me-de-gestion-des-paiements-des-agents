package service;

import model.Payment;
import model.enums.PaymentType;
import repository.IPaymentRepository;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentStatisticsService {

    private final IPaymentRepository paymentRepository;

    public PaymentStatisticsService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public String getAgentStatistics(int agentId) {
        List<Payment> payments = paymentRepository.findByAgentId(agentId);

        if (payments.isEmpty()) {
            return "⚠ Aucun paiement trouvé pour cet agent.";
        }


        double total = payments.stream().mapToDouble(Payment::getAmount).sum();


        Map<PaymentType, Long> countByType = payments.stream()
                .collect(Collectors.groupingBy(Payment::getPaymentType, Collectors.counting()));

        DoubleSummaryStatistics stats = payments.stream()
                .mapToDouble(Payment::getAmount)
                .summaryStatistics();


        StringBuilder sb = new StringBuilder();
        sb.append("📊 Statistiques de l’agent [ID: ").append(agentId).append("]\n")
                .append("Total annuel: ").append(total).append(" DH\n")
                .append("Nombre de paiements: ").append(payments.size()).append("\n")
                .append("Montant max: ").append(stats.getMax()).append(" DH\n")
                .append("Montant min: ").append(stats.getMin()).append(" DH\n\n")
                .append("Répartition par type:\n");

        countByType.forEach((type, count) -> {
            sb.append(" - ").append(type).append(": ").append(count).append("\n");
        });

        return sb.toString();
    }
}

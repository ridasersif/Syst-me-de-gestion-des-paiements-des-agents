package repository;


import model.Agent;
import model.Payment;
import model.enums.PaymentType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPaymentRepository {
    boolean addPayment(Payment payment);


    boolean updatePayment(Payment payment);

    Optional<Payment> getPaymentById(int idPaiement);


    boolean deletePayment(int idPaiement);

    List<Payment>getAllPaiement();
    /*
    List<Payment> getPaymentsByAgent(int agentId);



    List<Payment> filterPaymentsByType(int agentId, PaymentType type);

    List<Payment> filterPaymentsByDate(int agentId, LocalDate from, LocalDate to);

    double calculateTotalPayments(int agentId);

    List<Payment> detectAnomalies(int agentId);

     */
}

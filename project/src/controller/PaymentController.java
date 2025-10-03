package controller;

import model.Agent;
import model.Payment;
import model.enums.PaymentType;
import service.PaymentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public boolean addPayment(Agent agent, PaymentType type, double amount, String reason, LocalDate date, boolean conditionValidated) {
        return paymentService.addPayment(agent, type, amount, reason, date, conditionValidated);
    }

    public boolean updatePayment(int paymentId, Agent agent, PaymentType type, double amount, String reason, LocalDate date, boolean conditionValidated) {
        return paymentService.updatePayment(paymentId, agent, type, amount, reason, date, conditionValidated);
    }

    public Optional<Payment> getPaymentById(int paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    public boolean  deletePayment(int paymentId ){
        return paymentService.deletePayment(paymentId);
    }

    public List<Payment> getAllPayments(){
        List<Payment> payments = paymentService.getAllPaymentBy();
        return payments;
    }

    public Optional<Agent> getAgentById(int id) {
        return paymentService.getAgentById(id);
    }
}

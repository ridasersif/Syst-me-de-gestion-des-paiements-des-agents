package model;

import java.time.LocalDate;
import model.enums.PaymentType;

public class Payment {
    private int idPayment;
    private PaymentType paymentType;
    private double amount;
    private LocalDate date;
    private String reason;
    private Agent agent;
    private Boolean conditionValidated;

    public Payment(int idPayment, PaymentType paymentType, double amount, LocalDate date, String reason, Agent agent, Boolean conditionValidated) {
        this.idPayment = idPayment;
        this.paymentType = paymentType;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.agent = agent;
        this.conditionValidated = conditionValidated;
    }

    // Getters & Setters
    public int getIdPayment() { return idPayment; }
    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType type) { this.paymentType = paymentType; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }
    public Boolean getConditionValidated() { return conditionValidated; }
    public void setConditionValidated(Boolean conditionValidated) { this.conditionValidated = conditionValidated; }
}

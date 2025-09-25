package model;

import java.time.LocalDate;
import model.enums.PaymentType;

public abstract class Payment {
    private int idPayment;
    private PaymentType type;
    private double amount;
    private LocalDate date;
    private String reason;
    private Agent agent;

    // Constructor
    public Payment(int idPayment, PaymentType type, double amount, LocalDate date, String reason, Agent agent) {
        this.idPayment = idPayment;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.agent = agent;
    }

    // Getters & Setters
    public int getIdPayment() { return idPayment; }
    public PaymentType getType() { return type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public String getReason() { return reason; }
    public Agent getAgent() { return agent; }
}

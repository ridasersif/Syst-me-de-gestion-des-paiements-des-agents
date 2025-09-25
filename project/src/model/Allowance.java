package model;

import java.time.LocalDate;
import model.enums.PaymentType;

public class Allowance extends Payment {
    private boolean conditionValidated;

    public Allowance(int idPayment, double amount, LocalDate date, String reason, Agent agent, boolean conditionValidated) {
        super(idPayment, PaymentType.ALLOWANCE, amount, date, reason, agent);
        this.conditionValidated = conditionValidated;
    }

    public boolean isConditionValidated() { return conditionValidated; }
    public void setConditionValidated(boolean conditionValidated) { this.conditionValidated = conditionValidated; }
}

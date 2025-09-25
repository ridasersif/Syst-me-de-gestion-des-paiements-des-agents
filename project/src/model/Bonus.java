package model;

import java.time.LocalDate;
import model.enums.PaymentType;

public class Bonus extends Payment {
    private boolean conditionValidated;

    public Bonus(int idPayment, double amount, LocalDate date, String reason, Agent agent, boolean conditionValidated) {
        super(idPayment, PaymentType.BONUS, amount, date, reason, agent);
        this.conditionValidated = conditionValidated;
    }

    public boolean isConditionValidated() { return conditionValidated; }
    public void setConditionValidated(boolean conditionValidated) { this.conditionValidated = conditionValidated; }
}

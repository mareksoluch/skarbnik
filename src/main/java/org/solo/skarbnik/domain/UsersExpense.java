package org.solo.skarbnik.domain;


import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

public class UsersExpense extends Expenses {
    @Transient
    private boolean payed;
    @Transient
    private BigDecimal leftToPay;

    public UsersExpense(Expenses expense, boolean payed, BigDecimal leftToPay) {
        this.payed = payed;
        this.description = expense.description;
        this.dueDate = expense.dueDate;
        this.id = expense.id;
        this.qty = expense.qty;
        this.leftToPay = leftToPay;
    }

    public boolean isPayed() {
        return payed;
    }

    public BigDecimal getLeftToPay() {
        return leftToPay;
    }
}

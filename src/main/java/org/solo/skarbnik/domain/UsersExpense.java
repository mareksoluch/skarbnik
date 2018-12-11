package org.solo.skarbnik.domain;


import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

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

    public BigDecimal getPayedQty() {
        return qty.add(leftToPay);
    }

    public UsersExpense add(UsersExpense expense){
        BigDecimal payedQty = getPayedQty().add(expense.getPayedQty());
        BigDecimal leftToPay = qty.subtract(payedQty);
        return new UsersExpense(expense, gtEqZero(leftToPay), leftToPay);
    }

    private boolean gtEqZero(BigDecimal totalIncome) {
        return totalIncome.compareTo(ZERO) >= 0;
    }
}

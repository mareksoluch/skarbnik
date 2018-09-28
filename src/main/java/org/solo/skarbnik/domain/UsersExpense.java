package org.solo.skarbnik.domain;


import org.springframework.data.annotation.Transient;

public class UsersExpense extends Expenses {
    @Transient
    private boolean payed;

    public UsersExpense(Expenses expense, boolean payed) {
        this.payed = payed;
        this.description = expense.description;
        this.dueDate = expense.dueDate;
        this.id = expense.id;
        this.qty = expense.qty;
    }

    public boolean isPayed() {
        return payed;
    }
}

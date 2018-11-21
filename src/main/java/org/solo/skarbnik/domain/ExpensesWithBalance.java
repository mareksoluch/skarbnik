package org.solo.skarbnik.domain;

import java.math.BigDecimal;
import java.util.List;

public class ExpensesWithBalance {

    private final List<UsersExpense> usersExpenses;
    private final BigDecimal balance;

    public ExpensesWithBalance(List<UsersExpense> usersExpenses, BigDecimal balance) {
        this.usersExpenses = usersExpenses;
        this.balance = balance;
    }

    public List<UsersExpense> getUsersExpenses() {
        return usersExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

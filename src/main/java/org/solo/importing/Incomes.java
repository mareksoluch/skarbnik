package org.solo.importing;

import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;

public class Incomes implements Persistable<Long> {

    private final Long id;
    private final String username;
    private final String account;
    private final BigDecimal qty;
    private final String description;
    private final Date transactiontime;

    public Incomes(Long id, String username, String account, BigDecimal qty, String description, Date transactiontime) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.qty = qty;
        this.description = description;
        this.transactiontime = transactiontime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public String getDescription() {
        return description;
    }

    public Date getTransactiontime() {
        return transactiontime;
    }
}

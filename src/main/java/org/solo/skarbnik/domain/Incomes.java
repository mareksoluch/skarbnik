package org.solo.skarbnik.domain;

import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class Incomes implements Persistable<Long> {

    private @Id Long id;
    private String username;
    private String account;
    private BigDecimal qty;
    private String description;
    private Date transactiontime;

    @PersistenceConstructor
    public Incomes(Long id, String username, String account, BigDecimal qty, String description, Date transactiontime) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.qty = qty;
        this.description = description;
        this.transactiontime = transactiontime;
    }

    public Incomes(String username, String account, BigDecimal qty, String description, Date transactiontime) {
        this(null, username, account, qty, description, transactiontime);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
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

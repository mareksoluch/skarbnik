package org.solo.skarbnik.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class Incomes implements Persistable<Long> {

    public static final String UNMAPPED_USER = "$$UNMAPPED";

    private @Id Long id;
    private String username;
    private String account;
    private BigDecimal qty;
    private String description;
    private String title;
    private String issuer;
    private Date transactionTime;
    private boolean enabled = true;

    @PersistenceConstructor
    public Incomes(Long id, String username, String account, BigDecimal qty, String description, String title, String issuer, Date transactionTime) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.qty = qty;
        this.description = description;
        this.title = title;
        this.issuer = issuer;
        this.transactionTime = transactionTime;
    }

    public Incomes(String username, String account, BigDecimal qty, String description, Date transactionTime, String title, String issuer) {
        this(null, username, account, qty, description, title, issuer, transactionTime);
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public String getTitle() {
        return title;
    }

    public String getIssuer() {
        return issuer;
    }

    @Transient
    public boolean userUnmapped(){
        return UNMAPPED_USER.equals(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void disable(){
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incomes incomes = (Incomes) o;
        return Objects.equals(account, incomes.account) &&
                Objects.equals(qty, incomes.qty) &&
                Objects.equals(description, incomes.description) &&
                Objects.equals(title, incomes.title) &&
                Objects.equals(issuer, incomes.issuer) &&
                Objects.equals(transactionTime, incomes.transactionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, qty, description, title, issuer, transactionTime);
    }
}

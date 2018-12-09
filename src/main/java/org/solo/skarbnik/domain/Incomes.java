package org.solo.skarbnik.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

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
    private Long expense1;
    private Long expense2;
    private Long expense3;
    private Long expense4;
    private Long expense5;

    @PersistenceConstructor
    public Incomes(Long id, String username, String account, BigDecimal qty, String description, String title,
                   String issuer, Date transactionTime, Long expense1, Long expense2, Long expense3, Long expense4,
                   Long expense5) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.qty = qty;
        this.description = description;
        this.title = title;
        this.issuer = issuer;
        this.transactionTime = transactionTime;
        this.expense1 = expense1;
        this.expense2 = expense2;
        this.expense3 = expense3;
        this.expense4 = expense4;
        this.expense5 = expense5;
    }

    public Incomes(String username, String account, BigDecimal qty, String description, Date transactionTime,
                   String title, String issuer, Long expense1, Long expense2, Long expense3, Long expense4, Long expense5) {
        this(null, username, account, qty, description, title, issuer, transactionTime, expense1, expense2,
                expense3, expense4, expense5);
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
    public boolean userUnmapped() {
        return UNMAPPED_USER.equals(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void disable() {
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

    public Long getExpense1() {
        return expense1;
    }

    public Long getExpense2() {
        return expense2;
    }

    public Long getExpense3() {
        return expense3;
    }

    public Long getExpense4() {
        return expense4;
    }

    public Long getExpense5() {
        return expense5;
    }

    public List<Long> getExpenses(){
        return of(expense1, expense2, expense3, expense4, expense5)
                .filter(Objects::nonNull)
                .collect(toList());
    }
}

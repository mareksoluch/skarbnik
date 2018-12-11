package org.solo.skarbnik.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expenses  implements Persistable<Long> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");
    @Id Long id;
    BigDecimal qty;
    String description;
    Date dueDate;
    String keywords;

    public Expenses() {
    }

    @PersistenceConstructor
    public Expenses(Long id, BigDecimal qty, String description, Date dueDate, String keywords) {
        this.id = id;
        this.qty = qty;
        this.description = description;
        this.dueDate = dueDate;
        this.keywords = keywords;
    }

    public Expenses(BigDecimal qty, String description, Date dueDate, String keywords) {
        this(null, qty, description, dueDate, keywords);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueDateString(String dueDate) {
        try {
            this.dueDate = DATE_FORMAT.parse(dueDate);
        } catch (ParseException e) {
            this.dueDate = null;
        }
    }

    public String getDueDateString() {
        return dueDate !=null ? DATE_FORMAT.format(dueDate) : null;
    }

    public UsersExpense payed(){
        return new UsersExpense(this, true, BigDecimal.ZERO);
    }

    public UsersExpense unpaid(BigDecimal leftToPay){
        return new UsersExpense(this, false, leftToPay);
    }

    public String getKeywords() {
        return keywords;
    }

    public boolean hasKeywords() {
        return keywords != null && !keywords.isEmpty();
    }

    public boolean hasNoKeywords() {
        return !hasKeywords();
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

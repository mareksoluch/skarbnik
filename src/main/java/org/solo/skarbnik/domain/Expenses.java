package org.solo.skarbnik.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;

public class Expenses  implements Persistable<Long> {

    private @Id Long id;
    private BigDecimal qty;
    private String description;
    private Date dueDate;

    @PersistenceConstructor
    public Expenses(Long id, BigDecimal qty, String description, Date dueDate) {
        this.id = id;
        this.qty = qty;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Expenses(BigDecimal qty, String description, Date dueDate) {
        this(null, qty, description, dueDate);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}

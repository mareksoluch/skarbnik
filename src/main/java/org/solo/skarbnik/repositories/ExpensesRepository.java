package org.solo.skarbnik.repositories;

import org.solo.skarbnik.domain.Expenses;
import org.solo.skarbnik.domain.Incomes;
import org.solo.skarbnik.domain.Users;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpensesRepository extends CrudRepository<Expenses, Long> {

    @Override
    List<Expenses> findAll();
}
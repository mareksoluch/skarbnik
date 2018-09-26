package org.solo.skarbnik.repositories;

import org.solo.skarbnik.domain.Incomes;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomesRepository extends CrudRepository<Incomes, Long> {

    @Query("SELECT * FROM incomes WHERE userName  = :userName")
    List<Incomes> findByUsername(@Param("userName") String userName);
}
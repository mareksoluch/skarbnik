package org.solo.skarbnik.repositories;

import org.solo.skarbnik.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends CrudRepository<Users, String> {

    @Override
    List<Users> findAll();
}
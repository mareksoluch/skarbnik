package org.solo.skarbnik.repositories;

import org.solo.skarbnik.domain.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, String> {
}
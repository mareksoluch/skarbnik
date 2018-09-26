package org.solo.repositories;

import org.solo.login.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, String> {
}
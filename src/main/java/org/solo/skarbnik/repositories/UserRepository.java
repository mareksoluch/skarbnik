package org.solo.skarbnik.repositories;

import org.solo.skarbnik.domain.Users;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<Users, String> {

    @Override
    List<Users> findAll();

    @Query("SELECT * FROM users WHERE email = :email")
    List<Users> findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE users set password = :password WHERE userName = :userName")
    int updatePassword(@Param("userName") String userName, @Param("password") String password);
}
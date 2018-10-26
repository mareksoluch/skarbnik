package org.solo.skarbnik.config;

import org.solo.skarbnik.billinginmport.BillingImporter;
import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJdbcRepositories("org.solo.skarbnik.repositories")
@Configuration
public class DbConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncomesRepository incomesRepository;

    @Bean
    public BillingImporter billingImporter(){
        return new BillingImporter(userRepository, incomesRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

}

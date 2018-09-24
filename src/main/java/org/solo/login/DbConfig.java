package org.solo.login;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;

@EnableJdbcRepositories("org.solo.repositories")
@Configuration
//@Import(JdbcConfiguration.class)
public class DbConfig {
}

package org.solo.skarbnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "org.solo.login", "org.solo.importing", "org.solo.skarbnik.repositories" })
@SpringBootApplication
public class SkarbnikApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkarbnikApplication.class, args);
	}
}

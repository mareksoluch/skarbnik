package org.solo.skarbnik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/loadbilling").setViewName("loadbilling");
        registry.addViewController("/upload").setViewName("upload");
        registry.addViewController("/uploadStatus").setViewName("uploadStatus");
        registry.addViewController("/listbillings").setViewName("listbillings");
        registry.addViewController("/expenses").setViewName("expenses");
        registry.addViewController("/hello").setViewName("hello");




    }

}
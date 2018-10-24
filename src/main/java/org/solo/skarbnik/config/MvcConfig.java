package org.solo.skarbnik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/upload").setViewName("upload");
        registry.addViewController("/paymentsReport").setViewName("paymentsReport");
        registry.addViewController("/expenses").setViewName("expenses");
        registry.addViewController("/payedExpensesReport").setViewName("payedExpensesReport");



    }

}
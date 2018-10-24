package org.solo.skarbnik.controllers;

import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.domain.Incomes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PaymentController {

    private static final String ADMIN = "ROLE_ADMIN";

    @Autowired
    private IncomesRepository incomesRepository;

    @GetMapping("/paymentsReport")
    public String listPayments(Model model) {
        model.addAttribute("payments", listPayments());
        return "paymentsReport";
    }

    private List<Incomes> listPayments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return isAdmin(auth)
                ? incomesRepository.findEnabledAndMapped()
                : incomesRepository.findEnabledByUsername(auth.getName());
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ADMIN::equals);
    }


}

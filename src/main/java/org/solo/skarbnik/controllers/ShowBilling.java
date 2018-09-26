package org.solo.skarbnik.controllers;

import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.domain.Incomes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.solo.skarbnik.utils.Utils.toList;

@Controller
public class ShowBilling {

    @Autowired
    IncomesRepository repository;

    @GetMapping("/listbillings")
    public String listBillings(Model model) {
        List<Incomes> billingEntries = listBillings();
        model.addAttribute("billings", billingEntries);
        return "listbillings";
    }

    private List<Incomes> listBillings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(isAdmin(auth)){
            return toList(repository.findAll());
        } else {
            return toList(repository.findByUsername(auth.getName()));
        }
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

}

package org.solo.importing;

import org.solo.repositories.IncomesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ShowBilling {

    @Autowired
    DataSource datasource;

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
            return StreamSupport.stream(repository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            return StreamSupport.stream(repository.findByUsername(auth.getName()).spliterator(), false)
                    .collect(Collectors.toList());
        }
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ROLE_ADMIN"::equals);
    }

}

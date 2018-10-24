package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.Incomes;
import org.solo.skarbnik.domain.Users;
import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Controller
public class MapUsersExpensesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomesRepository incomesRepository;

    @GetMapping("/mapUsersToPayments")
    public String getMapUserToPayment(Model model) {
        model.addAttribute("payments", unmappedPayments());
        model.addAttribute("users", allUserNames());

        return "mapUsersToPayments";
    }

    @PostMapping("/mapUsersToPayments")
    public String mapUserToPayment(@RequestParam("user") String user, @RequestParam("id") Long id, Model model) {
        return updateIncome(id, model, income -> setIncomeUser(user, income));
    }

    @PostMapping("/disableIncome")
    public String disableIncome(@RequestParam("id") Long id, Model model) {
        updateIncome(id, model, this::disableIncome);
        return getMapUserToPayment(model);
    }

    private Incomes setIncomeUser(@RequestParam("user") String user, Incomes income) {
        income.setUsername(user);
        return income;
    }

    private Incomes disableIncome(Incomes income) {
        income.disable();
        return income;
    }

    private String updateIncome(Long id, Model model, Function<Incomes, Incomes> incomeUpdate) {
        incomesRepository.findById(id)
                .ifPresent(income -> incomesRepository.save(incomeUpdate.apply(income)));
        return getMapUserToPayment(model);
    }

    private List<Incomes> unmappedPayments() {
        return incomesRepository.findEnabled().stream()
                .filter(Incomes::userUnmapped)
                .collect(toList());
    }

    private List<String> allUserNames() {
        return userRepository.findAll().stream().map(Users::getUsername).collect(toList());
    }
}

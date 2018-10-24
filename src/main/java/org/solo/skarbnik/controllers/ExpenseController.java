package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.Expenses;
import org.solo.skarbnik.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    ExpensesRepository expensesRepository;

    @GetMapping("/expenses")
    public String index(Model model) {
        model.addAttribute("expenses", listExpenses());
        model.addAttribute("newExpense", new Expenses());
        return "expenses";
    }

    private List<Expenses> listExpenses() {
        return expensesRepository.findAll();
    }

    @RequestMapping(value="/expenses", method=RequestMethod.POST)
    public String addExpense(@ModelAttribute Expenses newExpense, Model model) {
        expensesRepository.save(newExpense);
        return index(model);
    }

}

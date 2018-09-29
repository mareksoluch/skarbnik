package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.Expenses;
import org.solo.skarbnik.domain.UsersExpense;
import org.solo.skarbnik.repositories.ExpensesRepository;
import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.domain.Incomes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.solo.skarbnik.utils.Utils.toList;
import static org.solo.skarbnik.utils.Utils.toStream;

@Controller
public class ShowBilling {

    @Autowired
    IncomesRepository repository;

    @Autowired
    ExpensesRepository expensesRepository;

    @GetMapping("/listbillings")
    public String listBillings(Model model) {
        List<Incomes> billingEntries = listBillings();

        Map<String, List<Incomes>> incomesForUsers = billingEntries.stream().collect(groupingBy(Incomes::getUsername));
        List<Expenses> expenses = expensesSortedByDueDate();

        Map<String, List<UsersExpense>> userExpenses = incomesForUsers.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> toUsersExpenses(expenses, sumQty(entry.getValue()))));

        model.addAttribute("expenses", expenses);
        model.addAttribute("userExpenses", userExpenses);
        model.addAttribute("billings", billingEntries);
        return "listbillings";
    }

    @GetMapping("/usersExpenses")
    public String usersExpenses(Model model) {
        List<Incomes> billingEntries = listBillings();

        Map<String, List<Incomes>> incomesForUsers = billingEntries.stream().collect(groupingBy(Incomes::getUsername));
        List<Expenses> expenses = expensesSortedByDueDate();

        Map<String, List<UsersExpense>> userExpenses = incomesForUsers.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> toUsersExpenses(expenses, sumQty(entry.getValue()))));

        model.addAttribute("expenses", expenses);
        model.addAttribute("usersExpenses", userExpenses);
        model.addAttribute("billings", billingEntries);
        return "usersExpenses";
    }

    private List<Expenses> expensesSortedByDueDate() {
        return toStream(expensesRepository.findAll())
                .sorted(comparing(Expenses::getDueDate))
                .collect(toList());
    }

    private BigDecimal sumQty(List<Incomes> userIncomes) {
        return userIncomes.stream()
                .map(Incomes::getQty)
                .reduce(ZERO, BigDecimal::add);
    }

    private List<UsersExpense> toUsersExpenses(List<Expenses> expenses, BigDecimal totalIncome) {
        List<UsersExpense> usersExpenses = new ArrayList<>(expenses.size());
        for (Expenses expense : expenses) {
            totalIncome = totalIncome.subtract(expense.getQty());
            UsersExpense usersExpense = gtZero(totalIncome) ? expense.payed() : expense.unpayed();
            usersExpenses.add(usersExpense);
        }
        return usersExpenses;
    }

    private boolean gtZero(BigDecimal totalIncome) {
        return totalIncome.compareTo(ZERO) > 0;
    }

    private List<Incomes> listBillings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return isAdmin(auth)
                ? toList(repository.findAll())
                : toList(repository.findByUsername(auth.getName()));
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

}

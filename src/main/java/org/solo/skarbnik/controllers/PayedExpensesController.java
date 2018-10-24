package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.Expenses;
import org.solo.skarbnik.domain.Incomes;
import org.solo.skarbnik.domain.UsersExpense;
import org.solo.skarbnik.repositories.ExpensesRepository;
import org.solo.skarbnik.repositories.IncomesRepository;
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
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@Controller
public class PayedExpensesController {

    private static final String ADMIN = "ROLE_ADMIN";

    @Autowired
    private IncomesRepository incomesRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @GetMapping("/payedExpensesReport")
    public String usersExpenses(Model model) {
        List<Expenses> expenses = expensesSortedByDueDate();

        model.addAttribute("expenses", expenses);
        model.addAttribute("usersExpenses", userExpenses(expenses));
        return "payedExpensesReport";
    }

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

    private List<Expenses> expensesSortedByDueDate() {
        return expensesRepository.findAll().stream()
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
            UsersExpense usersExpense = gtZero(totalIncome) ? expense.payed() : expense.unpaid();
            usersExpenses.add(usersExpense);
        }
        return usersExpenses;
    }

    private boolean gtZero(BigDecimal totalIncome) {
        return totalIncome.compareTo(ZERO) > 0;
    }


    private Map<String, List<UsersExpense>> userExpenses(List<Expenses> expenses) {
        Map<String, List<Incomes>> incomesForUsers = listPayments().stream()
                .collect(groupingBy(Incomes::getUsername));

        return mapValues(incomesForUsers, entry -> toUsersExpenses(expenses, sumQty(entry.getValue())));
    }

    private Map<String, List<UsersExpense>> mapValues(Map<String, List<Incomes>> incomesForUsers, Function<Map.Entry<String, List<Incomes>>, List<UsersExpense>> valuesMapping) {
        return incomesForUsers
                .entrySet().stream()
                .collect(toMap(Map.Entry::getKey, valuesMapping));
    }
}

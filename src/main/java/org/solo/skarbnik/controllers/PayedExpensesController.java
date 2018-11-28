package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.*;
import org.solo.skarbnik.repositories.ExpensesRepository;
import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@Controller
public class PayedExpensesController {

    private static final String ADMIN = "ROLE_ADMIN";

    @Autowired
    private IncomesRepository incomesRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private UserRepository userRepository;

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

    private ExpensesWithBalance toUsersExpenses(List<Expenses> expenses, final BigDecimal totalIncome) {
        List<UsersExpense> usersExpenses = new ArrayList<>(expenses.size());
        BigDecimal incomeLeft = totalIncome;
        for (Expenses expense : expenses) {
            incomeLeft = incomeLeft.subtract(expense.getQty());
            UsersExpense usersExpense = gtEqZero(incomeLeft) ? expense.payed() : expense.unpaid(incomeLeft);
            usersExpenses.add(usersExpense);
        }
        BigDecimal balance = expenses.stream()
                .map(Expenses::getQty)
                .reduce(BigDecimal::add)
                .map(totalIncome::subtract)
                .get();
        return new ExpensesWithBalance(usersExpenses, balance);
    }

    private boolean gtEqZero(BigDecimal totalIncome) {
        return totalIncome.compareTo(ZERO) >= 0;
    }


    private Map<String, ExpensesWithBalance> userExpenses(List<Expenses> expenses) {
        Map<String, List<Incomes>> incomesForUsers = listPayments().stream()
                .collect(groupingBy(Incomes::getUsername));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, List<Incomes>> allUsersIncomes = isAdmin(auth)
                ? fillInUsersWithNoPayments(incomesForUsers)
                : incomesForUsers;

        return mapValues(allUsersIncomes, entry -> toUsersExpenses(expenses, sumQty(entry.getValue())));
    }

    private Map<String, List<Incomes>> fillInUsersWithNoPayments(Map<String, List<Incomes>> incomesForUsers) {
        Map<String, List<Incomes>> result = new HashMap<>(incomesForUsers);
        userRepository.findAll().stream()
                .filter(Users::isEnabled)
                .filter(user -> !result.containsKey(user.getUsername()))
                .forEach(user -> result.put(user.getUsername(), emptyList()));

        return result;
    }

    private Map<String, ExpensesWithBalance> mapValues(Map<String, List<Incomes>> incomesForUsers, Function<Map.Entry<String, List<Incomes>>, ExpensesWithBalance> valuesMapping) {
        return incomesForUsers
                .entrySet().stream()
                .collect(toMap(Map.Entry::getKey, valuesMapping));
    }
}

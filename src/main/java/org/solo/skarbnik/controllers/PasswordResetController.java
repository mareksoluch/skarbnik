package org.solo.skarbnik.controllers;

import org.solo.skarbnik.domain.PasswordReset;
import org.solo.skarbnik.domain.Users;
import org.solo.skarbnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PasswordResetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/resetPassword")
    public String getPasswordReset(Model model) {
        model.addAttribute("passwordReset", new PasswordReset());
        return "resetPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute PasswordReset passwordReset, Model model) {

        if (passwordReset.passwordIncorrect()) {
            return resetFail(model, "Hasła nie są identyczne");
        }

        List<Users> users = userRepository.findByEmail(passwordReset.getEmail());
        if (users != null && users.size() == 1) {
            Users user = users.get(0);
            if (childMatches(passwordReset, user)) {
                userRepository.updatePassword(user.getUsername(), passwordEncoder.encode(passwordReset.getPassword()));
            } else {
                return resetFail(model, "Imię / Nazwisko dziecka nie znalezione");
            }
        } else {
            return resetFail(model, "Nie odnaleziono użytkownika dla email-a " + passwordReset.getEmail());
        }

        model.addAttribute("passwordReset", new PasswordReset());
        return "passwordResetSuccess";
    }

    private boolean childMatches(PasswordReset passwordReset, Users user) {
        return user.getChildName().equalsIgnoreCase(passwordReset.getChildName()) &&
                user.getChildSurname().equalsIgnoreCase(passwordReset.getChildSurname());
    }

    private String resetFail(Model model, String message) {
        model.addAttribute("message", message);
        return "resetPassword";
    }

}

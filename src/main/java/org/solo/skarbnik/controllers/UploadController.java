package org.solo.skarbnik.controllers;

import org.solo.skarbnik.billinginmport.BillingImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class UploadController {

    @Autowired
    BillingImporter billingImporter;

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Wybierz plik do zaladowania");
            return "upload";
        }

        try {
            billingImporter.importBilling(file.getInputStream());
            redirectAttributes.addFlashAttribute("message",
                    "Udało sięzaładować plik '" + file.getOriginalFilename() + "'");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return billingImporter.hasUnmappedUsers() ? "redirect:/mapUsersToPayments" : "redirect:/";
    }

}

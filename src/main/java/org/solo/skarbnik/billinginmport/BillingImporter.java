package org.solo.skarbnik.billinginmport;

import org.solo.skarbnik.domain.BillingEntry;
import org.solo.skarbnik.domain.Incomes;
import org.solo.skarbnik.domain.Users;
import org.solo.skarbnik.repositories.IncomesRepository;
import org.solo.skarbnik.repositories.UserRepository;

import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import static org.solo.skarbnik.utils.PolishSignsRemover.map;
import static org.solo.skarbnik.utils.Utils.toList;

public class BillingImporter {

    private static final String POLISH_ENCODING = "ISO-8859-2";
    private static final String UNMAPPED_USER = "$$UNMAPPED";

    private UserRepository userRepository;
    private IncomesRepository incomesRepository;

    public BillingImporter(UserRepository userRepository, IncomesRepository incomesRepository) {
        this.userRepository = userRepository;
        this.incomesRepository = incomesRepository;
    }

    public void importBilling(InputStream inputStream) throws ParseException {
        List<Users> allUsers = toList(userRepository.findAll());

        Scanner scanner = new Scanner(inputStream, POLISH_ENCODING);
        skipUntilBillingReached(scanner);
        parseBillings(scanner).forEach(entry -> persistBillingEntry(allUsers, entry));
    }

    private void persistBillingEntry(List<Users> allUsers, BillingEntry entry) {
        incomesRepository.save(new Incomes(mapUser(allUsers, entry), entry.getAccountNumber(), entry.getAmount(), entry.getDescription(), new Date(entry.getOperationDate().getTime())));
    }

    private String mapUser(List<Users> allUsers, BillingEntry entry) {
        return allUsers.stream()
                .filter(users -> billingMatchesUser(entry, users))
                .findFirst()
                .map(Users::getUsername)
                .orElse(UNMAPPED_USER);
    }

    private boolean billingMatchesUser(BillingEntry entry, Users users) {
        return !users.getChildSurname().isEmpty() && !users.getChildName().isEmpty() &&
                (containsIgnoringPolishSigns(entry.getTitle(), users.getChildSurname())
                        || containsIgnoringPolishSigns(entry.getIssuer(), users.getChildSurname())) &&
                containsIgnoringPolishSigns(entry.getTitle(), users.getChildName());
    }

    private boolean containsIgnoringPolishSigns(String containingString, String phraseToSearch) {
        return map(containingString.toLowerCase()).contains(map(phraseToSearch.toLowerCase()));
    }


    private List<BillingEntry> parseBillings(Scanner scanner) throws ParseException {
        List<BillingEntry> result = new LinkedList<>();
        for (String line = scanner.nextLine(); scanner.hasNext() && BillingEntry.isParsable(line); line = scanner.nextLine()) {
            result.add(new BillingEntry(line));
        }
        return result;
    }

    private void skipUntilBillingReached(Scanner scanner) {
        String line;
        do {
            line = scanner.nextLine();
        }
        while (scanner.hasNext() && !reachedBilling(line));
    }

    private boolean reachedBilling(String line) {
        return line.startsWith("#Data operacji");
    }

}


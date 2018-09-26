package org.solo.importing;

import org.solo.login.Users;
import org.solo.repositories.IncomesRepository;
import org.solo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ImportController {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");
    private final PolishSignsRemover polishSignsRemover = new PolishSignsRemover();

    @Autowired
    DataSource datasource;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IncomesRepository incomesRepository;


    public void importBilling(Scanner scanner) throws ParseException, SQLException {
        List<BillingEntry> result = parseBilling(scanner);

        Connection connection = datasource.getConnection();
        connection.setAutoCommit(false);
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO incomes " +
//                "(username, account ,qty , description , transactiontime) " +
//                "VALUES (?,?,?,?,?)");

        Iterable<Users> all = userRepository.findAll();
        List<Users> allUsers = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());

        result.forEach(entry -> {

            Optional<String> userOptional = mapUser(entry, allUsers);

            userOptional.ifPresent(user ->
                    incomesRepository.save(new Incomes(null, user, entry.getAccountNumber(), entry.getAmount(), entry.getDescription(), new Date(entry.getOpertionDate().getTime()))));

            if (!userOptional.isPresent()) {
                // log that could not map user
            }

        });
        connection.commit();
    }

    private Optional<String> mapUser(BillingEntry entry, List<Users> allUsers) {

        return allUsers.stream()
                .filter(users -> billingMatchesUser(entry, users))
                .findFirst()
                .map(Users::getUsername);

    }

    private boolean billingMatchesUser(BillingEntry entry, Users users) {
        return !users.getChildSurname().isEmpty() && !users.getChildName().isEmpty() &&
                (containsIgnoringPolishSigns(entry.getTitle(), users.getChildSurname())
                        || containsIgnoringPolishSigns(entry.getIssuer(), users.getChildSurname())) &&
                containsIgnoringPolishSigns(entry.getTitle(), users.getChildName());
    }

    private boolean containsIgnoringPolishSigns(String containingString, String phraseToSearch) {
        return polishSignsRemover.map(containingString).contains(polishSignsRemover.map(phraseToSearch));
    }


    private List<BillingEntry> parseBilling(Scanner scanner) throws ParseException {
        List<BillingEntry> result = new LinkedList<>();
        String line = "";
        do {
            line = scanner.nextLine();
        }
        while (scanner.hasNext() && !reachedBilling(line));

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String[] split = line.split(";");
            System.out.println(split.length);
            if (split.length < 7) {
                break;
            }
            BillingEntry billingEntry = new BillingEntry(line);
            result.add(billingEntry);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ą".toUpperCase());
        System.out.println(new File("/Users/marek.soluch/tmp").exists());
        System.out.println(new File("/Users/marek.soluch/tmp/a.sql").exists());
//        Charset charset = Charset.forName("ISO-8859-2");
        Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get("/Users/marek.soluch/tmp/a.sql")));


        String line = "";
        do {
            line = scanner.nextLine();
            System.out.println(new PolishSignsRemover().map(line));
        }
        while (scanner.hasNext());


    }

    private BillingEntry parseLine(String line) {
        return null;
    }

    private boolean reachedBilling(String line) {
        return line.startsWith("#Data operacji");
    }
}


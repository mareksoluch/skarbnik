package org.solo.importing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class ImportController {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");
    private final PolishSignsRemover polishSignsRemover = new PolishSignsRemover();

    @Autowired
    DataSource datasource;


    public void importBilling(Scanner scanner) throws ParseException, SQLException {
        List<BillingEntry> result = parseBilling(scanner);

        Connection connection = datasource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO incomes " +
                "(username, account ,qty , description , transactiontime) " +
                "VALUES (?,?,?,?,?)");

        PreparedStatement selectUsersStatement = connection
                .prepareStatement("SELECT username, childName, childSurname FROM users");

        ResultSet resultSet = selectUsersStatement.executeQuery();
        List<User> allUsers = new LinkedList<>();
        while (resultSet.next()) {
            allUsers.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
        }

        result.forEach(entry -> {

            Optional<String> userOptional = mapUser(entry, allUsers);

            userOptional.ifPresent(user -> {
                try {
                    preparedStatement.setString(1, user);
                    preparedStatement.setString(2, entry.getAccountNumber());
                    preparedStatement.setBigDecimal(3, entry.getAmount());
                    preparedStatement.setString(4, entry.getDescription());
                    preparedStatement.setDate(5, new java.sql.Date(entry.getOpertionDate().getTime()));

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            if (!userOptional.isPresent()) {
                // log that could not map user
            }

        });
        connection.commit();
    }

    private Optional<String> mapUser(BillingEntry entry, List<User> allUsers) {

        return allUsers.stream()
                .filter(user -> billingMatchesUser(entry, user))
                .findFirst()
                .map(User::getUsername);

    }

    private boolean billingMatchesUser(BillingEntry entry, User user) {
        return !user.getChildSurname().isEmpty() && !user.getChildName().isEmpty() &&
                (containsIgnoringPolishSigns(entry.getTitle(), user.getChildSurname())
                        || containsIgnoringPolishSigns(entry.getIssuer(), user.getChildSurname())) &&
                containsIgnoringPolishSigns(entry.getTitle(), user.getChildName());
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

    private class User {
        private final String username;
        private final String childName;
        private final String childSurname;

        private User(String username, String childName, String childSurname) {
            this.username = username;
            this.childName = childName;
            this.childSurname = childSurname;
        }


        public String getUsername() {
            return username;
        }

        public String getChildName() {
            return childName;
        }

        public String getChildSurname() {
            return childSurname;
        }
    }
}

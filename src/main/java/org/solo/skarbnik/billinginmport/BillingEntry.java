package org.solo.skarbnik.billinginmport;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


class BillingEntry {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String SEPARATOR = ";";

    private final Date operationDate;
    private final Date bookingDate;
    private final String description;
    private final String title;
    private final String issuer;
    private final String accountNumber;
    private final BigDecimal amount;
    private final BigDecimal balanceAfterOperation;

    BillingEntry(String csv) throws ParseException {
        String[] split = csv.split(SEPARATOR);
        operationDate = DATE_FORMAT.parse(removeQuotes(split[0]));
        bookingDate = DATE_FORMAT.parse(removeQuotes(split[1]));
        description = removeQuotes(split[2]);
        title = removeQuotes(split[3]);
        issuer = removeQuotes(split[4]);
        accountNumber = removeQuotes(split[5]);
        amount = parse(removeQuotes(split[6]));
        balanceAfterOperation = parse(removeQuotes(split[7]));
    }

    private String removeQuotes(String source) {
        return source
                .replace("\"", "")
                .replace("'", "");
    }

    private BigDecimal parse(String source) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat format = new DecimalFormat("#.#", symbols);
        format.setParseBigDecimal(true);
        return (BigDecimal) format.parse(source);
    }

    Date getOperationDate() {
        return operationDate;
    }

    String getDescription() {
        return description;
    }

    String getTitle() {
        return title;
    }

    String getIssuer() {
        return issuer;
    }

    String getAccountNumber() {
        return accountNumber;
    }

    BigDecimal getAmount() {
        return amount;
    }

    BigDecimal getBalanceAfterOperation() {
        return balanceAfterOperation;
    }

    @Override
    public String toString() {
        return "BillingEntry{" +
                "operationDate=" + operationDate +
                ", bookingDate=" + bookingDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", issuer='" + issuer + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", balanceAfterOperation=" + balanceAfterOperation +
                '}';
    }

    static boolean isParsable(String line) {
        return line == null || line.split(SEPARATOR).length >= 7;
    }
}

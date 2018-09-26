package org.solo.skarbnik.domain;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BillingEntry {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");
    private static final String SEPARATOR = ";";

    private Long id;
    private final Date operationDate;
    private final Date bookingDate;
    private final String description;
    private final String title;
    private final String issuer;
    private final String accountNumber;
    private final BigDecimal amount;
    private final BigDecimal balanceAfterOperation;

    public BillingEntry(String csv) throws ParseException {
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

    public BillingEntry(Long id, Date operationDate, Date bookingDate, String description, String title, String issuer, String accountNumber, BigDecimal amount, BigDecimal balanceAfterOperation) {
        this.id = id;
        this.operationDate = operationDate;
        this.bookingDate = bookingDate;
        this.description = description;
        this.title = title;
        this.issuer = issuer;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceAfterOperation = balanceAfterOperation;
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

    public Date getOperationDate() {
        return operationDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfterOperation() {
        return balanceAfterOperation;
    }

    @Override
    public String toString() {
        return "BillingEntry{" +
                "id=" + id +
                ", operationDate=" + operationDate +
                ", bookingDate=" + bookingDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", issuer='" + issuer + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", balanceAfterOperation=" + balanceAfterOperation +
                '}';
    }

    public Long getId() {
        return id;
    }

    public static boolean isParsable(String line) {
        return line == null || line.split(SEPARATOR).length >= 7;
    }
}

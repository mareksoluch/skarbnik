package org.solo.importing;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;


public class BillingEntry {
    private Long id;
    private final Date opertionDate;
    private final Date bookingDate;
    private final String description;
    private final String title;
    private final String issuer;
    private final String accountNumber;
    private final BigDecimal amount;
    private final BigDecimal balanceAfterOperation;

    public BillingEntry(String csv) throws ParseException {
        String[] split = csv.split(";");
        opertionDate = ImportController.DATE_FORMAT.parse(removequotes(split[0]));
        bookingDate = ImportController.DATE_FORMAT.parse(removequotes(split[1]));
        description = removequotes(split[2]);
        title = removequotes(split[3]);
        issuer = removequotes(split[4]);
        accountNumber = removequotes(split[5]);
        amount = parse(removequotes(split[6]));
        balanceAfterOperation = parse(removequotes(split[7]));
    }

    public BillingEntry(Long id, Date opertionDate, Date bookingDate, String description, String title, String issuer, String accountNumber, BigDecimal amount, BigDecimal balanceAfterOperation) {
        this.id = id;
        this.opertionDate = opertionDate;
        this.bookingDate = bookingDate;
        this.description = description;
        this.title = title;
        this.issuer = issuer;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceAfterOperation = balanceAfterOperation;
    }

    private String removequotes(String source) {
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

    public Date getOpertionDate() {
        return opertionDate;
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
                ", opertionDate=" + opertionDate +
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
}

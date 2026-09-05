package models;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    final private String loanId;
    final private String patronId;
    final private String isbn;
    final private LocalDate issueDate;
    private LocalDate returnDate;

    public Loan(String patronId, String isbn, LocalDate issueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.patronId = patronId;
        this.isbn = isbn;
        this.issueDate = issueDate;
    }

    public Loan(Loan loan) {
        this.loanId = loan.getLoanId();
        this.patronId = loan.getPatronId();
        this.isbn = loan.getISBN();
        this.issueDate = loan.getIssueDate();
        this.returnDate = loan.getReturnDate();
    }

    public String getLoanId() {
        return loanId;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getISBN() {
        return isbn;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

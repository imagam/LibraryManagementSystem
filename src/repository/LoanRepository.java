package repository;

import exceptions.LoanNotFoundException;
import models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void addLoan(Loan loan);
    void updateLoan(Loan loan) throws LoanNotFoundException;
    Loan findById(String id) throws LoanNotFoundException;
    Optional<Loan> findActiveLoanByIsbn(String isbn);
    List<Loan> findLoansByPatronId(String patronId);
    List<Loan> findAllLoans();
}

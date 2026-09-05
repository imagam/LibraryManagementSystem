package repository;

import exceptions.LoanNotFoundException;
import models.Loan;

import java.util.*;

public class InMemoryLoanRepository implements LoanRepository {
    final Map<String, Loan> loans;

    public InMemoryLoanRepository() {
        loans = new HashMap<>();
    }


    @Override
    public void addLoan(Loan loan) {
        Loan newLoan = new Loan(loan);
        loans.put(newLoan.getLoanId(), newLoan);
    }

    @Override
    public void updateLoan(Loan loan) throws LoanNotFoundException {
        Loan matchingLoan = loans.get(loan.getLoanId());
        if(matchingLoan == null) {
            throw new LoanNotFoundException("Loan Not Found");
        }
        matchingLoan.setReturnDate(loan.getReturnDate());
    }

    @Override
    public Loan findById(String id) throws LoanNotFoundException {
        Loan matchingLoan = loans.get(id);
        if(matchingLoan == null) {
            throw new LoanNotFoundException("Loan Not Found");
        }
        return new Loan(matchingLoan);
    }

    @Override
    public Optional<Loan> findActiveLoanByIsbn(String isbn) {
        for(Loan loan : loans.values()){
            if(loan.getISBN().equals(isbn) && loan.getReturnDate() == null){
                return Optional.of(new Loan(loan));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findLoansByPatronId(String patronId) {
        List<Loan> matchingLoans = new ArrayList<>();
        for(Loan loan : loans.values()){
            if(loan.getPatronId().equals(patronId)){
                matchingLoans.add(new Loan(loan));
            }
        }
        return matchingLoans;
    }

    @Override
    public List<Loan> findAllLoans() {
        List<Loan> allLoans = new ArrayList<>();
        for(Loan loan : loans.values()){
            allLoans.add(new Loan(loan));
        }
        return allLoans;
    }
}

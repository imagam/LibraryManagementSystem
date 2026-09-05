package service;

import exceptions.*;
import models.Loan;
import repository.BookRepository;
import repository.LoanRepository;
import repository.PatronRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private static final Logger LOGGER = Logger.getLogger(LoanService.class.getName());

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    public Loan checkoutBook(String patronId, String isbn) throws PatronNotFoundException,
            BookNotFoundException, BookAlreadyBorrowedException {
        // check is patron exists
        patronRepository.findById(patronId);

        // check if book exists
        bookRepository.findByISBN(isbn);

        // check if book is available to lend
        Optional<Loan> loan = loanRepository.findActiveLoanByIsbn(isbn);
        if(loan.isPresent()) {
            throw new BookAlreadyBorrowedException("Book with ISBN " + isbn + " is already borrowed");
        }

        Loan newLoan = new Loan(patronId, isbn, LocalDate.now());
        loanRepository.addLoan(newLoan);
        LOGGER.info("Book with ISBN " + isbn + " checked out successfully");
        return newLoan;
    }

    public void returnBook(String loanId) throws LoanNotFoundException, BookAlreadyReturnedException {
        // if loan exists
        Loan loan = loanRepository.findById(loanId);

        // has the book been returned already
        if(loan.getReturnDate() != null) {
            throw new BookAlreadyReturnedException("Book with ISBN " + loan.getISBN() + " already returned");
        }

        loan.setReturnDate(LocalDate.now());
        loanRepository.updateLoan(loan);
        LOGGER.info("Book with ISBN " + loan.getISBN() + " returned successfully");
    }

    public boolean isBookAvailable(String isbn) throws BookNotFoundException {
        // check if the book actually exists
        bookRepository.findByISBN(isbn);

        return loanRepository.findActiveLoanByIsbn(isbn).isEmpty();
    }

    public List<Loan> getBorrowingHistory(String patronId) throws PatronNotFoundException {
        // check is patron exists
        patronRepository.findById(patronId);
        return loanRepository.findLoansByPatronId(patronId);
    }
}

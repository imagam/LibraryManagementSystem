import exceptions.*;
import models.Book;
import models.Loan;
import models.Patron;
import repository.BookRepository;
import repository.InMemoryBookRepository;
import repository.InMemoryLoanRepository;
import repository.InMemoryPatronRepository;
import repository.LoanRepository;
import repository.PatronRepository;
import service.BookService;
import service.LoanService;
import service.PatronService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --------------------------------------------------
        // 1. Create repositories
        // --------------------------------------------------

        BookRepository bookRepository = new InMemoryBookRepository();
        PatronRepository patronRepository = new InMemoryPatronRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();


        // --------------------------------------------------
        // 2. Create services and inject repositories
        // --------------------------------------------------

        BookService bookService = new BookService(bookRepository);
        PatronService patronService = new PatronService(patronRepository);

        LoanService loanService = new LoanService(
                loanRepository,
                bookRepository,
                patronRepository
        );


        try {

            // --------------------------------------------------
            // 3. Add books
            // --------------------------------------------------

            Book book1 = new Book(
                    "Clean Code",
                    "Robert C. Martin",
                    "9780132350884",
                    2008
            );

            Book book2 = new Book(
                    "Effective Java",
                    "Joshua Bloch",
                    "9780134685991",
                    2018
            );

            Book book3 = new Book(
                    "Design Patterns",
                    "Erich Gamma",
                    "9780201633610",
                    1994
            );

            bookService.addBook(book1);
            bookService.addBook(book2);
            bookService.addBook(book3);

            System.out.println("Books added successfully.");


            // --------------------------------------------------
            // 4. Add patrons
            // --------------------------------------------------

            Patron patron1 = new Patron(
                    "Agam",
                    "9999999999",
                    "Delhi"
            );

            Patron patron2 = new Patron(
                    "John",
                    "8888888888",
                    "Mumbai"
            );

            patronService.addPatron(patron1);
            patronService.addPatron(patron2);

            System.out.println("Patrons added successfully.");


            // --------------------------------------------------
            // 5. Search books
            // --------------------------------------------------

            System.out.println("\nBooks by Robert C. Martin:");

            List<Book> martinBooks =
                    bookService.searchByAuthor("Robert C. Martin");

            for (Book book : martinBooks) {
                System.out.println(book.getTitle());
            }


            // --------------------------------------------------
            // 6. Check availability
            // --------------------------------------------------

            System.out.println("\nIs Clean Code available?");

            boolean availableBeforeCheckout =
                    loanService.isBookAvailable(book1.getIsbn());

            System.out.println(availableBeforeCheckout);


            // --------------------------------------------------
            // 7. Checkout book
            // --------------------------------------------------

            System.out.println("\nChecking out Clean Code...");

            Loan loan = loanService.checkoutBook(
                    patron1.getPatronId(),
                    book1.getIsbn()
            );

            System.out.println("Loan created: " + loan.getLoanId());


            // --------------------------------------------------
            // 8. Check availability after checkout
            // --------------------------------------------------

            System.out.println("\nIs Clean Code available after checkout?");

            boolean availableAfterCheckout =
                    loanService.isBookAvailable(book1.getIsbn());

            System.out.println(availableAfterCheckout);


            // --------------------------------------------------
            // 9. Try checking out the same book again
            // --------------------------------------------------

            System.out.println("\nTrying to checkout Clean Code again...");

            try {
                loanService.checkoutBook(
                        patron2.getPatronId(),
                        book1.getIsbn()
                );
            } catch (Exception e) {
                System.out.println("Expected failure: " + e.getMessage());
            }


            // --------------------------------------------------
            // 10. Borrowing history
            // --------------------------------------------------

            System.out.println("\nBorrowing history for Agam:");

            List<Loan> history =
                    loanService.getBorrowingHistory(patron1.getPatronId());

            for (Loan historyLoan : history) {
                System.out.println(
                        "ISBN: " + historyLoan.getISBN()
                                + ", Issue Date: " + historyLoan.getIssueDate()
                                + ", Return Date: " + historyLoan.getReturnDate()
                );
            }


            // --------------------------------------------------
            // 11. Return book
            // --------------------------------------------------

            System.out.println("\nReturning Clean Code...");

            loanService.returnBook(loan.getLoanId());


            // --------------------------------------------------
            // 12. Check availability after return
            // --------------------------------------------------

            System.out.println("\nIs Clean Code available after return?");

            boolean availableAfterReturn =
                    loanService.isBookAvailable(book1.getIsbn());

            System.out.println(availableAfterReturn);


            // --------------------------------------------------
            // 13. Try returning the same loan again
            // --------------------------------------------------

            System.out.println("\nTrying to return the same loan again...");

            try {
                loanService.returnBook(loan.getLoanId());
            } catch (Exception e) {
                System.out.println("Expected failure: " + e.getMessage());
            }

            // --------------------------------------------------
// TEST 1: Search for a book with a nonexistent title
// Expected: empty list
// --------------------------------------------------

            System.out.println("\n========== TEST 1: Search nonexistent title ==========");

            List<Book> result = null;
            try {
                result = bookService.searchByTitle("Nonexistent Book");
            } catch (BookNotFoundException | InvalidSearchStrategy e) {}

            System.out.println("Number of books found: " + result.size());


// --------------------------------------------------
// TEST 2: Search for a book with a nonexistent author
// Expected: empty list
// --------------------------------------------------

            System.out.println("\n========== TEST 2: Search nonexistent author ==========");

            try {
                result = bookService.searchByAuthor("Nonexistent Author");
            } catch (BookNotFoundException | InvalidSearchStrategy e) {}

            System.out.println("Number of books found: " + result.size());


// --------------------------------------------------
// TEST 3: Find a book with a nonexistent ISBN
// Expected: BookNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 3: Find nonexistent ISBN ==========");

            try {
                bookService.findBookByISBN("0000000000000");

                System.out.println("ERROR: Expected BookNotFoundException");

            } catch (BookNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());
            }


// --------------------------------------------------
// TEST 4: Checkout a nonexistent book
// Expected: BookNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 4: Checkout nonexistent book ==========");

            try {
                loanService.checkoutBook(
                        patron1.getPatronId(),
                        "0000000000000"
                );

                System.out.println("ERROR: Expected BookNotFoundException");

            } catch (BookNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("ERROR: Unexpected exception: " + e.getClass().getSimpleName());
            }


// --------------------------------------------------
// TEST 5: Checkout with nonexistent patron
// Expected: PatronNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 5: Checkout with nonexistent patron ==========");

            try {
                loanService.checkoutBook(
                        "nonexistent-patron-id",
                        book2.getIsbn()
                );

                System.out.println("ERROR: Expected PatronNotFoundException");

            } catch (PatronNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("ERROR: Unexpected exception: " + e.getClass().getSimpleName());
            }


// --------------------------------------------------
// TEST 6: Update nonexistent book
// Expected: BookNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 6: Update nonexistent book ==========");

            try {
                Book nonexistentBook = new Book(
                        "Some Book",
                        "Some Author",
                        "0000000000000",
                        2026
                );

                bookService.updateBook(nonexistentBook);

                System.out.println("ERROR: Expected BookNotFoundException");

            } catch (BookNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());
            }


// --------------------------------------------------
// TEST 7: Update nonexistent patron
// Expected: PatronNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 7: Update nonexistent patron ==========");

            try {
                Patron nonexistentPatron = new Patron(
                        "Unknown",
                        "0000000000",
                        "Unknown"
                );

                patronService.updatePatron(nonexistentPatron);

                System.out.println("ERROR: Expected PatronNotFoundException");

            } catch (PatronNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());
            }


// --------------------------------------------------
// TEST 8: Return nonexistent loan
// Expected: LoanNotFoundException
// --------------------------------------------------

            System.out.println("\n========== TEST 8: Return nonexistent loan ==========");

            try {
                loanService.returnBook("nonexistent-loan-id");

                System.out.println("ERROR: Expected LoanNotFoundException");

            } catch (LoanNotFoundException e) {
                System.out.println("PASS: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("ERROR: Unexpected exception: " + e.getClass().getSimpleName());
            }


// --------------------------------------------------
// TEST 9: Add duplicate book
// Expected: DuplicateBookException
// --------------------------------------------------

            System.out.println("\n========== TEST 9: Add duplicate book ==========");

            try {
                Book duplicateBook = new Book(
                        "Another Title",
                        "Another Author",
                        book1.getIsbn(),
                        2026
                );

                bookService.addBook(duplicateBook);

                System.out.println("ERROR: Expected DuplicateBookException");

            } catch (DuplicateBookException e) {
                System.out.println("PASS: " + e.getMessage());
            }


// --------------------------------------------------
// TEST 10: Add duplicate patron
// Expected: DuplicatePatronException
//
// IMPORTANT:
// Patron ID is generated automatically, so we cannot
// create a new Patron with patron1's ID through the
// constructor.
// Instead, this test demonstrates the rule by trying
// to add the SAME patron object again.
// --------------------------------------------------

            System.out.println("\n========== TEST 10: Add duplicate patron ==========");

            try {
                patronService.addPatron(patron1);

                System.out.println("ERROR: Expected DuplicatePatronException");

            } catch (DuplicatePatronException e) {
                System.out.println("PASS: " + e.getMessage());
            }

// --------------------------------------------------
// TEST 11: Borrowing one book should not affect
// availability of another book
// Expected:
// book1 -> unavailable
// book2 -> available
// --------------------------------------------------

            System.out.println("\n========== TEST 11: Independent book availability ==========");

// Checkout book1
            Loan testLoan = loanService.checkoutBook(
                    patron1.getPatronId(),
                    book1.getIsbn()
            );

            System.out.println(
                    "Book 1 available: "
                            + loanService.isBookAvailable(book1.getIsbn())
            );

            System.out.println(
                    "Book 2 available: "
                            + loanService.isBookAvailable(book2.getIsbn())
            );

// Clean up
            loanService.returnBook(testLoan.getLoanId());

        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
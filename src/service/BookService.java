package service;

import enums.SearchType;
import exceptions.BookNotFoundException;
import exceptions.DuplicateBookException;
import exceptions.InvalidSearchStrategy;
import factory.BookSearchStrategyFactory;
import models.Book;
import repository.BookRepository;
import strategy.BookSearchStrategy;

import java.util.List;
import java.util.logging.Logger;

public class BookService {
    private final BookRepository bookRepository;
    private final static Logger LOGGER = Logger.getLogger(BookService.class.getName());

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) throws DuplicateBookException {
        // check is book already exists
        try {
            bookRepository.findByISBN(book.getIsbn());
            throw new DuplicateBookException("Duplicate book found");
        } catch (BookNotFoundException e) {
            // book doesn't exist, we can add book
            bookRepository.addBook(book);
            LOGGER.info("Book with ISBN " + book.getIsbn() + " was added successfully");
        }
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        bookRepository.removeBook(isbn);
        LOGGER.info("Book with ISBN " + isbn + " was removed successfully");
    }

    public void updateBook(Book book) throws BookNotFoundException {
        bookRepository.updateBook(book);
        LOGGER.info("Book with ISBN " + book.getIsbn() + " was updated successfully");
    }

    public Book findBookByISBN(String isbn) throws BookNotFoundException {
        return bookRepository.findByISBN(isbn);
    }

    public List<Book> searchByTitle(String title) throws BookNotFoundException, InvalidSearchStrategy {
        BookSearchStrategy searchStrategy = BookSearchStrategyFactory.getBookSearchStrategy(SearchType.TITLE);
        return searchStrategy.searchBooks(this.bookRepository, title);
    }

    public List<Book> searchByAuthor(String author) throws BookNotFoundException, InvalidSearchStrategy {
        BookSearchStrategy searchStrategy = BookSearchStrategyFactory.getBookSearchStrategy(SearchType.AUTHOR);
        return searchStrategy.searchBooks(this.bookRepository ,author);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }
}

package strategy;

import exceptions.BookNotFoundException;
import models.Book;
import repository.BookRepository;

import java.util.List;

public abstract class BookSearchStrategy {
    public abstract List<Book> searchBooks(BookRepository repository, String query)
            throws BookNotFoundException;
}

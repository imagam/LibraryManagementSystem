package strategy;

import exceptions.BookNotFoundException;
import models.Book;
import repository.BookRepository;

import java.util.List;

public class AuthorBookSearchStrategy extends BookSearchStrategy {

    @Override
    public List<Book> searchBooks(BookRepository repository, String query) throws BookNotFoundException {
        return repository.findByAuthor(query);
    }
}
